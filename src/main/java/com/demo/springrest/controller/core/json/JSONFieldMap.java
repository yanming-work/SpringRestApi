package com.demo.springrest.controller.core.json;


	import java.lang.annotation.ElementType;
	import java.lang.annotation.Repeatable;
	import java.lang.annotation.Retention;
	import java.lang.annotation.RetentionPolicy;
	import java.lang.annotation.Target;

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(JSONFieldMaps.class)   // 让方法支持多重@JSONFieldMap 注解
	public @interface JSONFieldMap {
		String  type() default "";
	    String include() default "";
	    String filter() default "";
	}