BEGIN;

CREATE TABLE IF NOT EXISTS public.users
(
    id                  bigint                 NOT NULL,
    user_id             character varying(255) NOT NULL,
    first_name          character varying(255),
    last_name           character varying(255),
    email               character varying(255) NOT NULL,
    phone               character varying(255),
    bio                 character varying(255),
    reference_id        character varying(255),
    created_by          bigint                 NOT NULL,
    updated_by          bigint,
    created_at          timestamp(6) with time zone default CURRENT_TIMESTAMP,
    updated_at          timestamp(6) with time zone default CURRENT_TIMESTAMP,
    account_non_expired boolean                NOT NULL,
    account_non_locked  boolean                NOT NULL,
    enabled             boolean                NOT NULL,
    image_url           character varying(255),
    last_login          timestamp(6) with time zone DEFAULT CURRENT_TIMESTAMP,
    login_attempts      integer,
    mfa                 boolean                NOT NULL,
    qr_code_img_url     text                        DEFAULT NULL,
    qr_code_secret      character varying(255),
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_user_id UNIQUE (user_id),
    CONSTRAINT fk_users_created_by FOREIGN KEY (created_by) references users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_users_updated_by FOREIGN KEY (updated_by) references users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS public.credentials
(
    id           bigint                         NOT NULL,
    created_at   timestamp(6) without time zone NOT NULL,
    created_by   bigint                         NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    updated_at   timestamp(6) without time zone NOT NULL,
    updated_by   bigint,
    password     character varying(255) COLLATE pg_catalog."default",
    user_id      bigint                         NOT NULL,
    CONSTRAINT pk_credentials PRIMARY KEY (id),
    CONSTRAINT uq_credentials_user_id UNIQUE (user_id),
    CONSTRAINT fk_credentials_user_id FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_credentials_created_by FOREIGN KEY (created_by) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_credentials_updated_by FOREIGN KEY (updated_by) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS public.confirmations
(
    id           bigint                         NOT NULL,
    created_at   timestamp(6) without time zone NOT NULL,
    created_by   bigint                         NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    updated_at   timestamp(6) without time zone NOT NULL,
    updated_by   bigint,
    key          character varying(255) COLLATE pg_catalog."default",
    user_id      bigint                         NOT NULL,
    CONSTRAINT pk_confirmations PRIMARY KEY (id),
    CONSTRAINT uq_confirmations_user_id UNIQUE (user_id),
    CONSTRAINT fk_confirmation_user_id FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_confirmation_created_by FOREIGN KEY (created_by) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_confirmation_updated_by FOREIGN KEY (updated_by) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE IF NOT EXISTS public.roles
(
    id           bigint                         NOT NULL,
    created_at   timestamp(6) without time zone NOT NULL,
    created_by   bigint                         NOT NULL,
    reference_id character varying(255) COLLATE pg_catalog."default",
    updated_at   timestamp(6) without time zone NOT NULL,
    updated_by   bigint,
    authorities  smallint,
    name         character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT pk_roles PRIMARY KEY (id),
    CONSTRAINT fk_roles_created_by FOREIGN KEY (created_by) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_roles_updated_by FOREIGN KEY (updated_by) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE RESTRICT

);

CREATE TABLE IF NOT EXISTS public.user_roles
(
    id      SERIAL primary key,
    role_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT fk_user_roles_created_by FOREIGN KEY (role_id) REFERENCES roles (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_user_roles_updated_by FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE CASCADE ON DELETE RESTRICT

);

CREATE TABLE IF NOT EXISTS public.book
(
    id bigint NOT NULL,
    author character varying(255) COLLATE pg_catalog."default",
    title character varying(255) COLLATE pg_catalog."default",
    added_at timestamp(6) without time zone,
    isbn character varying(255) COLLATE pg_catalog."default",
    quantity integer,
    description text,
    release_date date,
    CONSTRAINT book_pkey PRIMARY KEY (id)
);


CREATE INDEX IF NOT EXISTS index_users_email ON users (email);
CREATE INDEX IF NOT EXISTS index_users_user_id ON users (user_id);
CREATE INDEX IF NOT EXISTS index_confirmation_user_id ON confirmations (user_id);
CREATE INDEX IF NOT EXISTS index_credential_user_id ON credentials (user_id);
CREATE INDEX IF NOT EXISTS index_user_roles_user_id ON user_roles (user_id);

END;
