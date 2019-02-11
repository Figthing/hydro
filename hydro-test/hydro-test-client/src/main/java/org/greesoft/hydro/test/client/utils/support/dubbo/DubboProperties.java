package org.greesoft.hydro.test.client.utils.support.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Dubbo配置读取
 * <p> Date             :2018/1/3 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

@Component
@ConfigurationProperties(prefix = DubboProperties.DUBBO_PREFIX)
public class DubboProperties {

	public static final String DUBBO_PREFIX = "dubbo";

	private ApplicationConfig applicationConfig;

	private RegistryConfig registryConfig;

	public ApplicationConfig getApplicationConfig() {
		return applicationConfig;
	}

	public void setApplicationConfig(ApplicationConfig applicationConfig) {
		this.applicationConfig = applicationConfig;
	}

	public RegistryConfig getRegistryConfig() {
		return registryConfig;
	}

	public void setRegistryConfig(RegistryConfig registryConfig) {
		this.registryConfig = registryConfig;
	}
}
