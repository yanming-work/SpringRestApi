package com.demo.springrest.controller.core.json;


	import java.lang.annotation.ElementType;
	import java.lang.annotation.Repeatable;
	import java.lang.annotation.Retention;
	import java.lang.annotation.RetentionPolicy;
	import java.lang.annotation.Target;

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(JSONFields.class)   // 让方法支持多重@JSONField 注解
	public @interface JSONField {
	    Class<?> type();
	    String include() default "";
	    String filter() default "";
	}