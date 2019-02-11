package org.greesoft.hydro.test.client.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增用户
 * <p> Date             :2018/2/13 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
@Data
public class UserAddVO implements Serializable {

	private static final long serialVersionUID = 2046734250075203908L;

	@NotNull(message = "账号不能为空")
	private String account;

	@NotNull(message = "密码不能为空")
	private String password;

	@NotNull(message = "姓名不能为空")
	@ApiModelProperty(notes = "姓名")
	private String name;

	@ApiModelProperty(notes = "性别")
	private Boolean sex = null;

	@Range(min = 18, max = 100, message = "年龄输入错误只能是18-100之间")
	@ApiModelProperty(notes = "年龄")
	private Integer age = 18;

	@ApiModelProperty(notes = "地址")
	private String address;

	@ApiModelProperty(notes = "电话")
	private String phone;
}
