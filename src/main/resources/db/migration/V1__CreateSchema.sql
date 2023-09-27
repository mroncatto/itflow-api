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
CREATE SEQUENCE DEVICE_CATEGORY_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE DEVICE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE DEVICE_COMPUTER_CATEGORY_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE COMPUTER_MEMORY_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE DEVICE_COMPUTER_MEMORY_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE COMPUTER_CPU_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE DEVICE_COMPUTER_CPU_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE COMPUTER_STORAGE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE DEVICE_COMPUTER_STORAGE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SOFTWARE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SOFTWARE_LICENSE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE LICENSE_KEY_SEQ START WITH 1 INCREMENT BY 1;

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
    CONSTRAINT "ACCOUNT_ACCOUNT_USERNAME_UQ" UNIQUE (username),
    CONSTRAINT "ACCOUNT_STAFF_UQ" UNIQUE (staff_id),
    CONSTRAINT "ACCOUNT_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "role"
(
    "id" bigint NOT NULL,
    "role" varchar(75) NOT NULL,
    CONSTRAINT "ROLE_PK" PRIMARY KEY (id),
    CONSTRAINT "ROLE_ROLE_NAME_UQ" UNIQUE (role)
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
    CONSTRAINT "COMPANY_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "branch" (
    "id" bigint DEFAULT nextval('BRANCH_SEQ') NOT NULL,
    "name" varchar(45) NOT NULL,
    "company_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "BRANCH_PK" PRIMARY KEY (id),
    CONSTRAINT "BRANCH_COMPANY_FK" FOREIGN KEY (company_id) REFERENCES company(id)
);


CREATE TABLE IF NOT EXISTS "department" (
    "id" bigint DEFAULT nextval('DEPARTMENT_SEQ') NOT NULL,
    "name" varchar(45) NOT NULL,
    "branch_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "DEPARTMENT_PK" PRIMARY KEY (id),
    CONSTRAINT "DEPARTMENT_BRANCH_FK" FOREIGN KEY (branch_id) REFERENCES branch(id)
);

CREATE TABLE IF NOT EXISTS "occupation" (
    "id" bigint DEFAULT nextval('OCCUPATION_SEQ') NOT NULL ,
    "name" varchar(45) NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "OCCUPATION_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "staff" (
    "id" uuid DEFAULT uuid_generate_v4() NOT NULL,
    "full_name" varchar(75) NOT NULL,
    "email" varchar(45) NOT NULL,
    "department_id" bigint NOT NULL,
    "occupation_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "STAFF_DEPARTMENT_FK" FOREIGN KEY (department_id) REFERENCES department(id),
    CONSTRAINT "STAFF_OCCUPATION_FK" FOREIGN KEY (occupation_id) REFERENCES occupation(id),
    CONSTRAINT "STAFF_PK" PRIMARY KEY (id)
);

ALTER TABLE "account" ADD FOREIGN KEY (staff_id) REFERENCES "staff" (id) ON DELETE SET NULL ON UPDATE SET NULL;

CREATE TABLE IF NOT EXISTS "email_send_event" (
    "id" bigint DEFAULT nextval('EMAIL_SEND_EVENT_SEQ') NOT NULL ,
    "template" varchar(45) NOT NULL,
    "subject" varchar(75) NOT NULL,
    "event_date" timestamp NOT NULL,
    "send_date" timestamp,
    "sent" boolean NOT NULL DEFAULT false,
    CONSTRAINT "EMAIL_SEND_EVENT_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "email_send_recipient" (
    "id" bigint DEFAULT nextval('EMAIL_SEND_RECIPIENT_SEQ') NOT NULL ,
    "send_event_id" bigint NOT NULL,
    "recipient" varchar(75) NOT NULL,
    CONSTRAINT "EMAIL_SEND_RECIPIENT_PK" PRIMARY KEY (id),
    CONSTRAINT "SEND_RECIPIENT_SEND_EVENT_FK" FOREIGN KEY (send_event_id) REFERENCES email_send_event(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "email_event_data" (
    "id" bigint DEFAULT nextval('EMAIL_EVENT_DATA_SEQ') NOT NULL,
    "send_event_id" bigint NOT NULL,
    "variable_name" varchar(75) NOT NULL,
    "variable_value" varchar(75) NOT NULL,
    CONSTRAINT "EMAIL_EVENT_DATA_PK" PRIMARY KEY (id),
    CONSTRAINT "EVENT_DATA_SEND_EVENT_FK" FOREIGN KEY (send_event_id) REFERENCES email_send_event(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "device_category" (
    "id" bigint DEFAULT nextval('DEVICE_CATEGORY_SEQ') NOT NULL,
    "name" varchar(75) NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    CONSTRAINT "DEVICE_CATEGORY_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "device" (
    "id" bigint DEFAULT nextval('DEVICE_SEQ') NOT NULL,
    "code" varchar(45),
    "tag" varchar(45),
    "serial_number" varchar(45),
    "description" text,
    "hostname" varchar(75) NOT NULL,
    "device_category_id" bigint NOT NULL,
    "department_id" bigint NOT NULL,
    "active" boolean NOT NULL DEFAULT true,
    "created_date" timestamp,
    "last_modified_date" timestamp,
    "created_by" varchar(75),
    "last_modified_by" varchar(75),
    CONSTRAINT "DEVICE_PK" PRIMARY KEY (id),
    CONSTRAINT "DEVICE_CATEGORY_DEVICE_FK" FOREIGN KEY (device_category_id) REFERENCES device_category(id),
    CONSTRAINT "DEVICE_DEPARTMENT_DEVICE_FK" FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE IF NOT EXISTS "device_staff" (
    "device_id" bigint NOT NULL,
    "staff_id" uuid NOT NULL,
    "login" varchar(45),
    "created_date" timestamp,
    "last_modified_date" timestamp,
    "created_by" varchar(75),
    "last_modified_by" varchar(75),
    CONSTRAINT "DEVICE_USER_PK" PRIMARY KEY (device_id),
    CONSTRAINT "DEVICE_STAFF_DEVICE_FK" FOREIGN KEY (device_id) REFERENCES device(id),
    CONSTRAINT "DEVICE_STAFF_STAFF_DEVICE_FK" FOREIGN KEY (staff_id) REFERENCES staff(id)
);

CREATE TABLE IF NOT EXISTS "computer_category" (
    "id" BIGINT DEFAULT NEXTVAL('DEVICE_COMPUTER_CATEGORY_SEQ') NOT NULL,
    "name" VARCHAR(45) NOT NULL,
    "active" boolean NOT NULL DEFAULT TRUE,
    CONSTRAINT "COMPUTER_CATEGORY_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "device_computer" (
    "device_id" BIGINT NOT NULL,
    "computer_category_id" BIGINT NOT NULL,
    "description" VARCHAR(75),
    "virtual" boolean NOT NULL DEFAULT TRUE,
    "created_date" TIMESTAMP,
    "last_modified_date" TIMESTAMP,
    "created_by" VARCHAR(75),
    "last_modified_by" VARCHAR(75),
    CONSTRAINT "DEVICE_COMPUTER_PK" PRIMARY KEY (device_id),
    CONSTRAINT "DEVICE_COMPUTER_DEVICE_FK" FOREIGN KEY (device_id) REFERENCES device(id),
    CONSTRAINT "COMPUTER_CATEGORY_FK" FOREIGN KEY (computer_category_id) REFERENCES computer_category(id)
);

CREATE TABLE IF NOT EXISTS "computer_memory" (
    "id" BIGINT DEFAULT NEXTVAL('COMPUTER_MEMORY_SEQ') NOT NULL,
    "brand_name" VARCHAR(45) NOT NULL,
    "type" VARCHAR(25) NOT NULL,
    "size" VARCHAR(25),
    "frequency" VARCHAR(25),
    "active" boolean NOT NULL DEFAULT TRUE,
    CONSTRAINT "COMPUTER_MEMORY_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "device_computer_memory" (
    "id" BIGINT DEFAULT NEXTVAL('DEVICE_COMPUTER_MEMORY_SEQ') NOT NULL,
    "device_computer_id" BIGINT NOT NULL,
    "computer_memory_id" BIGINT NOT NULL,
    "unit" INT NOT NULL DEFAULT 1,
    CONSTRAINT "DEVICE_COMPUTER_MEMORY_PK" PRIMARY KEY (device_computer_id, computer_memory_id),
    CONSTRAINT "DEVICE_COMPUTER_MEMORY_COMPUTER_MEMORY_FK" FOREIGN KEY (device_computer_id) REFERENCES device_computer(device_id),
    CONSTRAINT "DEVICE_COMPUTER_MEMORY_DEVICE_COMPUTER_FK" FOREIGN KEY (computer_memory_id) REFERENCES computer_memory(id)
);

CREATE TABLE IF NOT EXISTS "computer_cpu" (
    "id" BIGINT DEFAULT NEXTVAL('DEVICE_COMPUTER_CPU_SEQ') NOT NULL,
    "brand_name" VARCHAR(45) NOT NULL,
    "model" VARCHAR(45) NOT NULL,
    "generation" VARCHAR(25),
    "socket" VARCHAR(25) NOT NULL,
    "core" VARCHAR(25),
    "frequency" VARCHAR(25),
    "fsb" VARCHAR(25),
    "active" boolean NOT NULL DEFAULT TRUE,
    CONSTRAINT "COMPUTER_CPU_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "device_computer_cpu" (
    "device_computer_id" BIGINT NOT NULL,
    "computer_cpu_id" BIGINT NOT NULL,
    "vcpu" VARCHAR(11) NOT NULL DEFAULT 1,
    "unit" VARCHAR(11) NOT NULL DEFAULT 1,
    CONSTRAINT "DEVICE_COMPUTER_CPU_PK" PRIMARY KEY (device_computer_id, computer_cpu_id),
    CONSTRAINT "DEVICE_COMPUTER_DEVICE_COMPUTER_CPU_FK" FOREIGN KEY (device_computer_id) REFERENCES device_computer(device_id),
    CONSTRAINT "COMPUTER_CPU_DEVICE_COMPUTER_CPU_FK" FOREIGN KEY (computer_cpu_id) REFERENCES computer_cpu(id)
);

CREATE TABLE IF NOT EXISTS "computer_storage" (
    "id" BIGINT DEFAULT NEXTVAL('COMPUTER_STORAGE_SEQ') NOT NULL,
    "brand_name" VARCHAR(45) NOT NULL,
    "transfer_rate" VARCHAR(25) NOT NULL,
    "type" VARCHAR(25),
    "active" boolean NOT NULL DEFAULT TRUE,
    CONSTRAINT "COMPUTER_STORAGE_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "device_computer_storage" (
    "id" BIGINT DEFAULT NEXTVAL('DEVICE_COMPUTER_STORAGE_SEQ') NOT NULL,
    "device_computer_id" BIGINT NOT NULL,
    "computer_storage_id" BIGINT NOT NULL,
    "size" VARCHAR(25),
    CONSTRAINT "DEVICE_COMPUTER_DEVICE_COMPUTER_STORAGE_FK" FOREIGN KEY (device_computer_id) REFERENCES device_computer(device_id),
    CONSTRAINT "COMPUTER_STORAGE_DEVICE_COMPUTER_STORAGE_FK" FOREIGN KEY (computer_storage_id) REFERENCES computer_storage(id),
    CONSTRAINT "DEVICE_COMPUTER_STORAGE_PK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "software" (
    "id" BIGINT DEFAULT NEXTVAL('SOFTWARE_SEQ') NOT NULL,
    "name" VARCHAR(45) NOT NULL,
    "developer" VARCHAR(45),
    "active" boolean NOT NULL DEFAULT true,
    "created_date" TIMESTAMP,
    "last_modified_date" TIMESTAMP,
    "created_by" VARCHAR(75),
    "last_modified_by" VARCHAR(75),
    CONSTRAINT "SOFTWARE_FK" PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "software_license" (
    "id" BIGINT DEFAULT NEXTVAL('SOFTWARE_LICENSE_SEQ') NOT NULL,
    "description" VARCHAR(75) NOT NULL,
    "code" VARCHAR(45),
    "expire_at" DATE,
    "software_id" BIGINT NOT NULL,
    "active" boolean NOT NULL DEFAULT TRUE,
    "created_date" TIMESTAMP,
    "last_modified_date" TIMESTAMP,
    "created_by" VARCHAR(75),
    "last_modified_by" VARCHAR(75),
    CONSTRAINT "SOFTWARE_LICENSE_FK" PRIMARY KEY (id),
    CONSTRAINT "SOFTWARE_SOFTWARE_LICENSE_FK" FOREIGN KEY (software_id) REFERENCES software(id)
);

CREATE TABLE IF NOT EXISTS "software_license_key" (
    "id" BIGINT DEFAULT NEXTVAL('LICENSE_KEY_SEQ') NOT NULL,
    "key" VARCHAR(45),
    "software_license_id" BIGINT NOT NULL,
    "volume" INT NOT NULL DEFAULT 0,
    CONSTRAINT "LICENSE_KEY_FK" PRIMARY KEY (id),
    CONSTRAINT "SOFTWARE_LICENSE_LICENSE_KEY_FK" FOREIGN KEY (software_license_id) REFERENCES software_license(id)
);

CREATE TABLE IF NOT EXISTS "device_computer_software" (
    "device_computer_id" BIGINT NOT NULL,
    "software_id" BIGINT NOT NULL,
    "software_license_key_id" BIGINT,
    "created_date" TIMESTAMP,
    "last_modified_date" TIMESTAMP,
    "created_by" VARCHAR(75),
    "last_modified_by" VARCHAR(75),
    CONSTRAINT "DEVICE_COMPUTER_SOFTWARE_FK" PRIMARY KEY (device_computer_id, software_id),
    CONSTRAINT "DEVICE_COMPUTER_DEVICE_COMPUTER_SOFTWARE_FK" FOREIGN KEY (device_computer_id) REFERENCES device_computer(device_id),
    CONSTRAINT "SOFTWARE_DEVICE_COMPUTER_SOFTWARE_FK" FOREIGN KEY (software_id) REFERENCES software(id),
    CONSTRAINT "LICENSE_KEY_DEVICE_COMPUTER_SOFTWARE_FK" FOREIGN KEY (software_license_key_id) REFERENCES software_license_key(id)
);

