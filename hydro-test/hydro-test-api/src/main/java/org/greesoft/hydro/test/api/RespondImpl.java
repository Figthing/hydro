package org.greesoft.hydro.test.api;

import java.util.ArrayList;
import java.util.List;


/**
 * Db处理响应实体类
 * <p> Date             :2017/10/12 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public final class RespondImpl implements Respond {

	private static final long serialVersionUID = -446171215578045299L;

	/**
	 * 执行是否成功
	 */
	private Boolean success = false;

	/**
	 * 异常信息
	 */
	private Exception exception;

	/**
	 * 查询数据存储
	 */
	private List<ResponseData> datas;

	/**
	 * 受影响记录数
	 */
	private List<Integer> affectedRecord;

	@Override
	public Boolean success() {
		return success;
	}

	@Override
	public Exception exception() {
		return exception;
	}

	@Override
	public List<ResponseData> getDatas() {
		return datas;
	}

	@Override
	public ResponseData getOne() {

		if (null == datas || datas.isEmpty()) {
			return null;
		}

		return datas.get(0);
	}

	@Override
	public Integer getAffectedRecord() {

		if (null == affectedRecord || affectedRecord.isEmpty()) {
			return 0;
		}

		return affectedRecord.get(0);
	}

	@Override
	public List<Integer> getAffectedRecords() {
		return affectedRecord;
	}

	/**
	 * 设置是否成功
	 *
	 * @param success the success
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * 设置异常
	 *
	 * @param e the e
	 */
	public void setException(Exception e) {
		exception = new Exception(e);
	}

	/**
	 * 设置数据集
	 *
	 * @param rows the rows
	 */
	public void setRows(List<ResponseData> rows) {

		if (null == datas) {
			datas = new ArrayList<>();
		}

		datas.addAll(rows);
	}

	/**
	 * 设置单个数据
	 *
	 * @param row the row
	 */
	public void setRow(ResponseData row) {

		if (null == datas) {
			datas = new ArrayList<>();
		}

		if (!datas.isEmpty()) {
			datas.clear();
		}

		datas.add(row);
	}

	/**
	 * 设置受影响的记录数
	 *
	 * @param records 受影响记录数
	 */
	public void setAffectedRecord(Integer records) {
		if (null == affectedRecord || affectedRecord.isEmpty()) {
			affectedRecord = new ArrayList<>();
		}

		affectedRecord.add(records);
	}

	/**
	 * 清除受影响的记录数
	 *
	 */
	public void clearAffectedRecord() {

		if (null == affectedRecord || affectedRecord.isEmpty()) {
			return;
		}
		affectedRecord.clear();
	}
}
