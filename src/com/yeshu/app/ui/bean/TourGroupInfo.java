/**
 * 
 */
package com.yeshu.app.ui.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author yeshu
 * @date 2013-7-18 
 *
 */

public class TourGroupInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String members;
	private int shop;	//购物点数量
	private int isShop;
	private String senddate;
	private String recvdate;
	private String routetitle;
	private String routedays;
	private String route;
	private String offices;
	private String type;
	private String remark;
	private String jidiao;
	private String guides;
	
	
	
	public TourGroupInfo(int id, String members, int shop, int isShop,
			String senddate, String recvdate, String routetitle,
			String routedays, String route, String offices, String type,
			String remark, String jidiao, String guides) {
		super();
		this.id = id;
		this.members = members;
		this.shop = shop;
		this.isShop = isShop;
		this.senddate = senddate;
		this.recvdate = recvdate;
		this.routetitle = routetitle;
		this.routedays = routedays;
		this.route = route;
		this.offices = offices;
		this.type = type;
		this.remark = remark;
		this.jidiao = jidiao;
		this.guides = guides;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getMembers() {
		return members;
	}



	public void setMembers(String members) {
		this.members = members;
	}



	public int getShop() {
		return shop;
	}



	public void setShop(int shop) {
		this.shop = shop;
	}



	public int getIsShop() {
		return isShop;
	}



	public void setIsShop(int isShop) {
		this.isShop = isShop;
	}



	public String getSenddate() {
		return senddate;
	}



	public void setSenddate(String senddate) {
		this.senddate = senddate;
	}



	public String getRecvdate() {
		return recvdate;
	}



	public void setRecvdate(String recvdate) {
		this.recvdate = recvdate;
	}



	public String getRoutetitle() {
		return routetitle;
	}



	public void setRoutetitle(String routetitle) {
		this.routetitle = routetitle;
	}



	public String getRoutedays() {
		return routedays;
	}



	public void setRoutedays(String routedays) {
		this.routedays = routedays;
	}



	public String getRoute() {
		return route;
	}



	public void setRoute(String route) {
		this.route = route;
	}



	public String getOffices() {
		return offices;
	}



	public void setOffices(String offices) {
		this.offices = offices;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getJidiao() {
		return jidiao;
	}



	public void setJidiao(String jidiao) {
		this.jidiao = jidiao;
	}



	public String getGuides() {
		return guides;
	}



	public void setGuides(String guides) {
		this.guides = guides;
	}
	
	
	
	
}

