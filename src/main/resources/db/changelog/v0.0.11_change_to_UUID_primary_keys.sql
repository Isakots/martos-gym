-- drop foreign keys
ALTER TABLE TICKETS DROP FOREIGN KEY tickets_ibfk_1;
ALTER TABLE TICKETS DROP FOREIGN KEY tickets_ibfk_2;

ALTER TABLE TRAINING_PARTICIPANTS DROP FOREIGN KEY training_participants_ibfk_1;
ALTER TABLE TRAINING_PARTICIPANTS DROP FOREIGN KEY training_participants_ibfk_2;

ALTER TABLE user_authority DROP FOREIGN KEY user_authority_ibfk_1;
ALTER TABLE user_authority DROP FOREIGN KEY user_authority_ibfk_2;

ALTER TABLE user_subscriptions DROP FOREIGN KEY user_subscriptions_ibfk_1;
ALTER TABLE user_subscriptions DROP FOREIGN KEY user_subscriptions_ibfk_2;

ALTER TABLE reservation DROP FOREIGN KEY FK_USER;

-- modify columns
ALTER TABLE tool MODIFY TOOL_ID VARCHAR(36);
ALTER TABLE reservation MODIFY RESERVATION_ID VARCHAR(36);
ALTER TABLE article MODIFY ARTICLE_ID VARCHAR(36);
ALTER TABLE gym_period MODIFY PERIOD_ID VARCHAR(36);
ALTER TABLE training MODIFY TRAINING_ID VARCHAR(36);
ALTER TABLE gym_user MODIFY USER_ID VARCHAR(36);

-- recreate foreign keys
ALTER TABLE TICKETS MODIFY USER_ID VARCHAR(36);
ALTER TABLE TICKETS ADD CONSTRAINT FK_TICKET_USER FOREIGN KEY (USER_ID) REFERENCES GYM_USER(USER_ID);
ALTER TABLE TICKETS MODIFY PERIOD_ID VARCHAR(36);
ALTER TABLE TICKETS ADD CONSTRAINT FK_TICKET_PERIOD FOREIGN KEY (PERIOD_ID) REFERENCES GYM_PERIOD(PERIOD_ID);

ALTER TABLE TRAINING_PARTICIPANTS MODIFY PARTICIPANT_ID VARCHAR(36);
ALTER TABLE TRAINING_PARTICIPANTS ADD CONSTRAINT FK_TRAINING_PARTICIPANT FOREIGN KEY (PARTICIPANT_ID) REFERENCES GYM_USER(USER_ID);
ALTER TABLE TRAINING_PARTICIPANTS MODIFY TRAINING_ID VARCHAR(36);
ALTER TABLE TRAINING_PARTICIPANTS ADD CONSTRAINT FK_TRAINING_TR FOREIGN KEY (TRAINING_ID) REFERENCES TRAINING(TRAINING_ID);

ALTER TABLE user_authority MODIFY USER_ID VARCHAR(36);
ALTER TABLE user_authority ADD CONSTRAINT FK_USER_AUTH_USER FOREIGN KEY (USER_ID) REFERENCES GYM_USER(USER_ID);
ALTER TABLE user_authority ADD CONSTRAINT FK_USER_AUTH_AUTH FOREIGN KEY (AUTHORITY_NAME) REFERENCES AUTHORITY(NAME);

ALTER TABLE user_subscriptions MODIFY USER_ID VARCHAR(36);
ALTER TABLE user_subscriptions ADD CONSTRAINT FK_USER_SUB_USER FOREIGN KEY (USER_ID) REFERENCES GYM_USER(USER_ID);
ALTER TABLE user_subscriptions ADD CONSTRAINT FK_USER_SUB_SUB FOREIGN KEY (SUBSCRIPTION_NAME) REFERENCES SUBSCRIPTION(NAME);

ALTER TABLE reservation MODIFY USER_ID VARCHAR(36);
ALTER TABLE reservation ADD CONSTRAINT FK_RESERVATION_USER FOREIGN KEY (USER_ID) REFERENCES GYM_USER(USER_ID);