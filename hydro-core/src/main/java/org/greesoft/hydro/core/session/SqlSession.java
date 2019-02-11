package org.greesoft.hydro.core.session;

import java.util.Map;

/**
 * Sql Session
 * <p> Date             :2017/12/12 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
public interface SqlSession {

	/**
	 * 获取SQL Session配置
	 *
	 * @return the configuration
	 */
	SqlSessionConfig getConfiguration();

	/**
	 * 执行SQL
	 *
	 * @param statement 执行方法
	 * @param params    参数
	 * @return the object
	 */
	Object executorSql(String statement, final Map<String, Object> params);

	/**
	 * Map转换为JavaBean
	 *
	 * @param <T>   the type parameter
	 * @param map   the map
	 * @param clazz the clazz
	 * @return the t
	 */
	<T> T transMap2Bean(Map map, Class<T> clazz);
}
