package org.greesoft.hydro.test.client.utils;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List验证包装器
 * <p> Date             :2018/2/6 </p>
 * <p> Module           : </p>
 * <p> Description      : </p>
 * <p> Remark           : </p>
 *
 * @author zhouqi
 * @version 1.0
 */

public class ListWrapper<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 8217515745648331546L;

	private List<T> list;

	public ListWrapper() {
		list = new ArrayList<>();
	}

	public ListWrapper(List<T> list) {
		this.list = list;
	}

	@Valid
	@JsonProperty("datas")
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public boolean add(T e) {
		return list.add(e);
	}

	public void clear() {
		list.clear();
	}
}
