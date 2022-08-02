package org.cinow.omh.community;

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
	public List<CommunityDataCategory> getCommunityData(String location, String locationType) {
		String sql = ""
			+ " select * "
			+ " from ( "
			+ " select "
			+ "   ic.id_ as category_id, ic.parent_category_id, ic.name_en as category_name_en, ic.name_es as category_name_es, "
			+ "   i.id_ as indicator_id, i.name_en as indicator_name_en, i.name_es as indicator_name_es, i.base_filter_type_id, "
			+ "   it.id_ as indicator_type_id, it.name_ as indicator_type_name, "
			+ "   iv.year_, iv.indicator_value, iv.suppress, iv.moe_low, iv.moe_high, iv.universe_value, "
			+ "   s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es, s.url_ as source_url, "
			+ "   fr.id_ as race_filter_option_id, fr.name_en as race_filter_name_en, fr.name_es as race_filter_name_es, "
			+ "   fb.id_ as base_filter_option_id, fb.name_en as base_filter_name_en, fb.name_es as base_filter_name_es, "
			+ "   rank() over(partition by iv.indicator_id order by iv.year_ desc) "
			+ " from tbl_indicator_categories ic "
			+ "   join tbl_indicators i on i.indicator_category_id = ic.id_ "
			+ "   join tbl_indicator_types it on it.id_ = i.indicator_type_id "
			+ "   left join tbl_indicator_values iv on iv.indicator_id = i.id_ "
			+ "     and iv.location_id = :location_id and iv.location_type_id = :location_type_id::numeric "
			+ "   left join tbl_sources s on s.id_ = i.source_id "
			+ "   left join tbl_filter_options fr on fr.id_ = iv.race_id "
			+ "   left join tbl_filter_options fb on fb.id_ = case "
			+ "     when i.base_filter_type_id = 2 then iv.age_id "
			+ "     when i.base_filter_type_id = 3 then iv.sex_id "
			+ "     when i.base_filter_type_id = 4 then iv.education_id "
			+ "     when i.base_filter_type_id = 5 then iv.income_id "
			+ "   end "
			+ " order by ic.sort_order, i.id_, fb.sort_order, fr.sort_order "
			+ " ) ranked_data "
			+ " where rank = 1 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("location_id", location);
		paramMap.addValue("location_type_id", locationType);

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
						indicatorData.getIndicator().setBaseFilterTypeId(rs.getString("base_filter_type_id"));
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
						CommunityDataPoint dataPoint = new CommunityDataPoint();
						if (rs.getLong("base_filter_type_id") != 0) {
							dataPoint.setBaseFilter(new FilterOption());
							dataPoint.getBaseFilter().setId(rs.getString("base_filter_option_id"));
							dataPoint.getBaseFilter().setTypeId(rs.getString("base_filter_type_id"));
							dataPoint.getBaseFilter().setName_en(rs.getString("base_filter_name_en"));
							dataPoint.getBaseFilter().setName_es(rs.getString("base_filter_name_es"));
						}
						dataPoint.setRaceFilter(new FilterOption());
						dataPoint.getRaceFilter().setId(rs.getString("race_filter_option_id"));
						dataPoint.getRaceFilter().setTypeId(FilterTypes.RACE.getId());
						dataPoint.getRaceFilter().setName_en(rs.getString("race_filter_name_en"));
						dataPoint.getRaceFilter().setName_es(rs.getString("race_filter_name_es"));
						dataPoint.setSuppressed(rs.getBoolean("suppress"));
						if (!dataPoint.isSuppressed()) {
							dataPoint.setMoeHigh(rs.getDouble("moe_high"));
							if (rs.wasNull()) {
								dataPoint.setMoeHigh(null);
							}
							dataPoint.setMoeLow(rs.getDouble("moe_low"));
							if (rs.wasNull()) {
								dataPoint.setMoeLow(null);
							}
							dataPoint.setUniverseValue(rs.getDouble("universe_value"));
							if (rs.wasNull()) {
								dataPoint.setUniverseValue(null);
							}
							dataPoint.setValue(rs.getDouble("indicator_value"));
							if (rs.wasNull()) {
								dataPoint.setValue(null);
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
			+ " where year_rank = 1 ";
		

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
}
