package com.demo.springrest.controller.core.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomerJsonSerializer {

	ObjectMapper mapper = new ObjectMapper();
	JacksonJsonFilter jacksonFilter = new JacksonJsonFilter();

	/**
	 * @param clazz
	 *            target type
	 * @param include
	 *            include fields
	 * @param filter
	 *            filter fields
	 */
	public void filter(Class<?> clazz, String include, String filter) {

		if (clazz == null)
			return;

		if (StringUtils.isNotBlank(include)) {
			jacksonFilter.include(clazz, include.split(","));
		}
		if (StringUtils.isNotBlank(filter)) {
			jacksonFilter.filter(clazz, filter.split(","));
		}
		mapper.addMixIn(clazz, jacksonFilter.getClass());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object filterMap(Object mapObj, String type, String include, String filter) {
		Object returnObj = null;
		if (mapObj != null) {
			if (type != null && !"".equals(type)) {

				if ("MAP".equals(type)) {
					Map map = (Map) mapObj;
					Map returnMap = setMap(map, include, filter);
					returnObj = returnMap;
				} else if ("MAPLIST".equals(type)) {
					List<Map> mapList = (List<Map>) mapObj;
					List<Map> returnmapList = new ArrayList<Map>();
					if (mapList != null && mapList.size() > 0) {
						for (int i = 0; i < mapList.size(); i++) {
							Map map = mapList.get(i);
							Map returnMap = setMap(map, include, filter);
							returnmapList.add(returnMap);
						}
					}

					returnObj = returnmapList;

				}

			}

		}
		return returnObj;
	}

	@SuppressWarnings("rawtypes")
	private Map setMap(Map map, String include, String filter) {
		if (StringUtils.isNotBlank(include)) {
			include=","+include+",";
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (!include.contains(","+key+",")) {
					//map.remove(key);
					iterator.remove();
				}
			}
		}
		if (StringUtils.isNotBlank(filter)) {
			filter=","+filter+",";
			Iterator iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				if (filter.contains(","+key+",")) {
					//map.remove(key);
					iterator.remove();
				}
			}
		}

		return map;
	}

	public String toJson(Object object) throws JsonProcessingException {
		mapper.setFilterProvider(jacksonFilter);
		return mapper.writeValueAsString(object);
	}

	public void filter(JSONField json) {
		this.filter(json.type(), json.include(), json.filter());
	}

	public Object filterMap(Object map, JSONFieldMap jsonmap) {
		return this.filterMap(map, jsonmap.type(), jsonmap.include(), jsonmap.filter());
	}

}
