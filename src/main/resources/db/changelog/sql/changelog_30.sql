--liquibase formatted sql

--changeset herronrb:admin_1

create table if not exists tbl_admin_users (
	email text primary key
);

create or replace function add_admin_user(email text) returns void
language sql
as '
	insert into tbl_admin_users (email) values (email);
';

create or replace function remove_admin_user(email text) returns void
language sql
as '
	delete from tbl_admin_users where email = email;
';
