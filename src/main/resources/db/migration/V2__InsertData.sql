-- DML
-- TODO: GERAR USUARIO DE FORMA AUTOMATIZADA!!
INSERT INTO "account" ("id","full_name", "avatar", "email", "username", "password", "last_login_date", "join_date", "active",
                       "non_locked") VALUES ('84857d3f-8c6c-4acb-a7ef-52804d29941a','Administrator', '', 'admin@itflow.com', 'admin', '$2a$10$MNsNLmxb1HnkGgO56021eu.Er4omFxesT0CEm.FH2kKWGkLQNPpPC', NULL, now(), '1', '1');
INSERT INTO "role" ("id", "role") VALUES ('1', 'ADMIN');
INSERT INTO "role" ("id", "role") VALUES ('2', 'MANAGER');
INSERT INTO "role" ("id", "role") VALUES ('3', 'COORDINATOR');
INSERT INTO "role" ("id", "role") VALUES ('4', 'INFRA');
INSERT INTO "role" ("id", "role") VALUES ('5', 'DEVOPS');
INSERT INTO "role" ("id", "role") VALUES ('6', 'HELPDESK');
INSERT INTO "role" ("id", "role") VALUES ('7', 'SUPPORT');
INSERT INTO "account_role" ("user_id", "role_id") VALUES ('84857d3f-8c6c-4acb-a7ef-52804d29941a', '1');
INSERT INTO "company" ("name", "document") VALUES ('IT FLOW', '000000000');
INSERT INTO "branch" ("name", "company_id") VALUES ('TECHNOLOGY', '1');
INSERT INTO "department" ("name", "branch_id") VALUES ('IT', '1');
INSERT INTO "occupation" ("name") VALUES ('Manager');
INSERT INTO "device_category" ("name", "active") VALUES ('Computer', '1');