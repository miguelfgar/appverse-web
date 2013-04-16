/*
 Copyright (c) 2012 GFT Appverse, S.L., Sociedad Unipersonal.

 This Source Code Form is subject to the terms of the Appverse Public License 
 Version 2.0 (“APL v2.0”). If a copy of the APL was not distributed with this 
 file, You can obtain one at http://www.appverse.mobi/licenses/apl_v2.0.pdf. [^]

 Redistribution and use in source and binary forms, with or without modification, 
 are permitted provided that the conditions of the AppVerse Public License v2.0 
 are met.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. EXCEPT IN CASE OF WILLFUL MISCONDUCT OR GROSS NEGLIGENCE, IN NO EVENT
 SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) 
 ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 POSSIBILITY OF SUCH DAMAGE.
 */
package org.appverse.web.framework.backend.frontfacade.json.controllers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/*/services")
public class JSONController {
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	CustomMappingJacksonHttpMessageConverter customMappingJacksonHttpMessageConverter;

	private void addDefaultResponseHeaders(HttpServletResponse response) {
		// Add headers to prevent Cross-site ajax calls issues
		response.addHeader("Content-Type", "application/json; charset=UTF-8");
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers",
				"Content-Type,X-Requested-With");
	}

	@PostConstruct
	public void bindMessageConverters() {
		ObjectMapper mapper = new ObjectMapper();
//		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS"));
//		SerializationConfig sc = mapper.getSerializationConfig();
		customMappingJacksonHttpMessageConverter
				.setObjectMapper(mapper);
	}

	private String createXSRFToken(final HttpServletRequest request)
			throws IOException {
		HttpSession session = request.getSession();
		String xsrfSessionToken = (String) session
				.getAttribute("X-XSRF-Cookie");
		if (xsrfSessionToken == null) {
			Random r = new Random(System.currentTimeMillis());
			long value = System.currentTimeMillis() + r.nextLong();
			char ids[] = session.getId().toCharArray();
			for (int i = 0; i < ids.length; i++) {
				value += ids[i] * (i + 1);
			}
			xsrfSessionToken = Long.toString(value);
			session.setAttribute("X-XSRF-Cookie", xsrfSessionToken);
		}
		return xsrfSessionToken;
	}

	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private void checkXSRFToken(final HttpServletRequest request)
			throws Exception {
		/**
		 * Currently this method is not used. Needs to be analyzed how this can be implemented in a "generic" way.
		 */
		String requestValue = request.getHeader("X-XSRF-Cookie");
		String sessionValue = (String) request.getSession().getAttribute(
				"X-XSRF-Cookie");
		if (sessionValue != null && !sessionValue.equals(requestValue)) {
			//throw new PreAuthenticatedCredentialsNotFoundException(
			//		"XSRF attribute not found in session.");
			throw new Exception(
					"XSRF attribute not found in session.");
		}
	}

	@RequestMapping(value = "/*.json")
	public String handleRequest(final HttpServletRequest request,
			final HttpServletResponse response, @RequestBody String payload)
			throws Exception {
		String path = request.getServletPath();
		String serviceMehtodName = path.substring(path.lastIndexOf('/') + 1,
				path.lastIndexOf('.'));
		String serviceName = serviceMehtodName.substring(0,
				serviceMehtodName.indexOf('-'));
		if (serviceName == null || serviceMehtodName.isEmpty()) {
			throw new IllegalArgumentException(
					"ServiceFacade requested is empty");
		}
		String methodName = serviceMehtodName.substring(serviceMehtodName
				.indexOf('-') + 1);
		if (methodName == null || methodName.isEmpty()) {
			throw new IllegalArgumentException(
					"Mehtod requested is empty for serviceFacade" + serviceName);
		}
		Object presentationService = applicationContext.getBean(serviceName);
		if (presentationService == null) {
			throw new IllegalArgumentException(
					"Requested ServiceFacade don't exists " + serviceName);
		}
		//if (!(presentationService instanceof AuthenticationServiceFacade)) {
			// checkXSRFToken(request);
			Method[] methods = presentationService.getClass().getMethods();
			Method method = null;
			for (Method methodItem : methods) {
				if (methodItem.getName().equals(methodName)) {
					method = methodItem;
					break;
				}
			}
			if (method == null) {
				throw new IllegalArgumentException(
						"Requested Method don't exists " + methodName
								+ " for serviceFacade " + serviceName);
			}
			Class<?>[] parameterTypes = method.getParameterTypes();
			Class<?> parameterType = null;
			if (parameterTypes.length > 1) {
				throw new IllegalArgumentException("Requested Method"
						+ methodName + " for serviceFacade " + serviceName
						+ " only accepts 0 or 1 parameter");
			}
			Object parameter = null;
			if (parameterTypes.length > 0) {
				parameterType = parameterTypes[0];
				parameter = customMappingJacksonHttpMessageConverter
						.readInternal(parameterType, payload);
			}
			try {
				Object result = method.invoke(presentationService, parameter);
				ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(
						response);
				customMappingJacksonHttpMessageConverter.write(result,
						MediaType.APPLICATION_JSON, outputMessage);
				addDefaultResponseHeaders(response);
			}catch(Throwable th) {
				//capture any service exception while invoking the business method.
//				ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(
//						response);
//				customMappingJacksonHttpMessageConverter.write(th,
//						MediaType.APPLICATION_JSON, outputMessage);
//				addDefaultResponseHeaders(response);
////				response.setStatus(403);
				response.sendError(500, th.getMessage());
//				response.flushBuffer();
//				response.sendError(401);
//				throw th;
			}
			return "";
//		} else if (presentationService instanceof AuthenticationServiceFacade
//				&& methodName.equals(AuthenticationServiceFacade.class
//						.getMethod("getXSRFSessionToken"))) {
//			createXSRFToken(request);
//			return "";
//		}
//		return null;
	}

}