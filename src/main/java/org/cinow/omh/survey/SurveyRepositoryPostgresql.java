package org.cinow.omh.survey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class SurveyRepositoryPostgresql implements SurveyRepository {

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
	public void addSurveyResponse(SurveyResponse surveyResponse) {
		String sql = ""
			+ " insert into tbl_visitor_survey (question, response, lang, ip_address) "
			+ " values (:question, :response, :lang, :ip_address) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("question", surveyResponse.getQuestion());
		paramMap.addValue("response", surveyResponse.getResponse());
		paramMap.addValue("lang", surveyResponse.getLang());
		paramMap.addValue("ip_address", surveyResponse.getIpAddress());

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SurveyResponse> findAll() {
		String sql = ""
			+ " select date_, question, response, lang, ip_address "
			+ " from tbl_visitor_survey "
			+ " order by date_";

		return this.jdbcTemplate.query(sql, new RowMapper<SurveyResponse>() {
			@Override
			public SurveyResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
				SurveyResponse surveyResponse = new SurveyResponse();
				surveyResponse.setQuestion(rs.getString("question"));
				surveyResponse.setResponse(rs.getString("response"));
				surveyResponse.setDate(rs.getTimestamp("date_").toLocalDateTime());
				surveyResponse.setLang(rs.getString("lang"));
				surveyResponse.setIpAddress(rs.getString("ip_address"));
				
				return surveyResponse;
			}
		});
	}
}
