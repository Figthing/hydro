package org.greesoft.hydro.test.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 启动
 * <p> Date             :2017/12/28 </p>
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

@SpringBootApplication
@ComponentScan(value = "org.greesoft.hydro.test.client")
public class ApplicationContext {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationContext.class, args);
	}

}
