package com.xaut.daoimpl;

import com.xaut.dao.LinearAccelerationDao;
import com.xaut.util.DBOperation;

public class LinearAccelerationDaoImpl implements LinearAccelerationDao {

	@Override
	public boolean Sample(String szImei, double x, double y, double z) {
		// TODO Auto-generated method stub
		
		String[] sql = new String[1];

		sql[0] = "insert into linearacceleration (DeviceId, X, Y, Z) values ('"
				+ szImei + "'," + x + ", " + y + ", " + z + ");";

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

		LinearAccelerationDao test = new LinearAccelerationDaoImpl();
		boolean result = test.Sample("3100931008", 1, 1, 1);
		System.out.println(result);
	}

}
