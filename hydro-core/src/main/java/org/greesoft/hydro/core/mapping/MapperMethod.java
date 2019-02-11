package org.greesoft.hydro.core.mapping;

import org.greesoft.hydro.core.annotation.Param;
import org.greesoft.hydro.core.session.SqlSession;
import org.greesoft.hydro.core.session.SqlSessionConfig;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 方法映射
 * <p> Date             :2017/12/8 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class MapperMethod {

	/**
	 * SQL命令
	 */
	private final SqlCommand command;

	/**
	 * 方法签名
	 */
	private final MethodSignature method;

	public MapperMethod(Class<?> mapperInterface, Method method, SqlSessionConfig configuration) {
		this.method = new MethodSignature(method);
		this.command = new SqlCommand(configuration, mapperInterface, method);
	}

	/**
	 * 执行
	 *
	 * @param sqlSession the sql session
	 * @param args       the args
	 * @return the object
	 */
	public Object execute(SqlSession sqlSession, Object[] args) {

		// 参数
		Map<String, Object> params = this.method.convertArgsToSqlCommandParam(args);

		Object result = sqlSession.executorSql(this.command.getStatementName(), params);

		if (this.method.isReturnsVoid()) {
			return null;
		}

		return result;
	}

	//SQL命令，静态内部类
	private static class SqlCommand {

		/**
		 * 映射封装对象
		 */
		private final String statementName;

		@SuppressWarnings("all")
		private SqlSessionConfig config;

		public SqlCommand(SqlSessionConfig configuration, Class<?> mapperInterface, Method method) {
			this.statementName = mapperInterface.getName() + "." + method.getName();
			this.config = configuration;
		}

		public String getStatementName() {
			return statementName;
		}
	}

	//方法签名，静态内部类
	private static class MethodSignature {
		private final boolean returnsVoid;
		private final Class<?> returnType;
		private final SortedMap<Integer, String> paramsName;

		public MethodSignature(Method method) {
			this.returnType = method.getReturnType();
			this.returnsVoid = void.class.equals(this.returnType);
			this.paramsName = Collections.unmodifiableSortedMap(getParams(method));
		}

		/**
		 * 得到所有参数名
		 *
		 * @param method 函数
		 * @return the params
		 */
		private SortedMap<Integer, String> getParams(Method method) {
			//用一个TreeMap,这样就保证还是按参数的先后顺序
			final SortedMap<Integer, String> params = new TreeMap<>();
			final Class<?>[] argTypes = method.getParameterTypes();
			for (int i = 0; i < argTypes.length; i++) {
				String paramName = String.valueOf(params.size());
				paramName = getParamNameFromAnnotation(method, i, paramName);
				params.put(i, paramName);
			}
			return params;
		}

		/**
		 * 根据Annotation获取参数名
		 *
		 * @param method    函数
		 * @param i         位置
		 * @param paramName 参数名
		 * @return the param name from annotation
		 */
		private String getParamNameFromAnnotation(Method method, int i, String paramName) {
			final Object[] paramAnnos = method.getParameterAnnotations()[i];
			for (Object paramAnno : paramAnnos) {
				if (paramAnno instanceof Param) {
					paramName = ((Param) paramAnno).value();
					break;
				}
			}
			return paramName;
		}

		public boolean isReturnsVoid() {
			return returnsVoid;
		}

		public SortedMap<Integer, String> getParams() {
			return paramsName;
		}

		public Class<?> getReturnType() {
			return returnType;
		}

		/**
		 * 组合参数对应
		 *
		 * @param args 参数
		 * @return the map
		 */
		public Map<String, Object> convertArgsToSqlCommandParam(Object[] args) {
			final int paramCount = this.paramsName.size();
			Map<String, Object> params = new HashMap<>();

			// 如果没参数
			if (args == null || paramCount == 0) {
				return null;
			}

			// 只有一个参数
			if (paramCount == 1) {
				params.put(this.paramsName.get(0), args[0]);
				return params;
			}

			// 多个参数
			for(int i = 0; i < paramCount; i++) {
				params.put(this.paramsName.get(i), args[i]);
			}

			return params;
		}
	}

}
