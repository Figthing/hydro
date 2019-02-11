package org.greesoft.hydro.core.io;

/**
 * 通过类加载器获得resource的辅助类
 * <p> Date             :2017/12/22 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class Resources {

	/**
	 * 大多数方法都是委托给ClassLoaderWrapper，再去做真正的事
	 */
	private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

	private Resources() {}

	/**
	 * 加载Class，并未进行链接，是通过ClassLoader进行
	 *
	 * @param className the class name
	 * @return the class
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static Class<?> classForName(String className) throws ClassNotFoundException {
		return classLoaderWrapper.classForName(className);
	}
}
