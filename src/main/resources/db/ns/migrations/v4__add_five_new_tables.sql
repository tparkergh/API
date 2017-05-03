/* ALTER TABLE public.hotline_contact_participant 
 ADD COLUMN hotline_contact_person_id integer NULL    -- FK to the hotline_contact_person_table
;
 */
 
 
/* Drop Tables */

DROP TABLE IF EXISTS public.language CASCADE
;

DROP TABLE IF EXISTS public.person_address CASCADE
;

DROP TABLE IF EXISTS public.person_language CASCADE
;

DROP TABLE IF EXISTS public.person_phone CASCADE
;

DROP TABLE IF EXISTS public.phone_number CASCADE
;



CREATE TABLE public.language
(
	language_id integer NOT NULL   DEFAULT NEXTVAL(('seq_language'::text)::regclass),    -- Unique, sequential primary key of the table.
	language_code_id varchar(50) NULL,    -- String associated with the language (e.g., english).  Future: FK to language_type.
	language_rank integer NULL,    -- A number value that indicates the order in which the langauges are added to the language(s) field assuming that the order indicates the proficiency of the langauges for the person. Current, as languages are parsed from the string, the order is recorded (second language in string has rank = 2) 
	create_user_id integer NULL,    -- The ID of the user that created the record.
	create_datetime timestamp NULL,    -- The date and time that the create_user created the record.
	update_user_id integer NULL,    -- The ID of the user that updated the record.
	update_datetime timestamp NULL    -- The date and time that the update_user updated the record.
)
;

CREATE TABLE public.person_address
(
	person_id integer NOT NULL,    -- FK to the person table. Composite PK
	address_id integer NOT NULL,    -- FK to address table.  Composite PK
	create_user_id varchar(50) NULL,
	create_datetime timestamp NULL,    -- The date and time that the create_user created the record.
	update_user_id varchar(50) NULL,    -- The ID of the user that updated the record.  1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.
	update_datetime timestamp NULL    -- The date and time that the update_user updated the record.
)
;

CREATE TABLE public.person_language
(
	person_id integer NOT NULL,    -- FK to person table.  Part of composite PK
	language_id integer NOT NULL,    -- FK to language table.  Part of composite PK
	create_user_id varchar(50) NULL,    -- The ID of the user that created the record.  Changed user_id to char since this is consistent with CWS/CMS user ids.
	create_datetime timestamp NULL,    -- The date and time that the create_user created the record.
	update_user_id varchar(50) NULL,    -- The ID of the user that updated the record.  Changed user_id to char since this is consistent with CWS/CMS user ids.
	update_datetime timestamp NULL    -- The date and time that the update_user updated the record.
)
;

CREATE TABLE public.person_phone
(
	person_id integer NOT NULL,    -- FK to person table.  Part of composite PK.
	phone_number_id integer NOT NULL,    -- FK to phone_number table.  Part of composite PK
	create_user_id varchar(50) NULL,    -- The ID of the user that created the record. 1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.
	create_datetime timestamp NULL,    -- The date and time that the create_user created the record.
	update_user_id varchar(50) NULL,    -- The ID of the user that updated the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.
	update_datetime timestamp NULL
)
;

CREATE TABLE public.phone_number
(
	phone_number_id integer NOT NULL   DEFAULT NEXTVAL(('seq_phone_number'::text)::regclass),
	phone_type_id varchar(50) NOT NULL,    -- Current: Store the string that describes the type of phone number (e.g., home, work, mobile).  Future: FK to phone_number_type.
	phone_number_value varchar(50) NOT NULL,    -- The phone number value (e.g., Phone number digits)
	create_user_id varchar(50) NULL,    -- The ID of the user that created the record.  1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.
	create_datetime timestamp NULL,    -- The date and time that the create_user created the record.
	update_user_id varchar(50) NULL,    -- The ID of the user that updated the record.  1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.
	update_datetime timestamp NULL,    -- The date and time that the update_user updated the record.
	person_id integer NULL
)
;

DROP SEQUENCE IF EXISTS public.seq_language
;

DROP SEQUENCE IF EXISTS public.seq_phone_number
;

CREATE SEQUENCE public.seq_language INCREMENT 1 START 1
;

CREATE SEQUENCE public.seq_phone_number INCREMENT 1 START 1
;

CREATE INDEX IXFK_address_person_address ON public.address (address_id ASC)
;

CREATE INDEX IXFK_hotline_contact_participant_hotline_contact ON public.hotline_contact_participant (hotline_contact_id ASC)
;

CREATE INDEX IXFK_language_person_language ON public.language (language_id ASC)
;

ALTER TABLE public.language ADD CONSTRAINT PK_language
	PRIMARY KEY (language_id)
;

CREATE INDEX IXFK_person_address_address ON public.person_address (address_id ASC)
;

CREATE INDEX IXFK_person_address_person ON public.person_address (person_id ASC)
;

ALTER TABLE public.person_address ADD CONSTRAINT PK_person_person_address
	PRIMARY KEY (person_id,address_id)
;

CREATE INDEX IXFK_person_language_language ON public.person_language (language_id ASC)
;

ALTER TABLE public.person_language ADD CONSTRAINT PK_person_language
	PRIMARY KEY (person_id,language_id)
;

CREATE INDEX IXFK_person_phone_person ON public.person_phone (person_id ASC)
;

ALTER TABLE public.person_phone ADD CONSTRAINT PK_person_phone
	PRIMARY KEY (person_id,phone_number_id)
;

CREATE INDEX IXFK_phone_number_person_phone ON public.phone_number (person_id ASC,phone_number_id ASC)
;

ALTER TABLE public.phone_number ADD CONSTRAINT PK_phone_number
	PRIMARY KEY (phone_number_id)
;

ALTER TABLE public.hotline_contact_participant ADD CONSTRAINT FK_hotline_contact_participant_hotline_contact
	FOREIGN KEY (hotline_contact_id) REFERENCES public.hotline_contact (hotline_contact_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE public.person_address ADD CONSTRAINT FK_person_address_address
	FOREIGN KEY (address_id) REFERENCES public.address (address_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE public.person_address ADD CONSTRAINT FK_person_address_person
	FOREIGN KEY (person_id) REFERENCES public.person (person_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE public.person_language ADD CONSTRAINT FK_person_language_language
	FOREIGN KEY (language_id) REFERENCES public.language (language_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE public.person_phone ADD CONSTRAINT FK_person_phone_person
	FOREIGN KEY (person_id) REFERENCES public.person (person_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE public.phone_number ADD CONSTRAINT FK_phone_number_person_phone
	FOREIGN KEY (person_id,phone_number_id) REFERENCES public.person_phone (person_id,phone_number_id) ON DELETE No Action ON UPDATE No Action
;

COMMENT ON TABLE public.address IS 'A physical location (e.g., where a incident occurred, a person resides a service can be obtained, etc).  This table is used to capture the hotline contact incident address ONLY.  For FK FK to the hotline_contact table.  Each hotline event can have one incident address or the address can be associated with the person(s) requesting services or support via the hotline.  Future: Apply Geographic Location to standardize county & city'
;

COMMENT ON TABLE public.language
	IS 'Stores the language values for a person as well as the ranking (order of proficiency) for a person.  Future: expand to indicate whether they are written or spoken preferences.  '
;

COMMENT ON TABLE public.person_address
	IS 'Intersection table that allows multiple addresses per person.  Each address must be distinct by effective date range and address type '
;

COMMENT ON TABLE public.person_language
	IS 'The one to many languages associated with and identified during the creation of a new person.  '
;

COMMENT ON TABLE public.person_phone
	IS 'Intersection table that supports the storage of various electronic addresses associated with a person (e.g.,  '
;

COMMENT ON TABLE public.phone_number
	IS 'Stores the phone type and value (e.g., 9165551212) for a person or organization. '
;

COMMENT ON COLUMN public.language.create_datetime
	IS 'The date and time that the create_user created the record.'
;

COMMENT ON COLUMN public.language.create_user_id
	IS 'The ID of the user that created the record.'
;

COMMENT ON COLUMN public.language.language_code_id
	IS 'String associated with the language (e.g., english).  Future: FK to language_type.'
;

COMMENT ON COLUMN public.language.language_id
	IS 'Unique, sequential primary key of the table.'
;

COMMENT ON COLUMN public.language.language_rank
	IS 'A number value that indicates the order in which the langauges are added to the language(s) field assuming that the order indicates the proficiency of the langauges for the person. Current, as languages are parsed from the string, the order is recorded (second language in string has rank = 2) '
;

COMMENT ON COLUMN public.language.update_datetime
	IS 'The date and time that the update_user updated the record.'
;

COMMENT ON COLUMN public.language.update_user_id
	IS 'The ID of the user that updated the record.'
;

COMMENT ON COLUMN public.person_address.address_id
	IS 'FK to address table.  Composite PK'
;

COMMENT ON COLUMN public.person_address.create_datetime
	IS 'The date and time that the create_user created the record.'
;

COMMENT ON COLUMN public.person_address.person_id
	IS 'FK to the person table. Composite PK'
;

COMMENT ON COLUMN public.person_address.update_datetime
	IS 'The date and time that the update_user updated the record.'
;

COMMENT ON COLUMN public.person_address.update_user_id
	IS 'The ID of the user that updated the record.  1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.person_language.create_datetime
	IS 'The date and time that the create_user created the record.'
;

COMMENT ON COLUMN public.person_language.create_user_id
	IS 'The ID of the user that created the record.  Changed user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.person_language.language_id
	IS 'FK to language table.  Part of composite PK'
;

COMMENT ON COLUMN public.person_language.person_id
	IS 'FK to person table.  Part of composite PK'
;

COMMENT ON COLUMN public.person_language.update_datetime
	IS 'The date and time that the update_user updated the record.'
;

COMMENT ON COLUMN public.person_language.update_user_id
	IS 'The ID of the user that updated the record.  Changed user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.person_phone.create_datetime
	IS 'The date and time that the create_user created the record.'
;

COMMENT ON COLUMN public.person_phone.create_user_id
	IS 'The ID of the user that created the record. 1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.person_phone.person_id
	IS 'FK to person table.  Part of composite PK.'
;

COMMENT ON COLUMN public.person_phone.phone_number_id
	IS 'FK to phone_number table.  Part of composite PK'
;

COMMENT ON COLUMN public.person_phone.update_user_id
	IS 'The ID of the user that updated the record. 1/25/17 - Change all user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.phone_number.create_datetime
	IS 'The date and time that the create_user created the record.'
;

COMMENT ON COLUMN public.phone_number.create_user_id
	IS 'The ID of the user that created the record.  1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.'
;

COMMENT ON COLUMN public.phone_number.phone_number_value
	IS 'The phone number value (e.g., Phone number digits)'
;

COMMENT ON COLUMN public.phone_number.phone_type_id
	IS 'Current: Store the string that describes the type of phone number (e.g., home, work, mobile).  Future: FK to phone_number_type.'
;

COMMENT ON COLUMN public.phone_number.update_datetime
	IS 'The date and time that the update_user updated the record.'
;

COMMENT ON COLUMN public.phone_number.update_user_id
	IS 'The ID of the user that updated the record.  1/25/17 - Changed user_id to char since this is consistent with CWS/CMS user ids.'
;
COMMENT ON COLUMN public.hotline_contact_participant.hotline_contact_person_id
	IS 'FK to the hotline_contact_person_table'
;
