/**
 * 
 */
package com.yeshu.app.ui.bean;

import java.io.Serializable;

/**
 * 
 * @author yeshu
 * @date 2013-7-18 
 *
 */

public class GuideInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String phone;
	
	

	public GuideInfo(int id, String name, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
	}

	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}

