package org.cinow.omh.indicators;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class IndicatorRepositoryPostgresql implements IndicatorRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<IndicatorCategory> findIndicatorCategories() {
		String sql = ""
			+ " select id_, name_en, name_es "
			+ " from tbl_indicator_categories "
			+ " order by sort_order ";

		return this.jdbcTemplate.query(sql, new RowMapper<IndicatorCategory>() {
			@Override
			public IndicatorCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
				IndicatorCategory indicatorCategory = new IndicatorCategory();
				indicatorCategory.setId(rs.getLong("id_"));
				indicatorCategory.setName_en(rs.getString("name_en"));
				indicatorCategory.setName_es(rs.getString("name_es"));
				
				return indicatorCategory;
			}
		});
	}

	@Override
	public List<Indicator> findIndicatorsByCategory(long categoryId) {
		String sql = ""
			+ " select id_, indicator_type_id, indicator_category_id, name_en, name_es, description_en, description_es "
			+ " from tbl_indicators "
			+ " where indicator_category_id = :indicator_category_id ";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("indicator_category_id", categoryId);

		return this.namedParameterJdbcTemplate.query(sql, paramMap, new RowMapper<Indicator>() {
			@Override
			public Indicator mapRow(ResultSet rs, int rowNum) throws SQLException {
				Indicator indicator = new Indicator();
				indicator.setTypeId(rs.getLong("indicator_type_id"));
				indicator.setCategoryId(rs.getLong("indicator_category_id"));
				indicator.setId(rs.getLong("id_"));
				indicator.setName_en(rs.getString("name_en"));
				indicator.setName_es(rs.getString("name_es"));

				return indicator;
			}
		});
	}

	@Override
	public Indicator getIndicator(long id) {
		String sql = ""
			+ " select indicator_category_id, indicator_type_id, base_filter_type_id, "
			+ "   name_en, name_es, description_en, description_es "
			+ " from tbl_indicators "
			+ " where id_ = :id ";
		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("id", id);
		
		return this.namedParameterJdbcTemplate.queryForObject(sql, paramMap, new RowMapper<Indicator>() {
			@Override
			public Indicator mapRow(ResultSet rs, int rowNum) throws SQLException {
				Indicator indicator = new Indicator();
				indicator.setId(id);
				indicator.setCategoryId(rs.getLong("indicator_category_id"));
				indicator.setTypeId(rs.getLong("indicator_type_id"));
				indicator.setBaseFilterTypeId(rs.getLong("base_filter_type_id"));
				indicator.setName_en(rs.getString("name_en"));
				indicator.setName_es(rs.getString("name_es"));
				indicator.setDescription_en(rs.getString("description_en"));
				indicator.setDescription_es(rs.getString("description_es"));

				return indicator;
			}
		});
	}
}
