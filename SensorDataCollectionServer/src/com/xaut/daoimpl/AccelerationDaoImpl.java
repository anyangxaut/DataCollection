package com.xaut.daoimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xaut.dao.AccelerationDao;
import com.xaut.util.DBOperation;

public class AccelerationDaoImpl implements AccelerationDao {

	@Override
	public boolean Sample(String szImei, String action, String person, 
			List<Double> x, List<Double> y, List<Double> z, List<String> time) {
		// TODO Auto-generated method stub
		
		String[] sql = new String[x.size()];
		
		for(int i = 0; i < x.size(); i++){
			
			sql[i] = "insert into acceleration (DeviceId, Action, Person, X, Y, Z, Time" +
					") values ('" + szImei + "', '" + action + "', '" + person + "'," +
					x.get(i) + ", " + y.get(i) + ", " + z.get(i) + ",'" + time.get(i) + "');";
		}
		
		DBOperation dboperation = new DBOperation ();
		
		boolean rs = dboperation.excutesql(sql);
		
		dboperation.closeConn();
		
		return rs;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Double> value= new ArrayList<Double>();
		value.add(1.2);
		AccelerationDao test = new AccelerationDaoImpl();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		List<String> time = new ArrayList<String>();
		time.add(dateFormat.format(new Date()));
		boolean result = test.Sample("3100931008","静止", "安洋", value, value, value, time);
		System.out.println(result);
	}

}
