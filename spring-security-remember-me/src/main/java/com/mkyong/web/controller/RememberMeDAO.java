package com.mkyong.web.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class RememberMeDAO extends JdbcDaoSupport implements
		PersistentTokenRepository {

	public static final String CREATE_TABLE_SQL = "create table persistent_logins1 (username1 varchar(200) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)";
	public static final String DEF_TOKEN_BY_SERIES_SQL = "select username1,series,token,last_used from persistent_logins1 where series = ?";
	public static final String DEF_INSERT_TOKEN_SQL = "insert into persistent_logins1 (username1, series, token, last_used) values(?,?,?,?)";
	public static final String DEF_UPDATE_TOKEN_SQL = "update persistent_logins1 set token = ?, last_used = ? where series = ?";
	public static final String DEF_REMOVE_USER_TOKENS_SQL = "delete from persistent_logins1 where username1 = ?";
	private String tokensBySeriesSql;
	private String insertTokenSql;
	private String updateTokenSql;
	private String removeUserTokensSql;
	private boolean createTableOnStartup;

	public RememberMeDAO() {
		this.tokensBySeriesSql = "select username1,series,token,last_used from persistent_logins1 where series = ?";
		this.insertTokenSql = "insert into persistent_logins1 (username1, series, token, last_used) values(?,?,?,?)";
		this.updateTokenSql = "update persistent_logins1 set token = ?, last_used = ? where series = ?";
		this.removeUserTokensSql = "delete from persistent_logins1 where username1 = ?";
	}

	protected void initDao() {
		if (this.createTableOnStartup) {
			getJdbcTemplate()
					.execute(
							"create table persistent_logins1 (username1 varchar(200) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null)");
		}
	}

	public void createNewToken(PersistentRememberMeToken token) {
		getJdbcTemplate().update(
				this.insertTokenSql,
				new Object[] { token.getUsername(), token.getSeries(),
						token.getTokenValue(), token.getDate() });
	}

	public void updateToken(String series, String tokenValue, Date lastUsed) {
		getJdbcTemplate().update(this.updateTokenSql,
				new Object[] { tokenValue, new Date(), series });
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		try {
			return ((PersistentRememberMeToken) getJdbcTemplate()
					.queryForObject(this.tokensBySeriesSql, new RowMapper() {
						public PersistentRememberMeToken mapRow(ResultSet rs,
								int rowNum) throws SQLException {
							return new PersistentRememberMeToken(rs
									.getString(1), rs.getString(2), rs
									.getString(3), rs.getTimestamp(4));
						}
					}, new Object[] { seriesId }));
		} catch (EmptyResultDataAccessException zeroResults) {
			if (this.logger.isDebugEnabled())
				this.logger.debug("Querying token for series '" + seriesId
						+ "' returned no results.", zeroResults);
		} catch (IncorrectResultSizeDataAccessException moreThanOne) {
			this.logger.error("Querying token for series '" + seriesId
					+ "' returned more than one value. Series"
					+ " should be unique");
		} catch (DataAccessException e) {
			this.logger.error("Failed to load token for series " + seriesId, e);
		}

		return null;
	}

	public void removeUserTokens(String username) {
		getJdbcTemplate().update(this.removeUserTokensSql,
				new Object[] { username });
	}

	public void setCreateTableOnStartup(boolean createTableOnStartup) {
		this.createTableOnStartup = createTableOnStartup;
	}

}
