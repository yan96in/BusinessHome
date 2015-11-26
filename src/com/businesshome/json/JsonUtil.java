package com.businesshome.json;

import java.lang.reflect.Type;
import java.util.List;

import com.businesshome.model.News;
import com.businesshome.model.Policy;
import com.businesshome.model.Race;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


	public class JsonUtil {
		public List<News> stringFromJsonToNews(String result) {
			Type listType = new TypeToken<List<News>>() {
			}.getType();
			Gson gson = new Gson();
			List<News> list = gson.fromJson(result, listType);
			return list;
		}
		public List<Race> stringFromJsonToRaces(String result) {
			Type listType = new TypeToken<List<Race>>() {
			}.getType();
			Gson gson = new Gson();
			List<Race> list = gson.fromJson(result, listType);
			return list;
		}
		public List<Policy> stringFromJsonToPolicys(String result) {
			Type listType = new TypeToken<List<Policy>>() {
			}.getType();
			Gson gson = new Gson();
			List<Policy> list = gson.fromJson(result, listType);
			return list;
		}
		
	}
