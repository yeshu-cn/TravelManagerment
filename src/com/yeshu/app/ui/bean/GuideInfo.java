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
	private String nickname;
	
	

	public GuideInfo(int id, String name, String phone, String nickname) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.nickname = nickname;
	}

	
	
	public String getNickname() {
		return nickname;
	}



	public void setNickname(String nickname) {
		this.nickname = nickname;
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

