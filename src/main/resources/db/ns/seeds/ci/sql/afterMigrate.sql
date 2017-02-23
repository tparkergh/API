truncate table hotline_contact, hotline_contact_participant, person, address, person_address, person_language, person_phone, phone_number, language, ethnicity, person_ethnicity, race, person_race;

INSERT INTO address (address_id, street_address, city, state, zip, create_user_id, create_datetime, update_user_id, update_datetime, address_type_id) VALUES (1, '742 Evergreen Terrace', 'Springfield', 'WA', '6525', NULL, NULL, NULL, '2016-11-14 16:18:34.262', 'Home');
INSERT INTO address (address_id, street_address, city, state, zip, create_user_id, create_datetime, update_user_id, update_datetime, address_type_id) VALUES (2, '742 Evergreen Terrace', 'Springfield', 'WA', '6525', NULL, NULL, NULL, '2016-11-18 15:22:55.315', 'Office');


INSERT INTO person (person_id, first_name, last_name, gender, date_of_birth, ssn, create_user_id, create_datetime, update_user_id, update_datetime) VALUES (1, 'Bart', 'Simpson', 'Male', '1990-04-01', '999551111', NULL, NULL, NULL, '2016-11-01 20:10:23.354');
INSERT INTO person (person_id, first_name, last_name, gender, date_of_birth, ssn, create_user_id, create_datetime, update_user_id, update_datetime) VALUES (2, 'Maggie', 'Simpson', 'Male', '1997-04-01', '999551111', NULL, NULL, NULL, '2016-11-02 21:41:20.767');


INSERT INTO phone_number(phone_number_id, phone_type_id, phone_number_value) VALUES (1, '408 987-6543', 'Home');
INSERT INTO phone_number(phone_number_id, phone_type_id, phone_number_value) VALUES (2, '690 123-4567', 'Other');

INSERT INTO language(language_id, language_code_id, update_datetime) VALUES (1, 'English', '2017-02-22 13:52:27.801');
INSERT INTO language(language_id, language_code_id, update_datetime) VALUES (2, 'Telugu', '2017-02-22 13:52:27.801');


INSERT INTO race(race_id, race_type_id, subrace_type_id, update_datetime) VALUES (1, 'white', 'American', '2017-02-22 13:52:27.801');
INSERT INTO race(race_id, race_type_id, subrace_type_id, update_datetime) VALUES (2, 'Brown', 'European', '2017-02-22 13:52:27.801');

INSERT INTO ethnicity(ethnicity_id, ethnicity_type_id, sub_ethnicity_id, update_datetime) VALUES (1, 'Unknown', 'North American', '2017-02-22 13:52:27.801');
INSERT INTO ethnicity(ethnicity_id, ethnicity_type_id, sub_ethnicity_id, update_datetime) VALUES (2, 'Unknown', 'South American', '2017-02-22 13:52:27.801');