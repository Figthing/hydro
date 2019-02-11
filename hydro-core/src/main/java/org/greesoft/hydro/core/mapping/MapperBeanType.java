package org.greesoft.hydro.core.mapping;

import org.greesoft.hydro.core.exception.SqlSessionException;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

/**
 * JavaBean类型
 * <p> Date             :2017/12/21 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public enum MapperBeanType {
	INT("java.lang.Integer"),
	INTEGER("java.lang.Integer"),
	LONG("java.lang.Long"),
	DATE,
	BOOLEAN,
	STRING,
	DOUBLE("java.lang.Double"),
	FLOAT("java.lang.Float");

	private Class<?> clazz;
	private Method valueOf;

	MapperBeanType() {}

	MapperBeanType(String str) {
		try {
			this.clazz = Class.forName(str);
			this.valueOf = clazz.getMethod("valueOf", String.class);
		} catch (Exception e) {
			throw new SqlSessionException("Class forName error!", e);
		}
	}

	/**
	 * 根据类型自动转换为数据
	 *
	 * @param val the val
	 * @return the object
	 */
	public Object toValue(Object val) {

		try {

			String result = val.toString();

			if (this == STRING) {
				return result;
			}

			if (this == DATE) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return sdf.parse(result);
			}

			if (this == BOOLEAN) {
				return result.equals("0") || result.toLowerCase().equals("false") ? Boolean.FALSE : Boolean.TRUE;
			}

			return this.valueOf.invoke(null, result);
		} catch (Exception e) {
			throw new SqlSessionException("toValue fail!", e);
		}
	}
}
