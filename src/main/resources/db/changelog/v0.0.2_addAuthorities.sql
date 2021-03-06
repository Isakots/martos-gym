CREATE TABLE AUTHORITY
(
  NAME VARCHAR(20) PRIMARY KEY
);

CREATE TABLE USER_AUTHORITY (
    USER_ID INTEGER(36),
    AUTHORITY_NAME VARCHAR(20),
    FOREIGN KEY (USER_ID) REFERENCES GYM_USER(USER_ID),
    FOREIGN KEY (AUTHORITY_NAME) REFERENCES AUTHORITY(NAME)
);
# ADDING AUTHORITIES
INSERT INTO AUTHORITY VALUES ('ROLE_USER');
INSERT INTO AUTHORITY VALUES ('ROLE_MEMBER');
