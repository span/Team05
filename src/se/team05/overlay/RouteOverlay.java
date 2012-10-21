/**
	This file is part of Personal Trainer.

    Personal Trainer is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    Personal Trainer is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Personal Trainer.  If not, see <http://www.gnu.org/licenses/>.

    (C) Copyright 2012: Daniel Kvist, Henrik Hugo, Gustaf Werlinder, Patrik Thitusson, Markus Schutzer
 */

/**

 * Based on: http://djsolid.net/blog/android---draw-a-path-array-of-points-in-mapview
 * https://bitbucket.org/djsolid/android-helper-files/src/f3dbd9c31be3/RoutePathOverlay.java
 * 
 * Edited some stroke widths and radius and formatted code. Also change some variable names.
 * 
 */

package se.team05.overlay;

import java.util.List;

import se.team05.content.ParcelableGeoPoint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

/**
 * This overlay uses a list of geopoints to draw a path on a map view. At the
 * start and end point an oval is also drawn to mark the start and finish of the
 * route.
 * 
 * @author Daniel Kvist 
 * 		   This class was inspired by a blog post that was found on
 *         the web. Unfortunately I cannot find the blog post again and the URL
 *         has been lost. The original code was free to use and has been
 *         modified to suit our needs.
 * 
 */
public class RouteOverlay extends Overlay
{

	private int color;
	private final List<ParcelableGeoPoint> geoPoints;

	/**
	 * Constructor which uses a default red color for the route and takes a list
	 * of geopoints.
	 * 
	 * @param points
	 *            the geo points to draw
	 */
	public RouteOverlay(List<ParcelableGeoPoint> geoPoints)
	{
		this(geoPoints, Color.RED);
	}

	/**
	 * Constructor which sets a default color for the route and takes a list of
	 * geopoints.
	 * 
	 * @param points
	 *            the geo points to draw
	 * @param color
	 *            the color of the path
	 */
	public RouteOverlay(List<ParcelableGeoPoint> points, int color)
	{
		this.color = color;
		this.geoPoints = points;
	}

	/**
	 * Draws an oval with the radius of the at the specified point with the
	 * specified color
	 * 
	 * @param canvas
	 *            the canvas to draw on
	 * @param paint
	 *            the paint to drawn with
	 * @param point
	 *            the point to draw
	 */
	private void drawOval(Canvas canvas, Paint paint, Point point)
	{
		Paint ovalPaint = new Paint(paint);
		ovalPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		ovalPaint.setStrokeWidth(3);

		int radius = 10;
		RectF oval = new RectF(point.x - radius, point.y - radius, point.x + radius, point.y + radius);

		canvas.drawOval(oval, ovalPaint);
	}

	/**
	 * This method is called by the system when the overlay is abut to be drawn.
	 * We start by looping through the geopoints to find start and end points.
	 * We then set a paint object which will be used to draw with and then
	 * execute the draw. The start and end points are drawn as ovals.
	 */
	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow, long when)
	{
		Projection projection = mapView.getProjection();
		if (shadow == false && geoPoints != null)
		{
			Point startPoint = null;
			Point endPoint = null;
			Path path = new Path();

			int i = 0;
			for (GeoPoint firstPoint : geoPoints)
			{
				Point secondPoint = new Point();
				projection.toPixels(firstPoint, secondPoint);
				if (i == 0)
				{
					startPoint = secondPoint;
					path.moveTo(secondPoint.x, secondPoint.y);
				}
				else
				{
					if (i == (geoPoints.size() - 1))
					{
						endPoint = secondPoint;
					}
					path.lineTo(secondPoint.x, secondPoint.y);
				}
				i++;
			}

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(color);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeWidth(10);
			paint.setAlpha(95);

			if (startPoint != null)
			{
				drawOval(canvas, paint, startPoint);
			}
			if (endPoint != null)
			{
				drawOval(canvas, paint, endPoint);
			}
			if (!path.isEmpty())
			{
				canvas.drawPath(path, paint);
			}

		}
		return super.draw(canvas, mapView, shadow, when);
	}
}