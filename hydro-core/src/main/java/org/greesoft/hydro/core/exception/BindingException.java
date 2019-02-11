package org.greesoft.hydro.core.exception;

/**
 * 绑定异常
 * <p> Date             :2017/12/12 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class BindingException extends ORMException {

	public BindingException(String message) {
		super(message);
	}

	public BindingException(String message, Throwable cause) {
		super(message, cause);
	}
}
