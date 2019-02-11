package org.greesoft.hydro.test.client.bean.po;

import lombok.*;
import org.greesoft.hydro.core.annotation.Mapping;

/**
 * 用户扩展信息
 * <p> Date             :2018/1/23 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
@Data
public class UserExtendPO extends UserPO {

	private String uid;

	private String name;

	private Boolean sex = null;

	private int age;

	@Mapping("address")
	private String addr;

	private String phone;
}
