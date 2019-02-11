package org.greesoft.hydro.core.scan;

import org.greesoft.hydro.core.mapping.MapperFactoryBean;
import org.greesoft.hydro.core.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.util.Set;

/**
 * 类扫描器
 * <p> Date             :2017/12/8 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

	/**
	 * 映射工厂
	 */
	private MapperFactoryBean<?> mapperFactoryBean = new MapperFactoryBean<>();

	/**
	 * SqlSessionFactory
	 */
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * SqlSessionFactoryBeanName
	 */
	private String sqlSessionFactoryBeanName;

	/**
	 * 日志
	 */
	private static final Logger log = LoggerFactory.getLogger(ClassPathMapperScanner.class);

	public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
		super(registry, false);
	}

	@Override
	protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

		if (beanDefinitions.isEmpty()) {
			log.debug("No mapper was found in [{}] package. Please check your configuration.", basePackages);
		} else {
			processBeanDefinitions(beanDefinitions);
		}

		return beanDefinitions;
	}

	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setSqlSessionFactoryBeanName(String sqlSessionFactoryBeanName) {
		this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
	}

	/**
	 * 注册过滤器
	 */
	public void registerFilters() {
		addIncludeFilter((MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) -> true);
		addExcludeFilter((MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) -> {
			String className = metadataReader.getClassMetadata().getClassName();
			return className.endsWith("package-info");
		});
	}

	/**
	 * 创建扫描后的接口
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param beanDefinitions 参数说明
	 * @return void
	 */
	private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {

		GenericBeanDefinition definition;
		for (BeanDefinitionHolder holder : beanDefinitions) {
			definition = (GenericBeanDefinition) holder.getBeanDefinition();

			definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());

			// 设置Bean为代理工厂Bean
			definition.setBeanClass(this.mapperFactoryBean.getClass());

			if (null != this.sqlSessionFactoryBeanName) {
				// 添加SqlSessionFactory属性
				definition.getPropertyValues().add("sqlSessionFactory", new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
			} else if (null != this.sqlSessionFactory) {
				// 添加SqlSessionFactory属性
				definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
			}

			// 设置注入形式为Autowire
			definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

			log.debug("Creating MapperFactoryBean with name => [{}], mapperInterface => [{}]", holder.getBeanName(), definition.getBeanClassName());
		}

		log.debug("<================== ORM scan interface create mapper end!");
	}
}
