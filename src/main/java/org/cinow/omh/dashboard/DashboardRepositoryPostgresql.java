package org.cinow.omh.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.cinow.omh.filters.FilterTypes;
import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DashboardRepositoryPostgresql implements DashboardRepository {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<DashboardDataLocation> getDashboardData(DashboardDataRequest dataRequest, boolean allLocations) {
		//TODO: testing for the following scenarios:
		// 1. 5-year trend intervals for ACS 5-year
		// 2. only trending years that are between the geom years for tracts
		String sql = ""
			+ " with trend_interval as (select trend_interval from tbl_sources where id_ = (select source_id from tbl_indicators where id_ = :indicator::numeric)) "
			+ " select l.id_ as l_id, l.name_en as l_name_en, l.name_es as l_name_es, "
			+ "   lt.id_ as lt_id, lt.name_en as lt_name_en, lt.name_es as lt_name_es, "
			+ "   lg.geojson as lg_geojson, "
			+ "   iv.year_ as iv_year, iv.indicator_value as iv_indicator_value, iv.moe_low as iv_moe_low, iv.moe_high as iv_moe_high, iv.universe_value as iv_universe_value "
			+ " from tbl_locations l "
			+ "   join tbl_location_types lt on lt.id_ = l.location_type_id and lt.id_ = :location_type_id::numeric "
			+ "   left join tbl_location_geometries lg on lg.location_id = l.id_ "
			+ "     and lg.location_type_id = lt.id_ "
			+ "     and ((lg.min_year is null and lg.max_year is null) or (:year::numeric between lg.min_year and lg.max_year)) "
			+ "   left join tbl_indicator_values iv on iv.location_id = l.id_ "
			+ "     and iv.location_type_id = lt.id_ "
			+ "     and iv.indicator_id = :indicator::numeric "
			+ "     and mod(:year::numeric - iv.year_::numeric, coalesce((select trend_interval from trend_interval), 1)) = 0 "
			+ "     and ((lg.min_year is null and lg.max_year is null) or (iv.year_::numeric between lg.min_year and lg.max_year)) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("year", dataRequest.getFilters().getYear());
		paramMap.addValue("indicator", dataRequest.getIndicator());
		paramMap.addValue("location_type_id", dataRequest.getFilters().getLocationType());
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.RACE.getId()) != null) {
			sql += "     and iv.race_id = :race::numeric ";
			paramMap.addValue("race", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.RACE.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.AGE.getId()) != null) {
			sql += "     and iv.age_id = :age::numeric ";
			paramMap.addValue("age", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.AGE.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.SEX.getId()) != null) {
			sql += "     and iv.sex_id = :sex::numeric ";
			paramMap.addValue("sex", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.SEX.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.EDUCATION.getId()) != null) {
			sql += "     and iv.education_id = :education::numeric ";
			paramMap.addValue("education", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.EDUCATION.getId()).getId());
		}
		if (dataRequest.getFilters().getIndicatorFilters().get(FilterTypes.INCOME.getId()) != null) {
			sql += "     and iv.income_id = :income::numeric ";
			paramMap.addValue("income", dataRequest.getFilters().getIndicatorFilters()
				.get(FilterTypes.INCOME.getId()).getId());
		}
		if (!allLocations) {
			sql += " where l.id_ = :location_id ";
			paramMap.addValue("location_id", dataRequest.getFilters().getLocation());
		}
		sql += " order by lt.id_, l.id_, iv.year_ ";
			
		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<DashboardDataLocation>>() {
			@Override
			public List<DashboardDataLocation> extractData(ResultSet rs) throws SQLException, DataAccessException {
				String currentLocationId = "-1";
				String currentLocationTypeId = "-1";
				List<DashboardDataLocation> locationDataList = new ArrayList<>();
				DashboardDataLocation locationData = new DashboardDataLocation();
				while (rs.next()) {
					if (!rs.getString("l_id").equals(currentLocationId) || !rs.getString("lt_id").equals(currentLocationTypeId)) {
						currentLocationId = rs.getString("l_id");
						currentLocationTypeId = rs.getString("lt_id");
						
						locationData = new DashboardDataLocation();
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
						DashboardDataPoint dataPoint = new DashboardDataPoint();
						dataPoint.setValue(rs.getDouble("iv_indicator_value"));
						dataPoint.setMoeLow(rs.getDouble("iv_moe_low"));
						dataPoint.setMoeHigh(rs.getDouble("iv_moe_high"));
						dataPoint.setUniverseValue(rs.getDouble("iv_universe_value"));

						locationData.getYearData().put(rs.getString("iv_year"), dataPoint);
					}
				}
				
				return locationDataList;
			}
		});
	}
}
