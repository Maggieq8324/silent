package pms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pms.service.InterceptorService;

public class DefaultHandlerInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private InterceptorService interceptorService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return interceptorService.preHandle(request, response, handler);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, readOnly = false)
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		interceptorService.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		interceptorService.afterCompletion(request, response, handler, ex);
	}

}
