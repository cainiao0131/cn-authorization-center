-- public.t_user definition

-- Drop table

-- DROP TABLE public.t_user;

CREATE TABLE public.t_user (
	id SERIAL PRIMARY KEY,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL
);

-- public.t_lark_user definition

-- Drop table

-- DROP TABLE public.t_lark_user;

CREATE TABLE public.t_lark_user (
	id SERIAL PRIMARY KEY,
	lau_user_id bigint NOT NULL,
	lau_lark_user_id varchar(128) DEFAULT NULL::character varying NULL,
	lau_open_id varchar(128) DEFAULT NULL::character varying NULL,
	lau_union_id varchar(128) DEFAULT NULL::character varying NULL,
	created_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	updated_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
	created_by varchar(100) NULL,
	updated_by varchar(100) NULL,
	deleted bool DEFAULT false NOT NULL
);
