package org.cinow.omh.faq;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FaqRepositoryPostgresql implements FaqRepository {

	/**
	 * The JDBC template.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Faq> getFaqs() {
		String sql = ""
			+ " select id_, question_en, question_es, answer_en, answer_es, sort_order "
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

				return faq;
			}
		});
	}
	
}
