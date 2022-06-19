package org.cinow.omh.dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.cinow.omh.filters.FilterRequest;
import org.cinow.omh.filters.FilterTypes;
import org.cinow.omh.locations.Location;
import org.cinow.omh.locations.LocationType;
import org.geojson.GeoJsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DashboardRepositoryPostgresql implements DashboardRepository {

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<DashboardDataLocation> getDashboardData(FilterRequest filterRequest) {
		//TODO: want this to get all years, but the year-bound geometries won't allow that. hmm...
		String sql = ""
			+ " select l.id_ as l_id, l.name_en as l_name_en, l.name_es as l_name_es, "
			+ "   lt.id_ as lt_id, lt.name_en as lt_name_en, lt.name_es as lt_name_es, "
			+ "   st_asgeojson(lg.geometry_) as lg_geometry, "
			+ "   iv.year_ as iv_year, iv.indicator_value as iv_indicator_value, iv.moe_low as iv_moe_low, iv.moe_high as iv_moe_high, iv.universe_value as iv_universe_value "
			+ " from tbl_locations l "
			+ "   join tbl_location_types lt on lt.id_ = l.location_type_id "
			+ "   left join tbl_location_geometries lg on lg.location_id = l.id_ "
			+ "     and lg.location_type_id = lt.id_ "
			+ "     and :year between lg.min_year and lg.max_year "
			+ "   left join tbl_indicator_values iv on iv.location_id = l.id_ "
			+ "     and iv.location_type_id = lt.id_ "
			+ "     and iv.indicator_id = :indicator ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("year", filterRequest.getYear());
		paramMap.addValue("indicator", filterRequest.getIndicator());
		if (filterRequest.getFilterTypes().contains(FilterTypes.RACE.getId())) {
			sql += "     and iv.race_id = :race ";
			paramMap.addValue("race", filterRequest.getFilterOptions()
				.get(filterRequest.getFilterTypes().indexOf(FilterTypes.RACE.getId())));
		}
		if (filterRequest.getFilterTypes().contains(FilterTypes.AGE.getId())) {
			sql += "     and iv.age_id = :age ";
			paramMap.addValue("age", filterRequest.getFilterOptions()
				.get(filterRequest.getFilterTypes().indexOf(FilterTypes.AGE.getId())));
		}
		if (filterRequest.getFilterTypes().contains(FilterTypes.SEX.getId())) {	
			sql += "     and iv.sex_id = :sex ";
			paramMap.addValue("sex", filterRequest.getFilterOptions()
				.get(filterRequest.getFilterTypes().indexOf(FilterTypes.SEX.getId())));
		}
		if (filterRequest.getFilterTypes().contains(FilterTypes.EDUCATION.getId())) {
			sql += "     and iv.education_id = :education ";
			paramMap.addValue("education", filterRequest.getFilterOptions()
				.get(filterRequest.getFilterTypes().indexOf(FilterTypes.EDUCATION.getId())));
		}
		if (filterRequest.getFilterTypes().contains(FilterTypes.INCOME.getId())) {
			sql += "     and iv.income_id = :income ";
			paramMap.addValue("income", filterRequest.getFilterOptions()
				.get(filterRequest.getFilterTypes().indexOf(FilterTypes.INCOME.getId())));
		}

		sql += " order by lt.id_, l.id_, iv.year_ ";
			
		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<DashboardDataLocation>>() {
			@Override
			public List<DashboardDataLocation> extractData(ResultSet rs) throws SQLException, DataAccessException {
				long currentLocationId = -1;
				long currentLocationTypeId = -1;
				List<DashboardDataLocation> locationDataList = new ArrayList<>();
				DashboardDataLocation locationData = new DashboardDataLocation();
				ObjectMapper objectMapper = new ObjectMapper();
				while (rs.next()) {
					if (rs.getLong("l_id") != currentLocationId && rs.getLong("lt_id") != currentLocationTypeId) {
						currentLocationId = rs.getLong("l_id");
						currentLocationTypeId = rs.getLong("lt_id");
						
						locationData = new DashboardDataLocation();
						locationDataList.add(locationData);

						Location location = new Location();
						location.setId(rs.getLong("l_id"));
						location.setTypeId(rs.getLong("lt_id"));
						location.setName_en(rs.getString("l_name_en"));
						location.setName_es(rs.getString("l_name_es"));
						locationData.setLocation(location);

						LocationType locationType = new LocationType();
						locationType.setId(rs.getLong("lt_id"));
						locationType.setName_en(rs.getString("lt_name_en"));
						locationType.setName_es(rs.getString("lt_name_es"));
						locationData.setLocationType(locationType);

						try {
							locationData.setGeometry(objectMapper.readValue(rs.getString("lg_geometry"), GeoJsonObject.class));
						} catch (JsonProcessingException|IllegalArgumentException e) {
							locationData.setGeometry(null);
						}
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
