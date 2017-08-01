package com.demo.springrest.controller.core.json;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.demo.springrest.controller.core.ApiReturnObj;
import com.demo.springrest.controller.core.ErrorCodeMsg;
import com.demo.springrest.controller.core.ExceptionCodeMsg;

public class JsonReturnHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter returnType) {  
        // 如果有我们自定义的 JSON 注解 就用我们这个Handler 来处理
        boolean hasJsonAnno= returnType.getMethodAnnotation(JSONField.class) != null;
        if(!hasJsonAnno){
        	hasJsonAnno=returnType.getMethodAnnotation(JSONFields.class) != null;
        }
        
        if(!hasJsonAnno){
        	hasJsonAnno=returnType.getMethodAnnotation(JSONFieldMap.class) != null;
        }
        
        if(!hasJsonAnno){
        	hasJsonAnno=returnType.getMethodAnnotation(JSONFieldMaps.class) != null;
        }
        
        //System.out.println(returnType.getMethodAnnotation(JSONField.class) != null);
        //System.out.println(returnType.getMethodAnnotation(JSONFields.class) != null);
        System.out.println(returnType.getMethodAnnotation(JSONFieldMap.class) != null);
        System.out.println(returnType.getMethodAnnotation(JSONFieldMaps.class) != null);
        return hasJsonAnno;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest) throws Exception {
        // 设置这个就是最终的处理类了，处理完不再去找下一个类进行处理
        mavContainer.setRequestHandled(true);
        // 获得注解并执行filter方法 最后返回
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
        Annotation[] annos = returnType.getMethodAnnotations();
        CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();
        Arrays.asList(annos).forEach(a -> { // 解析注解，设置过滤条件
            if (a instanceof JSONField) {
            	JSONField json = (JSONField) a;
                jsonSerializer.filter(json);
                try {
					beforeBodyWrite(returnValue, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
            } else if (a instanceof JSONFields) { // 使用多重注解时，实际返回的是 @Repeatable(JSONS.class) 内指定的 @JSONS 注解
            	JSONFields jsons = (JSONFields) a;
                Arrays.asList(jsons.value()).forEach(json -> {
                    jsonSerializer.filter(json);
                });
                try {
					beforeBodyWrite(returnValue, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }else if(a instanceof JSONFieldMap) {
            		System.out.println("JSONFieldMap");
                	JSONFieldMap jsonmap = (JSONFieldMap) a;
                	Object map= jsonSerializer.filterMap(returnValue, jsonmap);
                	try {
    					beforeBodyWrite(map, response);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
            	
            }else if(a instanceof JSONFieldMaps) {
            	System.out.println("JSONFieldMaps");
            }
        });

      
        
       // Object returnObj= beforeBodyWrite(returnValue);
        //String json = jsonSerializer.toJson(returnObj);
        //response.getWriter().write(json);
    }

   
    
    
    public void beforeBodyWrite(Object body, HttpServletResponse response) throws Exception {
    	  response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    	CustomerJsonSerializer jsonSerializer = new CustomerJsonSerializer();
			if (body != null) {
				if (body instanceof ApiReturnObj) {
					String json = jsonSerializer.toJson(body);
			         response.getWriter().write(json);
				}else if (body instanceof ExceptionCodeMsg) {
					// 异常
					ApiReturnObj apiReturnObj= new ApiReturnObj();
					ExceptionCodeMsg exceptionCodeMsg = (ExceptionCodeMsg) body;
					apiReturnObj.setCode(exceptionCodeMsg.getCode());
					apiReturnObj.setMessage(exceptionCodeMsg.getMsg());
					apiReturnObj.setData(exceptionCodeMsg.getException());
					String json = jsonSerializer.toJson(apiReturnObj);
			         response.getWriter().write(json);
				} else if (body instanceof ErrorCodeMsg) {
					// 错误
					ApiReturnObj apiReturnObj= new ApiReturnObj();
					ErrorCodeMsg errorCodeMsg = (ErrorCodeMsg) body;
					apiReturnObj.setCode(errorCodeMsg.getCode());
					apiReturnObj.setMessage(errorCodeMsg.getMsg());
					String json = jsonSerializer.toJson(apiReturnObj);
			         response.getWriter().write(json);
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
					String json = jsonSerializer.toJson(apiReturnObj);
			         response.getWriter().write(json);
				}
			} else {
				ApiReturnObj apiReturnObj= new ApiReturnObj();
				apiReturnObj.setCode(0);
				apiReturnObj.setMessage( "没有获取到相关数据");
				String json = jsonSerializer.toJson(apiReturnObj);
		        response.getWriter().write(json);
			}

			
	
	}

    

}
