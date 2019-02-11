package org.greesoft.hydro.core.session;

import org.greesoft.hydro.core.exception.SqlSessionException;

import java.lang.reflect.Constructor;

/**
 * SqlSessionFactory 实体类
 * <p> Date             :2017/12/14 </p>
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

public class DefaultSqlSessionFactory<T extends SqlSession> implements SqlSessionFactory {

	/**
	 * SqlSession配置
	 */
	private SqlSessionConfig configuration;

	/**
	 * SqlSession Class
	 */
	private Class<T> sqlSessionClass;

	public DefaultSqlSessionFactory(SqlSessionConfig configuration, Class<T> clazz) {
		this.configuration = configuration;
		this.sqlSessionClass = clazz;
	}

	@Override
	public SqlSession getSqlSession() {
		SqlSession sqlSession = null;
		try {
			Constructor constructor = sqlSessionClass.getDeclaredConstructor(new Class[]{SqlSessionConfig.class});
			constructor.setAccessible(true);
			sqlSession = (SqlSession)constructor.newInstance(this.configuration);
		} catch (Exception e) {
			throw new SqlSessionException(e);
		}
		return sqlSession;
	}

	@Override
	public SqlSessionConfig getConfiguration() {
		return this.configuration;
	}
}
