package org.cinow.omh.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MaterializedViewRepositoryPostgresql implements MaterializedViewRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void populateMaterializedViews() {
		String sql = " select 1 from mv_indicator_metadata ";
		try {
		this.jdbcTemplate.execute(sql);
		} catch (UncategorizedSQLException e) {
			if ("55000".equals(e.getSQLException().getSQLState())) {
				this.jdbcTemplate.execute(" select refresh_materialized_views() ");
			} else {
				throw e;
			}
		}
	}
}
