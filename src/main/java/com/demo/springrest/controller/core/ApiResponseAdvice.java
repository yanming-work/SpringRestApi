package com.demo.springrest.controller.core;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@SuppressWarnings("rawtypes")
@ControllerAdvice
public class ApiResponseAdvice extends ApiResponseAdviceException implements ResponseBodyAdvice {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

		if (selectedContentType.includes(MediaType.APPLICATION_JSON)
				|| selectedContentType.includes(MediaType.APPLICATION_JSON_UTF8)) {
			
			if (body != null) {
				if (body instanceof ApiReturnObj) {
					return body;
				}else if (body instanceof ExceptionCodeMsg) {
					// 异常
					ApiReturnObj apiReturnObj= new ApiReturnObj();
					ExceptionCodeMsg exceptionCodeMsg = (ExceptionCodeMsg) body;
					apiReturnObj.setCode(exceptionCodeMsg.getCode());
					apiReturnObj.setMessage(exceptionCodeMsg.getMsg());
					apiReturnObj.setData(exceptionCodeMsg.getException());
					return apiReturnObj;
				} else if (body instanceof ErrorCodeMsg) {
					// 错误
					ApiReturnObj apiReturnObj= new ApiReturnObj();
					ErrorCodeMsg errorCodeMsg = (ErrorCodeMsg) body;
					apiReturnObj.setCode(errorCodeMsg.getCode());
					apiReturnObj.setMessage(errorCodeMsg.getMsg());
					return apiReturnObj;
				} else {
					// 正常
//					Map<String, Object> map = new HashMap<>();
//					map.put("code", 1);
//					map.put("message", "成功");
//					map.put("data", body);
//					return map;
					ApiReturnObj apiReturnObj= new ApiReturnObj();
					apiReturnObj.setCode(1);
					apiReturnObj.setMessage("成功");
					apiReturnObj.setData(body);
					return apiReturnObj;
				}
			} else {
				ApiReturnObj apiReturnObj= new ApiReturnObj();
				apiReturnObj.setCode(0);
				apiReturnObj.setMessage( "没有获取到相关数据");
				return apiReturnObj;
			}

			/**
			 * //有些错误已经格式化为指定的返回类型 SDKConstant.NOT_TRANSFORM_BODY if
			 * (!response.getHeaders().containsKey("{}")) {
			 * 
			 * Map<String, Object> map = new HashMap<>(); map.put("code", 200);
			 * map.put("message", "失败"); map.put("data", body); return map; }
			 * else { Map<String, Object> map = new HashMap<>(); map.put("code",
			 * 1); map.put("message", "成功"); map.put("data", body); return map;
			 * }
			 **/
		}
		return body;
	}

}