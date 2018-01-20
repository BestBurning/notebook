package com.diyishuai.notebook.bean;

import com.diyishuai.notebook.constants.Constants;

public class NoteBook {
	private String rowKey;
	private String name;
	private String createTime;
	private String status;

	public NoteBook() {
	}

	public NoteBook(String rowKey, String name, String createTime, String status) {
		this.rowKey = rowKey;
		this.name = name;
		this.createTime = createTime;
		this.status = status;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(rowKey)
			.append(Constants.STRING_SEPARATOR)
			.append(name)
			.append(Constants.STRING_SEPARATOR)
			.append(createTime)
			.append(Constants.STRING_SEPARATOR)
			.append(status);
		return sb.toString();
	}
}
