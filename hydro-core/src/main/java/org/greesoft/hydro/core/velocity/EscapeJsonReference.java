package org.greesoft.hydro.core.velocity;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.app.event.implement.EscapeReference;

/**
 * JSON转义
 * <p> Date             :2017/12/20 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class EscapeJsonReference extends EscapeReference {

	/**
	 * JSON+SQL
	 *
	 * @param text
	 * @return An escaped String.
	 * @see <a href="http://jakarta.apache.org/commons/lang/api/org/apache/commons/lang/StringEscapeUtils.html#escapeHtml(java.lang.String)">StringEscapeUtils</a>
	 */
	protected String escape(Object text) {
		String str = StringEscapeUtils.escapeJavaScript(text.toString());
		return StringEscapeUtils.escapeSql(str);
	}

	/**
	 * @return attribute "eventhandler.escape.html.match"
	 */
	protected String getMatchAttribute()
	{
		return "eventhandler.escape.json.match";
	}

}
