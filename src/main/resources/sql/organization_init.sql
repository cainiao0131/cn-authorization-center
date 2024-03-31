-- public.t_tenant definition

-- Drop table

-- DROP TABLE public.t_tenant;

CREATE TABLE public.t_tenant (
	id SERIAL PRIMARY KEY,
    ten_name varchar(128) DEFAULT NULL::character varying NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL
);

-- public.t_tenant_user definition

-- Drop table

-- DROP TABLE public.t_tenant_user;

CREATE TABLE public.t_tenant_user (
	id SERIAL PRIMARY KEY,
	teu_tenant_id bigint NOT NULL,
    teu_user_id bigint NOT NULL,
    teu_tenant_user_id varchar(128) DEFAULT NULL::character varying NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL
);
