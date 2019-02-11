package org.greesoft.hydro.core.exception;

/**
 * SqlSession异常类
 * <p> Date             :2017/12/12 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class SqlSessionException extends ORMException {

	public SqlSessionException(String message) {
		super(message);
	}

	public SqlSessionException(Throwable cause) {
		super(cause);
	}

	public SqlSessionException(String message, Throwable cause) {
		super(message, cause);
	}
}
