package com.eflake.efframework.model;

/**   
* @Title LogBean.java 
* @Description Model class of log content.
* @author Eflake 
* @date 2014-6-6 下午4:14:08 
*/
public class LogBean {
	private final int mLogId;
	private final String mLogContent;

	public LogBean(final int logId, final String logContent) {
		mLogId = logId;
		mLogContent = logContent;
	}

	public int getId() {
		return mLogId;
	}

	public String getContent() {
		return mLogContent;
	}
}
