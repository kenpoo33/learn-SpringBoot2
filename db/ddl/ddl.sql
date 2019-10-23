﻿-- 学習用DDL
CREATE DATABASE SB2; 

CREATE USER 'app01' @'localhost' IDENTIFIED BY
  'password'; 

GRANT ALL 
  on SB2.* to 'app01' @'localhost'; 

flush privileges; 

DROP TABLE IF EXISTS SB2.ID_TBL; 

DROP TABLE IF EXISTS SB2.ID_ROLE_TBL; 

DROP TABLE IF EXISTS SB2.ROLE_KGN_TBL; 

DROP TABLE IF EXISTS SB2.PASSWD_TBL; 

CREATE TABLE SB2.ID_TBL( 
  ID CHAR (5) NOT NULL
  , NAME VARCHAR (20) NOT NULL
  , PRIMARY KEY (ID)
); 

CREATE TABLE SB2.ID_ROLE_TBL( 
  ID CHAR (5) NOT NULL
  , ROLE_CD VARCHAR (5) NOT NULL
  , PRIMARY KEY (ID,ROLE_CD)
); 

CREATE TABLE SB2.ROLE_KGN_TBL( 
  ROLE_CD CHAR (5) NOT NULL
  , KENGEN_CD CHAR (5) NOT NULL
  , PRIMARY KEY (ROLE_CD,KENGEN_CD)
);

CREATE TABLE SB2.ROLE_KGN_TBL( 
  ROLE_CD CHAR (5) NOT NULL
  , KENGEN_CD CHAR (5) NOT NULL
  , PRIMARY KEY (ROLE_CD,KENGEN_CD)
);

CREATE TABLE SB2.PASSWD_TBL( 
  ID CHAR (5) NOT NULL
  , ID_PASSWD VARCHAR(60) NOT NULL
  , PRIMARY KEY (ID)
);
