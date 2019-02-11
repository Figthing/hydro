package org.greesoft.hydro.test.client.utils.support.hydro.extend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.greesoft.hydro.core.io.Resources;
import org.greesoft.hydro.core.session.SqlSession;
import org.greesoft.hydro.test.api.Operation;
import org.greesoft.hydro.test.api.Respond;
import org.greesoft.hydro.test.api.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Sql执行器
 * <p> Date             :2017/12/19 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class SqlExecutor {

	private static Operation operation;

	private static SqlExecutor executor = null;

	private String resultType;

	private SqlSession sqlSession;

	/**
	 * JSON中的SQL KEY名称
	 */
	public static final String SQL_JSON_KEY = "sql";
	private static final String ERROR_STR_1 = "sqls is null!";
	private static final String ERROR_STR_2 = "sql excutor fail!";
	private static final String ERROR_STR_3 = "resultType Class => [{}] not found!";

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlExecutor.class);

	private SqlExecutor() {}

	public static synchronized SqlExecutor getInstance(Object dbOperation, String resultType, SqlSession sqlSession) {
		if (null == executor) {
			executor = new SqlExecutor();
			operation = (Operation) dbOperation;
		}

		executor.resultType = resultType;
		executor.sqlSession = sqlSession;
		return executor;
	}

	/**
	 * 执行获取一条数据
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param strJsonSQL the sql json
	 * @return Object
	 */
	public Object findOne(final String strJsonSQL) {

		SqlDTO sqlDTO = new SqlDTO(strJsonSQL);

		if (sqlDTO.isArray()) {
			LOGGER.error("sql [{}] not is Json Object!", strJsonSQL);
			return null;
		}

		String[] sqls = sqlDTO.getSQL();

		if (0 >= sqls.length) {
			LOGGER.error(ERROR_STR_1);
			return null;
		}

		Respond respond = operation.findOne(sqls[0]);

		// 异常返回
		if (!respond.success()) {
			LOGGER.error(ERROR_STR_2, respond.exception());
			return null;
		}

		// 原始返回
		if (null == resultType) {
			return respond;
		}

		// 数据转换处理
		try {
			Class<?> clazz = Resources.classForName(resultType);

			// boolean
			if (clazz.isAssignableFrom(Boolean.class)) {
				return Integer.valueOf(respond.getOne().entrySet().iterator().next().getValue().toString()) > 0 ? Boolean.TRUE : Boolean.FALSE;
			}

			// int
			if (clazz.isAssignableFrom(Integer.class)) {
				return Integer.valueOf(respond.getOne().entrySet().iterator().next().getValue().toString());
			}

			// Object
			return sqlSession.transMap2Bean(respond.getOne(), clazz);

		} catch (ClassNotFoundException e) {
			LOGGER.error(ERROR_STR_3, resultType);
		}

		return null;
	}

	/**
	 * 执行获取多条数据
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param strJsonSQL the sql json
	 * @return Object
	 */
	public Object findAll(final String strJsonSQL) {

		SqlDTO sqlDTO = new SqlDTO(strJsonSQL);

		if (sqlDTO.isArray()) {
			LOGGER.error("sql [{}] not is Json Object!", strJsonSQL);
			return null;
		}

		String[] sqls = sqlDTO.getSQL();

		if (0 >= sqls.length) {
			LOGGER.error(ERROR_STR_1);
			return null;
		}

		Respond respond = operation.findAll(sqls[0]);

		// 异常返回
		if (!respond.success()) {
			LOGGER.error(ERROR_STR_2, respond.exception());
			return null;
		}

		// 原始返回
		if (null == resultType) {
			return respond;
		}

		// 数据转换处理
		try {
			Class<?> clazz = Resources.classForName(resultType);

			List<Object> result = new ArrayList<>();

			List<ResponseData> list = respond.getDatas();

			if (0 < list.size()) {
				for(Map<String, Object> item: list) {
					Object object = sqlSession.transMap2Bean(item, clazz);
					result.add(object);
				}
			}

			return result;

		} catch (ClassNotFoundException e) {
			LOGGER.error(ERROR_STR_3, resultType);
		}

		return null;
	}

	/**
	 * 执行新增数据（单条/批量）
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param strJsonSQL the String
	 * @return Object
	 */
	@SuppressWarnings("all")
	public Object insert(final String strJsonSQL) {

		SqlDTO sqlDTO = new SqlDTO(strJsonSQL);

		String[] sqls = sqlDTO.getSQL();

		if (0 >= sqls.length) {
			LOGGER.error(ERROR_STR_1);
			return null;
		}

		Respond respond;

		if (sqlDTO.isArray()) {
			respond = operation.insert(Arrays.asList(sqls));
		} else {
			respond = operation.insert(sqls[0]);
		}

		// 异常返回
		if (!respond.success()) {
			LOGGER.error(ERROR_STR_2, respond.exception());
			return null;
		}

		// 原始返回
		if (null == resultType) {
			return respond;
		}

		// 数据转换处理
		try {
			Class<?> clazz = Resources.classForName(resultType);

			// boolean
			if (clazz.isAssignableFrom(Boolean.class)) {
				int affectedRecord = sqlDTO.isArray() ? respond.getAffectedRecords().size() : respond.getAffectedRecord();
				return 0 < affectedRecord ? Boolean.TRUE : Boolean.FALSE;
			}

			// int
			if (clazz.isAssignableFrom(Integer.class)) {

				int affectedRecord = respond.getAffectedRecord();

				if (sqlDTO.isArray()) {
					affectedRecord = 0;
					for(Integer item: respond.getAffectedRecords()) {
						affectedRecord += item;
					}
				}

				return affectedRecord;
			}

			return respond;

		} catch (ClassNotFoundException e) {
			LOGGER.error(ERROR_STR_3, resultType);
		}

		return null;
	}

	/**
	 * 删除数据执行一条SQL
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param strJsonSQL String
	 * @return Object
	 */
	public Object delete(final String strJsonSQL) {
		SqlDTO sqlDTO = new SqlDTO(strJsonSQL);

		String[] sqls = sqlDTO.getSQL();

		if (0 >= sqls.length) {
			LOGGER.error(ERROR_STR_1);
			return null;
		}

		Respond respond;

		if (sqlDTO.isArray()) {
			respond = operation.delete(Arrays.asList(sqls));
		} else {
			respond = operation.delete(sqls[0]);
		}

		// 异常返回
		if (!respond.success()) {
			LOGGER.error(ERROR_STR_2, respond.exception());
			return null;
		}

		// 原始返回
		if (null == resultType) {
			return respond;
		}

		// 数据转换处理
		try {
			Class<?> clazz = Resources.classForName(resultType);

			// boolean
			if (clazz.isAssignableFrom(Boolean.class)) {
				return Boolean.TRUE;    // TODO 只要执行成功都是true
			}

			// int
			if (clazz.isAssignableFrom(Integer.class)) {

				int affectedRecord = respond.getAffectedRecord();

				if (sqlDTO.isArray()) {
					affectedRecord = 0;
					for(Integer item: respond.getAffectedRecords()) {
						affectedRecord += item;
					}
				}

				return affectedRecord;
			}

			return respond;

		} catch (ClassNotFoundException e) {
			LOGGER.error(ERROR_STR_3, resultType);
		}

		return null;
	}

	/**
	 * 更新数据执行一条SQL
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param strJsonSQL String
	 * @return Object
	 */
	public Object update(final String strJsonSQL) {
		SqlDTO sqlDTO = new SqlDTO(strJsonSQL);

		String[] sqls = sqlDTO.getSQL();

		if (0 >= sqls.length) {
			LOGGER.error(ERROR_STR_1);
			return null;
		}

		Respond respond;

		if (sqlDTO.isArray()) {
			respond = operation.update(Arrays.asList(sqls));
		} else {
			respond = operation.update(sqls[0]);
		}

		// 异常返回
		if (!respond.success()) {
			return null;
		}

		// 原始返回
		if (null == resultType) {
			return respond;
		}

		// 数据转换处理
		try {
			Class<?> clazz = Resources.classForName(resultType);

			// boolean
			if (clazz.isAssignableFrom(Boolean.class)) {
				return Boolean.TRUE;    // TODO 只要执行成功都是true
			}

			// int
			if (clazz.isAssignableFrom(Integer.class)) {

				int affectedRecord = respond.getAffectedRecord();

				if (sqlDTO.isArray()) {
					affectedRecord = 0;
					for(Integer item: respond.getAffectedRecords()) {
						affectedRecord += item;
					}
				}

				return affectedRecord;
			}

			return respond;

		} catch (ClassNotFoundException e) {
			LOGGER.error(ERROR_STR_3, resultType);
		}

		return null;
	}

	/**
	 * SQL-DTO
	 */
	private class SqlDTO {

		/**
		 * JSON-Obj
		 */
		private Object jsonObj;

		public SqlDTO(String sql) {
			this.jsonObj = JSONObject.parse(sql);
		}

		/**
		 * SQL是否数组
		 *
		 * @return the boolean
		 */
		public boolean isArray() {
			return jsonObj instanceof JSONArray;
		}

		/**
		 * 获取SQL
		 *
		 * @return the string [ ]
		 */
		public String[] getSQL() {

			JSONArray jsonArray = new JSONArray();

			if (jsonObj instanceof JSONObject) {
				jsonArray.add(jsonObj);
			} else {
				jsonArray.addAll((JSONArray)jsonObj);
			}

			String[] sqls = new String[jsonArray.size()];
			int i = 0;
			for(Object item: jsonArray) {

				if (!isSqlKey(item)) {
					continue;
				}

				sqls[i] = ((JSONObject)item).get(SQL_JSON_KEY).toString();
				i++;
			}

			String str = StringUtils.join(sqls, ",");
			LOGGER.info("getSQL: [{}]", str);

			return sqls;
		}

		/**
		 * JSONObject中,SQLKey是否存在
		 * @param item JSONObject
		 * @return boolean
		 */
		private boolean isSqlKey(Object item) {
			return ((JSONObject)item).containsKey(SQL_JSON_KEY);
		}
	}
}
