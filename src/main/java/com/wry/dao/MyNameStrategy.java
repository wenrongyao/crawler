package com.wry.dao;

import org.hibernate.cfg.DefaultNamingStrategy;

import com.wry.constant.R;

public class MyNameStrategy extends DefaultNamingStrategy {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String classToTableName(String className) {
		return super.classToTableName(className + R.TABLENAME);
	}

}
