package org.greesoft.hydro.test.client.bean.vo;

import lombok.Data;

/**
 * 用户列表对象
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

@Data
public class UserVO {

	private String id;

	private String account;

	private String name;

	private Boolean sex = null;

	private Integer age;

	private String address;

	private String phone;
}
