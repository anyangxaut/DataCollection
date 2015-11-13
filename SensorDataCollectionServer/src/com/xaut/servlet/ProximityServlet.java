package com.xaut.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xaut.dao.ProximityDao;
import com.xaut.daoimpl.ProximityDaoImpl;

@SuppressWarnings("serial")
public class ProximityServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		ProximityDao dao = new ProximityDaoImpl();
		
		String szImei = request.getParameter("szImei");
		double x = Double.parseDouble(request.getParameter("X"));
			
		boolean result = dao.Sample(szImei, x);

			
		out.print(result);
			
		
		out.flush();//清理servlet容器的缓冲区
		out.close();//关闭输出流对象，释放输出流资源
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		doGet(request,response);
		
	}
}
