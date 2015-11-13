package com.xaut.daoimpl;

import com.xaut.dao.RotationDao;
import com.xaut.util.DBOperation;

public class RotationDaoImpl implements RotationDao {

	@Override
	public boolean Sample(String szImei, double x, double y, double z) {
		// TODO Auto-generated method stub
		
		String[] sql = new String[1];

		sql[0] = "insert into rotation (DeviceId, X, Y, Z) values ('"
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

		RotationDao test = new RotationDaoImpl();
		boolean result = test.Sample("3100931008", 1, 1, 1);
		System.out.println(result);
	}

}
