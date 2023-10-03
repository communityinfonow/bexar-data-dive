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
			+ "   case when exists (select 1 from mv_indicator_metadata where indicator_id = id_ and has_data = true limit 1) then true else false end as has_data, "
			+ "   case when exists (select 1 from mv_indicator_metadata where indicator_id = id_ and has_moe = true limit 1) then true else false end as has_moe, "
			+ "   is_aggregable, show_points, show_report "
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
				indicator.setHasMoe(rs.getBoolean("has_moe"));
				indicator.setAggregable(rs.getBoolean("is_aggregable"));
				indicator.setShowPoints(rs.getBoolean("show_points"));
				indicator.setShowReport(rs.getBoolean("show_report"));

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
			+ "   name_en, name_es, description_en, description_es, show_points, show_report, "
			+ "   case when exists (select 1 from mv_indicator_metadata where indicator_id = id_ and has_data = true limit 1) then true else false end as has_data, "
			+ "   case when exists (select 1 from mv_indicator_metadata where indicator_id = id_ and has_moe = true limit 1) then true else false end as has_moe, "
			+ "   is_aggregable "
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
				indicator.setShowPoints(rs.getBoolean("show_points"));
				indicator.setShowReport(rs.getBoolean("show_report"));
				indicator.setHasData(rs.getBoolean("has_data"));
				indicator.setHasMoe(rs.getBoolean("has_moe"));
				indicator.setAggregable(rs.getBoolean("is_aggregable"));

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
			+ "   name_en, name_es, description_en, description_es, show_points, show_report "
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
				indicator.setShowPoints(rs.getBoolean("show_points"));
				indicator.setShowReport(rs.getBoolean("show_report"));

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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Indicator> findIndicators() {
		String sql = ""
			+ " select id_, indicator_category_id, indicator_type_id, name_en, name_es, description_en, description_es, source_id, featured, display, rate_per, is_aggregable, show_points, show_report "
			+ " from tbl_indicators "
			+ " order by id_";

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
				indicator.setSourceId(rs.getString("source_id"));
				indicator.setFeatured(rs.getBoolean("featured"));
				indicator.setDisplay(rs.getBoolean("display"));
				indicator.setRatePer(rs.getInt("rate_per"));
				indicator.setAggregable(rs.getBoolean("is_aggregable"));
				indicator.setShowPoints(rs.getBoolean("show_points"));
				indicator.setShowReport(rs.getBoolean("show_report"));

				return indicator;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IndicatorType> findIndicatorTypes() {
		String sql = ""
			+ " select id_, name_ "
			+ " from tbl_indicator_types ";

		return this.jdbcTemplate.query(sql, new RowMapper<IndicatorType>() {
			@Override
			public IndicatorType mapRow(ResultSet rs, int rowNum) throws SQLException {
				IndicatorType type = new IndicatorType();
				type.setId(rs.getString("id_"));
				type.setName(rs.getString("name_"));
				
				return type;
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addIndicator(Indicator indicator, String username) {
		String sql = ""
			+ " insert into tbl_indicators (id_, indicator_category_id, indicator_type_id, name_en, name_es, description_en, description_es, source_id, featured, display, user_modified, rate_per, is_aggregable) "
			+ " values (:id::numeric, :indicator_category_id::numeric, :indicator_type_id::numeric, :name_en, :name_es, :description_en, :description_es, :source_id::numeric, :featured, :display, :username, :rate_per, :is_aggregable) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", indicator.getId());
		paramMap.addValue("indicator_category_id", indicator.getCategoryId());
		paramMap.addValue("indicator_type_id", indicator.getTypeId());
		paramMap.addValue("name_en", indicator.getName_en());
		paramMap.addValue("name_es", indicator.getName_es());
		paramMap.addValue("description_en", indicator.getDescription_en());
		paramMap.addValue("description_es", indicator.getDescription_es());
		paramMap.addValue("source_id", indicator.getSourceId());
		paramMap.addValue("featured", indicator.isFeatured());
		paramMap.addValue("display", indicator.isDisplay());
		paramMap.addValue("username", username);
		paramMap.addValue("rate_per", indicator.getRatePer());
		paramMap.addValue("is_aggregable", indicator.isAggregable());

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateIndicator(Indicator indicator, String username) {
		String sql = ""
			+ " update tbl_indicators set "
			+ "   indicator_category_id = :indicator_category_id::numeric, "
			+ "   indicator_type_id = :indicator_type_id::numeric, "
			+ "   name_en = :name_en, "
			+ "   name_es = :name_es, "
			+ "   description_en = :description_en, "
			+ "   description_es = :description_es, "
			+ "   source_id = :source_id::numeric, "
			+ "   featured = :featured, "
			+ "   display = :display, "
			+ "   user_modified = :username, "
			+ "   date_modified = current_timestamp, "
			+ "   rate_per = :rate_per, "
			+ "   is_aggregable = :is_aggregable "
			+ " where id_ = :id::numeric ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_category_id", indicator.getCategoryId());
		paramMap.addValue("indicator_type_id", indicator.getTypeId());
		paramMap.addValue("name_en", indicator.getName_en());
		paramMap.addValue("name_es", indicator.getName_es());
		paramMap.addValue("description_en", indicator.getDescription_en());
		paramMap.addValue("description_es", indicator.getDescription_es());
		paramMap.addValue("source_id", indicator.getSourceId());
		paramMap.addValue("featured", indicator.isFeatured());
		paramMap.addValue("display", indicator.isDisplay());
		paramMap.addValue("username", username);
		paramMap.addValue("id", indicator.getId());
		paramMap.addValue("rate_per", indicator.getRatePer());
		paramMap.addValue("is_aggregable", indicator.isAggregable());

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}
}
