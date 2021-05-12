package pms.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.util.UrlPathHelper;

import pms.support.BusinessException;

public class DefaultHandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver {

	private String defaultErrorView;

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		if (handlerMethod == null) {
			return null;
		}
		Method method = handlerMethod.getMethod();
		if (method == null) {
			return null;
		}
		ResponseBody responseBody = AnnotationUtils.findAnnotation(method, ResponseBody.class);
		if (responseBody == null) {
			responseBody = AnnotationUtils.findAnnotation(handlerMethod.getClass(), ResponseBody.class);
		}
		RestController restController = AnnotationUtils.findAnnotation(handlerMethod.getClass(), RestController.class);
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		String lookupPath = urlPathHelper.getLookupPathForRequest(request);
		if ((responseBody != null || restController != null) && !lookupPath.endsWith("home") && !lookupPath.endsWith("wehome")) {
			return handleResponseBody(request, response, exception);
		} else {
			ModelAndView modelAndView = super.doResolveHandlerMethodException(request, response, handlerMethod,
					exception);
			Map<String, Object> map = new HashMap<String, Object>();
			if (exception instanceof BusinessException) {
				map.put("error", exception.getLocalizedMessage());
				request.setAttribute("exceptionMessage", exception.getLocalizedMessage());
			} else {
				map.put("error", "系统错误：" + exception.getLocalizedMessage());
				request.setAttribute("exceptionMessage", "系统错误：" + exception.getLocalizedMessage());
				logger.error(exception.getLocalizedMessage(), exception);
			}
			if (lookupPath.endsWith("home")) {
				if (modelAndView == null) {
					request.getSession().invalidate();
					return new ModelAndView("relogin", map);
				} else {
					modelAndView.setViewName("relogin");
					modelAndView.addAllObjects(map);
					request.getSession().invalidate();
					return modelAndView;
				}
			} else if (lookupPath.endsWith("weHome")) {
				if (modelAndView == null) {
					request.getSession().invalidate();
					return new ModelAndView("welogin", map);
				} else {
					modelAndView.setViewName("welogin");
					modelAndView.addAllObjects(map);
					request.getSession().invalidate();
					return modelAndView;
				}
			} else {
				if (modelAndView == null) {
					return new ModelAndView(defaultErrorView, map);
				} else {
					modelAndView.setViewName(defaultErrorView);
					modelAndView.addAllObjects(map);
					return modelAndView;
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ModelAndView handleResponseBody(HttpServletRequest request, HttpServletResponse response,
			Exception exception) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (exception instanceof BusinessException) {
			map.put("error", exception.getLocalizedMessage());
			request.setAttribute("exceptionMessage", exception.getLocalizedMessage());
		} else {
			map.put("error", "系统错误：" + exception.getLocalizedMessage());
			request.setAttribute("exceptionMessage", "系统错误：" + exception.getLocalizedMessage());
			logger.error(exception.getLocalizedMessage(), exception);
		}
		ServletServerHttpRequest inputMessage = new ServletServerHttpRequest(request);
		List<MediaType> acceptedMediaTypes = inputMessage.getHeaders().getAccept();
		if (acceptedMediaTypes.isEmpty()) {
			acceptedMediaTypes = Collections.singletonList(MediaType.ALL);
		}
		MediaType.sortByQualityValue(acceptedMediaTypes);
		ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
		Class<?> mapType = map.getClass();
		List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
		if (messageConverters != null) {
			for (MediaType acceptedMediaType : acceptedMediaTypes) {
				for (HttpMessageConverter messageConverter : messageConverters) {
					if (messageConverter.canWrite(mapType, acceptedMediaType)) {
						try {
							messageConverter.write(map, acceptedMediaType, outputMessage);
						} catch (HttpMessageNotWritableException e) {
							outputMessage.close();
							return null;
						} catch (IOException e) {
							outputMessage.close();
							return null;
						}
						outputMessage.close();
						return new ModelAndView();
					}
				}
			}
		}
		outputMessage.close();
		return null;
	}

}
