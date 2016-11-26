package com.zhuoyue.model;
/*
 * @author 王璇
 */
public class MenuInfo {
	private int id;   
    private String menuContent;
    private String menuPath;
    private int menuOrder;
    private int parentmenuId;   
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMenuContent() {
		return menuContent;
	}
	public void setMenuContent(String menuContent) {
		this.menuContent = menuContent;
	}
	public String getMenuPath() {
		return menuPath;
	}
	public void setMenuPath(String menuPath) {
		this.menuPath = menuPath;
	}
	public int getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(int menuOrder) {
		this.menuOrder = menuOrder;
	}
	public int getParentmenuId() {
		return parentmenuId;
	}
	public void setParentmenuId(int parentmenuId) {
		this.parentmenuId = parentmenuId;
	}
	

	

}
