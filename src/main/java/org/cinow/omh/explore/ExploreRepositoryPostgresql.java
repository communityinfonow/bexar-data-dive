package org.cinow.omh.explore;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.cinow.omh.filters.FilterTypes;
import org.cinow.omh.indicators.IndicatorType;
import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class ExploreRepositoryPostgresql implements ExploreRepository {

	/**
	 * The named parameter jdbc template.
	 */
	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExploreDataLocation<ExploreDataPoint>> getExploreData(ExploreDataRequest dataRequest, boolean allLocations) {
		String sql = ""
			+ " with trend_interval as (select trend_interval from tbl_sources where id_ = (select source_id from tbl_indicators where id_ = :indicator::numeric)) "
			+ " select l.id_ as l_id, l.name_en as l_name_en, l.name_es as l_name_es, "
			+ "   lt.id_ as lt_id, lt.name_en as lt_name_en, lt.name_es as lt_name_es, "
			+ "   lg.geojson as lg_geojson, "
			+ "   iv.year_ as iv_year, round(iv.indicator_value, 1) as iv_indicator_value, iv.suppress as iv_suppressed, round(iv.moe_low, 1) as iv_moe_low, round(iv.moe_high, 1) as iv_moe_high, round(iv.universe_value, 1) as iv_universe_value "
			+ " from tbl_locations l "
			+ "   join tbl_location_types lt on lt.id_ = l.location_type_id and lt.id_ = :location_type_id::numeric "
			+ "   left join tbl_location_geometries lg on lg.location_id = l.id_ "
			+ "     and lg.location_type_id = lt.id_ "
			+ "     and ((lg.vintage_min_year is null and lg.vintage_max_year is null) or (:year::numeric between lg.vintage_min_year and lg.vintage_max_year)) "
			+ "   left join tbl_indicator_values iv on iv.location_id = l.id_ "
			+ "     and iv.location_type_id = lt.id_ "
			+ "     and iv.indicator_id = :indicator::numeric "
			+ "     and mod(:year::numeric - iv.year_::numeric, coalesce((select trend_interval from trend_interval), 1)) = 0 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("year", dataRequest.getFilters().getYear());
		paramMap.addValue("indicator", dataRequest.getIndicator());
		paramMap.addValue("location_type_id", dataRequest.getFilters().getLocationType());
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.RACE.getId()) != null) {
			sql += "     and (iv.race_id = :race::numeric or iv.race_id is null and :race::numeric is null) ";
			paramMap.addValue("race", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.RACE.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.AGE.getId()) != null) {
			sql += "     and (iv.age_id = :age::numeric or iv.age_id is null and :age::numeric is null) ";
			paramMap.addValue("age", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.AGE.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.SEX.getId()) != null) {
			sql += "     and (iv.sex_id = :sex::numeric or iv.sex_id is null and :sex::numeric is null) ";
			paramMap.addValue("sex", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.SEX.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.EDUCATION.getId()) != null) {
			sql += "     and (iv.education_id = :education::numeric or iv.education_id is null and :education::numeric is null) ";
			paramMap.addValue("education", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.EDUCATION.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.INCOME.getId()) != null) {
			sql += "     and (iv.income_id = :income::numeric or iv.income_id is null and :income::numeric is null) ";
			paramMap.addValue("income", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.INCOME.getId()).getId());
		}
		if (!allLocations) {
			sql += " where l.id_ = :location_id ";
			paramMap.addValue("location_id", dataRequest.getFilters().getLocation());
		}
		sql += " order by lt.id_, l.id_, iv.year_ ";
			
		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<ExploreDataLocation<ExploreDataPoint>>>() {
			@Override
			public List<ExploreDataLocation<ExploreDataPoint>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				String currentLocationId = "-1";
				String currentLocationTypeId = "-1";
				List<ExploreDataLocation<ExploreDataPoint>> locationDataList = new ArrayList<>();
				ExploreDataLocation<ExploreDataPoint> locationData = new ExploreDataLocation<>();
				while (rs.next()) {
					if (!rs.getString("l_id").equals(currentLocationId) || !rs.getString("lt_id").equals(currentLocationTypeId)) {
						currentLocationId = rs.getString("l_id");
						currentLocationTypeId = rs.getString("lt_id");
						
						locationData = new ExploreDataLocation<>();
						locationDataList.add(locationData);

						Location location = new Location();
						location.setId(rs.getString("l_id"));
						location.setTypeId(rs.getString("lt_id"));
						location.setName_en(rs.getString("l_name_en"));
						location.setName_es(rs.getString("l_name_es"));
						locationData.setLocation(location);

						LocationType locationType = new LocationType();
						locationType.setId(rs.getString("lt_id"));
						locationType.setName_en(rs.getString("lt_name_en"));
						locationType.setName_es(rs.getString("lt_name_es"));
						locationData.setLocationType(locationType);
						locationData.setGeojson(rs.getString("lg_geojson"));
						locationData.setYearData(new LinkedHashMap<>());
					}
					if (rs.getString("iv_year") != null) {
						ExploreDataPointSingle dataPoint = new ExploreDataPointSingle();
						dataPoint.setSuppressed(rs.getBoolean("iv_suppressed"));
						if (!dataPoint.isSuppressed()) {
							dataPoint.setValue(rs.getBigDecimal("iv_indicator_value"));
							if (rs.wasNull()) {
								dataPoint.setValue(null);
							}
							dataPoint.setMoeLow(rs.getBigDecimal("iv_moe_low"));
							if (rs.wasNull()) {
								dataPoint.setMoeLow(null);
							}
							dataPoint.setMoeHigh(rs.getBigDecimal("iv_moe_high"));
							if (rs.wasNull()) {
								dataPoint.setMoeHigh(null);
							}
							dataPoint.setUniverseValue(rs.getBigDecimal("iv_universe_value"));
							if (rs.wasNull()) {
								dataPoint.setUniverseValue(null);
							}
						}

						locationData.getYearData().put(rs.getString("iv_year"), dataPoint);
					}
				}
				
				return locationDataList;
			}
		});
	}

	@Override
	public ExploreDataLocation<ExploreDataPoint> getCustomExploreData(ExploreDataRequest dataRequest, boolean allLocations) {
		String sql = ""
			+ " with trend_interval as (select trend_interval from tbl_sources where id_ = (select source_id from tbl_indicators where id_ = :indicator::numeric)) "
			+ " select l.id_ as l_id, l.name_en as l_name_en, l.name_es as l_name_es, "
			+ "   7 as lt_id, 'Custom' as lt_name_en, 'Personalizadas' as lt_name_es, "
			+ "   cl.id_ as cl_id, cl.name_ as cl_name, cl.geojson as lg_geojson, "
			+ "   i.indicator_type_id as it_id, i.is_aggregable, i.rate_per, "
			+ "   iv.year_ as iv_year, round(iv.indicator_value, 1) as iv_indicator_value, iv.suppress as iv_suppressed, round(iv.moe_low, 1) as iv_moe_low, round(iv.moe_high, 1) as iv_moe_high, round(iv.universe_value, 1) as iv_universe_value, iv.count_value as iv_count_value, iv.universe_moe as iv_universe_moe, iv.count_moe as iv_count_moe "
			+ " from tbl_custom_locations cl "
			+ "   join tbl_locations l on cl.location_ids @> array[l.id_] "
			+ "   join tbl_location_types lt on lt.id_ = l.location_type_id and lt.id_ = cl.location_type_id "
			+ "   join tbl_indicators i on i.id_ = :indicator::numeric "
			+ "   left join tbl_indicator_values iv on iv.location_id = l.id_ "
			+ "     and iv.location_type_id = lt.id_ "
			+ "     and iv.indicator_id = :indicator::numeric "
			+ "     and mod(:year::numeric - iv.year_::numeric, coalesce((select trend_interval from trend_interval), 1)) = 0 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("year", dataRequest.getFilters().getYear());
		paramMap.addValue("indicator", dataRequest.getIndicator());
		paramMap.addValue("location_type_id", dataRequest.getFilters().getLocationType());
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.RACE.getId()) != null) {
			sql += "     and (iv.race_id = :race::numeric or iv.race_id is null and :race::numeric is null) ";
			paramMap.addValue("race", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.RACE.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.AGE.getId()) != null) {
			sql += "     and (iv.age_id = :age::numeric or iv.age_id is null and :age::numeric is null) ";
			paramMap.addValue("age", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.AGE.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.SEX.getId()) != null) {
			sql += "     and (iv.sex_id = :sex::numeric or iv.sex_id is null and :sex::numeric is null) ";
			paramMap.addValue("sex", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.SEX.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.EDUCATION.getId()) != null) {
			sql += "     and (iv.education_id = :education::numeric or iv.education_id is null and :education::numeric is null) ";
			paramMap.addValue("education", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.EDUCATION.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.INCOME.getId()) != null) {
			sql += "     and (iv.income_id = :income::numeric or iv.income_id is null and :income::numeric is null) ";
			paramMap.addValue("income", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.INCOME.getId()).getId());
		}
		if (!allLocations) {
			sql += " where cl.id_ = :location_id ";
			paramMap.addValue("location_id", dataRequest.getFilters().getLocation());
		}
		sql += " order by iv.year_, lt.id_, l.id_ ";
			
		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<ExploreDataLocation<ExploreDataPoint>>() {
			@Override
			public ExploreDataLocation<ExploreDataPoint> extractData(ResultSet rs) throws SQLException, DataAccessException {
				String currentYear = "-1";
				ExploreDataLocation<ExploreDataPoint> locationData = new ExploreDataLocation<>();
				ExploreDataPointMultiple dataPoint = new ExploreDataPointMultiple();
				while (rs.next()) {
					if ("-1".equals(currentYear)) {
						Location location = new Location();
						location.setId(rs.getString("cl_id"));
						location.setTypeId(rs.getString("lt_id"));
						location.setName_en(rs.getString("cl_name"));
						location.setName_es(rs.getString("cl_name"));
						locationData.setLocation(location);

						LocationType locationType = new LocationType();
						locationType.setId(rs.getString("lt_id"));
						locationType.setName_en(rs.getString("lt_name_en"));
						locationType.setName_es(rs.getString("lt_name_es"));
						locationData.setLocationType(locationType);

						locationData.setGeojson(rs.getString("lg_geojson"));
						locationData.setYearData(new LinkedHashMap<>());
					}
					if (rs.getString("iv_year") != null && !currentYear.equals(rs.getString("iv_year"))) {
						currentYear = rs.getString("iv_year");
						dataPoint = new ExploreDataPointMultiple();
						IndicatorType indicatorType = new IndicatorType();
						indicatorType.setId(rs.getString("it_id"));
						dataPoint.setIndicatorType(indicatorType);
						dataPoint.setAggregable(rs.getBoolean("is_aggregable"));
						dataPoint.setRatePer(rs.getInt("rate_per"));
						locationData.getYearData().put(rs.getString("iv_year"), dataPoint);
					}
					if (rs.getString("iv_year") != null) {
						dataPoint.getSuppresseds().put(rs.getString("l_id"), rs.getBoolean("iv_suppressed"));
						
						dataPoint.getValues().put(rs.getString("l_id"), rs.getBigDecimal("iv_indicator_value"));
						if (rs.wasNull()) {
							dataPoint.getValues().put(rs.getString("l_id"), null);
						}
						dataPoint.getUniverseValues().put(rs.getString("l_id"), rs.getBigDecimal("iv_universe_value"));
						if (rs.wasNull()) {
							dataPoint.getUniverseValues().put(rs.getString("l_id"), null);
						}
						dataPoint.getCountValues().put(rs.getString("l_id"), rs.getBigDecimal("iv_count_value"));
						if (rs.wasNull()) {
							dataPoint.getCountValues().put(rs.getString("l_id"), null);
						}
						dataPoint.getUniverseMoes().put(rs.getString("l_id"), rs.getBigDecimal("iv_universe_moe"));
						if (rs.wasNull()) {
							dataPoint.getUniverseMoes().put(rs.getString("l_id"), null);
						}
						dataPoint.getCountMoes().put(rs.getString("l_id"), rs.getBigDecimal("iv_count_moe"));
						if (rs.wasNull()) {
							dataPoint.getCountMoes().put(rs.getString("l_id"), null);
						}
						BigDecimal value = dataPoint.getValues().get(rs.getString("l_id"));
						BigDecimal moeHigh = rs.getBigDecimal("iv_moe_high");
						if (value != null && !rs.wasNull()) {
							dataPoint.getValueMoes().put(rs.getString("l_id"), moeHigh.subtract(value));
						} else {
							dataPoint.getValueMoes().put(rs.getString("l_id"), null);
						}
					}
				}
				
				return locationData;
			}
		});
	}

	@Override
	public List<PointCollection> getPoints() {
		String sql = ""
			+ " select pt.id_ as pt_id, pt.name_en as pt_name_en, pt.name_es as pt_name_es, pt.color as pt_color, "
			+ "   p.id_ as p_id, p.year_ as p_year, p.value_label_en as p_value_label_en, p.value_label_es as p_value_label_es, p.value_ as p_value, p.feature_properties_en as p_feature_properties_en, p.feature_properties_es as p_feature_properties_es, p.geojson as p_geojson "
			+ " from tbl_point_types pt "
			+ "   left join tbl_points p on p.point_type_id = pt.id_ and p.year_ = (select max(year_) from tbl_points where point_type_id = pt.id_) "
			+ " order by pt.sort_order ";

		return this.namedParameterJdbcTemplate.query(sql, new ResultSetExtractor<List<PointCollection>>() {
			@Override
			public List<PointCollection> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<PointCollection> pointCollections = new ArrayList<>();
				PointCollection currentPointCollection = null;
				PointType currentPointType = null;
				List<Point> currentPoints = null;
				while (rs.next()) {
					if (currentPointType == null || currentPointType.getId() != rs.getInt("pt_id")) {
						currentPointCollection = new PointCollection();
						currentPointType = new PointType();
						currentPointType.setId(rs.getInt("pt_id"));
						currentPointType.setName_en(rs.getString("pt_name_en"));
						currentPointType.setName_es(rs.getString("pt_name_es"));
						currentPointType.setColor(rs.getString("pt_color"));
						currentPointType.setYear(rs.getString("p_year"));
						currentPoints = new ArrayList<>();
						currentPointCollection.setPointType(currentPointType);
						currentPointCollection.setPoints(currentPoints);
						pointCollections.add(currentPointCollection);
					}
					if (rs.getString("p_geojson") != null) {
						Point point = new Point();
						point.setId(rs.getString("p_id"));
						point.setFeatureProperties_en(rs.getString("p_feature_properties_en"));
						point.setFeatureProperties_es(rs.getString("p_feature_properties_es"));
						point.setValueLabel_en(rs.getString("p_value_label_en"));
						point.setValueLabel_es(rs.getString("p_value_label_es"));
						point.setValue(rs.getInt("p_value"));
						point.setGeojson(rs.getString("p_geojson"));
						currentPoints.add(point);
					}
				}

				return pointCollections;
			}
		});
	}

	@Override
	public List<ExploreLocationReportItem> getLocationReport(int locationTypeId, String locationId) {
		String sql = ""
			+ " select * from ("
			+ " select indicator_name_en, indicator_name_es, year_, location_id, location_type_id, indicator_value, moe, sort, "
			+ "   dense_rank() over (partition by indicator_name_en, year_ order by year_ desc) as ranking "
			+ " from tbl_location_report "
			+ " where location_type_id = :location_type_id::numeric "
			+ "   and location_id = :location_id "
			+ " order by sort "
			+ " ) as ranked where ranking = 1 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_type_id", locationTypeId);
		paramMap.addValue("location_id", locationId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<ExploreLocationReportItem>() {
			@Override
			public ExploreLocationReportItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				ExploreLocationReportItem item = new ExploreLocationReportItem();
				item.setIndicatorName_en(rs.getString("indicator_name_en"));
				item.setIndicatorName_es(rs.getString("indicator_name_es"));
				item.setYear_(rs.getString("year_"));
				item.setLocationId(rs.getString("location_id"));
				item.setLocationTypeId(rs.getString("location_type_id"));
				item.setIndicatorValue(rs.getBigDecimal("indicator_value"));
				item.setMoe(rs.getBigDecimal("moe"));
				item.setSort(rs.getInt("sort"));
				
				return item;
			}
		});
	}
}
