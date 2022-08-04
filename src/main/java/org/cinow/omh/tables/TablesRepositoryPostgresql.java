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
		String sql = ""
			+ " select l.name_en as location_en, l.name_es as location_es, "
			+ " 	lt.name_en as location_type_en, lt.name_es as location_type_es, "
			+ " 	iv.year_, round(iv.indicator_value, 2) as indicator_value, round(iv.moe_low, 2) as moe_low, round(iv.moe_high, 2) as moe_high, round(iv.universe_value, 2) as universe_value, iv.suppress, "
			+ " 	race.name_en as race_en, race.name_es as race_es, "
			+ " 	age.name_en as age_en, age.name_es as age_es, "
			+ " 	sex.name_en as sex_en, sex.name_es as sex_es, "
			+ " 	edu.name_en as edu_en, edu.name_es as edu_es, "
			+ " 	inc.name_en as inc_en, inc.name_es as inc_es "
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
				item.setLocationType_en(rs.getString("location_type_en"));
				item.setLocationType_es(rs.getString("location_type_es"));
				item.setLocation_en(rs.getString("location_en"));
				item.setLocation_es(rs.getString("location_es"));
				item.setYear(rs.getString("year_"));
				item.setRace_en(rs.getString("race_en"));
				item.setRace_es(rs.getString("race_es"));
				item.setAge_en(rs.getString("age_en"));
				item.setAge_es(rs.getString("age_es"));
				item.setSex_en(rs.getString("sex_en"));
				item.setSex_es(rs.getString("sex_es"));
				item.setEducation_en(rs.getString("edu_en"));
				item.setEducation_es(rs.getString("edu_es"));
				item.setIncome_en(rs.getString("inc_en"));
				item.setIncome_es(rs.getString("inc_es"));
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
				item.setSuppressed(rs.getBoolean("suppress"));

				return item;
			}
		});
	}
}
