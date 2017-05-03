ALTER TABLE public.hotline_contact 
 DROP COLUMN IF EXISTS hotline_contact_participant_array
;

/* Drop Tables */

DROP TABLE IF EXISTS public.hotline_contact_participant CASCADE
;

DROP SEQUENCE IF EXISTS public.seq_hotline_contact_id
;

DROP TABLE IF EXISTS public.seq_hotline_contact_id CASCADE
;

DROP SEQUENCE IF EXISTS public.seq_hotline_contact_participant_contact_id CASCADE
;

DROP SEQUENCE IF EXISTS public.seq_hotline_contact_participant_id CASCADE
;

CREATE TABLE public.hotline_contact_participant
(
	hotline_contact_participant_id integer NOT NULL   DEFAULT NEXTVAL(('seq_hotline_contact_participant_id'::text)::regclass),    -- Unique, sequential primary key of the table.
	hotline_contact_id integer NOT NULL,    -- ID for the hotline contact event that this participant is associated with.  Foreign key to the hotline_contact table.
	person_id integer NOT NULL,    -- ID for a person identified as a participant identified during a hotline contact.  Foreign key to the person table.
	hotline_contact_participant_type varchar(50) NULL,    -- Value corresponding to the type of participant mentioned in the communication processed by the hotline.  Potential values include: requester, reporter, alleged victim, alleged perpetrator, etc.   
	create_user_id integer NULL,    -- The ID of the user that created the record.
	create_datetime timestamp NULL,    -- The date and time that the user created the record.
	update_user_id integer NULL,    -- The date and time that the user created the record.
	update_datetime timestamp NULL
)
;

CREATE SEQUENCE public.seq_hotline_contact_participant_id INCREMENT 1 START 1
;

ALTER TABLE public.hotline_contact_participant ADD CONSTRAINT PK_hotline_contact_person
	PRIMARY KEY (hotline_contact_participant_id)
;

ALTER TABLE public.hotline_contact_participant ADD CONSTRAINT FK_hotline_contact_participant_person
	FOREIGN KEY (person_id) REFERENCES public.person (person_id) ON DELETE No Action ON UPDATE No Action
;

COMMENT ON TABLE public.hotline_contact_participant
	IS 'An intersection table that supports the relationship of one to many persons to a hotline contact (aka screening event). Currently, as part of version 1, this relationship is maintained by the existance of one or more persons ids within the hotline_contact_participant_array field within hotline_contact.   v1.2 - Stopped generating this table in the DDL. '
;

COMMENT ON COLUMN public.hotline_contact_participant.create_datetime
	IS 'The date and time that the user created the record.'
;

COMMENT ON COLUMN public.hotline_contact_participant.create_user_id
	IS 'The ID of the user that created the record.'
;

COMMENT ON COLUMN public.hotline_contact_participant.hotline_contact_id
	IS 'ID for the hotline contact event that this participant is associated with.  Foreign key to the hotline_contact table.'
;

COMMENT ON COLUMN public.hotline_contact_participant.hotline_contact_participant_id
	IS 'Unique, sequential primary key of the table.'
;

COMMENT ON COLUMN public.hotline_contact_participant.hotline_contact_participant_type
	IS 'Value corresponding to the type of participant mentioned in the communication processed by the hotline.  Potential values include: requester, reporter, alleged victim, alleged perpetrator, etc.   '
;

COMMENT ON COLUMN public.hotline_contact_participant.person_id
	IS 'ID for a person identified as a participant identified during a hotline contact.  Foreign key to the person table.'
;

COMMENT ON COLUMN public.hotline_contact_participant.update_user_id
	IS 'The date and time that the user created the record.'
;
