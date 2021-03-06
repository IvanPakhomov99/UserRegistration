-- SQL Manager Lite for PostgreSQL 5.9.5.52424
-- ---------------------------------------
-- Хост         : localhost
-- База данных  : test_db
-- Версия       : PostgreSQL 10.6, compiled by Visual C++ build 1800, 64-bit



SET check_function_bodies = false;
--
-- Structure for table user_table (OID = 33193) : 
--
SET search_path = public, pg_catalog;
CREATE TABLE public.user_table (
    user_name varchar(50) NOT NULL,
    password varchar(4) NOT NULL,
    auto_log_out integer NOT NULL
)
WITH (oids = false);
--
-- Data for table public.user_table (OID = 33193) (LIMIT 0,2)
--
INSERT INTO user_table (user_name, password, auto_log_out)
VALUES ('pakhomovivan', '0000', 10);

INSERT INTO user_table (user_name, password, auto_log_out)
VALUES ('biloyspolina', '5555', 20);

--
-- Definition for index table_pkey (OID = 33196) : 
--
ALTER TABLE ONLY user_table
    ADD CONSTRAINT table_pkey
    PRIMARY KEY (user_name);
--
-- Comments
--
COMMENT ON SCHEMA public IS 'standard public schema';
