package org.greesoft.hydro.core.velocity;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

import java.io.IOException;
import java.io.Writer;

/**
 * velocity JSON转换驱动
 * <p> Date             :2017/12/14 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class JsonDirective extends Directive {

	@Override
	public String getName() {
		return "Object2JsonString";
	}

	@Override
	public int getType() {
		return LINE;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		SimpleNode sn_region = (SimpleNode) node.jjtGetChild(0);
		Object data = sn_region.value(context);
		writer.append(JSON.toJSONString(data));
		return true;
	}
}
