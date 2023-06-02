package org.cinow.omh.locations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
			+ " where location_type_id = :location_type_id::numeric "
			+ " order by id_::numeric ";
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

	@Override
	public void saveCustomLocation(CustomLocation location) {
		String sql = ""
			+ " insert into tbl_custom_locations (id_, name_, location_type_id, location_ids, geojson) "
			+ " values (:id_, :name_, :location_type_id::numeric, :location_ids, :geojson::json) "
			+ " on conflict (id_) do update "
			+ " set name_ = :name_, location_type_id = :location_type_id::numeric, location_ids = :location_ids, geojson = :geojson::json ";
		
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id_", location.getId());
		paramMap.addValue("name_", location.getName());
		paramMap.addValue("location_type_id", location.getTypeId());
		paramMap.addValue("location_ids", location.getIds().toArray(new String[0]), Types.ARRAY);
		paramMap.addValue("geojson", location.getGeojson());

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public Location findCustomLocation(String id) {
		String sql = ""
			+ " select id_, '7' as location_type_id, name_ as name_en, name_ as name_es"
			+ " from tbl_custom_locations "
			+ " where id_ = :id ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, this.locationRowMapper());
	}

	@Override
	public String findCustomLocationType(String id) {
		String sql = ""
			+ " select location_type_id "
			+ " from tbl_custom_locations "
			+ " where id_ = :id ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, String.class);
	}

	@Override
	public List<String> findCustomLocationIds(String id) {
		String sql = ""
			+ " select unnest(location_ids) "
			+ " from tbl_custom_locations "
			+ " where id_ = :id ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return this.namedParameterJdbcTemplate.queryForList(sql, paramMap, String.class);
	}

	@Override
	public String findCustomLocationGeojson(String id) {
		String sql = ""
			+ " select geojson::text "
			+ " from tbl_custom_locations "
			+ " where id_ = :id ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, String.class);
	}
}
