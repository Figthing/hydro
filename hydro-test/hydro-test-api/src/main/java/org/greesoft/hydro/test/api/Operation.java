package org.greesoft.hydro.test.api;

import java.util.List;

/**
 * DB 操作类
 * <p> Date             :2017/9/25 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
public interface Operation {

	/**
	 * 获取数据执行一条SQL,一条数据
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param sql the sql
	 * @return Respond db respond
	 */
	Respond findOne(final String sql);

	/**
	 * 获取数据执行一条SQL,多条数据
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param sql the sql
	 * @return Respond db respond
	 */
	Respond findAll(final String sql);

	/**
	 * 新增数据执行一条SQL
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param sql the sql
	 * @return Respond db respond
	 */
	Respond insert(final String sql);

	/**
	 * 新增数据执行多条SQL
	 * <p> 描述           :可对应多个不同表，同时入库</p>
	 * <p> 备注           :</p>
	 *
	 * @param list List<String>
	 * @return Respond db respond
	 */
	Respond insert(final List<String> list);

	/**
	 * 删除数据执行一条SQL
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param sql the sql
	 * @return Respond db respond
	 */
	Respond delete(final String sql);

	/**
	 * 删除数据执行多条SQL
	 * <p> 描述           :可对应多个不同表</p>
	 * <p> 备注           :</p>
	 *
	 * @param list List<String>
	 * @return Respond db respond
	 */
	Respond delete(final List<String> list);

	/**
	 * 更新数据执行一条SQL
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param sql the sql
	 * @return Respond db respond
	 */
	Respond update(final String sql);

	/**
	 * 更新数据执行多条SQL
	 * <p> 描述           :</p>
	 * <p> 备注           :</p>
	 *
	 * @param list List<String>
	 * @return Respond db respond
	 */
	Respond update(final List<String> list);
}
