CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "account"
(
    "id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "full_name" varchar(75) NOT NULL,
    "avatar" varchar(75) NULL,
    "email" varchar(45) NOT NULL,
    "username" varchar(25) NOT NULL,
    "password" varchar(197) NOT NULL,
    "last_login_date" timestamp,
    "join_date"  timestamp NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    "non_locked" boolean NOT NULL DEFAULT true,
    "created_date" timestamp,
    "last_modified_date" timestamp,
    "created_by" varchar(75),
    "last_modified_by" varchar(75),
    "staff_id" uuid NULL,
    CONSTRAINT "uq_account_email" UNIQUE ("email"),
    CONSTRAINT "uq_account_username" UNIQUE ("username"),
    CONSTRAINT "uq_staff_id" UNIQUE ("staff_id"),
    CONSTRAINT "account_pk" PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "role"
(
    "id" bigint NOT NULL,
    "role" varchar(75) NOT NULL,
    CONSTRAINT "role_pk" PRIMARY KEY ("id"),
    CONSTRAINT "uq_role_name" UNIQUE ("role")
);

CREATE TABLE IF NOT EXISTS "account_role"
(
    "user_id" uuid NOT NULL,
    "role_id" bigint NOT NULL,
    CONSTRAINT "fk_account_role" FOREIGN KEY (user_id) REFERENCES account (id) ON DELETE CASCADE,
    CONSTRAINT "fk_role_account" FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);


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


-- sequences
CREATE SEQUENCE company_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999999 START 1 CACHE 1;
CREATE SEQUENCE branch_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999999 START 1 CACHE 1;
CREATE SEQUENCE department_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999999 START 1 CACHE 1;

-- tables
CREATE TABLE IF NOT EXISTS "company" (
    "id" bigint DEFAULT nextval('company_id_seq') NOT NULL,
    "name" varchar(45) NOT NULL,
    "document" varchar(45),
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "company_pk" PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "branch" (
    "id" bigint DEFAULT nextval('branch_id_seq') NOT NULL,
    "name" varchar(45) NOT NULL,
    "company_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "branch_pk" PRIMARY KEY ("id"),
    CONSTRAINT "company_branch_fk" FOREIGN KEY (company_id) REFERENCES company(id)
);


CREATE TABLE IF NOT EXISTS "department" (
    "id" bigint DEFAULT nextval('department_id_seq') NOT NULL,
    "name" varchar(45) NOT NULL,
    "branch_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "department_pk" PRIMARY KEY ("id"),
    CONSTRAINT "branch_fk" FOREIGN KEY (branch_id) REFERENCES branch(id)
);


-- sequences
CREATE SEQUENCE occupation_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 99999999999 START 1 CACHE 1;

-- TABLES
CREATE TABLE IF NOT EXISTS "occupation" (
    "id" bigint DEFAULT nextval('occupation_id_seq') NOT NULL ,
    "name" varchar(45) NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "occupation_pk" PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "staff" (
    "id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "full_name" varchar(75) NOT NULL,
    "email" varchar(45) NOT NULL,
    "department_id" bigint NOT NULL,
    "occupation_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "entity_email_uq" UNIQUE (email),
    CONSTRAINT "department_fk" FOREIGN KEY (department_id) REFERENCES department(id),
    CONSTRAINT "occupation_id_fk" FOREIGN KEY (occupation_id) REFERENCES occupation(id),
    CONSTRAINT "entity_pk" PRIMARY KEY (id)
);

ALTER TABLE "account" ADD FOREIGN KEY ("staff_id") REFERENCES "staff" ("id") ON DELETE SET NULL ON UPDATE SET NULL;

INSERT INTO "company" ("name", "document") VALUES ('IT FLOW', '0000000000');
INSERT INTO "branch" ("name", "company_id") VALUES ('MATRIZ', '1');
INSERT INTO "department" ("name", "branch_id") VALUES ('IT', '1');
INSERT INTO "occupation" ("name") VALUES ('Manager');