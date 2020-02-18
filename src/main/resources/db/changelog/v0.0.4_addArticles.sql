CREATE TABLE ARTICLE
(
  ARTICLE_ID INTEGER(36) PRIMARY KEY AUTO_INCREMENT,
  TITLE VARCHAR(63) NOT NULL,
  TYPE VARCHAR(15) NOT NULL,
  INTRODUCTION VARCHAR(511) NOT NULL,
  CONTENT VARCHAR(4095) NOT NULL,
  CREATED_DATE TIMESTAMP NOT NULL,
  UPDATED_DATE TIMESTAMP NOT NULL
)