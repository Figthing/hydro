package org.greesoft.hydro.core.exception;

/**
 * 编译异常
 * <p> Date             :2017/12/12 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class BuilderException extends ORMException {

	public BuilderException(String message) {
		super(message);
	}

	public BuilderException(String message, Throwable cause) {
		super(message, cause);
	}
}
