package org.greesoft.hydro.test.client.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

/**
 * 用户更新VO
 * <p> Date             :2018/2/13 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

@Data
public class UserUpdateVO {

	@ApiModelProperty(hidden = true)
	private String id;

	@ApiModelProperty(notes = "姓名")
	private String name;

	@ApiModelProperty(notes = "性别")
	private Boolean sex = null;

	@Range(min = 18, max = 100, message = "年龄输入错误只能是18-100之间")
	@ApiModelProperty(notes = "年龄")
	private Integer age;

	@ApiModelProperty(notes = "地址")
	private String address;

	@ApiModelProperty(notes = "电话")
	private String phone;
}
