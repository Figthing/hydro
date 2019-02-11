package org.greesoft.hydro.test.client.utils.support.hydro.extend;

import org.greesoft.hydro.core.mapping.MapperStatement;
import org.greesoft.hydro.core.session.DefaultSqlSession;
import org.greesoft.hydro.core.session.SqlSessionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * SqlSession
 * <p> Date             :2017/12/14 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class SqlSession extends DefaultSqlSession {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SqlSession.class);

	public SqlSession(SqlSessionConfig configuration) {
		super(configuration);

		// 使用Velocity
		this.initVelocity();
	}

	@Override
	public Object executorSql(String statement, Map<String, Object> params) {
		MapperStatement ms = this.configuration.getMappedStatement(statement);
		SqlCommand command = Enum.valueOf(SqlCommand.class, ms.getType());

		// 拼装SQL
		String sql = assemblySql(ms);

		logger.debug("executorSql assemblySql => [{}]", sql);

		// 解析字符串
		String mergeSql = velocityParser(sql, params);

		logger.debug("executorSql merge sql => [{}]", mergeSql);

		// 构造执行器
		SqlExecutor executor = SqlExecutor.getInstance(this.configuration.getDatasource(), ms.getResultType(),this);

		// 执行执行器
		Object result = command.executor(executor, mergeSql);

		logger.debug("executorSql command result => [{}]", result);

		return result;
	}

	/**
	 * 组装SQL并返回JSON字符串
	 *
	 * @param ms the ms
	 * @return the string
	 */
	@SuppressWarnings("all")
	private String assemblySql(final MapperStatement ms) {
		StringBuilder builder = new StringBuilder();

		// 1、是否循环
		if (null != ms.getLoop()) {
			builder.append(String.format("#set($count = $%s.size())", ms.getLoop()));
			builder.append("[");
			builder.append(String.format("#foreach($%s in $%s)", ms.getItem(), ms.getLoop()));
		}
		builder.append("{");

		// 2、兼容分页
		StringBuilder sqlBuilder = new StringBuilder();

		if (null != ms.getStartRow() && null != ms.getEndRow()) {
			// Oracle分页
//			sqlBuilder.append("SELECT * FROM (");
//			sqlBuilder.append("SELECT TAB.*, ROWNUM RN FROM (");
//			sqlBuilder.append(ms.getSql());
//			sqlBuilder.append(String.format(") TAB WHERE ROWNUM <= $%s", ms.getEndRow()));
//			sqlBuilder.append(String.format(") WHERE RN >= $%s", ms.getStartRow()));

			// MYSQL分页
			sqlBuilder.append(String.format("%s LIMIT $%s, $%s", ms.getSql(), ms.getStartRow(), ms.getEndRow()));
		} else {
			sqlBuilder.append(ms.getSql());
		}

		// 3、载入SQL
		builder.append(String.format("%s:\"%s\"",
				SqlExecutor.SQL_JSON_KEY,
				sqlBuilder.toString()
						.replaceAll("\"\"", "\"-\"")
						.replaceAll("\t", " ")
						.replaceAll("\n", "")
						.replaceAll("\r", "")
						.trim()
		));

		builder.append("}");

		// 4、循环结束
		if (null != ms.getLoop()) {

			// 处理循环结束逗号问题
			builder.append("#if($velocityCount!=$count)");
			builder.append(",");
			builder.append("#end");

			// 循环结束
			builder.append("#end");
			builder.append("]");
		}

		return builder.toString();
	}
}
