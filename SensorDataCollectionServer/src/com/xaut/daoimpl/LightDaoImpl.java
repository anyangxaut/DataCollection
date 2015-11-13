package com.xaut.daoimpl;

import com.xaut.dao.LightDao;
import com.xaut.util.DBOperation;

public class LightDaoImpl implements LightDao {

	@Override
	public boolean Sample(String szImei, double x) {
		// TODO Auto-generated method stub

		String[] sql = new String[1];

		sql[0] = "insert into light (DeviceId, X) values ('"
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

		LightDao test = new LightDaoImpl();
		boolean result = test.Sample("3100931008", 1);
		System.out.println(result);
	}

}
