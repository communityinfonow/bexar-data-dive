package org.cinow.omh.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepositoryPostgresql implements AdminRepository {

	/**
	 * The JDBC template.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void refreshMaterializedViews() {
		String sql = " select refresh_materialized_views() ";
		this.jdbcTemplate.execute(sql);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AuditLogEntry> findAuditLogEntries() {
		String sql = ""
			+ " select date_, type_, table_, old_data, new_data "
			+ " from tbl_audit_log "
			+ " order by date_ desc ";

		return this.jdbcTemplate.query(sql, new RowMapper<AuditLogEntry>() {
			@Override
			public AuditLogEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
				AuditLogEntry entry = new AuditLogEntry();
				entry.setDate(rs.getTimestamp("date_").toLocalDateTime());
				entry.setType(rs.getString("type_"));
				entry.setTable(rs.getString("table_"));
				entry.setOldData(rs.getString("old_data"));
				entry.setNewData(rs.getString("new_data"));
				
				return entry;
			}
		});
	}

	@Override
	public List<MissedTranslation> findMissedTranslations() {
		String sql = ""
			+ " select 'tbl_announcements' as table_, row_to_json(a.*) as data_ "
			+ " from tbl_announcements a "
			+ " where (title_es is null or trim(title_es) = '' or trim(title_en) = trim(replace(title_es, '(es)', ''))) "
			+ " 	or (message_es is null or trim(message_es) = '' or trim(message_en) = trim(replace(message_es, '(es)', ''))) "
			+ " union all "
			+ " select 'tbl_faqs' as table_, row_to_json(f.*) as data_ "
			+ " from tbl_faqs f "
			+ " where (question_es is null or trim(question_es) = '' or trim(question_en) = trim(replace(question_es, '(es)', ''))) "
			+ " 	or (answer_es is null or trim(answer_es) = '' or trim(answer_en) = trim(replace(answer_es, '(es)', ''))) "
			+ " union all "
			+ " select 'tbl_filter_options' as table_, row_to_json(f.*) as data_ "
			+ " from tbl_filter_options f "
			+ " where (name_es is null or trim(name_es) = '' or trim(name_en) = trim(replace(name_es, '(es)', ''))) "
			+ " union all "
			+ " select 'tbl_filter_types' as table_, row_to_json(f.*) as data_ "
			+ " from tbl_filter_types f "
			+ " where (name_es is null or trim(name_es) = '' or trim(name_en) = trim(replace(name_es, '(es)', ''))) "
			+ " union all "
			+ " select 'tbl_indicator_categories' as table_, row_to_json(c.*) as data_ "
			+ " from tbl_indicator_categories c "
			+ " where (name_es is null or trim(name_es) = '' or trim(name_en) = trim(replace(name_es, '(es)', ''))) "
			+ " union all "
			+ " select 'tbl_indicators' as table_, row_to_json(i.*) as data_ "
			+ " from tbl_indicators i "
			+ " where (name_es is null or trim(name_es) = '' or trim(name_en) = trim(replace(name_es, '(es)', ''))) "
			+ " 	or (description_es is null or trim(description_es) = '' or trim(description_en) = trim(replace(description_es, '(es)', ''))) "
			+ " union all "
			+ " select 'tbl_location_types' as table_, row_to_json(l.*) as data_ "
			+ " from tbl_location_types l "
			+ " where (name_es is null or trim(name_es) = '' or trim(name_en) = trim(replace(name_es, '(es)', ''))) "
			+ " union all "
			+ " select 'tbl_locations' as table_, row_to_json(l.*) as data_ "
			+ " from tbl_locations l "
			+ " where (name_es is null or trim(name_es) = '' or trim(name_en) = trim(replace(name_es, '(es)', ''))) "
			+ " 	and l.location_type_id <> 4 "
			+ " union all "
			+ " select 'tbl_sources' as table_, row_to_json(s.*) as data_ "
			+ " from tbl_sources s "
			+ " where (name_es is null or trim(name_es) = '' or trim(name_en) = trim(replace(name_es, '(es)', ''))) ";

		return this.jdbcTemplate.query(sql, new RowMapper<MissedTranslation>() {
			@Override
			public MissedTranslation mapRow(ResultSet rs, int rowNum) throws SQLException {
				MissedTranslation entry = new MissedTranslation();
				entry.setTable(rs.getString("table_"));
				entry.setData(rs.getString("data_"));
				
				return entry;
			}
		});
	}
}
