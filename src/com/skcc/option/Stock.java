package com.skcc.option;

import java.util.HashMap;

public class Stock {
	
	public final static HashMap<String, String> codeMap = new HashMap<>();
	
	static {
		codeMap.put("cd", "종목코드");
		codeMap.put("nm", "종목명");
		codeMap.put("nv", "현재가");
		codeMap.put("cv", "전일비");
		codeMap.put("cr", "등락률");
		codeMap.put("rf", "rf");
		codeMap.put("mks", "시가총액_억");
		codeMap.put("aa", "거래대금_백만");
	}
}