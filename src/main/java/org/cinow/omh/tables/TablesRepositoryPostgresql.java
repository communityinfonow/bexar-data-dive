package org.cinow.omh.tables;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.filters.IndicatorFilter;
import org.cinow.omh.indicators.IndicatorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
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
		String sql = this.buildSql(request);
		MapSqlParameterSource paramMap = this.buildParamMap(request);

		data.setItems(this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<TablesDataItem>>() {
			@Override
			public List<TablesDataItem> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<TablesDataItem> items = new ArrayList<TablesDataItem>();
				TablesDataItemMultiple customLocationItem = null;
				int currentRecordNumber = -1;
				while (rs.next()) {
					data.setTotalRows(rs.getInt("total_rows"));
					if (rs.getString("location_type_id").equals("7")) {
						if (rs.getInt("record_number") != currentRecordNumber) {
								currentRecordNumber = rs.getInt("record_number");
								customLocationItem = new TablesDataItemMultiple();
								items.add(customLocationItem);
						}
						customLocationItem.setIndicatorType(new IndicatorType());
						customLocationItem.getIndicatorType().setId(rs.getString("indicator_type_id"));
						customLocationItem.setAggregable(rs.getBoolean("is_aggregable"));
						customLocationItem.setRatePer(rs.getInt("rate_per"));
						customLocationItem.setLocationTypeId(rs.getString("location_type_id"));
						customLocationItem.setLocationType_en(rs.getString("location_type_en"));
						customLocationItem.setLocationType_es(rs.getString("location_type_es"));
						customLocationItem.setLocationId(rs.getString("location_id"));
						customLocationItem.setLocation_en(rs.getString("location_en"));
						customLocationItem.setLocation_es(rs.getString("location_es"));
						customLocationItem.setYear(rs.getString("year_"));
						customLocationItem.setRaceId(rs.getString("race_id"));
						customLocationItem.setRace_en(rs.getString("race_en"));
						customLocationItem.setRace_es(rs.getString("race_es"));
						customLocationItem.setAge_en(rs.getString("age_en"));
						customLocationItem.setAge_es(rs.getString("age_es"));
						customLocationItem.setSex_en(rs.getString("sex_en"));
						customLocationItem.setSex_es(rs.getString("sex_es"));
						customLocationItem.setEducation_en(rs.getString("edu_en"));
						customLocationItem.setEducation_es(rs.getString("edu_es"));
						customLocationItem.setIncome_en(rs.getString("inc_en"));
						customLocationItem.setIncome_es(rs.getString("inc_es"));
						BigDecimal value = rs.getBigDecimal("indicator_value");
						if (rs.wasNull()) {
							value = null;
						}
						customLocationItem.getValues().put(rs.getString("component_location_id"), value);
						BigDecimal countValue = rs.getBigDecimal("count_value");
						if (rs.wasNull()) {
							countValue = null;
						}
						customLocationItem.getCountValues().put(rs.getString("component_location_id"), countValue);
						BigDecimal universeValue = rs.getBigDecimal("universe_value");
						if (rs.wasNull()) {
							universeValue = null;
						}
						customLocationItem.getUniverseValues().put(rs.getString("component_location_id"), universeValue);
						customLocationItem.getCountMoes().put(rs.getString("component_location_id"), rs.getBigDecimal("count_moe"));
						if (rs.wasNull()) {
							customLocationItem.getCountMoes().put(rs.getString("component_location_id"), null);
						}
						customLocationItem.getUniverseMoes().put(rs.getString("component_location_id"), rs.getBigDecimal("universe_moe"));
						if (rs.wasNull()) {
							customLocationItem.getUniverseMoes().put(rs.getString("component_location_id"), null);
						}
						customLocationItem.getSuppresseds().put(rs.getString("component_location_id"), rs.getBoolean("suppress"));
						BigDecimal moeHigh = rs.getBigDecimal("moe_high");
						if (value != null && !rs.wasNull()) {
							customLocationItem.getValueMoes().put(rs.getString("location_id"), moeHigh.subtract(value));
						} else {
							customLocationItem.getValueMoes().put(rs.getString("location_id"), null);
						}
					} else {
						TablesDataItemSingle item = new TablesDataItemSingle();
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
						item.setValue(rs.getBigDecimal("indicator_value"));
						if (rs.wasNull()) {
							item.setValue(null);
						}
						item.setMoeLow(rs.getBigDecimal("moe_low"));
						if (rs.wasNull()) {
							item.setMoeLow(null);
						}
						item.setMoeHigh(rs.getBigDecimal("moe_high"));
						if (rs.wasNull()) {
							item.setMoeHigh(null);
						}
						item.setUniverseValue(rs.getBigDecimal("universe_value"));
						if (rs.wasNull()) {
							item.setUniverseValue(null);
						}
						item.setSuppressed(rs.getBoolean("suppress"));

						items.add(item);
					}
				}
				return items;
			}
		}));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void populateTablesDataExport(TablesDataRequest request, TablesDataExport data, List<IndicatorFilter> filters) {
		String sql = this.buildSql(request);
		MapSqlParameterSource paramMap = this.buildParamMap(request);

		data.setItems(this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<String>() {
			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				StringBuilder items = new StringBuilder();
				TablesDataItemSingle item = null;
				TablesDataItemMultiple customLocationItem = null;
				int currentRecordNumber = -1;
				while (rs.next()) {
					data.setTotalRows(rs.getInt("total_rows"));
					if (rs.getString("location_type_id").equals("7")) {
						if (rs.getInt("record_number") != currentRecordNumber) {
							if (customLocationItem != null) {
								TablesRepositoryPostgresql.this.appendItemString(request, data, items, customLocationItem, filters);
							}
							currentRecordNumber = rs.getInt("record_number");
							customLocationItem = new TablesDataItemMultiple();
						}
						customLocationItem.setIndicatorType(new IndicatorType());
						customLocationItem.getIndicatorType().setId(rs.getString("indicator_type_id"));
						customLocationItem.setAggregable(rs.getBoolean("is_aggregable"));
						customLocationItem.setLocationTypeId(rs.getString("location_type_id"));
						customLocationItem.setLocationType_en(rs.getString("location_type_en"));
						customLocationItem.setLocationType_es(rs.getString("location_type_es"));
						customLocationItem.setLocationId(rs.getString("location_id"));
						customLocationItem.setLocation_en(rs.getString("location_en"));
						customLocationItem.setLocation_es(rs.getString("location_es"));
						customLocationItem.setYear(rs.getString("year_"));
						customLocationItem.setRaceId(rs.getString("race_id"));
						customLocationItem.setRace_en(rs.getString("race_en"));
						customLocationItem.setRace_es(rs.getString("race_es"));
						customLocationItem.setAge_en(rs.getString("age_en"));
						customLocationItem.setAge_es(rs.getString("age_es"));
						customLocationItem.setSex_en(rs.getString("sex_en"));
						customLocationItem.setSex_es(rs.getString("sex_es"));
						customLocationItem.setEducation_en(rs.getString("edu_en"));
						customLocationItem.setEducation_es(rs.getString("edu_es"));
						customLocationItem.setIncome_en(rs.getString("inc_en"));
						customLocationItem.setIncome_es(rs.getString("inc_es"));
						BigDecimal value = rs.getBigDecimal("indicator_value");
						if (rs.wasNull()) {
							value = null;
						}
						customLocationItem.getValues().put(rs.getString("component_location_id"), value);
						BigDecimal countValue = rs.getBigDecimal("count_value");
						if (rs.wasNull()) {
							countValue = null;
						}
						customLocationItem.getCountValues().put(rs.getString("component_location_id"), countValue);
						BigDecimal universeValue = rs.getBigDecimal("universe_value");
						if (rs.wasNull()) {
							universeValue = null;
						}
						customLocationItem.getUniverseValues().put(rs.getString("component_location_id"), universeValue);
						customLocationItem.getCountMoes().put(rs.getString("component_location_id"), rs.getBigDecimal("count_moe"));
						if (rs.wasNull()) {
							customLocationItem.getCountMoes().put(rs.getString("component_location_id"), null);
						}
						customLocationItem.getUniverseMoes().put(rs.getString("component_location_id"), rs.getBigDecimal("universe_moe"));
						if (rs.wasNull()) {
							customLocationItem.getUniverseMoes().put(rs.getString("component_location_id"), null);
						}
						customLocationItem.getSuppresseds().put(rs.getString("component_location_id"), rs.getBoolean("suppress"));
						BigDecimal moeHigh = rs.getBigDecimal("moe_high");
						if (value != null && !rs.wasNull()) {
							customLocationItem.getValueMoes().put(rs.getString("location_id"), moeHigh.subtract(value));
						} else {
							customLocationItem.getValueMoes().put(rs.getString("location_id"), null);
						}
					} else {
						item = new TablesDataItemSingle();
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
						item.setValue(rs.getBigDecimal("indicator_value"));
						if (rs.wasNull()) {
							item.setValue(null);
						}
						item.setMoeLow(rs.getBigDecimal("moe_low"));
						if (rs.wasNull()) {
							item.setMoeLow(null);
						}
						item.setMoeHigh(rs.getBigDecimal("moe_high"));
						if (rs.wasNull()) {
							item.setMoeHigh(null);
						}
						item.setUniverseValue(rs.getBigDecimal("universe_value"));
						if (rs.wasNull()) {
							item.setUniverseValue(null);
						}
						item.setSuppressed(rs.getBoolean("suppress"));

						TablesRepositoryPostgresql.this.appendItemString(request, data, items, item, filters);
					}
				}
				return items.toString();
			}
		}));
	}

	private String buildSql(TablesDataRequest request) {
		String sql = ""
			+ " select * from ( "	
			+ " select * from ( "
			+ " select *, max(record_number) over () as total_rows from ( "
			+ " select *, dense_rank() over(order by location_type_id, location_id, year_ desc, race_id nulls first, age_id nulls first, sex_id nulls first, edu_id nulls first, inc_id nulls first) as record_number from ( "
			+ " select l.id_ as location_id, null as component_location_id, l.name_en as location_en, l.name_es as location_es, "
			+	" 	 lt.id_ as location_type_id, lt.name_en as location_type_en, lt.name_es as location_type_es, "
			+	" 	 iv.year_, round(iv.indicator_value, 1) as indicator_value, round(iv.moe_low, 1) as moe_low, round(iv.moe_high, 1) as moe_high, round(iv.universe_value, 1) as universe_value, round(iv.count_value, 1) as count_value, iv.universe_moe as universe_moe, iv.count_moe as count_moe, iv.suppress, "
			+	" 	 race.id_ as race_id, coalesce(race.name_en, 'All') as race_en, coalesce(race.name_es, 'Todos') as race_es, "
			+	" 	 age.id_ as age_id, coalesce(age.name_en, 'All') as age_en, coalesce(age.name_es, 'Todos') as age_es, "
			+	" 	 sex.id_ as sex_id, coalesce(sex.name_en, 'All') as sex_en, coalesce(sex.name_es, 'Todos') as sex_es, "
			+	" 	 edu.id_ as edu_id, coalesce(edu.name_en, 'All') as edu_en, coalesce(edu.name_es, 'Todos') as edu_es, "
			+	" 	 inc.id_ as inc_id, coalesce(inc.name_en, 'All') as inc_en, coalesce(inc.name_es, 'Todos') as inc_es, "
			+	" 	 i.indicator_type_id, i.is_aggregable, i.rate_per "
			+	"  from tbl_indicator_values iv "
			+	" 	 join tbl_locations l on iv.location_id = l.id_ and iv.location_type_id = l.location_type_id "
			+	" 	 join tbl_location_types lt on l.location_type_id = lt.id_ "
			+	" 	 left join tbl_filter_options race on race.id_ = iv.race_id "
			+	" 	 left join tbl_filter_options age on age.id_ = iv.age_id "
			+	" 	 left join tbl_filter_options sex on sex.id_ = iv.sex_id "
			+	" 	 left join tbl_filter_options edu on edu.id_ = iv.education_id "
			+	" 	 left join tbl_filter_options inc on inc.id_ = iv.income_id "
			+ 	" 	 join tbl_indicators i on i.id_ = iv.indicator_id "
			+	"  where iv.indicator_id = :indicator_id::numeric ";
		if (!request.getLocationTypes().isEmpty()) {
			sql += " and (lt.id_::text in (:location_type_ids)) ";
		}
		if (!request.getLocations().isEmpty()) {
			sql += " and (l.location_type_id || '_' || l.id_ in (:location_ids)) ";
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
		if (StringUtils.hasText(request.getSearch())) {
			sql += "   and (lower(lt.name_en) like lower(:search) or lower(lt.name_es) like lower(:search) ";
			sql += "   or lower(l.name_en) like lower(:search) or lower(l.name_es) like lower(:search) ";
			sql += "   or lower(year_) like lower(:search) ";
			sql += "   or lower(race.name_en) like lower(:search) or lower(race.name_es) like lower(:search) ";
			sql += "   or lower(age.name_en) like lower(:search) or lower(age.name_es) like lower(:search) ";
			sql += "   or lower(sex.name_en) like lower(:search) or lower(sex.name_es) like lower(:search) ";
			sql += "   or lower(edu.name_en) like lower(:search) or lower(edu.name_es) like lower(:search) ";
			sql += "   or lower(inc.name_en) like lower(:search) or lower(inc.name_es) like lower(:search) ";
			sql += "   or lower(indicator_value::text) like lower(:search) ";
			sql += "   or lower(moe_low::text) like lower(:search) ";
			sql += "   or lower(moe_high::text) like lower(:search) ";
			sql += "   or lower(universe_value::text) like lower(:search)) ";
		}
		sql +=	"  union "
			+	"  select cl.id_ as location_id, iv.location_id as component_location_id, cl.name_ as location_en, cl.name_ as location_es, "
			+	" 	 7 as location_type_id, 'Custom' as location_type_en, 'Custom (es)' as location_type_es, "
			+	" 	 iv.year_, round(iv.indicator_value, 1) as indicator_value, round(iv.moe_low, 1) as moe_low, round(iv.moe_high, 1) as moe_high, round(iv.universe_value, 1) as universe_value, round(iv.count_value, 1) as count_value, iv.universe_moe as universe_moe, iv.count_moe as count_moe, iv.suppress, "
			+	" 	 race.id_ as race_id, coalesce(race.name_en, 'All') as race_en, coalesce(race.name_es, 'Todos') as race_es, "
			+	" 	 age.id_ as age_id, coalesce(age.name_en, 'All') as age_en, coalesce(age.name_es, 'Todos') as age_es, "
			+	" 	 sex.id_ as sex_id, coalesce(sex.name_en, 'All') as sex_en, coalesce(sex.name_es, 'Todos') as sex_es, "
			+	" 	 edu.id_ as edu_id, coalesce(edu.name_en, 'All') as edu_en, coalesce(edu.name_es, 'Todos') as edu_es, "
			+	" 	 inc.id_ as inc_id, coalesce(inc.name_en, 'All') as inc_en, coalesce(inc.name_es, 'Todos') as inc_es, "
			+	" 	 i.indicator_type_id, i.is_aggregable, i.rate_per "
			+	"  from tbl_indicator_values iv "
			+	" 	 join tbl_custom_locations cl ON cl.location_type_id = iv.location_type_id and cl.location_ids @> array[iv.location_id] and cl.id_ in (:custom_location_ids)"
			+	" 	 left join tbl_filter_options race on race.id_ = iv.race_id "
			+	" 	 left join tbl_filter_options age on age.id_ = iv.age_id "
			+	" 	 left join tbl_filter_options sex on sex.id_ = iv.sex_id "
			+	" 	 left join tbl_filter_options edu on edu.id_ = iv.education_id "
			+	" 	 left join tbl_filter_options inc on inc.id_ = iv.income_id "
			+ 	" 	 join tbl_indicators i on i.id_ = iv.indicator_id "
			+	"  where iv.indicator_id = :indicator_id::numeric ";
		if (!request.getLocationTypes().isEmpty()) {
			sql += " and ('7' in (:location_type_ids)) ";
		}
		if (!request.getLocations().isEmpty()) {
			sql += " and ('7_' || cl.id_ in (:location_ids)) ";
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
		if (StringUtils.hasText(request.getSearch())) {
			sql += "   and (lower('Custom') like lower(:search) or lower('Custom (es)') like lower(:search) ";
			sql += "   or lower(cl.name_) like lower(:search) or lower(cl.name_) like lower(:search) ";
			sql += "   or lower(year_) like lower(:search) ";
			sql += "   or lower(race.name_en) like lower(:search) or lower(race.name_es) like lower(:search) ";
			sql += "   or lower(age.name_en) like lower(:search) or lower(age.name_es) like lower(:search) ";
			sql += "   or lower(sex.name_en) like lower(:search) or lower(sex.name_es) like lower(:search) ";
			sql += "   or lower(edu.name_en) like lower(:search) or lower(edu.name_es) like lower(:search) ";
			sql += "   or lower(inc.name_en) like lower(:search) or lower(inc.name_es) like lower(:search) ";
			sql += "   or lower(indicator_value::text) like lower(:search) ";
			sql += "   or lower(moe_low::text) like lower(:search) ";
			sql += "   or lower(moe_high::text) like lower(:search) ";
			sql += "   or lower(universe_value::text) like lower(:search)) ";
		}
		sql += " ) as filtered "
			+	" ) as ranked "
			+	" order by record_number "
			+	" ) as ordered ";
		if (StringUtils.hasText(request.getSortBy())) {
			sql += " order by " + this.getSortByColumn(request.getSortBy(), request.getLang())  + (request.isSortDesc() ? " desc nulls last " : " nulls first ");
		}
		sql += " ) user_searched_sorted ";
		if (request.getPerPage() > 0) {
			sql +=	" where record_number between :start and :end ";
		}

		return sql;
	}

	private MapSqlParameterSource buildParamMap(TablesDataRequest request) {
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", request.getIndicator());
		paramMap.addValue("start", ((request.getPage() - 1) * request.getPerPage()) + 1);
		paramMap.addValue("end", request.getPage() * request.getPerPage());
		paramMap.addValue("location_type_ids", request.getLocationTypes());
		paramMap.addValue("location_ids", request.getLocations());
		List<String> customLocationIds = request.getCustomLocationIds();
		if (customLocationIds != null && customLocationIds.isEmpty()) {
			customLocationIds = null;
		}
		paramMap.addValue("custom_location_ids", customLocationIds);
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

		return paramMap;
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

	private void appendItemString(TablesDataRequest request, TablesDataExport data, StringBuilder items, TablesDataItem item, List<IndicatorFilter> filters) {
		String indicator = "es".equalsIgnoreCase(request.getLang())
				? (data.getCategory().getParentCategoryId() != null ? data.getCategory().getName_es() + " - " : "") + data.getIndicator().getName_es()
				: (data.getCategory().getParentCategoryId() != null ? data.getCategory().getName_en() + " - " : "") + data.getIndicator().getName_en();
		items.append(enquote(indicator) + ",");
		items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? data.getSource().getName_es() : data.getSource().getName_en()) + ",");
		items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? item.getLocationType_es() : item.getLocationType_en()) + ",");
		items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? item.getLocation_es() : item.getLocation_en()) + ",");
		items.append(enquote(item.getYear()) + ",");
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("1"))) {
			items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? item.getRace_es() : item.getRace_en()) + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("2"))) {
			items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? item.getAge_es() : item.getAge_en()) + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("3"))) {
			items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? item.getSex_en() : item.getSex_en()) + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("4"))) {
			items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? item.getEducation_es() : item.getEducation_en()) + ",");
		}
		if (filters.stream().anyMatch(f -> f.getType().getId().equals("5"))) {
			items.append(enquote("es".equalsIgnoreCase(request.getLang()) ? item.getIncome_es() : item.getIncome_en()) + ",");
		}
		items.append((item.getValue() != null ? item.getValue() : "") + ",");
		items.append((item.getMoeLow() != null ? item.getMoeLow() : "") + ",");
		items.append((item.getMoeHigh() != null ? item.getMoeHigh() : "") + ",");
		items.append((item.getUniverseValue() != null ? item.getUniverseValue() : "") + ",");
		items.append("\n");
	}

	//FIXME: DRY
	private String enquote(String text) {
		return "\"" + (text != null ? text : "") + "\"";
	}
}
