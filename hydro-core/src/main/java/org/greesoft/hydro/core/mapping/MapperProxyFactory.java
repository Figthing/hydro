package org.greesoft.hydro.core.mapping;

import org.greesoft.hydro.core.session.SqlSession;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 映射代理工厂类
 * <p> Date             :2017/12/8 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class MapperProxyFactory<T> {

	/**
	 * 接口类
	 */
	private final Class<T> mapperInterface;

	/**
	 * 映射缓存
	 */
	private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<>();

	public MapperProxyFactory(Class<T> mapperInterface) {
		this.mapperInterface = mapperInterface;
	}

	/**
	 * new代理映射对象
	 *
	 * @param mapperProxy 代理映射对象
	 * @return the t
	 */
	protected T newInstance(MapperProxy<T> mapperProxy) {
		return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);
	}

	/**
	 * new代理映射对象
	 *
	 * @param sqlSession SqlSession
	 * @return 代理映射对象
	 */
	public T newInstance(SqlSession sqlSession) {
		final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface, methodCache);
		return newInstance(mapperProxy);
	}
}
