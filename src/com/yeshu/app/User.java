/**
 * 
 */
package com.yeshu.app;

/**
 * @author yeshu
 * @date 2013年10月4日
 *
 */
public class User {
	private static User mInstance = new User();
	
	private String username = null;	//用户姓名
	private String phone = null;		//用户电话
	private String rolename = null;	//用户角色名（职位）
	
	private User(){
		
	}
	
	public static User getInstance(){
		return mInstance;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	
}
