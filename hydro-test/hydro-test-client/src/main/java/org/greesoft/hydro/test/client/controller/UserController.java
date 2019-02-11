package org.greesoft.hydro.test.client.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.greesoft.hydro.test.client.bean.vo.UserAccountVO;
import org.greesoft.hydro.test.client.bean.vo.UserAddVO;
import org.greesoft.hydro.test.client.bean.vo.UserUpdateVO;
import org.greesoft.hydro.test.client.bean.vo.UserVO;
import org.greesoft.hydro.test.client.service.UserService;
import org.greesoft.hydro.test.client.utils.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * demo controller
 * <p> Date             :2017/12/28 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

@Api(value = "用户", description = "根用户相关的一系列操作，依赖于restful模式!")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private static final String MAP_KEY_ERROR = "error";

	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@ApiOperation(value = "根据条件查询用户", notes = "根据条件查询用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "账号", dataType = "String", paramType="query"),
			@ApiImplicitParam(name = "address", value = "地址信息，模糊查询", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "age", value = "年龄", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "name", value = "用户名称", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "电话号码", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "sex", value = "性别", dataType = "Boolean", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "分页起始位置", dataType = "int", defaultValue = "0" ,paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "分页结束位置", dataType = "int", defaultValue = "10" , paramType = "query")
	})
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getUserByParams(
			@RequestParam(name = "account", required = false) String account,
			@RequestParam(name = "address", required = false) String address,
			@RequestParam(name = "age", required = false) Integer age,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "phone", required = false) String phone,
			@RequestParam(name = "sex", required = false) Boolean sex,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
	) throws InvocationTargetException, IllegalAccessException {

		Map<String, String> params = new HashMap<>(6);

		if (!StringUtils.isBlank(account)) {
			params.put("account", account);
		}

		if (!StringUtils.isBlank(address)) {
			params.put("addr", address);
		}

		if (null != age && 0 < age) {
			params.put("age", String.valueOf(age));
		}

		if (!StringUtils.isBlank(name)) {
			params.put("name", name);
		}

		if (!StringUtils.isBlank(phone)) {
			params.put("phone", phone);
		}

		if (null != sex) {
			params.put("sex", sex ? "1" : "0");
		}

		UserVO userVO = userService.getVO(params);
		List<UserVO> result = userService.getUserListLimitByParams(userVO, page, pageSize);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "新增一个用户", notes = "根据UserExtendBean传参新增一个用户")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "UserExtendBean参数传递不正确")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user", value = "用户对象", dataType = "UserAddVO", paramType = "body")
	})
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(
			@ApiParam(name = "user", value = "用户扩展信息实体类")
			@Valid @RequestBody UserAddVO user,
			BindingResult bindingResult
	) {

		if (bindingResult.hasErrors()) {
			Map<String, String> result = new HashMap<>(1);
			result.put(MAP_KEY_ERROR, bindingResult.getFieldError().getDefaultMessage());

			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if(userService.checkUserAccount(user.getAccount())) {
			Map<String, String> result = new HashMap<>(1);
			result.put(MAP_KEY_ERROR, "账户已经存在");
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// 添加成功
		String uid = userService.addUser(user);

		// 查询添加成功的用户信息
		UserVO result = userService.getUserInfoById(uid);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "根据ID获取用户信息", notes = "根据url的id来获取用户的信息")
	@ApiResponses(value = {
			@ApiResponse(code = 404, message = "该ID用户不存在")
	})
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserVO> getUserInfoById(
			@PathVariable(name = "id") String id
	) {

		UserVO result = userService.getUserInfoById(id);

		if (null != result) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ApiOperation(value = "根据用户ID删除用户", notes = "根据用户ID删除用户")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "响应错误")
	})
	@ApiImplicitParam(name = "id", value = "编号", dataType = "String", paramType = "path")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delUserById(
			@PathVariable String id
	) {

		if(!userService.delUserById(id)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation(value = "根据ID更新用户信息", notes = "根据ID更新用户信息")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "响应错误")
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户ID", dataType = "String", paramType = "path"),
			@ApiImplicitParam(name = "userUpdateVO", value = "用户更新对象", dataType = "UserUpdateVO", paramType = "body")
	})
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity updateUser(
			@PathVariable(name = "id") String id,
			@Valid @RequestBody UserUpdateVO userUpdateVO,
			BindingResult bindingResult
	) {

		if (bindingResult.hasErrors()) {
			Map<String, String> err = new HashMap<>(1);
			err.put(MAP_KEY_ERROR, bindingResult.getFieldError().getDefaultMessage());
			return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
		}

		userUpdateVO.setId(id);

		if(!userService.updateUserInfo(userUpdateVO)) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		UserVO result = userService.getUserInfoById(id);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "获取账户列表", notes = "获取账户列表")
	@RequestMapping(value = "/account/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllUserAccount() {
		List<UserAccountVO> list = userService.getAllUserAccount();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "批量新增用户", notes = "批量新增用户")
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "参数传递不正确")
	})
	@RequestMapping(value = "/batch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addBatch(
			@Valid @RequestBody ListWrapper<UserAddVO> list,
			BindingResult bindingResult
	) {

		if (bindingResult.hasErrors()) {
			Map<String, String> result = new HashMap<>(1);
			result.put(MAP_KEY_ERROR, bindingResult.getFieldError().getDefaultMessage());

			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		for(UserAddVO item: list.getList()) {
			if(userService.checkUserAccount(item.getAccount())) {
				Map<String, String> result = new HashMap<>(1);
				result.put(MAP_KEY_ERROR, "账户已经存在");
				return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		if(!userService.addBatchUser(list.getList())) {
			return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity(HttpStatus.OK);
	}

	@ApiOperation(value = "检查账号是否存在", notes = "根据传参检查是否有该账号")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "用户账号", required = true, dataType = "String", paramType = "path")
	})
	@RequestMapping(value = "/account/{account}/check", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity checkUserAccount(
			@PathVariable(name = "account") String account
	) {

		boolean check = userService.checkUserAccount(account);

		JSONObject result = new JSONObject();
		result.put("check", check);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "获取用户总数", notes = "获取用户总的数量")
	@RequestMapping(value = "/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getUserCount() {
		int count = userService.getUserCount();
		JSONObject result = new JSONObject();
		result.put("count", count);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "根据ID获取账户信息", notes = "根据ID获取账户信息")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "获取该ID的账户信息"),
			@ApiResponse(code = 404, message = "用户不存在")
	})
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
	@RequestMapping(value = "/{id}/account", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserAccountVO> getUserAccount(
			@PathVariable(name = "id") String id
	) {

		UserAccountVO result = userService.getUserAccountInfoById(id);

		if (null != result) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	private void clientException(Exception e) {
		LOGGER.error("context", e);
	}
}
