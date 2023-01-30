package org.cinow.omh.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepositoryPostgresql implements AdminRepository {

	/**
	 * The JDBC template.
	 */
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public void refreshMaterializedViews() {
		String sql = " select refresh_materialized_views() ";
		this.jdbcTemplate.execute(sql);
	}
}
