package com.ca.golfstore.api;

import java.util.LinkedHashMap;

public class ValidationAPI {
	
	/**
	 * This method compares the compares two maps(expected result and actual result)
	 * 
	 * @param expectedMap - Map which contains the expected data in form of KEY/VALUE pair
	 * 	      actualMap - Map which contains the actual data in form of KEY/VALUE pair
	 * @return Returns the comparison result in a boolean form
	 */
	public boolean mapsAreEqual(LinkedHashMap<String, String> expectedMap,
			LinkedHashMap<String, String> actualMap) {
		try {
			for (String k : actualMap.keySet()) {
				if ((expectedMap.get(k) != null || expectedMap.get(k) == null )
						&& !expectedMap.get(k).equals(actualMap.get(k))) {
					return false;
				}
			}
			for (String y : expectedMap.keySet()) {
				if ((actualMap.get(y) != null) && (!actualMap.containsKey(y))) {
					return false;
				}
			}
		} catch (NullPointerException np) {
			return false;
		}
		return true;
	}
}
