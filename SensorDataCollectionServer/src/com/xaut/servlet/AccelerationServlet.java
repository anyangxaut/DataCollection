package com.xaut.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xaut.dao.AccelerationDao;
import com.xaut.daoimpl.AccelerationDaoImpl;

@SuppressWarnings("serial")
public class AccelerationServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		AccelerationDao dao = new AccelerationDaoImpl();
		
		String szImei = request.getParameter("szImei");
		
		List<Double> accx = new ArrayList<Double>();
		String temp1 = request.getParameter("X").toString().replace("[", "");
		String temp2 = temp1.replace("]", "");
		String[] temp3 = temp2.split(",");
		for(String temp : temp3){
	    	   
	    	  accx.add(Double.parseDouble(temp.trim()));
	       }
		
		 List<Double> accy = new ArrayList<Double>();
		 temp1 = request.getParameter("Y").toString().replace("[", "");
		 temp2 = temp1.replace("]", "");
		 temp3 = temp2.split(",");
		for(String temp : temp3){
	    	   
	    	  accy.add(Double.parseDouble(temp.trim()));
	       }
		
		 List<Double> accz = new ArrayList<Double>();
		 temp1 = request.getParameter("Z").toString().replace("[", "");
		 temp2 = temp1.replace("]", "");
		 temp3 = temp2.split(",");
		for(String temp : temp3){
	    	   
	    	  accz.add(Double.parseDouble(temp.trim()));
	       }
//		double x = Double.parseDouble(request.getParameter("X"));
//		double y = Double.parseDouble(request.getParameter("Y"));
//		double z = Double.parseDouble(request.getParameter("Z"));
		
		List<String> acctime = new ArrayList<String>();
		 temp1 = request.getParameter("time").toString().replace("[", "");
		 temp2 = temp1.replace("]", "");
		 temp3 = temp2.split(",");
		for(String temp : temp3){
	    	   
	    	  acctime.add(temp.trim());
	       }
		
		String action = request.getParameter("action");
		String person = request.getParameter("person");
		
			
		boolean result = dao.Sample(szImei, action, person, accx, accy, accz, acctime);

			
		out.print(result);
			
		
		out.flush();//清理servlet容器的缓冲区
		out.close();//关闭输出流对象，释放输出流资源
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request,response);
		
	}
}
