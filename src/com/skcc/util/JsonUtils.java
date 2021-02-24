package com.skcc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skcc.option.Stock;


public class JsonUtils {
	
	private final static ObjectMapper mapper = new ObjectMapper();
	
	private static JsonNode readValue(String str) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(str, JsonNode.class);
	}
	
	public static JsonNode getResponse(String stockNumber) throws IOException {
		HttpsURLConnection conn = ConnectionUtils.openConnection(stockNumber);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
        String inputLine ;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        conn.disconnect();
        
		return readValue(response.toString()).get("result").get("d").get(0);
	}

	public static JsonNode getStockInfo(String jsonData) throws JsonParseException, JsonMappingException, IOException {
		JsonNode json = readValue(jsonData);
		JsonNode items = json.get("items");
		for(JsonNode item : items) {
		    JsonNode stockInfo = getResponse(item.get("item").toString().replaceAll("\"", ""));
		    ((ObjectNode)item).remove("item");
		    for(String key : Stock.codeMap.keySet()) {
		    	((ObjectNode)item).put(Stock.codeMap.get(key), stockInfo.get(key).toString().replaceAll("\"", ""));
		    }
		}
		return json;
	}
}
