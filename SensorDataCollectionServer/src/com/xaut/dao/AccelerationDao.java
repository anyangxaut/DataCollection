package com.xaut.dao;

import java.util.List;


public interface AccelerationDao {

	public boolean Sample(String szImei, String action, String person, 
			List<Double> x, List<Double> y, List<Double> z, List<String> time);
	
}
