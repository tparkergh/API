truncate table hotline_contact, hotline_contact_participant, person, address, person_address, person_language, person_phone, phone_number, language;

INSERT INTO address (address_id, street_address, city, state, zip, create_user_id, create_datetime, update_user_id, update_datetime) VALUES (1, '742 Evergreen Terrace', 'Springfield', 'WA', '6525', NULL, NULL, NULL, '2016-11-14 16:18:34.262');
INSERT INTO address (address_id, street_address, city, state, zip, create_user_id, create_datetime, update_user_id, update_datetime) VALUES (2, '742 Evergreen Terrace', 'Springfield', 'WA', '6525', NULL, NULL, NULL, '2016-11-18 15:22:55.315');


INSERT INTO person (person_id, first_name, last_name, gender, date_of_birth, ssn, person_address_id, create_user_id, create_datetime, update_user_id, update_datetime) VALUES (1, 'Bart', 'Simpson', 'Male', '1990-04-01', '999551111', 1, NULL, NULL, NULL, '2016-11-01 20:10:23.354');
INSERT INTO person (person_id, first_name, last_name, gender, date_of_birth, ssn, person_address_id, create_user_id, create_datetime, update_user_id, update_datetime) VALUES (2, 'Maggie', 'Simpson', 'Male', '1997-04-01', '999551111', 2, NULL, NULL, NULL, '2016-11-02 21:41:20.767');


