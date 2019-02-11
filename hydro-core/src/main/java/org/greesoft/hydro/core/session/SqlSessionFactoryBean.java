package org.greesoft.hydro.core.session;

import org.greesoft.hydro.core.exception.SqlSessionException;
import org.greesoft.hydro.core.xml.XMapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * SqlSession工厂Bean
 * <p> Date             :2017/12/11 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>, InitializingBean, ApplicationListener<ApplicationEvent> {

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(SqlSessionFactoryBean.class);

	/**
	 * 静态资源XML
	 */
	private Resource[] mapperLocations;

	/**
	 * SqlSession工厂
	 */
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * SqlSession Class
	 */
	private Class<SqlSession> sqlSessionClass;

	/**
	 * 数据源
	 */
	private Object datasource;

	@Override
	public SqlSessionFactory getObject() throws Exception {
		if (this.sqlSessionFactory == null) {
			afterPropertiesSet();
		}

		return this.sqlSessionFactory;
	}

	@Override
	public Class<?> getObjectType() {
		return null;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		if (null == mapperLocations || 0 >= mapperLocations.length) {
			throw new SqlSessionException("not find mapper location!");
		}

		logger.debug("ORM scan xml create bean start ==================>");

		this.sqlSessionFactory = buildSqlSessionFactory();

		logger.debug("<================== ORM scan xml create bean end!");
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// fail-fast -> check all statements are completed
	}

	/**
	 * 生成一个SqlSession
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @return test.test.abc.core.db.tpl.DbSqlSessionFactory
	 */
	private SqlSessionFactory buildSqlSessionFactory() throws IOException {

		SqlSessionConfig configuration = new SqlSessionConfig(datasource);

		// 循环资源，解析XML
		for(Resource resource: mapperLocations) {
			logger.debug("SqlSessionFactory scan resource, path => [{}]", resource);

			XMapperBuilder mapperBuilder = new XMapperBuilder(resource.getInputStream(), resource.toString(), configuration);
			mapperBuilder.parse();
		}

		return new DefaultSqlSessionFactory<>(configuration, sqlSessionClass);
	}

	/**
	 * 设置资源
	 *
	 * @param mapperLocations the mapper locations
	 */
	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	public void setSqlSessionClass(Class sqlSessionClass) {
		this.sqlSessionClass = sqlSessionClass;
	}

	public void setDatasource(Object datasource) {
		this.datasource = datasource;
	}
}
