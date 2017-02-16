ALTER TABLE public.person 
 DROP COLUMN IF EXISTS person_address_id 
;

ALTER TABLE public.hotline_contact_participant  
 DROP COLUMN IF EXISTS hotline_contact_person_id 
;

ALTER TABLE public.language  
 ALTER COLUMN create_user_id TYPE varchar(50)
;

ALTER TABLE public.language  
 ALTER COLUMN update_user_id TYPE varchar(50) 
;

ALTER TABLE public.person_language ADD CONSTRAINT FK_person_language_person
	FOREIGN KEY (person_id) REFERENCES public.person (person_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE public.phone_number  
 DROP COLUMN IF EXISTS person_id 
;

ALTER TABLE public.person_phone ADD CONSTRAINT FK_person_phone_phone_number
	FOREIGN KEY (phone_number_id) REFERENCES public.phone_number (phone_number_id) ON DELETE No Action ON UPDATE No Action
;

DROP INDEX IF EXISTS IXFK_phone_number_person_phone
;

CREATE INDEX IXFK_phone_number_person_phone ON public.phone_number (phone_number_id ASC)
;

ALTER TABLE public.address 
 ADD COLUMN address_type_id varchar(50) NULL  -- FK to address_type LOV table.  Currently the UI will pass an address type string value (e.g., residence, mailing) from a UI dropdown.  The API must find the PK id and store it here. 
;

ALTER TABLE hotline_contact 
 ADD COLUMN response_time varchar(50) NULL    -- CW Worker in person incident response time selected based upon assessment and approval by supervisor.
;

ALTER TABLE public.language 
 DROP COLUMN IF EXISTS language_rank  
;

ALTER TABLE public.person_language 
 ADD COLUMN language_rank integer NULL  
;


/* Made existing columns hotline_contact_id and person_id a composite PK key */ 
/* Drop Sequences and PK before column drop */  

DROP SEQUENCE IF EXISTS public.seq_hotline_contact_participant
;


ALTER TABLE public.hotline_contact_participant DROP CONSTRAINT IF EXISTS PK_hotline_contact_person
;

ALTER TABLE public.hotline_contact_participant ADD CONSTRAINT PK_hotline_contact_person
	PRIMARY KEY (hotline_contact_id,person_id)
;

ALTER TABLE public.person 
 ADD COLUMN alternate_person_id VARCHAR(50) NULL      
;

/* Create Table Comments, Sequences for Autonumber Columns */

-- from Item 8
COMMENT ON COLUMN public.address.address_type_id
	IS 'FK to address_type LOV table.  Currently the UI will pass an address type string value (e.g., residence, mailing) from a UI dropdown.  The API must find the PK id and store it here.   '
;

COMMENT ON COLUMN public.hotline_contact.response_time
	IS 'CW Worker in person incident response time selected based upon assessment and approval by supervisor.'
;
-- from Item 9

COMMENT ON COLUMN public.person_language.language_rank
	IS 'A number value that indicates the order in which the languages are added to the language(s) field assuming that the order indicates the proficiency of the languages for the person. Current, as languages are parsed from the string, the order is recorded (second language in string has rank = 2) '
;

-- from Item 11

COMMENT ON COLUMN public.person.alternate_person_id
	IS 'Used to store an alternate person id (e.g., the id known to legacy as included in the ElasticSearch data set).  For example, a participant associated with a hotline_contact that is sourced from Legacy CWS/CMS will have a 10 char legacy ID.  A person saved in the new person GUI will not have a legacy 10 char legacy ID.  '
;
