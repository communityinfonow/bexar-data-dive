package org.cinow.omh.announcements;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class AnnouncementRepositoryPostgresql implements AnnouncementRepository {

	/**
	 * The JDBC template.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * The named parameter JDBC template.
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Announcement> findDisplayed() {
		String sql = ""
			+ " select id_, title_en, title_es, message_en, message_es, date_, display "
			+ " from tbl_announcements "
			+ " where display = true "
			+ " order by date_ desc ";

		return this.jdbcTemplate.query(sql, this.rowMapper());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Announcement> findAll() {
		String sql = ""
			+ " select id_, title_en, title_es, message_en, message_es, date_, display "
			+ " from tbl_announcements "
			+ " order by date_ desc ";

		return this.jdbcTemplate.query(sql, this.rowMapper());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Announcement announcement) {
		String sql = ""
			+ " insert into tbl_announcements (title_en, title_es, message_en, message_es, date_, display) "
			+ " values (:title_en, :title_es, :message_en, :message_es, :date, :display) ";

		this.namedParameterJdbcTemplate.update(sql, this.paramMap(announcement));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Announcement announcement) {
		String sql = ""
			+ " update tbl_announcements set "
			+ "   title_en = :title_en, "
			+ "   title_es = :title_es, "
			+ "   message_en = :message_en, "
			+ "   message_es = :message_es, "
			+ "   date_ = :date, "
			+ "   display = :display "
			+ " where id_ = :id ";
		
		this.namedParameterJdbcTemplate.update(sql, this.paramMap(announcement));
	}

	/**
	 * Builds a parameter rowmapper.
	 * 
	 * @return the rowmapper
	 */
	private RowMapper<Announcement> rowMapper() {
		return new RowMapper<Announcement>() {
			@Override
			public Announcement mapRow(ResultSet rs, int rowNum) throws SQLException {
				Announcement announcement = new Announcement();
				announcement.setId(rs.getInt("id_"));
				announcement.setTitle_en(rs.getString("title_en"));
				announcement.setTitle_es(rs.getString("title_es"));
				announcement.setMessage_en(rs.getString("message_en"));
				announcement.setMessage_es(rs.getString("message_es"));
				announcement.setDate(rs.getDate("date_").toLocalDate());
				announcement.setDisplay(rs.getBoolean("display"));
				
				return announcement;
			}
		};
	}

	/**
	 * Builds an announcment parameter map.
	 * 
	 * @param announcement the announcement
	 * @return the parameter map
	 */
	private MapSqlParameterSource paramMap(Announcement announcement) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		if (announcement.getId() != 0) {
			paramMap.addValue("id", announcement.getId());
		}
		paramMap.addValue("title_en", announcement.getTitle_en());
		paramMap.addValue("title_es", announcement.getTitle_es());
		paramMap.addValue("message_en", announcement.getMessage_en());
		paramMap.addValue("message_es", announcement.getMessage_es());
		paramMap.addValue("date", announcement.getDate());
		paramMap.addValue("display", announcement.isDisplay());

		return paramMap;
	}
}
