package org.greesoft.hydro.core.io;

/**
 * ClassLoader加载工具
 * <p> Date             :2017/12/22 </p>
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

public class ClassLoaderWrapper {

	//defaultClassLoader没地方初始化啊?
	private ClassLoader defaultClassLoader;
	private ClassLoader systemClassLoader;

	ClassLoaderWrapper() {
		try {
			systemClassLoader = ClassLoader.getSystemClassLoader();
		} catch (SecurityException ignored) {
			// AccessControlException on Google App Engine
		}
	}

	public Class<?> classForName(String name) throws ClassNotFoundException {
		return classForName(name, getClassLoaders(null));
	}

	/**
	 * 用5个类加载器一个个调用Class.forName(加载类)，只要其中任何一个加载成功，就返回
	 *
	 * @param name        the name
	 * @param classLoader the class loader
	 * @return the class
	 * @throws ClassNotFoundException the class not found exception
	 */
	public Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {

		for (ClassLoader cl : classLoader) {

			if (null != cl) {
				try {
					Class<?> c = Class.forName(name, true, cl);

					if (null != c) {
						return c;
					}

				} catch (ClassNotFoundException e) {
					// we'll ignore this until all classloaders fail to locate the class
				}
			}
		}

		throw new ClassNotFoundException("Cannot find class: " + name);

	}

	/**
	 * 一共5个类加载器
	 * @param classLoader ClassLoader
	 * @return ClassLoader[]
	 */
	private ClassLoader[] getClassLoaders(ClassLoader classLoader) {
		return new ClassLoader[]{
				classLoader,
				defaultClassLoader,
				Thread.currentThread().getContextClassLoader(),
				getClass().getClassLoader(),
				systemClassLoader};
	}
}
