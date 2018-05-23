package com.zzrbi.filter;

import com.zzrbi.util.XssHttpServletRequestWrapper;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Xss过滤器
 * 
 * @author sihang
 *
 */
public class XssFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(XssFilter.class);

	public void destroy() {
		logger.info("XssFilter destroy...");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	public void init(FilterConfig config) throws ServletException {
		logger.info("XSSInject Filter staring...");
	}
}
