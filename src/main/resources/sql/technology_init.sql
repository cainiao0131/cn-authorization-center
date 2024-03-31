-- public.t_system definition

-- Drop table

-- DROP TABLE public.t_system;

CREATE TABLE public.t_system (
	id SERIAL PRIMARY KEY,
	sys_tenant_id bigint NOT NULL,
    sys_name varchar(100) NOT NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL
);

-- public.t_system_user definition

-- Drop table

-- DROP TABLE public.t_system_user;

CREATE TABLE public.t_system_user (
	id SERIAL PRIMARY KEY,
	syu_system_id bigint NOT NULL,
    syu_user_id bigint NOT NULL,
    syu_system_user_id varchar(128) DEFAULT NULL::character varying NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL
);
