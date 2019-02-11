package org.greesoft.hydro.test.api;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Db处理响应
 * <p> Date             :2017/10/12 </p>
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

public interface Respond extends Serializable {

	/**
	 * 是否执行成功
	 * <p> 描述           :DB Server 执行完毕后返回</p>
	 * <p> 备注           :true-成功，false-失败，报异常</p>
	 *
	 * @return java.lang.Boolean
	 */
	Boolean success();

	/**
	 * 获取异常信息
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @return Exception
	 */
	Exception exception();

	/**
	 * 获取一组数据
	 * <p> 描述           :主要用于SELECT返回信息</p>
	 * <p> 备注           :</p>
	 *
	 * @return java.util.List<ResponseData>
	 */
	List<ResponseData> getDatas();

	/**
	 * 获取一条记录信息
	 * <p> 描述           :主要用于SELECT返回信息，键值对</p>
	 * <p> 备注           :</p>
	 *
	 * @return ResponseData
	 */
	ResponseData getOne();

	/**
	 * 获取受影响的记录数
	 * <p> 描述           :主要用在insert，update,delete语句</p>
	 * <p> 备注           :</p>
	 *
	 * @return java.lang.Integer
	 */
	Integer getAffectedRecord();

	/**
	 * 获取受影响的记录数（批量）
	 * <p> 描述           :主要用在insert，update,delete语句</p>
	 * <p> 备注           :批量SQL，每次执行受影响的记录数</p>
	 *
	 * @return java.lang.Integer
	 */
	List<Integer> getAffectedRecords();
}
