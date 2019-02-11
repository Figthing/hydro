package org.greesoft.hydro.test.client.bean.mapper;

import org.greesoft.hydro.test.client.bean.po.UserExtendPO;
import org.greesoft.hydro.test.client.bean.po.UserPO;
import org.greesoft.hydro.test.client.bean.vo.UserAccountVO;
import org.greesoft.hydro.test.client.bean.vo.UserAddVO;
import org.greesoft.hydro.test.client.bean.vo.UserUpdateVO;
import org.greesoft.hydro.test.client.bean.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用户对象转换器
 * <p> Date             :2018/2/12 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */
@Mapper(componentModel = "spring")
@SuppressWarnings("all")
public interface UserMapper extends BasicObjectMapper<UserVO, UserExtendPO> {

	/**
	 * The constant MAPPER.
	 */
	UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

	/**
	 * 用户添加
	 *
	 * @param userAddVO the user add vo
	 * @return the user extend po
	 */
	@Mapping(target = "addr", source = "address")
	UserExtendPO userAdd(UserAddVO userAddVO);

	/**
	 * 用户添加
	 *
	 * @param list the list
	 * @return the list
	 */
	List<UserExtendPO> userAdd(List<UserAddVO> list);

	/**
	 * 用户更新
	 *
	 * @param userUpdateVO the user update vo
	 * @return the user extend po
	 */
	@Mapping(target = "addr", source = "address")
	UserExtendPO userUpdate(UserUpdateVO userUpdateVO);

	/**
	 * 账户
	 *
	 * @param userPO the user po
	 * @return the user account vo
	 */
	UserAccountVO userAccount(UserPO userPO);

	/**
	 * 账户列表
	 *
	 * @param userPO the user po
	 * @return the list
	 */
	List<UserAccountVO> userAccount(List<UserPO> userPO);

	@Mapping(target = "address", source = "addr")
	@Override
	UserVO from(UserExtendPO var1);
}
