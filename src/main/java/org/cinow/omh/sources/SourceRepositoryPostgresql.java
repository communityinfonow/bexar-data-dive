package org.cinow.omh.sources;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SourceRepositoryPostgresql implements SourceRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Source getSourceByIndicator(long indicatorId) {
		String sql = ""
			+ " select id_, name_en, name_es, url_ "
			+ " from tbl_sources "
			+ " where id_ = (select source_id "
			+ "              from tbl_indicators "
			+ "              where id_ = :indicator_id) ";
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, new RowMapper<Source>() {
			@Override
			public Source mapRow(ResultSet rs, int rowNum) throws SQLException {
				Source source = new Source();
				source.setId(rs.getLong("id_"));
				source.setName_en(rs.getString("name_en"));
				source.setName_es(rs.getString("name_es"));
				source.setUrl(rs.getString("url_"));
				
				return source;
			}
		});
	}
}
