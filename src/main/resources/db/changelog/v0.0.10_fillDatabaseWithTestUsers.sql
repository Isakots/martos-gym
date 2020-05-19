# ADDING TEST USERS
INSERT INTO GYM_USER(EMAIL, PASSWORD, FIRSTNAME, LASTNAME, STUDENT_STATUS, INSTITUTION, FACULTY, IS_COLLEGIAN, ROOM_NUMBER, IMAGE_PATH)
VALUES ('admin@test.hu', '$2a$10$KoqtYaKaPA.OrYVuusjRNO0rFb.Np6m0zfep6mVUneLGA8yeirh8O', 'admin', 'admin', false, null, null, false, 0, null);
INSERT INTO GYM_USER(EMAIL, PASSWORD, FIRSTNAME, LASTNAME, STUDENT_STATUS, INSTITUTION, FACULTY, IS_COLLEGIAN, ROOM_NUMBER, IMAGE_PATH)
VALUES ('user@test.hu', '$2a$10$KoqtYaKaPA.OrYVuusjRNO0rFb.Np6m0zfep6mVUneLGA8yeirh8O', 'user', 'user', false, null, null, false, 0, null);

# ADDING CORRESPONDING AUTHORITIES TO TEST USERS
INSERT INTO USER_AUTHORITY(USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY(USER_ID, AUTHORITY_NAME) VALUES (1, 'ROLE_MEMBER');
INSERT INTO USER_AUTHORITY(USER_ID, AUTHORITY_NAME) VALUES (2, 'ROLE_USER');
