package org.cinow.omh.locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class LocationRepositoryPostgresql implements LocationRepository {

	/**
	 * The named parameter JDBC template.
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Location> findLocationByType(String typeId) {
		String sql = ""
			+ " select id_, location_type_id, name_en, name_es "
			+ " from tbl_locations "
			+ " where location_type_id = :location_type_id::numeric ";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_type_id", typeId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, this.locationRowMapper());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Location findLocation(String id, String typeId) {
		String sql = ""
			+ " select id_, location_type_id, name_en, name_es "
			+ " from tbl_locations "
			+ " where id_ = :id and location_type_id = :location_type_id::numeric ";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		paramMap.addValue("location_type_id", typeId);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, this.locationRowMapper());
	}
	
	/**
	 * {@inheritDoc}
	 */
	private RowMapper<Location> locationRowMapper() {
		return new RowMapper<Location>() {
			@Override
			public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
				Location location = new Location();
				location.setId(rs.getString("id_"));
				location.setName_en(rs.getString("name_en"));
				location.setName_es(rs.getString("name_es"));
				location.setTypeId(rs.getString("location_type_id"));

				return location;
			}
		};
	}
}
