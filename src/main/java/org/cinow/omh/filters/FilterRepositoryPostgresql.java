package org.cinow.omh.filters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class FilterRepositoryPostgresql implements FilterRepository {

	/**
	 * The jdbc template.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * The named parameter jdbc template.
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Filter getLocationTypeFilter(String indicatorId) {
		String sql = ""
			+ " select id_, name_en, name_es "
			+ " from tbl_location_types "
			+ " where exists (select 1 from tbl_indicator_values where indicator_id = :indicator_id::numeric and location_type_id = id_) "
			+ " order by sort_order ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<Filter>() {
			@Override
			public Filter extractData(ResultSet rs) throws SQLException, DataAccessException {
				Filter filter = new Filter();
				filter.setType(new FilterType());
				filter.getType().setName_en("Location Type");
				filter.getType().setName_es("Location Type (es)");
				while (rs.next()) {
					FilterOption option = new FilterOption();
					option.setId(rs.getString("id_"));
					option.setName_en(rs.getString("name_en"));
					option.setName_es(rs.getString("name_es"));
					filter.getOptions().add(option);
				}
				return filter;
			}	
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Filter getLocationFilter() {
		String sql = ""
			+ " select l.id_, l.location_type_id, l.name_en, l.name_es "
			+ " from tbl_locations l "
			+ " 	join tbl_location_types lt on lt.id_ = l.location_type_id "
			+ " order by lt.sort_order, l.id_::numeric ";

		return this.jdbcTemplate.query(sql, new ResultSetExtractor<Filter>() {
			@Override
			public Filter extractData(ResultSet rs) throws SQLException, DataAccessException {
				Filter filter = new Filter();
				filter.setType(new FilterType());
				filter.getType().setId("l");
				filter.getType().setName_en("Location");
				filter.getType().setName_es("Location (es)");
				while (rs.next()) {
					FilterOption option = new FilterOption();
					option.setId(rs.getString("id_"));
					option.setName_en(rs.getString("name_en"));
					option.setName_es(rs.getString("name_es"));
					option.setTypeId(rs.getString("location_type_id"));
					filter.getOptions().add(option);
				}
				return filter;
			}	
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Filter getYearFilter(String indicatorId) {
		String sql = ""
			+ " select distinct year_ "
			+ " from mv_indicator_years "
			+ " where indicator_id = :indicator_id::numeric "
			+ " order by year_ desc ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<Filter>() {
			@Override
			public Filter extractData(ResultSet rs) throws SQLException, DataAccessException {
				Filter filter = new Filter();
				filter.setType(new FilterType());
				filter.getType().setId("y");
				filter.getType().setName_en("Year");
				filter.getType().setName_es("Year (es)");
				while (rs.next()) {
					FilterOption option = new FilterOption();
					option.setId(rs.getString("year_"));
					option.setName_en(rs.getString("year_"));
					option.setName_es(rs.getString("year_"));
					filter.getOptions().add(option);
				}
				return filter;
			}	
		});
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<IndicatorFilter> getIndicatorFilters(String indicatorId) {
		String sql = ""
			+ " select distinct type_id, type_name_en, type_name_es, "
			+ " 	option_id, option_name_en, option_name_es, sort_order "
			+ " from mv_indicator_filters "
			+ " where indicator_id = :indicator_id::numeric "
			+ " order by type_id, sort_order ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<IndicatorFilter>>() {
			@Override
			public List<IndicatorFilter> extractData(ResultSet rs)
					throws SQLException, DataAccessException {
				
				List<IndicatorFilter> filters = new ArrayList<>();
				IndicatorFilter filter = new IndicatorFilter();
				String typeId = "0";
				while (rs.next()) {
					if (!rs.getString("type_id").equals(typeId)) {
						typeId = rs.getString("type_id");
						filter = new IndicatorFilter();
						FilterType type = new FilterType();
						type.setId(rs.getString("type_id"));
						type.setName_en(rs.getString("type_name_en"));
						type.setName_es(rs.getString("type_name_es"));
						filter.setType(type);
						filters.add(filter);
					}
					FilterOption option = new FilterOption();
					option.setId(rs.getString("option_id"));
					option.setName_en(rs.getString("option_name_en"));
					option.setName_es(rs.getString("option_name_es"));
					filter.getOptions().add(option);
				}
				return filters;
			}	
		});
	}

	@Override
	public List<List<String>> getCompatibleFitlerTypeIds(String indicatorId, String filterTypeId) {
		String sql = " "
			+ " select * "
			+ " from mv_indicator_filter_combos "
			+ " where indicator_id = :indicator_id::numeric ";
		if(FilterTypes.RACE.getId().equals(filterTypeId)) {
				sql += " and race = true ";
		} else if (FilterTypes.AGE.getId().equals(filterTypeId)) {
			sql += " and age = true ";
		} else if (FilterTypes.SEX.getId().equals(filterTypeId)) {
			sql += " and sex = true ";
		} else if (FilterTypes.INCOME.getId().equals(filterTypeId)) {
			sql += " and income = true ";
		} else if (FilterTypes.EDUCATION.getId().equals(filterTypeId)) {
			sql += " and education = true ";
		}

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);
		paramMap.addValue("filter_type_id", filterTypeId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<List<List<String>>>() {
			@Override
			public List<List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<List<String>> combos = new ArrayList<>();
				while (rs.next()) {
					List<String> combo = new ArrayList<>();
					if (rs.getBoolean("race")) {
						combo.add(FilterTypes.RACE.getId());
					}
					if (rs.getBoolean("age")) {
						combo.add(FilterTypes.AGE.getId());
					}
					if (rs.getBoolean("sex")) {
						combo.add(FilterTypes.SEX.getId());
					}
					if (rs.getBoolean("income")) {
						combo.add(FilterTypes.INCOME.getId());
					}
					if (rs.getBoolean("education")) {
						combo.add(FilterTypes.EDUCATION.getId());
					}
					combos.add(combo);
				}
				return combos;
			}
		});
	}	
}
