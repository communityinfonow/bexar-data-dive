package org.cinow.omh.indicators;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cinow.omh.sources.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import liquibase.pro.packaged.f;
import liquibase.pro.packaged.i;

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
	public List<FeaturedIndicator> getBexarDataFacts() {
		String sql = ""
			+ " with random_featured_indicators as ( "
			+ " select i.id_ "
			+ " from tbl_indicators i "
			+ " where i.featured = true "
			+ " and i.display = true "
			+ " order by random() "
			+ " limit 1 "
			+ " ), "
			+ " base_data as ( "
			+ " select * from ( "
			+ " select i.id_, i.indicator_category_id, i.indicator_type_id, "
			+ " case when c.parent_category_id is null then i.name_en else c.name_en || ' - ' || i.name_en end as name_en, "
			+ " case when c.parent_category_id is null then i.name_es else c.name_es || ' - ' || i.name_es end as name_es, "
			+ " i.description_en, i.description_es, i.show_points, i.show_report, "
			+ " s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es, "
			+ " round(iv.indicator_value, 1) as indicator_value, round(iv.moe_low, 1) as moe_low, round(iv.moe_high, 1) as moe_high, iv.suppress, iv.year_, "
			+ " rank() over (partition by i.id_ order by iv.year_ desc) as rank, "
			+ " null as race_en, null as race_es, null as age_en, null as age_es, null as sex_en, null as sex_es, null as income_en, null as income_es, null as education_en, null as education_es "
			+ " from random_featured_indicators rfi "
			+ " join tbl_indicators i on i.id_ = rfi.id_ "
			+ " join tbl_indicator_categories c on i.indicator_category_id = c.id_ "
			+ " join tbl_sources s on s.id_ = i.source_id "
			+ " join tbl_indicator_values iv on iv.indicator_id = i.id_ "
			+ " and iv.location_type_id = 1 and iv.location_id = '48029' "
			+ " and iv.race_id is null and iv.sex_id is null and iv.age_id is null and iv.income_id is null and iv.education_id is null "
			+ " ) ranked "
			+ " where rank = 1 "
			+ " ), "
			+ " filtered_data as ( "
			+ " select * from ( "
			+ " select i.id_, i.indicator_category_id, i.indicator_type_id, "
			+ " case when c.parent_category_id is null then i.name_en else c.name_en || ' - ' || i.name_en end as name_en, "
			+ " case when c.parent_category_id is null then i.name_es else c.name_es || ' - ' || i.name_es end as name_es, "
			+ " i.description_en, i.description_es, i.show_points, i.show_report, "
			+ " s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es, "
			+ " round(iv.indicator_value, 1) as indicator_value, round(iv.moe_low, 1) as moe_low, round(iv.moe_high, 1) as moe_high, iv.suppress, iv.year_, "
			+ " rank() over (partition by i.id_ order by iv.year_ desc) as rank, "
			+ " r.name_en as race_en, r.name_es as race_es, a.name_en as age_en, a.name_es as age_es, x.name_en as sex_en, x.name_es as sex_es, n.name_en as income_en, n.name_es as income_es, e.name_en as education_en, e.name_es as education_es"
			+ " from random_featured_indicators rfi "
			+ " join tbl_indicators i on i.id_ = rfi.id_ "
			+ " join tbl_indicator_categories c on i.indicator_category_id = c.id_ "
			+ " join tbl_sources s on s.id_ = i.source_id "
			+ " join tbl_indicator_values iv on iv.indicator_id = i.id_ "
			+ " and iv.location_type_id = 1 and iv.location_id = '48029' "
			+ " and not (iv.race_id is null and iv.sex_id is null and iv.age_id is null and iv.income_id is null and iv.education_id is null) "
			+ " left join tbl_filter_options r on r.type_id = 1 and  r.id_ = iv.race_id "
			+ " left join tbl_filter_options a on a.type_id = 2 and a.id_ = iv.age_id "
			+ " left join tbl_filter_options x on x.type_id = 3 and x.id_ = iv.sex_id "
			+ " left join tbl_filter_options n on n.type_id = 5 and n.id_ = iv.income_id "
			+ " left join tbl_filter_options e on e.type_id = 4 and e.id_ = iv.education_id "
			+ " ) ranked "
			+ " where rank = 1 "
			+ " order by random() "
			+ " limit 2 "
			+ " ) "
			+ " select * from base_data "
			+ " union all "
			+ " select * from filtered_data ";

		return this.jdbcTemplate.query(sql, new ResultSetExtractor<List<FeaturedIndicator>>() {
			@Override
			public List<FeaturedIndicator> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<FeaturedIndicator> featuredIndicators = new ArrayList<>();
				while (rs.next()) {
					FeaturedIndicator filteredIndicator = new FeaturedIndicator();
					filteredIndicator.setId(rs.getString("id_"));
					filteredIndicator.setCategoryId(rs.getString("indicator_category_id"));
					filteredIndicator.setTypeId(rs.getString("indicator_type_id"));
					filteredIndicator.setName_en(rs.getString("name_en"));
					filteredIndicator.setName_es(rs.getString("name_es"));
					filteredIndicator.setDescription_en(rs.getString("description_en"));
					filteredIndicator.setDescription_es(rs.getString("description_es"));
					filteredIndicator.setShowPoints(rs.getBoolean("show_points"));
					filteredIndicator.setShowReport(rs.getBoolean("show_report"));
					filteredIndicator.setSource(new Source());
					filteredIndicator.getSource().setId(rs.getString("source_id"));
					filteredIndicator.getSource().setName_en(rs.getString("source_name_en"));
					filteredIndicator.getSource().setName_es(rs.getString("source_name_es"));
					filteredIndicator.setValue(rs.getBigDecimal("indicator_value"));
					filteredIndicator.setMoeLow(rs.getBigDecimal("moe_low"));
					filteredIndicator.setMoeHigh(rs.getBigDecimal("moe_high"));
					filteredIndicator.setSuppressed(rs.getBoolean("suppress"));
					filteredIndicator.setYear(rs.getString("year_"));
					filteredIndicator.setRace_en(rs.getString("race_en"));
					filteredIndicator.setRace_es(rs.getString("race_es"));
					filteredIndicator.setAge_en(rs.getString("age_en"));
					filteredIndicator.setAge_es(rs.getString("age_es"));
					filteredIndicator.setSex_en(rs.getString("sex_en"));
					filteredIndicator.setSex_es(rs.getString("sex_es"));
					filteredIndicator.setIncome_en(rs.getString("income_en"));
					filteredIndicator.setIncome_es(rs.getString("income_es"));
					filteredIndicator.setEducation_en(rs.getString("education_en"));
					filteredIndicator.setEducation_es(rs.getString("education_es"));
					featuredIndicators.add(filteredIndicator);
				}

				return featuredIndicators;
			}

		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Indicator> getFeaturedIndicators() {
		String sql = ""
			+ " select * from ( "
			+ " select i.id_, i.indicator_category_id, i.indicator_type_id, "
			+ "   case when c.parent_category_id is null then i.name_en else c.name_en || ' - ' || i.name_en end as name_en, "
			+ "   case when c.parent_category_id is null then i.name_es else c.name_es || ' - ' || i.name_es end as name_es, "
			+ "   i.description_en, i.description_es, i.show_points, i.show_report, "
			+ "   s.id_ as source_id, s.name_en as source_name_en, s.name_es as source_name_es "
			+ " from tbl_indicators i "
			+ "   join tbl_indicator_categories c on i.indicator_category_id = c.id_ "
			+ "   join tbl_sources s on s.id_ = i.source_id "
			+ "   join mv_indicator_metadata im on i.id_ = im.indicator_id and location_type_id = 1 and im.has_data = true "
			+ " where i.featured = true "
			+ "   and i.display = true "
			+ " ) ranked "
			+ " order by random() "
			+ " limit 3";
		
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
				indicator.setSource(new Source());
				indicator.getSource().setId(rs.getString("source_id"));
				indicator.getSource().setName_en(rs.getString("source_name_en"));
				indicator.getSource().setName_es(rs.getString("source_name_es"));
				
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
				indicator.setSource(new Source());
				indicator.getSource().setId(rs.getString("source_id"));
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
