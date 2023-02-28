package org.cinow.omh.filters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.lang.Nullable;
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
				filter.getType().setName_es("Tipo de Ubicación");
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
				filter.getType().setName_es("Ubicación");
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
				filter.getType().setName_es("Año");
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

	/**
	 * {@inheritDoc}}
	 */
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

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public Map<String, List<String>> getLocationTypeYears(String indicatorId) {
		String sql = ""
			+ " select unnest(location_types) as location_type, year_"
			+ " from mv_indicator_years "
			+ " where indicator_id = :indicator_id::numeric "
			+ " order by location_type, year_ ";
		
			MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", indicatorId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<Map<String, List<String>>>() {
			@Override
			@Nullable
			public Map<String, List<String>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, List<String>> locationTypeYears = new LinkedHashMap<>();
				while (rs.next()) {
					if (locationTypeYears.get(rs.getString("location_type")) == null) {
						locationTypeYears.put(rs.getString("location_type"), new ArrayList<>());
					}
					locationTypeYears.get(rs.getString("location_type")).add(rs.getString("year_"));
				}
				
				return locationTypeYears;
			}
		});
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<FilterType> findFilterTypes() {
		String sql = ""
			+ " select id_, name_en, name_es "
			+ " from tbl_filter_types "
			+ " order by id_ ";

		return this.jdbcTemplate.query(sql, new RowMapper<FilterType>() {
			@Override
			public FilterType mapRow(ResultSet rs, int rowNum) throws SQLException {
				FilterType type = new FilterType();
				type.setId(rs.getString("id_"));
				type.setName_en(rs.getString("name_en"));
				type.setName_es(rs.getString("name_es"));

				return type;
			}

		});
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<FilterOption> findFilterOptions() {
		String sql = ""
			+ " select id_, type_id, name_en, name_es, sort_order, display "
			+ " from tbl_filter_options "
			+ " order by type_id, sort_order ";

		return this.jdbcTemplate.query(sql, new RowMapper<FilterOption>() {
			@Override
			public FilterOption mapRow(ResultSet rs, int rowNum) throws SQLException {
				FilterOption option = new FilterOption();
				option.setId(rs.getString("id_"));
				option.setTypeId(rs.getString("type_id"));
				option.setName_en(rs.getString("name_en"));
				option.setName_es(rs.getString("name_es"));
				option.setSortOrder(rs.getInt("sort_order"));
				option.setDisplay(rs.getBoolean("display"));

				return option;
			}
			
		});
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void addFilterOption(FilterOption filterOption, String username) {
		String sql = ""
			+ " insert into tbl_filter_options (id_, type_id, name_en, name_es, sort_order, display, user_modified) "
			+ " values (:id::numeric, :type_id::numeric, :name_en, :name_es, :sort_order, :display, :username) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", filterOption.getId());
		paramMap.addValue("type_id", filterOption.getTypeId());
		paramMap.addValue("name_en", filterOption.getName_en());
		paramMap.addValue("name_es", filterOption.getName_es());
		paramMap.addValue("sort_order", filterOption.getSortOrder());
		paramMap.addValue("display", filterOption.isDisplay());
		paramMap.addValue("username", username);

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void updateFilterOption(FilterOption filterOption, String username) {
		String sql = ""
			+ " update tbl_filter_options set "
			+ "   type_id = :type_id::numeric, "
			+ "   name_en = :name_en, "
			+ "   name_es = :name_es, "
			+ "   sort_order = :sort_order, "
			+ "   display = :display, "
			+ "   user_modified = :username, "
			+ "   date_modified = current_timestamp "
			+ " where id_ = :id::numeric ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", filterOption.getId());
		paramMap.addValue("type_id", filterOption.getTypeId());
		paramMap.addValue("name_en", filterOption.getName_en());
		paramMap.addValue("name_es", filterOption.getName_es());
		paramMap.addValue("sort_order", filterOption.getSortOrder());
		paramMap.addValue("display", filterOption.isDisplay());
		paramMap.addValue("username", username);

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}	
}
