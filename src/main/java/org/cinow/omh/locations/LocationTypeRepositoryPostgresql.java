package org.cinow.omh.locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LocationTypeRepositoryPostgresql implements LocationTypeRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<LocationType> findLocationTypes() {
		String sql = ""
			+ " select id_, name_en, name_es "
			+ " from tbl_location_types "
			+ " order by sort_order ";

		return this.jdbcTemplate.query(sql, new RowMapper<LocationType>() {
			@Override
			public LocationType mapRow(ResultSet rs, int rowNum) throws SQLException {
				LocationType locationType = new LocationType();
				locationType.setId(rs.getString("id_"));
				locationType.setName_en(rs.getString("name_en"));
				locationType.setName_es(rs.getString("name_es"));
				return locationType;
			}
		});
	}
}
