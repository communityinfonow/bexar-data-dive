package org.cinow.omh.sources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class SourceRepositoryPostgresql implements SourceRepository {

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
	public Source getSourceByIndicator(String indicatorId) {
		String sql = ""
			+ " select id_, name_en, name_es, url_, trend_interval, display "
			+ " from tbl_sources "
			+ " where id_ = (select source_id "
			+ "              from tbl_indicators "
			+ "              where id_ = :indicator_id::numeric) ";
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, this.rowMapper());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Source> findSources() {
		String sql = ""
			+ " select id_, name_en, name_es, url_, trend_interval, display "
			+ " from tbl_sources "
			+ " order by id_ ";

		return this.jdbcTemplate.query(sql, this.rowMapper());
	}

	private RowMapper<Source> rowMapper() {
		return new RowMapper<Source>() {
			@Override
			public Source mapRow(ResultSet rs, int rowNum) throws SQLException {
				Source source = new Source();
				source.setId(rs.getString("id_"));
				source.setName_en(rs.getString("name_en"));
				source.setName_es(rs.getString("name_es"));
				source.setUrl(rs.getString("url_"));
				source.setTrendInterval(rs.getInt("trend_interval"));
				if (rs.wasNull()) {
					source.setTrendInterval(1);
				}
				source.setDisplay(rs.getBoolean("display"));
				
				return source;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addSource(Source source) {
		String sql = ""
			+ " insert into tbl_sources (id_, name_en, name_es, url_, trend_interval, display) "
			+ " values (:id::numeric, :name_en, :name_es, :url, :trend_interval, :display) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", source.getId());
		paramMap.addValue("name_en", source.getName_en());
		paramMap.addValue("name_es", source.getName_es());
		paramMap.addValue("url", source.getUrl());
		paramMap.addValue("trend_interval", source.getTrendInterval());
		paramMap.addValue("display", source.isDisplay());

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateSource(Source source) {
		String sql = ""
			+ " update tbl_sources set "
			+ "   name_en = :name_en, "
			+ "   name_es = :name_es, "
			+ "   url_ = :url, "
			+ "   trend_interval = :trend_interval, "
			+ "   display = :display "
			+ " where id_ = :id::numeric ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("name_en", source.getName_en());
		paramMap.addValue("name_es", source.getName_es());
		paramMap.addValue("url", source.getUrl());
		paramMap.addValue("trend_interval", source.getTrendInterval());
		paramMap.addValue("display", source.isDisplay());
		paramMap.addValue("id", source.getId());

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}
}
