CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "account"
(
    "id"              uuid DEFAULT uuid_generate_v4() NOT NULL,
    "full_name"       character varying(75)                    NOT NULL,
    "avatar"          character varying(75)                    NOT NULL,
    "email"           character varying(45)                    NOT NULL,
    "username"        character varying(25)                    NOT NULL,
    "password"        character varying(197)                   NOT NULL,
    "last_login_date" timestamp,
    "join_date"       timestamp                                NOT NULL,
    "active"          boolean                                  NOT NULL,
    "non_locked"      boolean                                  NOT NULL,
    "created_date"      timestamp,
    "last_modified_date"      timestamp,
    "created_by"          character varying(75),
    "last_modified_by"          character varying(75),
    CONSTRAINT "uq_account_email" UNIQUE ("email"),
    CONSTRAINT "uq_account_username" UNIQUE ("username"),
    CONSTRAINT "account_pk" PRIMARY KEY ("id")
) WITH (oids = false);

CREATE TABLE IF NOT EXISTS "role"
(
    "id"   bigint                NOT NULL,
    "role" character varying(75) NOT NULL,
    CONSTRAINT "role_pk" PRIMARY KEY ("id"),
    CONSTRAINT "uq_role_name" UNIQUE ("role")
) WITH (oids = false);

CREATE TABLE IF NOT EXISTS "account_role"
(
    "user_id" uuid NOT NULL,
    "role_id" bigint NOT NULL,
    CONSTRAINT "fk_account_role" FOREIGN KEY (user_id) REFERENCES account (id) ON DELETE CASCADE,
    CONSTRAINT "fk_role_account" FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
) WITH (oids = false);


INSERT INTO "account" ("full_name", "avatar", "email", "username", "password", "last_login_date", "join_date", "active",
                       "non_locked") VALUES ('Administrator', '', 'admin@example.com', 'administrator', '$2a$10$KCWuNujqzvCveAHd64oroeuFFMbCaZagcF1ktaFOzDklnPZ9bFABS', NULL, now(), '1', '1');

INSERT INTO "role" ("id", "role") VALUES ('1', 'ADMIN');
INSERT INTO "role" ("id", "role") VALUES ('2', 'MANAGER');
INSERT INTO "role" ("id", "role") VALUES ('3', 'COORDINATOR');
INSERT INTO "role" ("id", "role") VALUES ('4', 'INFRA');
INSERT INTO "role" ("id", "role") VALUES ('5', 'DEVOPS');
INSERT INTO "role" ("id", "role") VALUES ('6', 'HELPDESK');
INSERT INTO "role" ("id", "role") VALUES ('7', 'SUPPORT');
INSERT INTO "role" ("id", "role") VALUES ('8', 'TRAINEE');

INSERT INTO "account_role" ("user_id", "role_id") VALUES ((SELECT id FROM account LIMIT 1), '1');
