-- EXTENSIONS
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- SEQUENCES
CREATE SEQUENCE COMPANY_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE BRANCH_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE DEPARTMENT_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE OCCUPATION_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE EMAIL_SEND_EVENT_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE EMAIL_SEND_RECIPIENT_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE EMAIL_EVENT_DATA_SEQ START WITH 1 INCREMENT BY 1;

-- DDL
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
    "password_expired" boolean NOT NULL DEFAULT false,
    "created_date" timestamp,
    "last_modified_date" timestamp,
    "created_by" varchar(75),
    "last_modified_by" varchar(75),
    "staff_id" uuid NULL,
    CONSTRAINT "ACCOUNT_ACCOUNT_EMAIL_UQ" UNIQUE ("email"),
    CONSTRAINT "ACCOUNT_ACCOUNT_USERNAME_UQ" UNIQUE ("username"),
    CONSTRAINT "ACCOUNT_STAFF_UQ" UNIQUE ("staff_id"),
    CONSTRAINT "ACCOUNT_PK" PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "role"
(
    "id" bigint NOT NULL,
    "role" varchar(75) NOT NULL,
    CONSTRAINT "ROLE_PK" PRIMARY KEY ("id"),
    CONSTRAINT "ROLE_ROLE_NAME_UQ" UNIQUE ("role")
);

CREATE TABLE IF NOT EXISTS "account_role"
(
    "user_id" uuid NOT NULL,
    "role_id" bigint NOT NULL,
    CONSTRAINT "ACCOUNT_ROLE_ACCOUNT_ROLE_FK" FOREIGN KEY (user_id) REFERENCES account (id) ON DELETE CASCADE,
    CONSTRAINT "ACCOUNT_ROLE_ROLE_ACCOUNT_FK" FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "company" (
    "id" bigint DEFAULT nextval('COMPANY_SEQ') NOT NULL,
    "name" varchar(45) NOT NULL,
    "document" varchar(45),
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "COMPANY_PK" PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "branch" (
    "id" bigint DEFAULT nextval('BRANCH_SEQ') NOT NULL,
    "name" varchar(45) NOT NULL,
    "company_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "BRANCH_PK" PRIMARY KEY ("id"),
    CONSTRAINT "BRANCH_COMPANY_FK" FOREIGN KEY (company_id) REFERENCES company(id)
);


CREATE TABLE IF NOT EXISTS "department" (
    "id" bigint DEFAULT nextval('DEPARTMENT_SEQ') NOT NULL,
    "name" varchar(45) NOT NULL,
    "branch_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "DEPARTMENT_PK" PRIMARY KEY ("id"),
    CONSTRAINT "DEPARTMENT_BRANCH_FK" FOREIGN KEY (branch_id) REFERENCES branch(id)
);

CREATE TABLE IF NOT EXISTS "occupation" (
    "id" bigint DEFAULT nextval('OCCUPATION_SEQ') NOT NULL ,
    "name" varchar(45) NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "OCCUPATION_PK" PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "staff" (
    "id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "full_name" varchar(75) NOT NULL,
    "email" varchar(45) NOT NULL,
    "department_id" bigint NOT NULL,
    "occupation_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "STAFF_EMAIL_UQ" UNIQUE (email),
    CONSTRAINT "STAFF_DEPARTMENT_FK" FOREIGN KEY (department_id) REFERENCES department(id),
    CONSTRAINT "STAFF_OCCUPATION_FK" FOREIGN KEY (occupation_id) REFERENCES occupation(id),
    CONSTRAINT "STAFF_PK" PRIMARY KEY (id)
);

ALTER TABLE "account" ADD FOREIGN KEY ("staff_id") REFERENCES "staff" ("id") ON DELETE SET NULL ON UPDATE SET NULL;

CREATE TABLE IF NOT EXISTS "email_send_event" (
    "id" bigint DEFAULT nextval('EMAIL_SEND_EVENT_SEQ') NOT NULL ,
    "template" varchar(45) NOT NULL,
    "subject" varchar(75) NOT NULL,
    "event_date" timestamp NOT NULL,
    "send_date" timestamp,
    "sent" boolean NOT NULL DEFAULT false,
    CONSTRAINT "EMAIL_SEND_EVENT_PK" PRIMARY KEY ("id")
);

CREATE TABLE IF NOT EXISTS "email_send_recipient" (
    "id" bigint DEFAULT nextval('EMAIL_SEND_RECIPIENT_SEQ') NOT NULL ,
    "send_event_id" bigint NOT NULL,
    "recipient" varchar(75) NOT NULL,
    CONSTRAINT "EMAIL_SEND_RECIPIENT_PK" PRIMARY KEY ("id"),
    CONSTRAINT "SEND_RECIPIENT_SEND_EVENT_FK" FOREIGN KEY (send_event_id) REFERENCES email_send_event(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "email_event_data" (
    "id" bigint DEFAULT nextval('EMAIL_EVENT_DATA_SEQ') NOT NULL,
    "send_event_id" bigint NOT NULL,
    "variable_name" varchar(75) NOT NULL,
    "variable_value" varchar(75) NOT NULL,
    CONSTRAINT "EMAIL_EVENT_DATA_PK" PRIMARY KEY ("id"),
    CONSTRAINT "EVENT_DATA_SEND_EVENT_FK" FOREIGN KEY (send_event_id) REFERENCES email_send_event(id) ON DELETE CASCADE
);

-- DML
INSERT INTO "account" ("full_name", "avatar", "email", "username", "password", "last_login_date", "join_date", "active",
                       "non_locked") VALUES ('Administrator', '', 'admin@itflow.com', 'admin', '$2a$10$MNsNLmxb1HnkGgO56021eu.Er4omFxesT0CEm.FH2kKWGkLQNPpPC', NULL, now(), '1', '1');
INSERT INTO "role" ("id", "role") VALUES ('1', 'ADMIN');
INSERT INTO "role" ("id", "role") VALUES ('2', 'MANAGER');
INSERT INTO "role" ("id", "role") VALUES ('3', 'COORDINATOR');
INSERT INTO "role" ("id", "role") VALUES ('4', 'INFRA');
INSERT INTO "role" ("id", "role") VALUES ('5', 'DEVOPS');
INSERT INTO "role" ("id", "role") VALUES ('6', 'HELPDESK');
INSERT INTO "role" ("id", "role") VALUES ('7', 'SUPPORT');
INSERT INTO "account_role" ("user_id", "role_id") VALUES ((SELECT id FROM account LIMIT 1), '1');
INSERT INTO "company" ("name", "document") VALUES ('IT FLOW', '000000000');
INSERT INTO "branch" ("name", "company_id") VALUES ('TECHNOLOGY', '1');
INSERT INTO "department" ("name", "branch_id") VALUES ('IT', '1');
INSERT INTO "occupation" ("name") VALUES ('Manager');
