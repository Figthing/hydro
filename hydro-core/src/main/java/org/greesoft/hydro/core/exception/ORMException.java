package org.greesoft.hydro.core.exception;

/**
 * DB SQL 异常基类
 * <p> Date             :2017/12/11 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class ORMException extends RuntimeException {

	public ORMException(String message) {
		super(message);
	}

	public ORMException(String message, Throwable cause) {
		super(message, cause);
	}

	public ORMException(Throwable cause) {
		super(cause);
	}
}
