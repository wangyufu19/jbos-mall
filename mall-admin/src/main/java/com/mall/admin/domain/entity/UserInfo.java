package com.mall.admin.domain.entity;
/**
 * UserInfo
 * @author youfu.wang
 * @date 2019-01-31
 */

public class UserInfo extends BaseEntity{

	private String username;
	private String password;	
	private String salt;
	private String userType;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getUserType(){return userType;}
	public void setUserType(String userType){this.userType=userType;}
}
