-- public.oauth2_registered_client definition

-- Drop table

-- DROP TABLE public.oauth2_registered_client;

CREATE TABLE public.oauth2_registered_client (
	id varchar(100) NOT NULL,
	client_id varchar(100) NOT NULL,
	client_id_issued_at timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	client_secret varchar(200) DEFAULT NULL::character varying NULL,
	client_secret_expires_at timestamp NULL,
	client_name varchar(200) NOT NULL,
	client_authentication_methods varchar(1000) NOT NULL,
	authorization_grant_types varchar(1000) NOT NULL,
	redirect_uris varchar(1000) DEFAULT NULL::character varying NULL,
	post_logout_redirect_uris varchar(1000) DEFAULT NULL::character varying NULL,
	scopes varchar(1000) NOT NULL,
	client_settings varchar(2000) NULL,
	token_settings varchar(2000) NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT oauth2_registered_client_pkey PRIMARY KEY (id)
);

-- public.oauth2_client_registration definition

-- Drop table

-- DROP TABLE public.oauth2_client_registration;

CREATE TABLE oauth2_client_registration (
	id varchar(100) NOT NULL,
	registration_id varchar(100) NOT NULL,
	client_id varchar(100) NOT NULL,
	client_secret varchar(200) DEFAULT NULL::character varying NULL,
	authorization_grant_type varchar(100) DEFAULT NULL::character varying NULL,
	client_name varchar(200) NOT NULL,
	redirect_uri varchar(500) DEFAULT NULL::character varying NOT NULL,
	scopes varchar(1000) DEFAULT NULL::character varying NULL,
	client_authentication_method varchar(1000) DEFAULT NULL::character varying NULL,
	authorization_uri varchar(500) DEFAULT NULL::character varying NULL,
	token_uri varchar(500) DEFAULT NULL::character varying NULL,
	jwk_set_uri varchar(500) DEFAULT NULL::character varying NULL,
	issuer_uri varchar(500) DEFAULT NULL::character varying NULL,
	configuration_metadata varchar(2000) DEFAULT NULL::character varying NULL,
	user_info_uri varchar(500) DEFAULT NULL::character varying NULL,
	user_info_authentication_method varchar(100) DEFAULT NULL::character varying NULL,
	user_name_attribute_name varchar(100) DEFAULT NULL::character varying NULL,
	from_issuer_location bool DEFAULT false NOT NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT oauth2_client_registration_pkey PRIMARY KEY (id)
);

-- public.t_system definition

-- Drop table

-- DROP TABLE public.t_system;

CREATE TABLE public.t_system (
	id varchar(100) NOT NULL,
    s_name varchar(100) NOT NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT system_pkey PRIMARY KEY (id)
);

-- public.t_application definition

-- Drop table

-- DROP TABLE public.t_application;

CREATE TABLE public.t_application (
	id varchar(100) NOT NULL,
    a_system_id varchar(100) NOT NULL,
    a_name varchar(100) NOT NULL,
    a_service_name varchar(100) DEFAULT NULL::character varying NULL,
    a_service_uri varchar(100) DEFAULT NULL::character varying NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT application_pkey PRIMARY KEY (id)
);

-- public.t_ui_module definition

-- Drop table

-- DROP TABLE public.t_ui_module;

CREATE TABLE public.t_ui_module (
	id varchar(100) NOT NULL,
    um_parent_id varchar(100) DEFAULT NULL::character varying NULL,
    um_application_id varchar(100) NOT NULL,
    um_name varchar(100) NOT NULL,
    um_uri varchar(200) DEFAULT NULL::character varying NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL,
	CONSTRAINT ui_module_pkey PRIMARY KEY (id)
);
