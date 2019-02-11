package org.greesoft.hydro.core.mapping;

import org.greesoft.hydro.core.session.SqlSessionConfig;

/**
 * 映射构建器助手
 * <p> Date             :2017/12/13 </p>
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

public class MapperBuilderAssistant {

	/**
	 * SqlSession配置
	 */
	private SqlSessionConfig configuration;

	/**
	 * 当前命名空间
	 */
	private String currentNamespace;

	/**
	 * 资源文件
	 */
	private final String resource;

	public MapperBuilderAssistant(SqlSessionConfig configuration, String resource) {
		this.configuration = configuration;
		this.resource = resource;
	}

	/**
	 * 设置当前命名空间
	 *
	 * @param namespace 命名空间名称
	 */
	public void setCurrentNamespace(String namespace) {
		this.currentNamespace = namespace;
	}

	/**
	 * 获取命名空间名称
	 *
	 * @return the current namespace
	 */
	public String getCurrentNamespace() {
		return this.currentNamespace;
	}

	/**
	 * 增加映射语句
	 *
	 * @param id         the id
	 * @param type       the type
	 * @param resultType the result type
	 * @param route      the route
	 * @param loop       the loop
	 * @param item       the item
	 * @param sql        the sql
	 * @param startRow   the start row
	 * @param endRow     the end row
	 * @return the mapped statement
	 */
	@SuppressWarnings("all")
	public MapperStatement addMappedStatement(
			String id,
			String type,
			String resultType,
			String route,
			String loop,
			String item,
			String sql,
			String startRow,
			String endRow
	) {

		MapperStatement mapperStatement = new MapperStatement();
		mapperStatement.setId(currentNamespace + "." + id);
		mapperStatement.setType(type);
		mapperStatement.setResultType(resultType);
		mapperStatement.setRoute(route);
		mapperStatement.setLoop(loop);
		mapperStatement.setItem(item);
		mapperStatement.setSql(sql);
		mapperStatement.setStartRow(startRow);
		mapperStatement.setEndRow(endRow);
		mapperStatement.setResource(this.resource);

		this.configuration.addMappedStatement(mapperStatement);

		return mapperStatement;
	}
}
