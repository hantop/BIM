package com.zzrbi.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.zzrbi.util.XssHttpServletRequestWrapper;



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
