package org.greesoft.hydro.test.client.utils.support.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.greesoft.hydro.test.api.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Dubbo初始化
 * <p> Date             :2018/1/3 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

@Order(1)
@Configuration
@EnableConfigurationProperties(DubboProperties.class)
@ConditionalOnClass(value = {ApplicationConfig.class, RegistryConfig.class})
public class DubboAutoConfigure {

	@Autowired
	private DubboProperties dubboProperties;

	@Bean
	public ReferenceConfig<Operation> referenceConfig() {

		ReferenceConfig<Operation> referenceConfig = new ReferenceConfig<>();
		referenceConfig.setApplication(dubboProperties.getApplicationConfig());
		referenceConfig.setRegistry(dubboProperties.getRegistryConfig());
		referenceConfig.setInterface(Operation.class);

		return referenceConfig;
	}
}
