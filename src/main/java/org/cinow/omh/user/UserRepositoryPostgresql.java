package org.cinow.omh.user;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

/**
 * {@inheritDoc}
 */
@Repository
public class UserRepositoryPostgresql implements UserRepository {

	/**
	 * The named parameter JDBC template.
	 */
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public boolean userExists(String email) {
		String sql = ""
			+ " select 1 "
			+ " from tbl_admin_users "
			+ " where lower(email) = lower(:email) ";

		MapSqlParameterSource paramMap = new MapSqlParameterSource();
		paramMap.addValue("email", email);

		SqlRowSet rowSet = this.namedParameterJdbcTemplate.queryForRowSet(sql, paramMap);

		return rowSet.next();
	}
	
}
