package org.greesoft.hydro.test.service.server.impl;

import com.alibaba.dubbo.config.annotation.Service;
import javassist.bytecode.StackMapTable;
import org.greesoft.hydro.test.api.Operation;
import org.greesoft.hydro.test.api.Respond;
import org.greesoft.hydro.test.api.RespondImpl;
import org.greesoft.hydro.test.api.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SQL语句执行器
 * <p> Date             :2017/12/27 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 * <p>--------------------------------------------------------------</p>
 * <p>修改历史</p>
 * <p>    序号    日期    修改人    修改原因    </p>
 * <p>    1                                     </p>
 */

@Service
public class OperationImpl implements Operation {

	private Logger logger = LoggerFactory.getLogger(OperationImpl.class);
	private static final String LOGGER_EXCEPTION_STR = "DataAccessException";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Respond findOne(String sql) {
		RespondImpl respond = new RespondImpl();

		try {
			Map<String, Object> result = jdbcTemplate.queryForMap(sql);

			if (!result.isEmpty()) {
				ResponseData responseData = new ResponseData();
				responseData.putAll(result);
				respond.setRow(responseData);
			}

			respond.setSuccess(true);
		} catch (EmptyResultDataAccessException e) {
			respond.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error(LOGGER_EXCEPTION_STR, e);
			respond.setException(e);
		}

		return respond;
	}

	@Override
	public Respond findAll(String sql) {
		RespondImpl respond = new RespondImpl();

		try {
			List<ResponseData> result = (List)jdbcTemplate.queryForList(sql);
			respond.setRows(result);
			respond.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error(LOGGER_EXCEPTION_STR, e);
			respond.setException(e);
		}

		return respond;
	}

	@Override
	public Respond insert(String sql) {
		return this.executor(sql);
	}

	@Override
	public Respond insert(List<String> list) {
		return this.executor(list);
	}

	@Override
	public Respond delete(String sql) {
		return this.executor(sql);
	}

	@Override
	public Respond delete(List<String> list) {
		return this.executor(list);
	}

	@Override
	public Respond update(String sql) {
		return this.executor(sql);
	}

	@Override
	public Respond update(List<String> list) {
		return this.executor(list);
	}

	/**
	 * 执行SQL语句
	 *
	 * @param sql   SQL语句
	 * @return DbRespond
	 */
	private Respond executor(String sql) {
		RespondImpl respond = new RespondImpl();

		try {
			int records = jdbcTemplate.update(sql);
			respond.setAffectedRecord(records);
			respond.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error(LOGGER_EXCEPTION_STR, e);
			respond.setException(e);
		}

		return respond;
	}

	/**
	 * 执行SQL语句
	 *
	 * @param sqls   SQL语句
	 * @return DbRespond
	 */
	private Respond executor(List<String> sqls) {
		RespondImpl respond = new RespondImpl();

		try {

			for(String sql: sqls) {
				int records = jdbcTemplate.update(sql);
				respond.setAffectedRecord(records);
			}

			respond.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error(LOGGER_EXCEPTION_STR, e);
			respond.setException(e);
		}

		return respond;
	}
}
