/**
 * 
 */
package com.yeshu.app.ui;

/**
 * 
 * @author yeshu
 * @date 2013-9-7 
 *
 */

public class SideMenuItem {
	private String name;
	private int icon;



	public SideMenuItem(String name, int icon) {
		super();
		this.name = name;
		this.icon = icon;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}

