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

-- DDL
CREATE TABLE "ACCOUNT"
(
    "ID" UUID DEFAULT RANDOM_UUID() NOT NULL,
    "FULL_NAME" VARCHAR(75) NOT NULL,
    "AVATAR" VARCHAR(75) NULL,
    "EMAIL" VARCHAR(45) NOT NULL,
    "USERNAME" VARCHAR(25) NOT NULL,
    "PASSWORD" VARCHAR(197) NOT NULL,
    "LAST_LOGIN_DATE" TIMESTAMP,
    "JOIN_DATE"  TIMESTAMP NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    "NON_LOCKED" BOOLEAN NOT NULL DEFAULT TRUE,
    "PASSWORD_EXPIRED" BOOLEAN NOT NULL DEFAULT FALSE,
    "CREATED_DATE" TIMESTAMP,
    "LAST_MODIFIED_DATE" TIMESTAMP,
    "CREATED_BY" VARCHAR(75),
    "LAST_MODIFIED_BY" VARCHAR(75),
    "STAFF_ID" UUID NULL,
    CONSTRAINT "ACCOUNT_ACCOUNT_USERNAME_UQ" UNIQUE ("USERNAME"),
    CONSTRAINT "ACCOUNT_STAFF_UQ" UNIQUE ("STAFF_ID"),
    CONSTRAINT "ACCOUNT_PK" PRIMARY KEY ("ID")
);

CREATE TABLE "ROLE"
(
    "ID" BIGINT NOT NULL,
    "ROLE" VARCHAR(75) NOT NULL,
    CONSTRAINT "ROLE_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "ROLE_ROLE_NAME_UQ" UNIQUE ("ROLE")
);

CREATE TABLE "ACCOUNT_ROLE"
(
    "USER_ID" UUID NOT NULL,
    "ROLE_ID" BIGINT NOT NULL,
    CONSTRAINT "ACCOUNT_ROLE_ACCOUNT_ROLE_FK" FOREIGN KEY (USER_ID) REFERENCES ACCOUNT (ID) ON DELETE CASCADE,
    CONSTRAINT "ACCOUNT_ROLE_ROLE_ACCOUNT_FK" FOREIGN KEY (ROLE_ID) REFERENCES ROLE (ID) ON DELETE CASCADE
);

CREATE TABLE "COMPANY" (
    "ID" BIGINT DEFAULT NEXTVAL('COMPANY_SEQ') NOT NULL,
    "NAME" VARCHAR(45) NOT NULL,
    "DOCUMENT" VARCHAR(45),
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT "COMPANY_PK" PRIMARY KEY ("ID")
);

CREATE TABLE "BRANCH" (
    "ID" BIGINT DEFAULT NEXTVAL('BRANCH_SEQ') NOT NULL,
    "NAME" VARCHAR(45) NOT NULL,
    "COMPANY_ID" BIGINT NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT "BRANCH_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "BRANCH_COMPANY_FK" FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)
);


CREATE TABLE "DEPARTMENT" (
    "ID" BIGINT DEFAULT NEXTVAL('DEPARTMENT_SEQ') NOT NULL,
    "NAME" VARCHAR(45) NOT NULL,
    "BRANCH_ID" BIGINT NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT "DEPARTMENT_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "DEPARTMENT_BRANCH_FK" FOREIGN KEY (BRANCH_ID) REFERENCES BRANCH(ID)
);

CREATE TABLE "OCCUPATION" (
    "ID" BIGINT DEFAULT NEXTVAL('OCCUPATION_SEQ') NOT NULL ,
    "NAME" VARCHAR(45) NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT "OCCUPATION_PK" PRIMARY KEY ("ID")
);

CREATE TABLE "STAFF" (
    "ID" UUID DEFAULT RANDOM_UUID() NOT NULL,
    "FULL_NAME" VARCHAR(75) NOT NULL,
    "EMAIL" VARCHAR(45) NOT NULL,
    "DEPARTMENT_ID" BIGINT NOT NULL,
    "OCCUPATION_ID" BIGINT NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT "STAFF_DEPARTMENT_FK" FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(ID),
    CONSTRAINT "STAFF_OCCUPATION_FK" FOREIGN KEY (OCCUPATION_ID) REFERENCES OCCUPATION(ID),
    CONSTRAINT "STAFF_PK" PRIMARY KEY (ID)
);

ALTER TABLE "ACCOUNT" ADD FOREIGN KEY ("STAFF_ID") REFERENCES "STAFF" ("ID") ON DELETE SET NULL ON UPDATE SET NULL;

CREATE TABLE "EMAIL_SEND_EVENT" (
    "ID" BIGINT DEFAULT NEXTVAL('EMAIL_SEND_EVENT_SEQ') NOT NULL ,
    "TEMPLATE" VARCHAR(45) NOT NULL,
    "SUBJECT" VARCHAR(75) NOT NULL,
    "EVENT_DATE" TIMESTAMP NOT NULL,
    "SEND_DATE" TIMESTAMP,
    "SENT" BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT "EMAIL_SEND_EVENT_PK" PRIMARY KEY ("ID")
);

CREATE TABLE "EMAIL_SEND_RECIPIENT" (
    "ID" BIGINT DEFAULT NEXTVAL('EMAIL_SEND_RECIPIENT_SEQ') NOT NULL ,
    "SEND_EVENT_ID" BIGINT NOT NULL,
    "RECIPIENT" VARCHAR(75) NOT NULL,
    CONSTRAINT "EMAIL_SEND_RECIPIENT_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "SEND_RECIPIENT_SEND_EVENT_FK" FOREIGN KEY (SEND_EVENT_ID) REFERENCES EMAIL_SEND_EVENT (ID) ON DELETE CASCADE
);

CREATE TABLE "EMAIL_EVENT_DATA" (
    "ID" BIGINT DEFAULT NEXTVAL('EMAIL_EVENT_DATA_SEQ') NOT NULL,
    "SEND_EVENT_ID" BIGINT NOT NULL,
    "VARIABLE_NAME" VARCHAR(75) NOT NULL,
    "VARIABLE_VALUE" VARCHAR(75) NOT NULL,
    CONSTRAINT "EMAIL_EVENT_DATA_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "EVENT_DATA_SEND_EVENT_FK" FOREIGN KEY (SEND_EVENT_ID) REFERENCES EMAIL_SEND_EVENT (ID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "DEVICE_CATEGORY" (
    "ID" BIGINT DEFAULT NEXTVAL('DEVICE_CATEGORY_SEQ') NOT NULL,
    "NAME" VARCHAR(75) NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT "DEVICE_CATEGORY_PK" PRIMARY KEY ("ID")
);

CREATE TABLE IF NOT EXISTS "DEVICE" (
    "ID" BIGINT DEFAULT NEXTVAL('DEVICE_SEQ') NOT NULL,
    "CODE" VARCHAR(45),
    "TAG" VARCHAR(45),
    "SERIAL_NUMBER" VARCHAR(45),
    "DESCRIPTION" TEXT,
    "HOSTNAME" VARCHAR(75) NOT NULL,
    "DEVICE_CATEGORY_ID" BIGINT NOT NULL,
    "DEPARTMENT_ID" BIGINT NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    "CREATED_DATE" TIMESTAMP,
    "LAST_MODIFIED_DATE" TIMESTAMP,
    "CREATED_BY" VARCHAR(75),
    "LAST_MODIFIED_BY" VARCHAR(75),
    CONSTRAINT "DEVICE_PK" PRIMARY KEY ("ID"),
    CONSTRAINT "DEVICE_CATEGORY_DEVICE_FK" FOREIGN KEY (DEVICE_CATEGORY_ID) REFERENCES DEVICE_CATEGORY(ID),
    CONSTRAINT "DEVICE_DEPARTMENT_DEVICE_FK" FOREIGN KEY (DEPARTMENT_ID) REFERENCES DEPARTMENT(ID)
);

CREATE TABLE IF NOT EXISTS "DEVICE_STAFF" (
    "DEVICE_ID" bigint NOT NULL,
    "STAFF_ID" uuid NOT NULL,
    "LOGIN" varchar(45),
    "CREATED_DATE" TIMESTAMP,
    "LAST_MODIFIED_DATE" TIMESTAMP,
    "CREATED_BY" VARCHAR(75),
    "LAST_MODIFIED_BY" VARCHAR(75),
    CONSTRAINT "DEVICE_USER_PK" PRIMARY KEY (device_id),
    CONSTRAINT "DEVICE_STAFF_DEVICE_FK" FOREIGN KEY (device_id) REFERENCES device(id),
    CONSTRAINT "DEVICE_STAFF_STAFF_DEVICE_FK" FOREIGN KEY (staff_id) REFERENCES staff(id)
);

CREATE TABLE IF NOT EXISTS "DEVICE_COMPUTER_CATEG" (
    "ID" BIGINT DEFAULT NEXTVAL('DEVICE_COMPUTER_CATEGORY_SEQ') NOT NULL,
    "NAME" VARCHAR(45) NOT NULL,
    "ACTIVE" BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT "DEVICE_COMPUTER_CATEGORY_PK" PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS "DEVICE_COMPUTER" (
    "DEVICE_ID" BIGINT NOT NULL,
    "COMPUTER_CATEG_ID" BIGINT NOT NULL,
    "DESCRIPTION" VARCHAR(75),
    "VIRTUAL" BOOLEAN NOT NULL DEFAULT TRUE,
    "CREATED_DATE" TIMESTAMP,
    "LAST_MODIFIED_DATE" TIMESTAMP,
    "CREATED_BY" VARCHAR(75),
    "LAST_MODIFIED_BY" VARCHAR(75),
    CONSTRAINT "DEVICE_COMPUTER_PK" PRIMARY KEY (DEVICE_ID),
    CONSTRAINT "DEVICE_COMPUTER_DEVICE_FK" FOREIGN KEY (DEVICE_ID) REFERENCES DEVICE(ID),
    CONSTRAINT "DEVICE_COMPUTER_CATEG_FK" FOREIGN KEY (COMPUTER_CATEG_ID) REFERENCES DEVICE(ID)
);
