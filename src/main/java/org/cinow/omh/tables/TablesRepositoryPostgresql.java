package org.cinow.omh.tables;

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
public class TablesRepositoryPostgresql implements TablesRepository{

	/**
	 * The named parameter JDBC template.
	 */
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TablesDataItem> getTablesData(String indicator) {
		String sql = "" //TODO: espanol
			+ " select l.name_en as location, "
			+ " 	lt.name_en as location_type, "
			+ " 	iv.year_, iv.indicator_value, iv.moe_low, iv.moe_high, iv.universe_value, iv.suppress, "
			+ " 	race.name_en as race, age.name_en as age, sex.name_en as sex, edu.name_en as edu, inc.name_en as inc "
			+ " from tbl_indicator_values iv "
			+ " 	join tbl_locations l on iv.location_id = l.id_"
			+ " 	join tbl_location_types lt on l.location_type_id = lt.id_ "
			+ " 	left join tbl_filter_options race on race.id_ = iv.race_id "
			+ " 	left join tbl_filter_options age on age.id_ = iv.age_id "
			+ " 	left join tbl_filter_options sex on sex.id_ = iv.sex_id "
			+ " 	left join tbl_filter_options edu on edu.id_ = iv.education_id "
			+ " 	left join tbl_filter_options inc on inc.id_ = iv.income_id "
			+ " where iv.indicator_id = :indicator_id::numeric "
			+ " order by l.name_en, iv.year_ desc, race.id_, age.id_, sex.id_, edu.id_, inc.id_ ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicator);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<TablesDataItem>() {
			@Override
			public TablesDataItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				TablesDataItem item = new TablesDataItem();
				item.setLocationType(rs.getString("location_type"));
				item.setLocation(rs.getString("location"));
				item.setYear(rs.getString("year_"));
				item.setRace(rs.getString("race"));
				item.setAge(rs.getString("age"));
				item.setSex(rs.getString("sex"));
				item.setEducation(rs.getString("edu"));
				item.setIncome(rs.getString("inc"));
				item.setValue(rs.getDouble("indicator_value"));
				if (rs.wasNull()) {
					item.setValue(null);
				}
				item.setMoeLow(rs.getDouble("moe_low"));
				if (rs.wasNull()) {
					item.setMoeLow(null);
				}
				item.setMoeHigh(rs.getDouble("moe_high"));
				if (rs.wasNull()) {
					item.setMoeHigh(null);
				}
				item.setUniverseValue(rs.getDouble("universe_value"));
				if (rs.wasNull()) {
					item.setUniverseValue(null);
				}
				item.setSuppressed(rs.getBoolean("suppressed"));

				return item;
			}
		});
	}
}
