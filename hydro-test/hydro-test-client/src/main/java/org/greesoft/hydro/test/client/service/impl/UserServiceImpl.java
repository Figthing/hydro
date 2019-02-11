package org.greesoft.hydro.test.client.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.greesoft.hydro.test.client.bean.mapper.UserMapper;
import org.greesoft.hydro.test.client.bean.po.UserExtendPO;
import org.greesoft.hydro.test.client.bean.po.UserPO;
import org.greesoft.hydro.test.client.bean.vo.UserAccountVO;
import org.greesoft.hydro.test.client.bean.vo.UserAddVO;
import org.greesoft.hydro.test.client.bean.vo.UserUpdateVO;
import org.greesoft.hydro.test.client.bean.vo.UserVO;
import org.greesoft.hydro.test.client.dao.UserDao;
import org.greesoft.hydro.test.client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 用户server
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

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserVO getVO(Map params) throws InvocationTargetException, IllegalAccessException {
		UserVO userVO = new UserVO();
		BeanUtils.populate(userVO, params);
		return userVO;
	}

	@Override
	public UserVO getUserInfoById(String id) {
		UserExtendPO userExtendPO = userDao.getUserInfoById(id);

		if (null == userExtendPO) {
			return null;
		}

		return UserMapper.MAPPER.from(userExtendPO);
	}

	@Override
	public List<UserVO> getUserListLimitByParams(UserVO userVO, int page, int endPage) {
		UserExtendPO userExtendPO = UserMapper.MAPPER.to(userVO);
		List<UserExtendPO> list = userDao.getUserListLimitByParams(userExtendPO, page, endPage);
		return UserMapper.MAPPER.from(list);
	}

	@Override
	public UserAccountVO getUserAccountInfoById(String id) {

		UserPO userPO = new UserPO();
		userPO.setId(id);

		userPO = userDao.getUserInfoByParams(userPO);
		return UserMapper.MAPPER.userAccount(userPO);
	}

	@Override
	public List<UserAccountVO> getAllUserAccount() {
		List<UserPO> list = userDao.getAllUserAccount();
		return UserMapper.MAPPER.userAccount(list);
	}

	@Override
	public int getUserCount() {
		return userDao.getUserCount();
	}

	@Override
	public boolean checkUserAccount(String account) {
		return userDao.checkUserAccount(account);
	}

	@Override
	public String addUser(UserAddVO userAddVO) {

		String id = UUID.randomUUID().toString();

		UserExtendPO userExtendPO = UserMapper.MAPPER.userAdd(userAddVO);

		// 设置ID
		userExtendPO.setId(id);
		userExtendPO.setUid(id);

		// 添加账号
		userDao.addUser(userExtendPO);

		// 添加扩展信息
		userDao.addUserExtend(userExtendPO);

		return id;
	}

	@Override
	public boolean addBatchUser(List<UserAddVO> list) {

		List<UserExtendPO> batchList = UserMapper.MAPPER.userAdd(list);

		for(UserExtendPO item: batchList) {

			// 设置ID
			String id = UUID.randomUUID().toString();
			item.setId(id);
			item.setUid(id);
		}

		// 批量添加账号
		userDao.addBatchUser(batchList);

		// 批量添加扩展信息
		userDao.addBatchUserExtend(batchList);

		return true;
	}

	@Override
	public boolean updateUserInfo(UserUpdateVO userUpdateVO) {

		UserExtendPO userExtendPO = UserMapper.MAPPER.userUpdate(userUpdateVO);

		return 0< userDao.updateUserExtend(userExtendPO);
	}

	@Override
	public boolean delUserById(String id) {
		userDao.deleteUserById(id);
		return true;
	}

}
