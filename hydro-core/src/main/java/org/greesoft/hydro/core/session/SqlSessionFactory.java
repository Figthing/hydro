package org.greesoft.hydro.core.session;

/**
 * SqlSession工厂接口
 * <p> Date             :2017/12/11 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
public interface SqlSessionFactory {

	/**
	 * 获取SqlSession
	 *
	 * @return the sql session
	 */
	SqlSession getSqlSession();

	/**
	 * 获取SqlSession配置
	 *
	 * @return the configuration
	 */
	SqlSessionConfig getConfiguration();

}
