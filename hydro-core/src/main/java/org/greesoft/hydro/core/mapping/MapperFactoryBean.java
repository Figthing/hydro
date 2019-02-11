package org.greesoft.hydro.core.mapping;

import org.greesoft.hydro.core.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * 接口映射工厂bean
 * <p> Date             :2017/12/8 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class MapperFactoryBean<T> implements FactoryBean<T> {

	/**
	 * 映射接口
	 */
	private Class<T> mapperInterface;

	/**
	 * SqlSessionFactory
	 */
	private SqlSessionFactory sqlSessionFactory;

	public MapperFactoryBean() {}

	@Override
	public T getObject() throws Exception {
		// 获取接口反射的对象
		return sqlSessionFactory.getConfiguration().getMapper(this.mapperInterface, sqlSessionFactory.getSqlSession());
	}

	@Override
	public Class<?> getObjectType() {
		return this.mapperInterface;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public MapperFactoryBean(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

	public void setSqlSessionFactory(SqlSessionFactory sessionFactory) {
		this.sqlSessionFactory = sessionFactory;
	}
}
