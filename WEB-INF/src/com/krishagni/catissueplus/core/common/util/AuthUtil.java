package com.krishagni.catissueplus.core.common.util;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.krishagni.catissueplus.core.administrative.domain.Institute;
import com.krishagni.catissueplus.core.administrative.domain.User;

public class AuthUtil {
	private static final Log logger = LogFactory.getLog(AuthUtil.class);

	private static final String OS_AUTH_TOKEN_HDR = "X-OS-API-TOKEN";

	public static Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static boolean isSignedIn() {
		return getCurrentUser() != null;
	}
	
	public static User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		
		return (User)auth.getPrincipal();
	}

	public static Institute getCurrentUserInstitute() {
		User user = AuthUtil.getCurrentUser();
		return (user != null) ? user.getInstitute() : null;
	}
	
	public static String getRemoteAddr() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			return null;
		}
		
		Object obj = auth.getDetails();
		if (obj instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails details = (WebAuthenticationDetails)obj;
			return details.getRemoteAddress();
		}
		
		return null;
	}

	public static void setCurrentUser(User user) {
		setCurrentUser(user, null, null);
	}

	public static void setCurrentUser(User user, String authToken, HttpServletRequest httpReq) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, authToken, user.getAuthorities());
		if (httpReq != null) {
			token.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpReq));
		}

		SecurityContextHolder.getContext().setAuthentication(token);
	}

	public static void clearCurrentUser() {
		SecurityContextHolder.clearContext();
	}
	
	public static boolean isAdmin() {
		return getCurrentUser() != null && getCurrentUser().isAdmin();
	}
	
	public static boolean isInstituteAdmin() {
		return getCurrentUser() != null && getCurrentUser().isInstituteAdmin();
	}
	
	public static String encodeToken(String token) {
		return new String(Base64.encode(token.getBytes()));
	}
	
	public static String decodeToken(String token) {
		return new String(Base64.decode(token.getBytes()));
	}
	
	public static String getTokenFromCookie(HttpServletRequest httpReq) {
		String cookieHdr = httpReq.getHeader("Cookie");
		if (StringUtils.isBlank(cookieHdr)) {
			return null;
		}

		String[] cookies = cookieHdr.split(";");
		String authToken = null;
		for (String cookie : cookies) {
			if (!cookie.trim().startsWith("osAuthToken")) {
				continue;
			}

			String[] authTokenParts = cookie.trim().split("=");
			if (authTokenParts.length == 2) {
				try {
					authToken = URLDecoder.decode(authTokenParts[1], "utf-8");
					if (authToken.startsWith("%") || Utility.isQuoted(authToken)) {
						authToken = authToken.substring(1, authToken.length() - 1);
					}
				} catch (Exception e) {
					logger.error("Error obtaining token from cookie", e);
				}
				break;
			}
		}

		return authToken;
	}

	public static void setTokenCookie(HttpServletRequest httpReq, HttpServletResponse httpResp, String authToken) {
		Cookie cookie = new Cookie("osAuthToken", authToken);
		cookie.setPath(StringUtils.isBlank(httpReq.getContextPath()) ? "/" : httpReq.getContextPath());
		cookie.setHttpOnly(true);
		cookie.setSecure(httpReq.isSecure());

		if (authToken == null) {
			cookie.setMaxAge(0);
		}

		httpResp.addCookie(cookie);
	}

	public static void clearTokenCookie(HttpServletRequest httpReq, HttpServletResponse httpResp) {
		setTokenCookie(httpReq, httpResp, null);
	}

	public static void resetTokenCookie(HttpServletRequest httpReq, HttpServletResponse httpResp) {
		setTokenCookie(httpReq, httpResp, getAuthTokenFromHeader(httpReq));
	}

	public static String getAuthTokenFromHeader(HttpServletRequest httpReq) {
		return httpReq.getHeader(OS_AUTH_TOKEN_HDR);
	}
}