package org.cinow.omh.indicators;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class IndicatorRepositoryPostgresql implements IndicatorRepository {

	/**
	 * The JDBC template.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * The named parameter JDBC template.
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IndicatorCategory> findIndicatorCategories() {
		String sql = ""
			+ " select id_, name_en, name_es, parent_category_id "
			+ " from tbl_indicator_categories "
			+ " order by sort_order ";

		return this.jdbcTemplate.query(sql, new RowMapper<IndicatorCategory>() {
			@Override
			public IndicatorCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
				IndicatorCategory indicatorCategory = new IndicatorCategory();
				indicatorCategory.setId(rs.getString("id_"));
				indicatorCategory.setName_en(rs.getString("name_en"));
				indicatorCategory.setName_es(rs.getString("name_es"));
				indicatorCategory.setParentCategoryId(rs.getString("parent_category_id"));
				
				return indicatorCategory;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Indicator> findIndicatorsByCategory(String categoryId) {
		String sql = ""
			+ " select id_, indicator_type_id, indicator_category_id, name_en, name_es, description_en, description_es, "
			+ "   case when exists (select 1 from mv_indicator_metadata where indicator_id = id_ and has_data = true limit 1) then true else false end as has_data "
			+ " from tbl_indicators "
			+ " where indicator_category_id = :indicator_category_id::numeric "
			+ "   and display = true"
			+ " order by id_::numeric ";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_category_id", categoryId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<Indicator>() {
			@Override
			public Indicator mapRow(ResultSet rs, int rowNum) throws SQLException {
				Indicator indicator = new Indicator();
				indicator.setTypeId(rs.getString("indicator_type_id"));
				indicator.setCategoryId(rs.getString("indicator_category_id"));
				indicator.setId(rs.getString("id_"));
				indicator.setName_en(rs.getString("name_en"));
				indicator.setName_es(rs.getString("name_es"));
				indicator.setDescription_en(rs.getString("description_en"));
				indicator.setDescription_es(rs.getString("description_es"));
				indicator.setHasData(rs.getBoolean("has_data"));

				return indicator;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Indicator getIndicator(String id) {
		String sql = ""
			+ " select indicator_category_id, indicator_type_id, "
			+ "   name_en, name_es, description_en, description_es "
			+ " from tbl_indicators "
			+ " where id_ = :id::numeric "
			+ "   and display = true ";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, new RowMapper<Indicator>() {
			@Override
			public Indicator mapRow(ResultSet rs, int rowNum) throws SQLException {
				Indicator indicator = new Indicator();
				indicator.setId(id);
				indicator.setCategoryId(rs.getString("indicator_category_id"));
				indicator.setTypeId(rs.getString("indicator_type_id"));
				indicator.setName_en(rs.getString("name_en"));
				indicator.setName_es(rs.getString("name_es"));
				indicator.setDescription_en(rs.getString("description_en"));
				indicator.setDescription_es(rs.getString("description_es"));

				return indicator;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Indicator> getFeaturedIndicators() {
		String sql = ""
			+ " select id_, indicator_category_id, indicator_type_id, "
			+ "   name_en, name_es, description_en, description_es "
			+ " from tbl_indicators "
			+ " where featured = true "
			+ "   and display = true ";
		
		return this.jdbcTemplate.query(sql, new RowMapper<Indicator>() {
			@Override
			public Indicator mapRow(ResultSet rs, int rowNum) throws SQLException {
				Indicator indicator = new Indicator();
				indicator.setId(rs.getString("id_"));
				indicator.setCategoryId(rs.getString("indicator_category_id"));
				indicator.setTypeId(rs.getString("indicator_type_id"));
				indicator.setName_en(rs.getString("name_en"));
				indicator.setName_es(rs.getString("name_es"));
				indicator.setDescription_en(rs.getString("description_en"));
				indicator.setDescription_es(rs.getString("description_es"));

				return indicator;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IndicatorCategory getIndicatorCategory(String id) {
		String sql = ""
			+ " select ic.id_, ic.name_en, ic.name_es, ic.parent_category_id "
			+ " from tbl_indicator_categories ic "
			+ "   join tbl_indicators i on i.indicator_category_id = ic.id_ "
			+ " where i.id_ = :indicator_id::numeric ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", id);

		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, new RowMapper<IndicatorCategory>() {
			@Override
			public IndicatorCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
				IndicatorCategory category = new IndicatorCategory();
				category.setId(rs.getString("id_"));
				category.setName_en(rs.getString("name_en"));
				category.setName_es(rs.getString("name_es"));
				category.setParentCategoryId(rs.getString("parent_category_id"));

				return category;
			}
		});
	}

	@Override
	public boolean hasData(String id) {
		String sql = ""
			+ " select 1 "
			+ " from tbl_indicator_values "
			+ " where indicator_id = :indicator_id::numeric "
			+ " limit 1 ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_id", id);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new ResultSetExtractor<Boolean>() {
			@Override
			public Boolean extractData(ResultSet rs) throws SQLException, DataAccessException {
				return rs.next();
			}
		});
	}
}
