ALTER TABLE public.person 
 ALTER COLUMN create_user_id TYPE varchar(50)
;

ALTER TABLE public.person 
 ALTER COLUMN update_user_id TYPE varchar(50)
;

ALTER TABLE public.person 
 ADD COLUMN middle_name varchar(50) NULL    -- Middle name of Person.
;

ALTER TABLE public.person 
 ADD COLUMN name_suffix varchar(50) NULL    -- Name suffix of Person being created (e.g., esq, ii, iii, iv, jr, sr, md, phd, jd)
;

ALTER TABLE public.address 
 ALTER COLUMN create_user_id TYPE varchar(50)
;

ALTER TABLE public.address 
 ALTER COLUMN update_user_id TYPE varchar(50)
;

ALTER TABLE public.hotline_contact 
 ALTER COLUMN create_user_id TYPE varchar(50)
;

ALTER TABLE public.hotline_contact 
 ALTER COLUMN update_user_id TYPE varchar(50)
;

ALTER TABLE public.hotline_contact_participant 
 ALTER COLUMN create_user_id TYPE varchar(50)
;

ALTER TABLE public.hotline_contact_participant 
 ALTER COLUMN update_user_id TYPE varchar(50)
;

COMMENT ON TABLE public.person IS 'Stores data associated with a person: 1. Identified as a participant in the CWS-NS Screening area 2. Created via the CWS-NS New Person screen.       Sources: 1. Use of the Participant field allows multiple persons in an Elasticsearch document to be found and associated with a screening. The attributes stored are a function of the API content from ELasticsearch desired by UI.    2. Currently this person is created during the screening business process of Intake where a hotline contact is being processed with the assumption is that the person does not exist within the existing database search (currently Elasticsearch) which is sourced from tables that hold person in CWS-CMS.    Once created, it is planned to add the person to Elasticsearch which makes it available via Participant in the Screening section.  Use of newly created Person, where  Hotline Contact is: * Info only need - no referral created If the hotline contact event does not move forward to be a referral (as opposed to an information only request that does not require the creation of a referral), the person data will still be available to use for screening and assessment statistics and to contact a person (added via the participant) to provide services.   *  Referral is created If the hotline contact event does move forward to be a referral that is created and evaluated out or continues to investigation), the Case Commons approach is that the person data should not overwrite existing person (aka client) data until the user can more concretely verify the person (e.g., name, demographics, address).  Once "validated" (e.g., thorough verification the person is not a duplicate), the data can be insert into CWS/CMS (e.g., client table) or the person existing in CWS/CMS can be updated, making sure to capture the history of all changes.   Current: FK to the person_address for multiple addresses Store the address type including physical, mailing, geophysical, electronic  Current: FK to electronic_address for multiple communication channels of varying types (email, Facebook persona, phone, fax, etc.).    '
;

COMMENT ON COLUMN public.person.create_user_id IS 'The ID of the user that created the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.person.middle_name IS 'Middle name of Person. '
;

COMMENT ON COLUMN public.person.name_suffix IS 'Name suffix of Person being created (e.g., esq, ii, iii, iv, jr, sr, md, phd, jd)'
;

COMMENT ON COLUMN public.person.update_user_id IS 'The ID of the user which made the last update to the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.address.create_user_id IS 'The ID of the user that created the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.address.update_user_id IS 'The ID of the user which made the last update to the record. 1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.hotline_contact.create_user_id IS 'The ID of the user that created the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.hotline_contact.update_user_id IS 'The ID of the user which made the last update to the record. 1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.hotline_contact_participant.create_user_id
	IS 'The ID of the user that create_user created the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.hotline_contact_participant.update_user_id
	IS 'The ID of the user that updated the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.'
;

