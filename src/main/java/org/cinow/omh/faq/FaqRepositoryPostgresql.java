package org.cinow.omh.faq;

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
public class FaqRepositoryPostgresql implements FaqRepository {

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

	@Override
	public List<Faq> getFaqs() {
		String sql = ""
			+ " select id_, question_en, question_es, answer_en, answer_es, sort_order, display "
			+ " from tbl_faqs "
			+ " order by sort_order ";

		return this.jdbcTemplate.query(sql, new RowMapper<Faq>() {
			@Override
			public Faq mapRow(ResultSet rs, int rowNum) throws SQLException {
				Faq faq = new Faq();
				faq.setId(rs.getInt("id_"));
				faq.setQuestion_en(rs.getString("question_en"));
				faq.setQuestion_es(rs.getString("question_es"));
				faq.setAnswer_en(rs.getString("answer_en"));
				faq.setAnswer_es(rs.getString("answer_es"));
				faq.setSort_order(rs.getInt("sort_order"));
				faq.setDisplay(rs.getBoolean("display"));

				return faq;
			}
		});
	}

	@Override
	public void addFaq(Faq faq, String username) {
		String sql = ""
			+ " insert into tbl_faqs(id_, question_en, question_es, answer_en, answer_es, sort_order, display, user_modified) "
			+ " values ((select max(id_) + 1 from tbl_faqs), :question_en, :question_es, :answer_en, :answer_es, :sort_order, :display, :username) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("question_en", faq.getQuestion_en());
		paramMap.addValue("question_es", faq.getQuestion_es());
		paramMap.addValue("answer_en", faq.getAnswer_en());
		paramMap.addValue("answer_es", faq.getAnswer_es());
		paramMap.addValue("sort_order", faq.getSort_order());
		paramMap.addValue("display", faq.isDisplay());
		paramMap.addValue("username", username);

		this.namedParameterJdbcTemplate.update(sql, paramMap);
	}

	@Override
	public void updateFaq(Faq faq, String username) {
		String sql = ""
			+ " update tbl_faqs set "
			+ "   question_en = :question_en, "
			+ "   question_es = :question_es, "
			+ "   answer_en = :answer_en, "
			+ "   answer_es = :answer_es, "
			+ "   sort_order = :sort_order, "
			+ "   display = :display, "
			+ "   user_modified = :username, "
			+ "   date_modified = current_timestamp "
			+ " where id_ = :id ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("question_en", faq.getQuestion_en());
		paramMap.addValue("question_es", faq.getQuestion_es());
		paramMap.addValue("answer_en", faq.getAnswer_en());
		paramMap.addValue("answer_es", faq.getAnswer_es());
		paramMap.addValue("sort_order", faq.getSort_order());
		paramMap.addValue("display", faq.isDisplay());
		paramMap.addValue("username", username);
		paramMap.addValue("id", faq.getId());

		this.namedParameterJdbcTemplate.update(sql, paramMap);	
	}
}
