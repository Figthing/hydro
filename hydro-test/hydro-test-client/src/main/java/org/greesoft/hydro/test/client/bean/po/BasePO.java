package org.greesoft.hydro.test.client.bean.po;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础BEAN
 * <p> Date             :2018/1/2 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
@Data
public class BasePO implements Serializable {

	private String id;

	private Date createDate;

	private Date updateDate;
}
