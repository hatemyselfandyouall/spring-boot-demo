/**
 * 
 */
package com.wangxinenpu.springbootdemo.dataobject.vo;

import java.io.Serializable;

/**
 * @author xxh
 * @date 2017年6月15日 下午3:24:49
 */
public class FileSysRequestVo implements Serializable {

	private static final long serialVersionUID = 4054312952502371740L;

	private String oauthCode;
	
	private String redirectUrl;
	
	private String clientId;
	
	private String userInfoStr;
	
	private String accessToken;
	
	private String refreshToken;
	
	public String getOauthCode() {
		return oauthCode;
	}

	public void setOauthCode(String oauthCode) {
		this.oauthCode = oauthCode;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUserInfoStr() {
		return userInfoStr;
	}

	public void setUserInfoStr(String userInfoStr) {
		this.userInfoStr = userInfoStr;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
}
