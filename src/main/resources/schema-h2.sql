CREATE TABLE USER (
  ID INTEGER AUTO_INCREMENT PRIMARY KEY,
  USERID VARCHAR(500) NOT NULL UNIQUE,
  NAME VARCHAR(500) NOT NULL,
  LASTNAME VARCHAR(500) NOT NULL,
  SECONDNAME VARCHAR(500) NOT NULL,
  PASSWORD VARCHAR(500) NOT NULL,
  EDGE VARCHAR(500) NOT NULL,
  ISACCOUNTNONEXPIRED VARCHAR(500) NOT NULL,
  ISACCOUNTNONLOCKED VARCHAR(500) NOT NULL,
  ISCREDENTIALSNONEXPIRED VARCHAR(500) NOT NULL,
  ISENABLED VARCHAR(500) NOT NULL
);

CREATE TABLE AUTH_USER_GROUP (
  AUTH_USER_GROUP_ID INTEGER AUTO_INCREMENT PRIMARY KEY,
  USERID VARCHAR(500) NOT NULL,
  AUTH_GROUP VARCHAR(128) NOT NULL,
  CONSTRAINT USER_AUTH_USER_GROUP_FK FOREIGN KEY(USERID) REFERENCES USER(USERID),
  UNIQUE (USERID, AUTH_GROUP)
)