--liquibase formatted sql

--changeset herronrb:admin_6

create table tbl_audit_log (
	date_ timestamp default current_timestamp,
	type_ text not null,
	table_ text not null,
	old_data json,
	new_data json
);

create or replace function audit_log_trigger() returns trigger language 'plpgsql' as '
	begin
		if (tg_op = ''INSERT'') then
			insert into tbl_audit_log(type_, table_, old_data, new_data) values (''insert'', tg_table_name, null, row_to_json(new));
		elsif (tg_op = ''UPDATE'') then
			insert into tbl_audit_log(type_, table_, old_data, new_data) values (''update'', tg_table_name, row_to_json(old), row_to_json(new));
		elsif (tg_op = ''DELETE'') then
			insert into tbl_audit_log(type_, table_, old_data, new_data) values (''delete'', tg_table_name, row_to_json(old), null);
		end if;
		return new;
	end;
';

alter table tbl_announcements add user_modified text, add date_modified timestamp default current_timestamp;
alter table tbl_indicators add user_modified text, add date_modified timestamp default current_timestamp;
alter table tbl_filter_options add user_modified text, add date_modified timestamp default current_timestamp;
alter table tbl_sources add user_modified text, add date_modified timestamp default current_timestamp;
alter table tbl_faqs add user_modified text, add date_modified timestamp default current_timestamp;

create trigger trigger_tbl_announcements_audit after insert or update or delete on tbl_announcements for each row execute function audit_log_trigger();
create trigger trigger_tbl_indicators_audit after insert or update or delete on tbl_indicators for each row execute function audit_log_trigger();
create trigger trigger_tbl_filter_options_audit after insert or update or delete on tbl_filter_options for each row execute function audit_log_trigger();
create trigger trigger_tbl_sources_audit after insert or update or delete on tbl_sources for each row execute function audit_log_trigger();
create trigger trigger_tbl_faqs_audit after insert or update or delete on tbl_faqs for each row execute function audit_log_trigger();