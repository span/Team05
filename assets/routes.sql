CREATE TABLE routes (
  id mediumint(8) unsigned NOT NULL auto_increment, 
  _id MEDIUMINT default NULL,
  name TEXT default NULL,
  description TEXT default NULL,
  lengthcoach varchar(255) default NULL,
  timecoach varchar(255) default NULL,
  type varchar(255) default NULL,
  PRIMARY KEY (`id`)
) TYPE=MyISAM AUTO_INCREMENT=1; 


INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('1','mi, ac','Phasellus elit pede, malesuada vel, venenatis vel,','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('2','placerat, augue.','In nec orci. Donec nibh. Quisque nonummy','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('3','Nam ligula','Sed nec metus facilisis lorem tristique aliquet.','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('4','parturient montes,','sem elit, pharetra ut, pharetra sed, hendrerit','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('5','amet, consectetuer','Ut semper pretium neque. Morbi quis urna.','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('6','mauris ut','Integer id magna et ipsum cursus vestibulum.','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('7','aliquet vel,','ac libero nec ligula consectetuer rhoncus. Nullam','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('8','a, magna.','justo. Praesent luctus. Curabitur egestas nunc sed','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('9','sodales. Mauris','convallis est, vitae sodales nisi magna sed','-1','-1','-1');
INSERT INTO routes (_id,name,description,lengthcoach,timecoach,type) VALUES ('10','montes, nascetur','Etiam vestibulum massa rutrum magna. Cras convallis','-1','-1','-1');