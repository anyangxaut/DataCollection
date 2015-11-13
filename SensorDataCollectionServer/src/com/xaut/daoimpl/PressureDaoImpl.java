package com.xaut.daoimpl;

import com.xaut.dao.PressureDao;
import com.xaut.util.DBOperation;

public class PressureDaoImpl implements PressureDao {

	@Override
	public boolean Sample(String szImei, double x) {
		// TODO Auto-generated method stub
		String[] sql = new String[1];

		sql[0] = "insert into pressure (DeviceId, X) values ('"
				+ szImei + "'," + x + ");";

		DBOperation dboperation = new DBOperation();

		boolean rs = dboperation.excutesql(sql);

		dboperation.closeConn();

		return rs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PressureDao test = new PressureDaoImpl();
		boolean result = test.Sample("3100931008", 1);
		System.out.println(result);
	}

}
