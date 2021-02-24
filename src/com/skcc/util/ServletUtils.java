package com.skcc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {

	public static String getContentType(HttpServletRequest request) {
		String header = request.getHeader("content-type");
		if(header == null) return null;
		String contentType = header.split("/")[1];
		if(contentType == null) return null;
		return contentType;
	}

	public static String getData(HttpServletRequest request) throws IOException {
		BufferedReader reader = request.getReader();
		if(reader == null) return null;
		Stream<String> lines = reader.lines();
		if(lines == null) return null;
		String data = lines.collect(Collectors.joining(System.lineSeparator()));
		if(data == null) return null;
		return data;
	}

	public static void setReqRes(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/x-json;charset=UTF-8");
	}

}
