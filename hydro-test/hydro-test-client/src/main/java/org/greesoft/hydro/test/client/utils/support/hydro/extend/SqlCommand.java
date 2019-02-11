package org.greesoft.hydro.test.client.utils.support.hydro.extend;

import java.lang.reflect.Method;

/**
 * 命令类型
 * <p> Date             :2017/12/19 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public enum SqlCommand {
	FINDONE("findOne"),
	FINDALL("findAll"),
	INSERT("insert"),
	UPDATE("update"),
	DELETE("delete"),
	EXECUTE("EXECUTE");

	private String command;

	SqlCommand() {}

	SqlCommand(String command) {
		this.command = command;
	}

	public Object executor(SqlExecutor executor, String sql) {
		try {
			Method method = SqlExecutor.class.getMethod(this.command, String.class);
			return method.invoke(executor, sql);
		} catch (Exception e) {
			// not out error info!
		}

		return null;
	}
}
