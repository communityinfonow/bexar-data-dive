package org.cinow.omh.community;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.filters.FilterOption;
import org.cinow.omh.filters.FilterTypes;
import org.cinow.omh.indicators.Indicator;
import org.cinow.omh.indicators.IndicatorCategory;
import org.cinow.omh.indicators.IndicatorType;
import org.cinow.omh.sources.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

/**
 * {@inheritDoc}
 */
@Repository
public class CommunityRepositoryPostgresql implements CommunityRepository {

	/**
	 * The named parameter JDBC template.
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommunityDataCategory> getCommunityData(String location, String locationType, String filterType, String indicatorId) {
		String sql = ""
			+ " select * "
			+ " from ( "
			+ " select "
			+ "   ic.id_ as category_id, ic.parent_category_id, ic.name_en as category_name_en, ic.name_es as category_name_es, "
			+ "   i.id_ as indicator_id, i.name_en as indicator_name_en, i.name_es as indicator_name_es, i.description_en as indicator_description_en, i.description_es as indicator_description_es, i.rate_per, i.is_aggregable, "
			+ "   case when exists (select 1 from mv_indicator_metadata where indicator_id = i.id_ and has_data = true limit 1) then true else false end as has_data, "
			+ "   case when exists (select 1 "
			+ "    from tbl_data_corrections dc "
			+ "    where (dc.indicator_id = id_ or dc.source_id = i.source_id) and display = true "
			+ "      and date_corrected > current_date - interval '90 days' "
			+ "      and (dc.years @> array[iv.year_] or dc.years  = '{}') "
			+ "      and (dc.location_type_ids @> array[:location_type_id::numeric] or dc.location_type_ids  = '{}') ";
		if (StringUtils.equals(filterType, FilterTypes.RACE.getId())) {
			sql += " and (dc.filter_type_ids @> array[1::numeric] or dc.filter_type_ids  = '{}') ";
		} else if (StringUtils.equals(filterType, FilterTypes.AGE.getId())) {
			sql += " and (dc.filter_type_ids @> array[2::numeric] or dc.filter_type_ids  = '{}') ";
		} else if (StringUtils.equals(filterType, FilterTypes.SEX.getId())) {
			sql += " and (dc.filter_type_ids @> array[3::numeric] or dc.filter_type_ids  = '{}') ";
		} else if (StringUtils.equals(filterType, FilterTypes.EDUCATION.getId())) {
			sql += " and (dc.filter_type_ids @> array[4::numeric] or dc.filter_type_ids  = '{}') ";
		} else if (StringUtils.equals(filterType, FilterTypes.INCOME.getId())) {
			sql += " and (dc.filter_type_ids @> array[5::numeric] or dc.filter_type_ids  = '{}') ";
		}
		sql += "   ) then true else false end as recent_correction, "
			+ "   it.id_ as indicator_type_id, it.name_ as indicator_type_name, "
			+ "   iv.year_, round(iv.indicator_value, 1) as indicator_value, iv.suppress, round(iv.moe_low, 1) as moe_low, round(iv.moe_high, 1) as moe_high, round(iv.universe_value, 1) as universe_value, round(iv.count_value, 1) as count_value, round(iv.universe_moe, 1) as universe_moe, round(iv.count_moe, 1) as count_moe, "
			+ "   s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es, s.url_ as source_url, "
			+ "   fo.id_ as demographic_filter_option_id, fo.name_en as demographic_filter_name_en, fo.name_es as demographic_filter_name_es, "
			+ "   rank() over(partition by iv.indicator_id order by iv.year_ desc) "
			+ " from tbl_indicator_categories ic "
			+ "   join tbl_indicators i on i.indicator_category_id = ic.id_ and i.display = true "
			+ (indicatorId == null ? "" : " and i.id_ = :indicator_id::numeric ")
			+ "   join tbl_indicator_types it on it.id_ = i.indicator_type_id "
			+ "   left join tbl_indicator_values iv on iv.indicator_id = i.id_ "
			+ "     and iv.location_id = :location_id and iv.location_type_id = :location_type_id::numeric ";
			if (StringUtils.equals(filterType, FilterTypes.RACE.getId())) {
				sql += " and iv.age_id is null and iv.sex_id is null and iv.education_id is null and iv.income_id is null "
					+ " left join tbl_filter_options fo on fo.id_ = iv.race_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.AGE.getId())) {
				sql += " and iv.race_id is null and iv.sex_id is null and iv.education_id is null and iv.income_id is null "
					+ " left join tbl_filter_options fo on fo.id_ = iv.age_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.SEX.getId())) {
				sql += " and iv.race_id is null and iv.age_id is null and iv.education_id is null and iv.income_id is null "
				+ " left join tbl_filter_options fo on fo.id_ = iv.sex_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.EDUCATION.getId())) {
				sql += " and iv.race_id is null and iv.age_id is null and iv.sex_id is null and iv.income_id is null "
				+ " left join tbl_filter_options fo on fo.id_ = iv.education_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.INCOME.getId())) {
				sql += " and iv.race_id is null and iv.age_id is null and iv.sex_id is null and iv.education_id is null "
				+ " left join tbl_filter_options fo on fo.id_ = iv.income_id ";
			}
			sql += "   left join tbl_sources s on s.id_ = i.source_id "
			+ " order by ic.sort_order, i.id_, fo.sort_order nulls first "
			+ " ) ranked_data "
			+ " where rank = 1 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_id", location);
		paramMap.addValue("location_type_id", locationType);
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<CommunityDataCategory>>() {
			@Override
			public List<CommunityDataCategory> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<CommunityDataCategory> communityData = new ArrayList<>();
				CommunityDataCategory categoryData = null;
				CommunityDataIndicator indicatorData = null;
				while (rs.next()) {
					if (categoryData == null || !categoryData.getCategory().getId().equals(rs.getString("category_id"))) {
						categoryData = new CommunityDataCategory();
						categoryData.setCategory(new IndicatorCategory());
						categoryData.getCategory().setId(rs.getString("category_id"));
						categoryData.getCategory().setParentCategoryId(rs.getString("parent_category_id"));
						categoryData.getCategory().setName_en(rs.getString("category_name_en"));
						categoryData.getCategory().setName_es(rs.getString("category_name_es"));
						communityData.add(categoryData);
					}
					if (indicatorData == null || !indicatorData.getIndicator().getId().equals(rs.getString("indicator_id"))) {
						indicatorData = new CommunityDataIndicator();
						categoryData.getIndicators().add(indicatorData);
						indicatorData.setIndicator(new Indicator());
						indicatorData.getIndicator().setId(rs.getString("indicator_id"));
						indicatorData.getIndicator().setTypeId(rs.getString("indicator_type_id"));
						indicatorData.getIndicator().setCategoryId(categoryData.getCategory().getId());
						indicatorData.getIndicator().setName_en(rs.getString("indicator_name_en"));
						indicatorData.getIndicator().setName_es(rs.getString("indicator_name_es"));
						indicatorData.getIndicator().setDescription_en(rs.getString("indicator_description_en"));
						indicatorData.getIndicator().setDescription_es(rs.getString("indicator_description_es"));
						indicatorData.getIndicator().setHasData(rs.getBoolean("has_data"));
						indicatorData.getIndicator().setRatePer(rs.getInt("rate_per"));
						indicatorData.getIndicator().setAggregable(rs.getBoolean("is_aggregable"));
						indicatorData.getIndicator().setRecentCorrection(rs.getBoolean("recent_correction"));
						indicatorData.setIndicatorType(new IndicatorType());
						indicatorData.getIndicatorType().setId(rs.getString("indicator_type_id"));
						indicatorData.getIndicatorType().setName(rs.getString("indicator_type_name"));
						indicatorData.setSource(new Source());
						indicatorData.getSource().setId(rs.getString("source_id"));
						indicatorData.getSource().setName_en(rs.getString("source_name_en"));
						indicatorData.getSource().setName_es(rs.getString("source_name_es"));
						indicatorData.getSource().setUrl(rs.getString("source_url"));
						indicatorData.setYear(rs.getString("year_"));
					}
					if (rs.getString("year_") != null) {
						CommunityDataPointSingle dataPoint = new CommunityDataPointSingle();
						dataPoint.setDemographicFilter(new FilterOption());
						dataPoint.getDemographicFilter().setId(rs.getString("demographic_filter_option_id"));
						dataPoint.getDemographicFilter().setTypeId(FilterTypes.RACE.getId());
						dataPoint.getDemographicFilter().setName_en(rs.getString("demographic_filter_name_en"));
						dataPoint.getDemographicFilter().setName_es(rs.getString("demographic_filter_name_es"));
						dataPoint.setSuppressed(rs.getBoolean("suppress"));
						if (!dataPoint.isSuppressed()) {
							dataPoint.setMoeHigh(rs.getBigDecimal("moe_high"));
							if (rs.wasNull()) {
								dataPoint.setMoeHigh(null);
							}
							dataPoint.setMoeLow(rs.getBigDecimal("moe_low"));
							if (rs.wasNull()) {
								dataPoint.setMoeLow(null);
							}
							dataPoint.setValue(rs.getBigDecimal("indicator_value"));
							if (rs.wasNull()) {
								dataPoint.setValue(null);
							}
							dataPoint.setUniverseValue(rs.getBigDecimal("universe_value"));
							if (rs.wasNull()) {
								dataPoint.setUniverseValue(null);
							}
							dataPoint.setCountValue(rs.getBigDecimal("count_value"));
							if (rs.wasNull()) {
								dataPoint.setCountValue(null);
							}
							
						}
						indicatorData.getDemographicData().add(dataPoint);
					}
				}

				return communityData;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommunityDataCategory> getCustomCommunityData(String location, String filterType) {
		String sql = ""
			+ " select * "
			+ " from ( "
			+ " select "
			+ "   ic.id_ as category_id, ic.parent_category_id, ic.name_en as category_name_en, ic.name_es as category_name_es, "
			+ "   i.id_ as indicator_id, i.name_en as indicator_name_en, i.name_es as indicator_name_es, i.rate_per, i.is_aggregable, "
			+ "   case when exists (select 1 from mv_indicator_metadata where indicator_id = i.id_ and has_data = true limit 1) then true else false end as has_data, "
			+ "   it.id_ as indicator_type_id, it.name_ as indicator_type_name, "
			+ "   iv.location_id, iv.year_, round(iv.indicator_value, 1) as indicator_value, iv.suppress, round(iv.moe_low, 1) as moe_low, round(iv.moe_high, 1) as moe_high, round(iv.universe_value, 1) as universe_value, round(iv.count_value, 1) as count_value, round(iv.universe_moe, 1) as universe_moe, round(iv.count_moe, 1) as count_moe, "
			+ "   s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es, s.url_ as source_url, "
			+ "   fo.id_ as demographic_filter_option_id, fo.name_en as demographic_filter_name_en, fo.name_es as demographic_filter_name_es, "
			+ "   rank() over(partition by iv.indicator_id order by iv.year_ desc) "
			+ " from tbl_indicator_categories ic "
			+ "   join tbl_indicators i on i.indicator_category_id = ic.id_ and i.display = true "
			+ "   join tbl_indicator_types it on it.id_ = i.indicator_type_id "
			+ "   left join tbl_indicator_values iv on iv.indicator_id = i.id_ "
			+ "     and iv.location_id = any(select unnest(location_ids) from tbl_custom_locations where id_ = :location_id) and iv.location_type_id = (select location_type_id from tbl_custom_locations where id_ = :location_id) ";
			if (StringUtils.equals(filterType, FilterTypes.RACE.getId())) {
				sql += " and iv.age_id is null and iv.sex_id is null and iv.education_id is null and iv.income_id is null "
					+ " left join tbl_filter_options fo on fo.id_ = iv.race_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.AGE.getId())) {
				sql += " and iv.race_id is null and iv.sex_id is null and iv.education_id is null and iv.income_id is null "
					+ " left join tbl_filter_options fo on fo.id_ = iv.age_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.SEX.getId())) {
				sql += " and iv.race_id is null and iv.age_id is null and iv.education_id is null and iv.income_id is null "
				+ " left join tbl_filter_options fo on fo.id_ = iv.sex_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.EDUCATION.getId())) {
				sql += " and iv.race_id is null and iv.age_id is null and iv.sex_id is null and iv.income_id is null "
				+ " left join tbl_filter_options fo on fo.id_ = iv.education_id ";
			} else if (StringUtils.equals(filterType, FilterTypes.INCOME.getId())) {
				sql += " and iv.race_id is null and iv.age_id is null and iv.sex_id is null and iv.education_id is null "
				+ " left join tbl_filter_options fo on fo.id_ = iv.income_id ";
			}
			sql += "   left join tbl_sources s on s.id_ = i.source_id "
			+ " order by ic.sort_order, i.id_, fo.sort_order nulls first, iv.location_id "
			+ " ) ranked_data "
			+ " where rank = 1 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_id", location);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<CommunityDataCategory>>() {
			@Override
			public List<CommunityDataCategory> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<CommunityDataCategory> communityData = new ArrayList<>();
				CommunityDataCategory categoryData = null;
				CommunityDataIndicator indicatorData = null;
				CommunityDataPointMultiple dataPoint = null;
				while (rs.next()) {
					if (categoryData == null || !categoryData.getCategory().getId().equals(rs.getString("category_id"))) {
						categoryData = new CommunityDataCategory();
						categoryData.setCategory(new IndicatorCategory());
						categoryData.getCategory().setId(rs.getString("category_id"));
						categoryData.getCategory().setParentCategoryId(rs.getString("parent_category_id"));
						categoryData.getCategory().setName_en(rs.getString("category_name_en"));
						categoryData.getCategory().setName_es(rs.getString("category_name_es"));
						communityData.add(categoryData);
					}
					if (indicatorData == null || !indicatorData.getIndicator().getId().equals(rs.getString("indicator_id"))) {
						indicatorData = new CommunityDataIndicator();
						categoryData.getIndicators().add(indicatorData);
						indicatorData.setIndicator(new Indicator());
						indicatorData.getIndicator().setId(rs.getString("indicator_id"));
						indicatorData.getIndicator().setTypeId(rs.getString("indicator_type_id"));
						indicatorData.getIndicator().setCategoryId(categoryData.getCategory().getId());
						indicatorData.getIndicator().setName_en(rs.getString("indicator_name_en"));
						indicatorData.getIndicator().setName_es(rs.getString("indicator_name_es"));
						indicatorData.getIndicator().setHasData(rs.getBoolean("has_data"));
						indicatorData.getIndicator().setRatePer(rs.getInt("rate_per"));
						indicatorData.getIndicator().setAggregable(rs.getBoolean("is_aggregable"));
						indicatorData.setIndicatorType(new IndicatorType());
						indicatorData.getIndicatorType().setId(rs.getString("indicator_type_id"));
						indicatorData.getIndicatorType().setName(rs.getString("indicator_type_name"));
						indicatorData.setSource(new Source());
						indicatorData.getSource().setId(rs.getString("source_id"));
						indicatorData.getSource().setName_en(rs.getString("source_name_en"));
						indicatorData.getSource().setName_es(rs.getString("source_name_es"));
						indicatorData.getSource().setUrl(rs.getString("source_url"));
						indicatorData.setYear(rs.getString("year_"));
					}
					if (rs.getString("year_") != null) {
						if (indicatorData.getDemographicData().isEmpty() 
							|| dataPoint == null 
							|| !StringUtils.equals(rs.getString("demographic_filter_option_id"), dataPoint.getDemographicFilter().getId())) {
							
								dataPoint = new CommunityDataPointMultiple();
								dataPoint.setIndicatorType(indicatorData.getIndicatorType());
								dataPoint.setAggregable(indicatorData.getIndicator().isAggregable());
								dataPoint.setRatePer(indicatorData.getIndicator().getRatePer());
								indicatorData.getDemographicData().add(dataPoint);
						}
						
						dataPoint.setDemographicFilter(new FilterOption());
						dataPoint.getDemographicFilter().setId(rs.getString("demographic_filter_option_id"));
						dataPoint.getDemographicFilter().setTypeId(FilterTypes.RACE.getId());
						dataPoint.getDemographicFilter().setName_en(rs.getString("demographic_filter_name_en"));
						dataPoint.getDemographicFilter().setName_es(rs.getString("demographic_filter_name_es"));
						dataPoint.getSuppresseds().put(rs.getString("location_id"), rs.getBoolean("suppress"));
						
						dataPoint.getValues().put(rs.getString("location_id"), rs.getBigDecimal("indicator_value"));
						if (rs.wasNull()) {
							dataPoint.getValues().put(rs.getString("location_id"), null);
						}
						dataPoint.getUniverseValues().put(rs.getString("location_id"), rs.getBigDecimal("universe_value"));
						if (rs.wasNull()) {
							dataPoint.getUniverseValues().put(rs.getString("location_id"), null);
						}
						dataPoint.getCountValues().put(rs.getString("location_id"), rs.getBigDecimal("count_value"));
						if (rs.wasNull()) {
							dataPoint.getCountValues().put(rs.getString("location_id"), null);
						}
						dataPoint.getUniverseMoes().put(rs.getString("location_id"), rs.getBigDecimal("universe_moe"));
						if (rs.wasNull()) {
							dataPoint.getUniverseMoes().put(rs.getString("location_id"), null);
						}
						dataPoint.getCountMoes().put(rs.getString("location_id"), rs.getBigDecimal("count_moe"));
						if (rs.wasNull()) {
							dataPoint.getCountMoes().put(rs.getString("location_id"), null);
						}
						BigDecimal value = dataPoint.getValues().get(rs.getString("location_id"));
						BigDecimal moeHigh = rs.getBigDecimal("moe_high");
						if (value != null && !rs.wasNull()) {
							dataPoint.getValueMoes().put(rs.getString("location_id"), moeHigh.subtract(value));
						} else {
							dataPoint.getValueMoes().put(rs.getString("location_id"), null);
						}
					}
				}

				return communityData;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CommunityLocation> getCommunityLocations(String locationType) {
		String sql = ""
			+ " select id_, location_type_id, name_en, name_es, geojson "
			+ " from ( "
			+ " 	select l.id_, l.location_type_id, l.name_en, l.name_es, "
			+ " 		g.geojson, rank() over(order by g.vintage_min_year desc) as year_rank "
			+ " 	from tbl_locations l "
			+ " 		join tbl_location_geometries g on g.location_id = l.id_ and g.location_type_id = l.location_type_id "
			+ " 	where l.location_type_id = :location_type::numeric "
			+ " ) ranked_geos "
			+ " where year_rank = 1 "
			+ " order by id_::numeric ";
		

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_type", locationType);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<CommunityLocation>() {
			@Override
			public CommunityLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommunityLocation location = new CommunityLocation();
				location.setId(rs.getString("id_"));
				location.setTypeId(rs.getString("location_type_id"));
				location.setName_en(rs.getString("name_en"));
				location.setName_es(rs.getString("name_es"));
				location.setGeojson(rs.getString("geojson"));
				return location;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommunityLocation getCommunityLocation(String location, String locationType) {
		String sql = ""
			+ " select id_, location_type_id, name_en, name_es, geojson "
			+ " from ( "
			+ " 	select l.id_, l.location_type_id, l.name_en, l.name_es, "
			+ " 		g.geojson, rank() over(order by g.vintage_min_year desc) as year_rank "
			+ " 	from tbl_locations l "
			+ " 		join tbl_location_geometries g on g.location_id = l.id_ and g.location_type_id = l.location_type_id "
			+ " 	where l.id_ = :location and l.location_type_id = :location_type::numeric "
			+ " ) ranked_geos "
			+ " where year_rank = 1 ";
		

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location", location);
		paramMap.addValue("location_type", locationType);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, new RowMapper<CommunityLocation>() {
			@Override
			public CommunityLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommunityLocation location = new CommunityLocation();
				location.setId(rs.getString("id_"));
				location.setTypeId(rs.getString("location_type_id"));
				location.setName_en(rs.getString("name_en"));
				location.setName_es(rs.getString("name_es"));
				location.setGeojson(rs.getString("geojson"));
				return location;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CommunityLocation getCustomCommunityLocation(String location) {
		String sql = ""
			+ " select id_, '7' as location_type_id, name_ as name_en, name_ as name_es, geojson "
			+ " from tbl_custom_locations "
			+ " where id_ = :location ";
		

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location", location);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, new RowMapper<CommunityLocation>() {
			@Override
			public CommunityLocation mapRow(ResultSet rs, int rowNum) throws SQLException {
				CommunityLocation location = new CommunityLocation();
				location.setId(rs.getString("id_"));
				location.setTypeId(rs.getString("location_type_id"));
				location.setName_en(rs.getString("name_en"));
				location.setName_es(rs.getString("name_es"));
				location.setGeojson(rs.getString("geojson"));
				return location;
			}
		});
	}
}
