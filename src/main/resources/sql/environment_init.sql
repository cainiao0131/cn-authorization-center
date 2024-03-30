-- public.t_environment definition

-- Drop table

-- DROP TABLE public.t_environment;

CREATE TABLE public.t_environment (
	id bigint NOT NULL,
	env_name varchar(100) NOT NULL,
	env_description varchar(100) NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT environment_pkey PRIMARY KEY (id)
);

-- public.t_environment_application definition

-- Drop table

-- DROP TABLE public.t_environment_application;

CREATE TABLE public.t_environment_application (
	id bigint NOT NULL,
	ena_environment_id bigint NOT NULL,
	ena_application_id bigint NOT NULL,
	ena_service_name varchar(100) DEFAULT NULL::character varying NULL,
	ena_service_uri varchar(100) DEFAULT NULL::character varying NULL,
	ena_registered_client_id varchar(100) DEFAULT NULL::character varying NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT environment_application_pkey PRIMARY KEY (id)
);
