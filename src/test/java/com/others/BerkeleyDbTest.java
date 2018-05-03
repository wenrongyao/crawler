package com.others;

import java.util.Map;
import java.util.Map.Entry;

import com.common.constant.R;
import com.weiboall.service.BerkeleyDBService;

public class BerkeleyDbTest {
	public static void main(String[] args) {
		BerkeleyDBService urls = new BerkeleyDBService(R.URLS);
		Map<String, String> urlMap = urls.getAll();
		for(Entry<String, String> entry : urlMap.entrySet()) {
			System.out.println(entry.getValue());
//			urls.remove(entry.getKey());
//			visitedUrls.put(entry.getKey(), entry.getValue(), false);
		}
	}
}
