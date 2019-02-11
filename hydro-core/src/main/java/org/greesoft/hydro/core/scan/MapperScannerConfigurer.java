package org.greesoft.hydro.core.scan;

import org.greesoft.hydro.core.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 接口映射扫描
 * <p> Date             :2017/12/8 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
public class MapperScannerConfigurer implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

	/**
	 * 扫描BasePackage
	 */
	private String basePackage;

	/**
	 * spring上下文
	 */
	private ApplicationContext applicationContext;

	/**
	 * SqlSession工厂
	 */
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * SqlSessionFactoryName
	 */
	private String sqlSessionFactoryBeanName;

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MapperScannerConfigurer.class);

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
		logger.debug("ORM scan interface create mapper start ==================>");
		ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
		scanner.setResourceLoader(this.applicationContext);
		scanner.registerFilters();
		scanner.setSqlSessionFactory(this.sqlSessionFactory);
		scanner.setSqlSessionFactoryBeanName(this.sqlSessionFactoryBeanName);
		scanner.scan(StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
		// left intentionally blank
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setSqlSessionFactoryBeanName(String sqlSessionFactoryBeanName) {
		this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
	}
}
