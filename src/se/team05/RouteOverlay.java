/**
 * Based on: http://djsolid.net/blog/android---draw-a-path-array-of-points-in-mapview
 * https://bitbucket.org/djsolid/android-helper-files/src/f3dbd9c31be3/RoutePathOverlay.java
 * 
 * Edited some stroke widths and radius and formatted code. Also change some variable names.
 * 
 */

package se.team05;

import java.util.List;

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

public class RouteOverlay extends Overlay
{

	private int color;
	private final List<GeoPoint> geoPoints;
	private boolean drawEndPoints;

	public RouteOverlay(List<GeoPoint> points)
	{
		this(points, Color.RED, true);
	}

	public RouteOverlay(List<GeoPoint> points, int color, boolean drawEndPoints)
	{
		this.color = color;
		this.geoPoints = points;
		this.drawEndPoints = drawEndPoints;
	}

	private void drawOval(Canvas canvas, Paint paint, Point point)
	{
		Paint ovalPaint = new Paint(paint);
		ovalPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		ovalPaint.setStrokeWidth(3);
		
		int radius = 10;
		RectF oval = new RectF(point.x - radius, point.y - radius, point.x + radius, point.y + radius);
		
		canvas.drawOval(oval, ovalPaint);
	}

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
			for(GeoPoint firstPoint : geoPoints)
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