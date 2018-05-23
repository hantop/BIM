package com.zzrbi.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * SQL注入过滤器
 * 
 * @author sihang
 *
 */
public class SqlInjectFilter implements Filter {
	
	private static Logger logger = Logger.getLogger(SqlInjectFilter.class);
	
	private static List<String> invalidsql = new ArrayList<String>();
	
    private static String error = "/error/sqlErrors.jsp";
    
    private static boolean debug = false;

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)throws IOException, ServletException {
        //防sql关键字注入
        if (debug) {
        	logger.info("prevent sql inject filter works");
        }
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Map<String, String> params = request.getParameterMap();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = request.getParameter(key);
            if (debug) {
            	logger.info("process params <key, value>: <" + key + ", " + value + ">");
            }
            for (String word : invalidsql) {
                if (word.equalsIgnoreCase(value) || value.contains(word)) {
                    if (value.contains("<")) {
                        value = value.replace("<", "<");
                    }
                    if (value.contains(">")) {
                        value = value.replace(">", ">");
                    }
                    if (value.contains("(")) {
                        value = value.replace("(", "（");
                    }
                    if (value.contains(")")) {
                        value = value.replace(")", "）");
                    }
                    request.getSession().setAttribute("sqlInjectError", "您输入的参数值  \"" + value + "\" 中包含关键字: \"" + word + "\"");
                    response.sendRedirect(request.getContextPath() + error);
                    return;
                }
            }
        }
        fc.doFilter(req, res);
    }

	public void init(FilterConfig conf) throws ServletException {
        String sql = conf.getInitParameter("invalidsql");
        String errorpage = conf.getInitParameter("error");
        String de = conf.getInitParameter("debug");
        if (errorpage != null) {
            error = errorpage;
        }
        if (sql != null) {
            String[] sqlarr = sql.split(" ");
            for (String sqlword : sqlarr) {
                invalidsql.add(sqlword);
            }
            invalidsql.add("<");
            invalidsql.add(">");
            invalidsql.add("(");
            invalidsql.add(")");
        }
        if (de != null && Boolean.parseBoolean(de)) {
            debug = true;
            logger.info("PreventSQLInject Filter staring...");
            logger.info("print filter details");
            logger.info("invalid words as fllows (split with blank):");
            for (String s : invalidsql) {
            	logger.info(s + " ");
            }
            logger.info("error page as fllows");
        }
    }

}
