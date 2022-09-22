package org.cinow.omh.tables;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
	public void populateTablesData(TablesDataRequest request, TablesData data) {
		String sql = ""
			+ " select *, count(*) over () as total_rows from ("
			+ " select l.id_ as location_id, l.name_en as location_en, l.name_es as location_es, "
			+ " 	lt.id_ as location_type_id, lt.name_en as location_type_en, lt.name_es as location_type_es, "
			+ " 	iv.year_, round(iv.indicator_value, 1) as indicator_value, round(iv.moe_low, 1) as moe_low, round(iv.moe_high, 1) as moe_high, round(iv.universe_value, 1) as universe_value, iv.suppress, "
			+ " 	race.id_ as race_id, coalesce(race.name_en, 'All') as race_en, coalesce(race.name_es, 'Todos') as race_es, "
			+ " 	age.id_ as age_id, coalesce(age.name_en, 'All') as age_en, coalesce(age.name_es, 'Todos') as age_es, "
			+ " 	sex.id_ as sex_id, coalesce(sex.name_en, 'All') as sex_en, coalesce(sex.name_es, 'Todos') as sex_es, "
			+ " 	edu.id_ as edu_id, coalesce(edu.name_en, 'All') as edu_en, coalesce(edu.name_es, 'Todos') as edu_es, "
			+ " 	inc.id_ as inc_id, coalesce(inc.name_en, 'All') as inc_en, coalesce(inc.name_es, 'Todos') as inc_es "
			+ " from tbl_indicator_values iv "
			+ " 	join tbl_locations l on iv.location_id = l.id_ and iv.location_type_id = l.location_type_id "
			+ " 	join tbl_location_types lt on l.location_type_id = lt.id_ "
			+ " 	left join tbl_filter_options race on race.id_ = iv.race_id "
			+ " 	left join tbl_filter_options age on age.id_ = iv.age_id "
			+ " 	left join tbl_filter_options sex on sex.id_ = iv.sex_id "
			+ " 	left join tbl_filter_options edu on edu.id_ = iv.education_id "
			+ " 	left join tbl_filter_options inc on inc.id_ = iv.income_id "
			+ " where iv.indicator_id = :indicator_id::numeric ";
		if (!request.getLocationTypes().isEmpty()) {
			sql += " and (lt.id_::text in (:location_type_ids)) ";
		}
		if (!request.getLocations().isEmpty()) {
			sql += " and (l.id_::text in (:location_ids)) ";
		}
		if (!request.getYears().isEmpty()) {
			sql += " and (iv.year_::text in (:years)) ";
		}
		if (!request.getRaces().isEmpty()) {
			sql += " and (race.id_::text in (:race_ids) or (:include_all_races and race.id_ is null)) ";
		}
		if (!request.getAges().isEmpty()) {
			sql += " and (age.id_::text in (:age_ids) or (:include_all_ages and age.id_ is null)) ";
		}
		if (!request.getSexes().isEmpty()) {
			sql += " and (sex.id_::text in (:sex_ids) or (:include_all_sexes and sex.id_ is null)) ";
		}
		if (!request.getEducations().isEmpty()) {
			sql += " and (edu.id_::text in (:edu_ids) or (:include_all_edus and edu.id_ is null)) ";
		}
		if (!request.getIncomes().isEmpty()) {
			sql += " and (inc.id_::text in (:inc_ids) or (:include_all_incs and inc.id_ is null)) ";
		}
		sql += " order by lt.id_, l.id_, iv.year_ desc, race.id_ nulls first, age.id_ nulls first, sex.id_ nulls first, edu.id_ nulls first, inc.id_ nulls first ";
		sql += " ) as t ";

		if (StringUtils.hasText(request.getSearch())) {
			sql += "   where lower(location_type_en) like lower(:search) or lower(location_type_es) like lower(:search) ";
			sql += "   or lower(location_en) like lower(:search) or lower(location_es) like lower(:search) ";
			sql += "   or lower(year_) like lower(:search) ";
			sql += "   or lower(race_en) like lower(:search) or lower(race_es) like lower(:search) ";
			sql += "   or lower(age_en) like lower(:search) or lower(age_es) like lower(:search) ";
			sql += "   or lower(sex_en) like lower(:search) or lower(sex_es) like lower(:search) ";
			sql += "   or lower(edu_en) like lower(:search) or lower(edu_es) like lower(:search) ";
			sql += "   or lower(inc_en) like lower(:search) or lower(inc_es) like lower(:search) ";
			sql += "   or lower(indicator_value::text) like lower(:search) ";
			sql += "   or lower(moe_low::text) like lower(:search) ";
			sql += "   or lower(moe_high::text) like lower(:search) ";
			sql += "   or lower(universe_value::text) like lower(:search) ";

		}
		if (StringUtils.hasText(request.getSortBy())) {
			sql += " order by " + this.getSortByColumn(request.getSortBy(), request.getLang())  + (request.isSortDesc() ? " desc nulls last " : " nulls first ");
		}
		if (request.getPerPage() > 0) {
			sql += " limit :per_page offset (:page * :per_page) ";
		}

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", request.getIndicator());
		paramMap.addValue("page", request.getPage() - 1);
		paramMap.addValue("per_page", request.getPerPage());
		paramMap.addValue("location_type_ids", request.getLocationTypes());
		paramMap.addValue("location_ids", request.getLocations());
		paramMap.addValue("years", request.getYears());
		paramMap.addValue("race_ids", request.getRaces());
		paramMap.addValue("include_all_races", request.getRaces().contains(null));
		paramMap.addValue("age_ids", request.getAges());
		paramMap.addValue("include_all_ages", request.getAges().contains(null));
		paramMap.addValue("sex_ids", request.getSexes());
		paramMap.addValue("include_all_sexes", request.getSexes().contains(null));
		paramMap.addValue("edu_ids", request.getEducations());
		paramMap.addValue("include_all_edus", request.getEducations().contains(null));
		paramMap.addValue("inc_ids", request.getIncomes());
		paramMap.addValue("include_all_incs", request.getIncomes().contains(null));
		if (StringUtils.hasText(request.getSearch())) {
			paramMap.addValue("search", "%" + request.getSearch() + "%");
		}

		data.setItems(this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<TablesDataItem>() {
			@Override
			public TablesDataItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				data.setTotalRows(rs.getInt("total_rows"));
				TablesDataItem item = new TablesDataItem();
				item.setLocationTypeId(rs.getString("location_type_id"));
				item.setLocationType_en(rs.getString("location_type_en"));
				item.setLocationType_es(rs.getString("location_type_es"));
				item.setLocationId(rs.getString("location_id"));
				item.setLocation_en(rs.getString("location_en"));
				item.setLocation_es(rs.getString("location_es"));
				item.setYear(rs.getString("year_"));
				item.setRaceId(rs.getString("race_id"));
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
		}));
	}

	private String getSortByColumn(String field, String lang) {
		switch (field) {
			case "locationType":
				return "location_type_" + lang;
			case "location":
				return "location_" + lang;
			case "year":
				return "year_";
			case "race": 
				return "race_" + lang;
			case "age":
				return "age_" + lang;
			case "sex":
				return "sex_" + lang;
			case "education":
				return "edu_" + lang;
			case "income":
				return "inc_" + lang;
			case "valueLabel":
				return "indicator_value";
			case "moeLowLabel":
				return "moe_low";
			case "moeHighLabel":
				return "moe_high";
			case "universeValueLabel":
				return "universe_value";
			default:
				return null;
		}
	}
}
