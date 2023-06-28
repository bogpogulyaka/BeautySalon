
DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE IF NOT EXISTS users (
    id            BIGSERIAL NOT NULL,
    name          VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    login         VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    date_created  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE IF NOT EXISTS roles (
    id 	 		BIGSERIAL NOT NULL,
    name     	VARCHAR(255) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_role CASCADE;
CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT pk_user_role PRIMARY KEY (user_id, role_id)
);

DROP TABLE IF EXISTS appointment CASCADE;
CREATE TABLE IF NOT EXISTS appointment (
    id 					BIGSERIAL NOT NULL,
    client_id 			BIGINT NOT NULL,
    employee_id 		BIGINT NOT NULL,
    start_datetime 	    TIMESTAMP NOT NULL,
    finish_datetime     TIMESTAMP NOT NULL,
    status   			VARCHAR(255) NOT NULL,
    CONSTRAINT pk_appointment PRIMARY KEY (id)
);

DROP TABLE IF EXISTS schedule_interval CASCADE;
CREATE TABLE IF NOT EXISTS schedule_interval (
    id 			        BIGSERIAL NOT NULL,
    employee_id 	    BIGINT NOT NULL,
    start_datetime 	    TIMESTAMP NOT NULL,
    finish_datetime     TIMESTAMP NOT NULL,
    CONSTRAINT pk_schedule_interval PRIMARY KEY (id)
);

DROP TABLE IF EXISTS review CASCADE;
CREATE TABLE IF NOT EXISTS review (
    id 	 		        BIGSERIAL NOT NULL,
    appointment_id 		BIGINT NOT NULL,
    content   			VARCHAR(255) NOT NULL,
    CONSTRAINT pk_review PRIMARY KEY (id)
);


-- !!!
-- CONSTRAINT UNIQUE FIELDS
-- !!!

ALTER TABLE users ADD CONSTRAINT
    uc_login UNIQUE(login);

ALTER TABLE roles ADD CONSTRAINT
    uc_roles UNIQUE(name);

ALTER TABLE user_role ADD CONSTRAINT
    uc_user_role UNIQUE(user_id, role_id);

ALTER TABLE review ADD CONSTRAINT
    uc_review_appointment UNIQUE(appointment_id);


-- !!!
-- CONSTRAINT RELATIONSHIPS
-- !!!

ALTER TABLE user_role ADD CONSTRAINT
    fk_user_role_user FOREIGN KEY (user_id)
    REFERENCES users(id)
    ON DELETE CASCADE;

ALTER TABLE user_role ADD CONSTRAINT
    fk_user_role_role FOREIGN KEY (role_id)
    REFERENCES roles(id)
    ON DELETE CASCADE;

ALTER TABLE appointment ADD CONSTRAINT
    fk_appointment_client FOREIGN KEY (client_id)
    REFERENCES users(id)
    ON DELETE CASCADE;

ALTER TABLE appointment ADD CONSTRAINT
    fk_appointment_employee FOREIGN KEY (employee_id)
    REFERENCES users(id)
    ON DELETE CASCADE;

ALTER TABLE schedule_interval ADD CONSTRAINT
    fk_schedule_interval_employee FOREIGN KEY (employee_id)
    REFERENCES users(id)
    ON DELETE CASCADE;

ALTER TABLE review ADD CONSTRAINT
    fk_review_appointment FOREIGN KEY (appointment_id)
    REFERENCES appointment(id)
    ON DELETE CASCADE;


-- !!!
-- CREATE DATA
-- !!!

INSERT INTO roles (name) VALUES('user');
INSERT INTO roles (name) VALUES('employee');
INSERT INTO roles (name) VALUES('admin');




