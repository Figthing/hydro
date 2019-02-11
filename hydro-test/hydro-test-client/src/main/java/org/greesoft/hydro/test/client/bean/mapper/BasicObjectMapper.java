package org.greesoft.hydro.test.client.bean.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * POJO转换器基础类
 * <p> Date             :2018/2/12 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @param <SOURCE> the type parameter
 * @param <TARGET> the type parameter
 * @author zhouqi
 * @version 1.0
 */
public interface BasicObjectMapper<SOURCE, TARGET> {

	/**
	 * Source转换为Target对象
	 *
	 * @param var1 the var 1
	 * @return the target
	 */
	@Mappings({})
	@InheritConfiguration
	TARGET to(SOURCE var1);

	/**
	 * Source转换为Target对象，列表形式
	 *
	 * @param var1 the var 1
	 * @return the list
	 */
	@InheritConfiguration
	List<TARGET> to(List<SOURCE> var1);

	/**
	 * Target转换为Source对象
	 *
	 * @param var1 the var 1
	 * @return the source
	 */
	@InheritInverseConfiguration
	SOURCE from(TARGET var1);

	/**
	 * Target转换为Source对象，列表形式
	 *
	 * @param var1 the var 1
	 * @return the list
	 */
	@InheritInverseConfiguration
	List<SOURCE> from(List<TARGET> var1);
}
