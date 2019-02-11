package org.greesoft.hydro.test.client.utils.support.hydro;

import org.greesoft.hydro.core.scan.MapperScannerConfigurer;
import org.greesoft.hydro.core.session.SqlSessionFactoryBean;
import org.greesoft.hydro.test.api.Operation;
import org.greesoft.hydro.test.client.utils.support.dubbo.DubboAutoConfigure;
import org.greesoft.hydro.test.client.utils.support.hydro.extend.SqlSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * Hydro自动启用
 * <p> Date             :2018/1/3 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

@Order(2)
@Configuration
@ConditionalOnClass(value = {DubboAutoConfigure.class})
public class HydroAutoConfigure {

	@Bean(name = "sqlSessionFactory")
	@ConditionalOnBean(value = {DubboAutoConfigure.class})
	public SqlSessionFactoryBean sqlSessionFactoryBean(DubboAutoConfigure dubboAutoConfigure) throws IOException {

		Operation operation = dubboAutoConfigure.referenceConfig().get();

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

		// datasource
		sqlSessionFactoryBean.setDatasource(operation);

		// sqlsession
		sqlSessionFactoryBean.setSqlSessionClass(SqlSession.class);

		// resources
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resources = resolver.getResources("classpath:mapping/*.xml");
		sqlSessionFactoryBean.setMapperLocations(resources);

		return sqlSessionFactoryBean;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("org.greesoft.hydro.test.client.dao");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}
}
