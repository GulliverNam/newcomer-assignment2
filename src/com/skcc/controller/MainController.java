package com.skcc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.fasterxml.jackson.databind.JsonNode;
import com.skcc.util.ConnectionUtils;
import com.skcc.util.JsonUtils;
import com.skcc.util.ServletUtils;
import com.skcc.util.XmlUtils;


@WebServlet("/main")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MainController() {
        super();
    }

	public void init(ServletConfig config) throws ServletException {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String contextPath = request.getContextPath();
		response.sendRedirect(contextPath+"/index.html");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("POST".equalsIgnoreCase(request.getMethod())){
			
			try {
				ConnectionUtils.ignoreSSL();
			} catch (KeyManagementException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			ServletUtils.setReqRes(request, response);
			
			PrintWriter out = response.getWriter();
			String contentType = ServletUtils.getContentType(request);
			String data = ServletUtils.getData(request);
			
			if("json".equals(contentType)) {
				JsonNode json = JsonUtils.getStockInfo(data);
				out.print(json.toPrettyString());
			} else if("xml".equals(contentType)) {
				Document xml = null;
				try {
					xml = XmlUtils.getStockInfo(data);
					out.print(new XMLOutputter(Format.getPrettyFormat()).outputString(xml));
				} catch (JDOMException | IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("Wrong Method");
		}
	}

}
