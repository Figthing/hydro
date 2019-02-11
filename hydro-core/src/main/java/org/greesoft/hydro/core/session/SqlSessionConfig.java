package org.greesoft.hydro.core.session;

import org.greesoft.hydro.core.mapping.MapperRegistry;
import org.greesoft.hydro.core.mapping.MapperStatement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 核心配置文件
 * <p> Date             :2017/12/11 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class SqlSessionConfig {

	/**
	 * 接口映射注册器
	 */
	private final MapperRegistry mapperRegistry = new MapperRegistry();

	/**
	 * 资源存储
	 */
	private final Set<String> loadedResources = new HashSet<String>();

	/**
	 * 节点属性映射
	 */
	private final Map<String, MapperStatement> mapperStatements = new HashMap<String, MapperStatement>();

	/**
	 * 数据源
	 */
	private final Object datasource;

	public SqlSessionConfig(Object datasource) {
		this.datasource = datasource;
	}

	/**
	 * 添加接口到映射器中
	 *
	 * @param type 扫描后的接口
	 */
	public <T> void addMapper(Class<T> type) {
		mapperRegistry.addMapper(type);
	}

	/**
	 * 通过接口获取实体类
	 *
	 * @param type       接口
	 * @param sqlSession SqlSession
	 * @return 实体类 mapper
	 */
	public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
		return MapperRegistry.getMapper(type, sqlSession);
	}

	/**
	 * 资源是否加载
	 *
	 * @param resource the resource
	 * @return the boolean
	 */
	public boolean isResourceLoaded(String resource) {
		return loadedResources.contains(resource);
	}

	/**
	 * 添加资源
	 *
	 * @param resource the resource
	 */
	public void addLoadedResource(String resource) {
		loadedResources.add(resource);
	}

	/**
	 * 映射接口是否注册
	 *
	 * @param type the type
	 * @return the boolean
	 */
	public boolean hasMapper(Class<?> type) {
		return mapperRegistry.hasMapper(type);
	}

	/**
	 * 添加节点属性映射
	 *
	 * @param ms MapperStatement
	 */
	public void addMappedStatement(MapperStatement ms) {
		mapperStatements.put(ms.getId(), ms);
	}

	/**
	 * 获取映射数据
	 *
	 * @param id the id
	 * @return the mapper statement
	 */
	public MapperStatement getMappedStatement(String id) {
		return mapperStatements.get(id);
	}

	/**
	 * 获取数据源
	 *
	 * @return the datasource
	 */
	public Object getDatasource() {
		return datasource;
	}
}
