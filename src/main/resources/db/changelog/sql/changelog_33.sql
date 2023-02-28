--liquibase formatted sql

--changeset herronrb:admin_2

create table if not exists tbl_admin_users (
	email text primary key
);

drop function add_admin_user;
create or replace function add_admin_user(emailaddress text) returns void
language sql
as '
	insert into tbl_admin_users (email) values (emailaddress);
';

drop function remove_admin_user;
create or replace function remove_admin_user(emailaddress text) returns void
language sql
as '
	delete from tbl_admin_users where email = emailaddress;
';
