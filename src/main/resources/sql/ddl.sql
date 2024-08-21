-- public.t_auth_namespace definition

CREATE TABLE IF NOT EXISTS public.t_auth_namespace (
	an_name varchar NULL, -- 权限命名空间名称
	an_description varchar NULL, -- 权限命名空间描述
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_auth_namespace_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_auth_namespace.an_name IS '权限命名空间名称';
COMMENT ON COLUMN public.t_auth_namespace.an_description IS '权限命名空间描述';
COMMENT ON COLUMN public.t_auth_namespace.created_by IS '创建人';
COMMENT ON COLUMN public.t_auth_namespace.created_at IS '创建时间';
COMMENT ON COLUMN public.t_auth_namespace.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_auth_namespace.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_auth_namespace.id IS '主键 ID';

-- public.t_lark_user definition

CREATE TABLE IF NOT EXISTS public.t_lark_user (
	lu_user_id int8 NULL, -- 与这个飞书用户关联的平台用户ID
	lu_lark_user_id varchar NULL, -- 飞书的 User ID
	lu_open_id varchar NULL, -- 用户在应用内的身份
	lu_union_id varchar NULL, -- 用户在同一应用服务商提供的多个应用间的统一身份
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_lark_user_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_lark_user.lu_user_id IS '与这个飞书用户关联的平台用户ID';
COMMENT ON COLUMN public.t_lark_user.lu_lark_user_id IS '飞书的 User ID';
COMMENT ON COLUMN public.t_lark_user.lu_open_id IS '用户在应用内的身份';
COMMENT ON COLUMN public.t_lark_user.lu_union_id IS '用户在同一应用服务商提供的多个应用间的统一身份';
COMMENT ON COLUMN public.t_lark_user.created_by IS '创建人';
COMMENT ON COLUMN public.t_lark_user.created_at IS '创建时间';
COMMENT ON COLUMN public.t_lark_user.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_lark_user.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_lark_user.id IS '主键 ID';

-- public.t_organization definition

CREATE TABLE IF NOT EXISTS public.t_organization (
	org_tenant_id int8 NULL, -- 租户 ID
	org_parent_id int8 NULL, -- 父机构 ID
	org_name varchar NULL, -- 机构名称
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_organization_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_organization.org_tenant_id IS '租户 ID';
COMMENT ON COLUMN public.t_organization.org_parent_id IS '父机构 ID';
COMMENT ON COLUMN public.t_organization.org_name IS '机构名称';
COMMENT ON COLUMN public.t_organization.created_by IS '创建人';
COMMENT ON COLUMN public.t_organization.created_at IS '创建时间';
COMMENT ON COLUMN public.t_organization.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_organization.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_organization.id IS '主键 ID';

-- public.t_organization_user definition

CREATE TABLE IF NOT EXISTS public.t_organization_user (
	ou_organization_id int8 NULL, -- 机构 ID
	ou_user_id int8 NULL, -- 用户 ID
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_organization_user_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_organization_user.ou_organization_id IS '机构 ID';
COMMENT ON COLUMN public.t_organization_user.ou_user_id IS '用户 ID';
COMMENT ON COLUMN public.t_organization_user.created_by IS '创建人';
COMMENT ON COLUMN public.t_organization_user.created_at IS '创建时间';
COMMENT ON COLUMN public.t_organization_user.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_organization_user.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_organization_user.id IS '主键 ID';

-- public.t_tenant definition

CREATE TABLE IF NOT EXISTS public.t_tenant (
	ten_name varchar NULL, -- 租户名称
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_tenant_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_tenant.ten_name IS '租户名称';
COMMENT ON COLUMN public.t_tenant.created_by IS '创建人';
COMMENT ON COLUMN public.t_tenant.created_at IS '创建时间';
COMMENT ON COLUMN public.t_tenant.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_tenant.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_tenant.id IS '主键 ID';

-- public.t_tenant_user definition

CREATE TABLE IF NOT EXISTS public.t_tenant_user (
	tu_tenant_id int8 NULL, -- 租户 ID
	tu_user_id int8 NULL, -- 平台用户 ID
	tu_tenant_user_id varchar NULL, -- 用户在租户中的唯一标识
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_tenant_user_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_tenant_user.tu_tenant_id IS '租户 ID';
COMMENT ON COLUMN public.t_tenant_user.tu_user_id IS '平台用户 ID';
COMMENT ON COLUMN public.t_tenant_user.tu_tenant_user_id IS '用户在租户中的唯一标识';
COMMENT ON COLUMN public.t_tenant_user.created_by IS '创建人';
COMMENT ON COLUMN public.t_tenant_user.created_at IS '创建时间';
COMMENT ON COLUMN public.t_tenant_user.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_tenant_user.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_tenant_user.id IS '主键 ID';

-- public.t_permission definition

CREATE TABLE IF NOT EXISTS public.t_permission (
	per_system_id int8 NULL, -- 系统 ID
	per_organization_id int8 NULL, -- 机构 ID
	per_auth_namespace_id int8 NULL, -- 权限命名空间 ID
	per_name varchar NULL, -- 权限名
	per_description varchar NULL, -- 权限描述
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_permission_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_permission.per_system_id IS '系统 ID';
COMMENT ON COLUMN public.t_permission.per_organization_id IS '机构 ID';
COMMENT ON COLUMN public.t_permission.per_auth_namespace_id IS '权限命名空间 ID';
COMMENT ON COLUMN public.t_permission.per_name IS '权限名';
COMMENT ON COLUMN public.t_permission.per_description IS '权限描述';
COMMENT ON COLUMN public.t_permission.created_by IS '创建人';
COMMENT ON COLUMN public.t_permission.created_at IS '创建时间';
COMMENT ON COLUMN public.t_permission.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_permission.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_permission.id IS '主键 ID';

-- public.t_role definition

CREATE TABLE IF NOT EXISTS public.t_role (
	rol_system_id int8 NULL, -- 系统 ID
	rol_organization_id int8 NULL, -- 机构 ID
	rol_auth_namespace_id int8 NULL, -- 权限命名空间 ID
	rol_name varchar NULL, -- 角色名
	rol_description varchar NULL, -- 角色描述
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_role_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_role.rol_system_id IS '系统 ID';
COMMENT ON COLUMN public.t_role.rol_organization_id IS '机构 ID';
COMMENT ON COLUMN public.t_role.rol_auth_namespace_id IS '权限命名空间 ID';
COMMENT ON COLUMN public.t_role.rol_name IS '角色名';
COMMENT ON COLUMN public.t_role.rol_description IS '角色描述';
COMMENT ON COLUMN public.t_role.created_by IS '创建人';
COMMENT ON COLUMN public.t_role.created_at IS '创建时间';
COMMENT ON COLUMN public.t_role.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_role.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_role.id IS '主键 ID';

-- public.t_role_permission definition

CREATE TABLE IF NOT EXISTS public.t_role_permission (
	rp_role_id int8 NULL, -- 角色 ID
	rp_permission_id int8 NULL, -- 权限 ID
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_role_permission_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_role_permission.rp_role_id IS '角色 ID';
COMMENT ON COLUMN public.t_role_permission.rp_permission_id IS '权限 ID';
COMMENT ON COLUMN public.t_role_permission.created_by IS '创建人';
COMMENT ON COLUMN public.t_role_permission.created_at IS '创建时间';
COMMENT ON COLUMN public.t_role_permission.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_role_permission.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_role_permission.id IS '主键 ID';

-- public.t_application definition

CREATE TABLE IF NOT EXISTS public.t_application (
	app_system_id int8 NULL, -- 所属系统 ID
	app_name varchar NULL, -- 应用名称
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_application_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_application.app_system_id IS '所属系统 ID';
COMMENT ON COLUMN public.t_application.app_name IS '应用名称';
COMMENT ON COLUMN public.t_application.created_by IS '创建人';
COMMENT ON COLUMN public.t_application.created_at IS '创建时间';
COMMENT ON COLUMN public.t_application.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_application.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_application.id IS '主键 ID';

-- public.t_system definition

CREATE TABLE IF NOT EXISTS public.t_system (
	sys_tenant_id int8 NULL, -- 所属租户 ID
	sys_access_scope text NULL, -- 什么范围的用户可以访问这个系统，空表示所有用户都能访问
	sys_client_id varchar NULL, -- 系统绑定的 OAuth2 客户端 ID，允许暂不绑定，因此可以为空
	sys_name varchar NULL, -- 系统名称
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_system_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_system.sys_tenant_id IS '所属租户 ID';
COMMENT ON COLUMN public.t_system.sys_access_scope IS '什么范围的用户可以访问这个系统，空表示所有用户都能访问';
COMMENT ON COLUMN public.t_system.sys_client_id IS '系统绑定的 OAuth2 客户端 ID，允许暂不绑定，因此可以为空';
COMMENT ON COLUMN public.t_system.sys_name IS '系统名称';
COMMENT ON COLUMN public.t_system.created_by IS '创建人';
COMMENT ON COLUMN public.t_system.created_at IS '创建时间';
COMMENT ON COLUMN public.t_system.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_system.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_system.id IS '主键 ID';

-- public.t_user_system definition

CREATE TABLE IF NOT EXISTS public.t_user_system (
	us_user_id int8 NULL, -- 用户 ID
	us_system_id int8 NULL, -- 系统 ID
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_user_system_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_user_system.us_user_id IS '用户 ID';
COMMENT ON COLUMN public.t_user_system.us_system_id IS '系统 ID';
COMMENT ON COLUMN public.t_user_system.created_by IS '创建人';
COMMENT ON COLUMN public.t_user_system.created_at IS '创建时间';
COMMENT ON COLUMN public.t_user_system.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_user_system.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_user_system.id IS '主键 ID';

-- public.t_ui_module definition

CREATE TABLE IF NOT EXISTS public.t_ui_module (
	um_application_id int8 NULL, -- 应用 ID
	um_parent_id int8 NULL, -- 父模块 ID
	um_key varchar NULL, -- 对应前端路由中的 name
	um_name varchar NULL, -- 模块名称
	um_description varchar NULL, -- 模块描述
	um_uri varchar NULL, -- UI 模块对应的前端 URI，用于登录后重放
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_ui_module_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_ui_module.um_application_id IS '应用 ID';
COMMENT ON COLUMN public.t_ui_module.um_parent_id IS '父模块 ID';
COMMENT ON COLUMN public.t_ui_module.um_key IS '对应前端路由中的 name';
COMMENT ON COLUMN public.t_ui_module.um_name IS '模块名称';
COMMENT ON COLUMN public.t_ui_module.um_description IS '模块描述';
COMMENT ON COLUMN public.t_ui_module.um_uri IS 'UI 模块对应的前端 URI，用于登录后重放';
COMMENT ON COLUMN public.t_ui_module.created_by IS '创建人';
COMMENT ON COLUMN public.t_ui_module.created_at IS '创建时间';
COMMENT ON COLUMN public.t_ui_module.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_ui_module.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_ui_module.id IS '主键 ID';

-- public.t_user definition

CREATE TABLE IF NOT EXISTS public.t_user (
	u_mobile varchar NULL, -- 手机号
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_user_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_user.u_mobile IS '手机号';
COMMENT ON COLUMN public.t_user.created_by IS '创建人';
COMMENT ON COLUMN public.t_user.created_at IS '创建时间';
COMMENT ON COLUMN public.t_user.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_user.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_user.id IS '主键 ID';

-- public.t_user_permission definition

CREATE TABLE IF NOT EXISTS public.t_user_permission (
	per_system_id int8 NULL, -- 系统 ID
	per_organization_id int8 NULL, -- 机构 ID
	per_auth_namespace_id int8 NULL, -- 权限命名空间 ID
	up_user_id int8 NULL, -- 用户 ID
	up_permission_id int8 NULL, -- 权限 ID
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_user_permission_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_user_permission.per_system_id IS '系统 ID';
COMMENT ON COLUMN public.t_user_permission.per_organization_id IS '机构 ID';
COMMENT ON COLUMN public.t_user_permission.per_auth_namespace_id IS '权限命名空间 ID';
COMMENT ON COLUMN public.t_user_permission.up_user_id IS '用户 ID';
COMMENT ON COLUMN public.t_user_permission.up_permission_id IS '权限 ID';
COMMENT ON COLUMN public.t_user_permission.created_by IS '创建人';
COMMENT ON COLUMN public.t_user_permission.created_at IS '创建时间';
COMMENT ON COLUMN public.t_user_permission.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_user_permission.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_user_permission.id IS '主键 ID';

-- public.t_user_role definition

CREATE TABLE IF NOT EXISTS public.t_user_role (
	per_system_id int8 NULL, -- 系统 ID
	per_organization_id int8 NULL, -- 机构 ID
	per_auth_namespace_id int8 NULL, -- 权限命名空间 ID
	ur_user_id int8 NULL, -- 用户 ID
	ur_role_id int8 NULL, -- 角色 ID
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_user_role_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_user_role.per_system_id IS '系统 ID';
COMMENT ON COLUMN public.t_user_role.per_organization_id IS '机构 ID';
COMMENT ON COLUMN public.t_user_role.per_auth_namespace_id IS '权限命名空间 ID';
COMMENT ON COLUMN public.t_user_role.ur_user_id IS '用户 ID';
COMMENT ON COLUMN public.t_user_role.ur_role_id IS '角色 ID';
COMMENT ON COLUMN public.t_user_role.created_by IS '创建人';
COMMENT ON COLUMN public.t_user_role.created_at IS '创建时间';
COMMENT ON COLUMN public.t_user_role.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_user_role.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_user_role.id IS '主键 ID';

-- public.t_client_user definition

CREATE TABLE IF NOT EXISTS public.t_client_user (
	cu_client_id varchar NULL, -- OAuth2 客户端 ID
	cu_user_id int8 NULL, -- 平台用户 ID
	cu_open_id varchar NULL, -- 用户在这个 OAuth2 客户端中的唯一标识
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT t_client_user_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.t_client_user.cu_client_id IS 'OAuth2 客户端 ID';
COMMENT ON COLUMN public.t_client_user.cu_user_id IS '平台用户 ID';
COMMENT ON COLUMN public.t_client_user.cu_open_id IS '用户在这个 OAuth2 客户端中的唯一标识';
COMMENT ON COLUMN public.t_client_user.created_by IS '创建人';
COMMENT ON COLUMN public.t_client_user.created_at IS '创建时间';
COMMENT ON COLUMN public.t_client_user.updated_by IS '最后更新人';
COMMENT ON COLUMN public.t_client_user.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.t_client_user.id IS '主键 ID';

-- public.oauth2_registered_client definition

CREATE TABLE IF NOT EXISTS public.oauth2_registered_client (
	orc_tenant_id int8 NULL, -- 租户 ID
	registered_client_id varchar NULL,
	client_id varchar NULL,
	client_id_issued_at text NULL,
	client_secret varchar NULL,
	client_secret_expires_at text NULL,
	client_name varchar NULL,
	client_authentication_methods text NULL,
	authorization_grant_types text NULL,
	redirect_uris text NULL,
	post_logout_redirect_uris text NULL,
	scopes text NULL,
	client_settings text NULL,
	token_settings text NULL,
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT oauth2_registered_client_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.oauth2_registered_client.orc_tenant_id IS '租户 ID';
COMMENT ON COLUMN public.oauth2_registered_client.created_by IS '创建人';
COMMENT ON COLUMN public.oauth2_registered_client.created_at IS '创建时间';
COMMENT ON COLUMN public.oauth2_registered_client.updated_by IS '最后更新人';
COMMENT ON COLUMN public.oauth2_registered_client.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.oauth2_registered_client.id IS '主键 ID';

-- public.oauth2_client_registration definition

CREATE TABLE IF NOT EXISTS public.oauth2_client_registration (
	registration_id varchar NULL,
	client_id varchar NULL,
	client_secret varchar NULL,
	authorization_grant_type text NULL,
	client_name varchar NULL,
	redirect_uri varchar NULL,
	scopes text NULL,
	client_authentication_method text NULL,
	authorization_uri varchar NULL,
	token_uri varchar NULL,
	user_info_uri varchar NULL, -- 用户信息 URI
	user_info_authentication_method text NULL, -- 用户信息鉴权方法
	user_name_attribute_name varchar NULL,
	jwk_set_uri varchar NULL,
	issuer_uri varchar NULL,
	configuration_metadata text NULL,
	from_issuer_location boolean NULL,
	created_by varchar NULL, -- 创建人
	created_at timestamp DEFAULT now() NOT NULL, -- 创建时间
	updated_by varchar NULL, -- 最后更新人
	updated_at timestamp DEFAULT now() NOT NULL, -- 最后更新时间
	id bigserial NOT NULL, -- 主键 ID
	CONSTRAINT oauth2_client_registration_pk PRIMARY KEY (id)
);

COMMENT ON COLUMN public.oauth2_client_registration.user_info_uri IS '用户信息 URI';
COMMENT ON COLUMN public.oauth2_client_registration.user_info_authentication_method IS '用户信息鉴权方法';
COMMENT ON COLUMN public.oauth2_client_registration.created_by IS '创建人';
COMMENT ON COLUMN public.oauth2_client_registration.created_at IS '创建时间';
COMMENT ON COLUMN public.oauth2_client_registration.updated_by IS '最后更新人';
COMMENT ON COLUMN public.oauth2_client_registration.updated_at IS '最后更新时间';
COMMENT ON COLUMN public.oauth2_client_registration.id IS '主键 ID';

