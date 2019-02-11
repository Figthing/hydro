package org.greesoft.hydro.test.client.dao;

import org.greesoft.hydro.core.annotation.Param;
import org.greesoft.hydro.test.client.bean.po.UserExtendPO;
import org.greesoft.hydro.test.client.bean.po.UserPO;

import java.util.List;

/**
 * 用户DAO
 * <p> Date             :2017/12/28 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
public interface UserDao {

	// ================================查询================================== //

	/**
	 * 根据ID获取用户信息
	 * <p>
	 * 查询一条数据，参数为变量，返回对象
	 * </p>
	 *
	 * @param id the id
	 * @return the user info by id
	 */
	UserExtendPO getUserInfoById(@Param("id") String id);

	/**
	 * 根据用户条件查询信息
	 * <p>
	 * 查询一条数据，参数为对象，返回对象
	 * </p>
	 *
	 * @param userPO the user po
	 * @return the user info by params
	 */
	UserPO getUserInfoByParams(@Param("params") UserPO userPO);

	/**
	 * 获取用户总数
	 * <p>
	 * 查询一条数据，返回int
	 * </p>
	 *
	 * @return the user count
	 */
	int getUserCount();

	/**
	 * 检查用户账号是否存在
	 * <p>
	 * 查询一条数据，返回boolean
	 * </p>
	 *
	 * @param account the account
	 * @return the boolean
	 */
	boolean checkUserAccount(@Param("account") String account);

	/**
	 * 根据参数查询用户列表
	 * <p>
	 * 查询数据，返回列表对象
	 * </p>
	 *
	 * @return the user all
	 */
	List<UserPO> getAllUserAccount();

	/**
	 * 根据参数查询用户列表，分页
	 * <p>
	 * 分页查询数据，返回列表对象
	 * </p>
	 *
	 * @param userExtendPO the user extend po
	 * @param page         the page      当前页码
	 * @param endPage      the endPage   结束页码
	 * @return the user list limit by params
	 */
	List<UserExtendPO> getUserListLimitByParams(@Param("user") UserExtendPO userExtendPO, @Param("page") int page, @Param("endPage") int endPage);

	// ================================添加================================== //

	/**
	 * 新增一个用户
	 * <p>
	 * 新增一条记录，返回boolean
	 * </p>
	 *
	 * @param userExtendPO the user extend po
	 * @return the Boolean
	 */
	boolean addUser(@Param("user") UserExtendPO userExtendPO);

	/**
	 * 新增一个用户扩展信息
	 * <p>
	 * 新增一条记录，返回boolean
	 * </p>
	 *
	 * @param userExtendPO the user extend po
	 * @return the boolean
	 */
	boolean addUserExtend(@Param("user") UserExtendPO userExtendPO);

	/**
	 * 批量新增用户
	 * <p>
	 * 插入批量数据，参数为列表对象，返回booean
	 * </p>
	 *
	 * @param list the list
	 * @return the boolean
	 */
	boolean addBatchUser(@Param("rows") List<UserExtendPO> list);

	/**
	 * 批量新增用户扩展信息
	 * <p>
	 * 插入批量数据，参数为列表对象，返回booean
	 * </p>
	 *
	 * @param list the list
	 * @return the boolean
	 */
	boolean addBatchUserExtend(@Param("rows") List<UserExtendPO> list);

	// ================================删除================================== //

	/**
	 * 根据用户ID删除用户账户
	 * <p>
	 * 删除，执行一条SQL语句，参数为类型，返回boolean
	 * </p>
	 *
	 * @param id the id
	 * @return boolean boolean
	 */
	boolean deleteUserById(@Param("id") String id);

	// ================================修改================================== //

	/**
	 * 更新用户扩展信息
	 * <p>
	 * 更新，执行一条SQL语句，参数为对象，返回int
	 * </p>
	 *
	 * @param userExtendPO the user extend po
	 * @return the int
	 */
	int updateUserExtend(@Param("params") UserExtendPO userExtendPO);
}
