package org.cinow.omh.locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LocationRepositoryPostgresql implements LocationRepository {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Location> findLocationByType(long typeId) {
		String sql = ""
			+ " select id_, name_en, name_es "
			+ " from omh.tbl_locations "
			+ " where location_type_id = :location_type_id ";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_type_id", typeId);

		return this.jdbcTemplate.query(sql, paramMap, new RowMapper<Location>() {
			@Override
			public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
				Location location = new Location();
				location.setId(rs.getLong("id_"));
				location.setName_en(rs.getString("name_en"));
				location.setName_es(rs.getString("name_es"));
				location.setTypeId(typeId);

				return location;
			}
		});
	}	
}
