package org.greesoft.hydro.core.mapping;

/**
 * SQL节点映射
 * <p> Date             :2017/12/13 </p>
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

public class MapperStatement {

	/**
	 * ID
	 */
	private String id;

	/**
	 * SQL命令类型
	 */
	private String type;

	/**
	 * 路由
	 */
	private String route;

	/**
	 * 返回类型
	 */
	private String resultType;

	/**
	 * 循环变量
	 */
	private String loop;

	/**
	 * 循环item类型
	 */
	private String item;

	/**
	 * SQL语句
	 */
	private String sql;

	/**
	 * 起始数据位置
	 */
	private String startRow;

	/**
	 * 结束数据位置
	 */
	private String endRow;

	/**
	 * 资源
	 */
	private String resource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getLoop() {
		return loop;
	}

	public void setLoop(String loop) {
		this.loop = loop;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getStartRow() {
		return startRow;
	}

	public void setStartRow(String startRow) {
		this.startRow = startRow;
	}

	public String getEndRow() {
		return endRow;
	}

	public void setEndRow(String endRow) {
		this.endRow = endRow;
	}
}
