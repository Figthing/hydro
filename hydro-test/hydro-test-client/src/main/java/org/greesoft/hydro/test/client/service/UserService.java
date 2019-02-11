package org.greesoft.hydro.test.client.service;

import org.greesoft.hydro.test.client.bean.vo.UserAccountVO;
import org.greesoft.hydro.test.client.bean.vo.UserAddVO;
import org.greesoft.hydro.test.client.bean.vo.UserUpdateVO;
import org.greesoft.hydro.test.client.bean.vo.UserVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 用户server接口
 * <p> Date             :2017/12/28 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
public interface UserService {

	/**
	 * 根据Map获取VO对象
	 *
	 * @param params the params
	 * @return the vo
	 * @throws InvocationTargetException the invocation target exception
	 * @throws IllegalAccessException    the illegal access exception
	 */
	UserVO getVO(Map params) throws InvocationTargetException, IllegalAccessException;

	/**
	 * 根据ID获取用户信息
	 *
	 * @param id the id
	 * @return the user info by id
	 */
	UserVO getUserInfoById(String id);

	/**
	 * 根据参数获取用户列表，分页
	 * <p>
	 * 注意：page和endPage并不是当前页和每页显示数，而是limit page,endPage
	 * </p>
	 *
	 * @param userVO  the user vo
	 * @param page    the 当前页      10
	 * @param endPage the 结束页码    20
	 * @return the user list by params
	 */
	List<UserVO> getUserListLimitByParams(UserVO userVO, int page, int endPage);

	/**
	 * 根据ID获取账户信息
	 *
	 * @param id the id
	 * @return the user info
	 */
	UserAccountVO getUserAccountInfoById(String id);

	/**
	 * 获取所有的用户账号
	 *
	 * @return the all user account
	 */
	List<UserAccountVO> getAllUserAccount();

	/**
	 * 获取用户总数
	 *
	 * @return the user count
	 */
	int getUserCount();

	/**
	 * 检查账号是否存在
	 *
	 * @param account the account
	 * @return the boolean
	 */
	boolean checkUserAccount(String account);

	/**
	 * 新增一个用户
	 *
	 * @param userAddVO the user add vo
	 * @return the String
	 */
	String addUser(UserAddVO userAddVO);

	/**
	 * 批量新增用户
	 *
	 * @param list the list
	 * @return the boolean
	 */
	boolean addBatchUser(List<UserAddVO> list);

	/**
	 * 更新用户信息
	 *
	 * @param userUpdateVO the user update vo
	 * @return the boolean
	 */
	boolean updateUserInfo(UserUpdateVO userUpdateVO);

	/**
	 * 用户用户ID删除用户
	 *
	 * @param id the id
	 * @return the boolean
	 */
	boolean delUserById(String id);
}
