CREATE TABLE job (
  ID INT NOT NULL,
  TITLE varchar(200) DEFAULT NULL,
  COMPANY varchar(200) DEFAULT NULL,
  REQUIRE_YEAR varchar(10) DEFAULT NULL,
  DATE_POST DATE DEFAULT NULL,
  DATE_EXPIRED DATE DEFAULT NULL,
  DESCRIPTION text,
  LINK text,
  TAG_IDS varchar(250) DEFAULT NULL,
  ADDRESS varchar(250) DEFAULT NULL,
  CREATED timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
)