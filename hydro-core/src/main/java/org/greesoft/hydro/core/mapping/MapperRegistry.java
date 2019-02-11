package org.greesoft.hydro.core.mapping;

import org.greesoft.hydro.core.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口映射注册器
 * <p> Date             :2017/12/11 </p>
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

public class MapperRegistry {

	/**
	 * 接口与实体类绑定映射关系
	 */
	private static final Map<Class<?>, MapperProxyFactory<?>> KNOWN_MAPPERS = new HashMap<>();

	/**
	 * 日志
	 */
	private static final Logger logger = LoggerFactory.getLogger(MapperRegistry.class);

	/**
	 * 通过接口获取实体类
	 *
	 * @param type       接口类
	 * @param sqlSession SqlSession
	 * @return T 实体类
	 */
	public static <T> T getMapper(Class<T> type, SqlSession sqlSession) {

		if (!KNOWN_MAPPERS.containsKey(type)) {
			return null;
		}

		final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) KNOWN_MAPPERS.get(type);
		return mapperProxyFactory.newInstance(sqlSession);
	}

	/**
	 * 该接口关系是否建立
	 *
	 * @param type 接口类
	 * @return the boolean
	 */
	public <T> boolean hasMapper(Class<T> type) {
		return KNOWN_MAPPERS.containsKey(type);
	}

	/**
	 * 添加映射（接口与实体类绑定映射关系）
	 *
	 * @param type 接口类
	 */
	public <T> void addMapper(Class<T> type) {

		if (!type.isInterface()) {
			logger.debug("MapperRegistery addMapper type [{}] not interface！", type);
			return;
		}

		if (hasMapper(type)) {
			logger.debug("Type [{}] is already known to the MapperRegistry.！", type);
			return;
		}

		boolean loadCompleted = false;
		try {
			KNOWN_MAPPERS.put(type, new MapperProxyFactory<T>(type));
			loadCompleted = true;
		} finally {
			if (!loadCompleted) {
				KNOWN_MAPPERS.remove(type);
			}
		}
	}
}
