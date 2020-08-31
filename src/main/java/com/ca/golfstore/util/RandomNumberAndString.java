package com.ca.golfstore.util;

import java.util.Random;

public class RandomNumberAndString {
	
	/**
	 * This function generates random integer number with given range between min and max.
	 * Example generateRandInt(10,20) can return like "14", "11" and "17".
	 * 
	 * @param min integer number
	 * @param max integer number
	 * @return returns a random number between min and max values provided by the caller
	 * 
	 * 
	 * @throws Exception if min<max
	 */
	
	public  Long generateRandInt(int min,int max) {
	
		if(max<min)
			throw new IllegalArgumentException("max should be greater than min but function is called using min="+min+" max="+max);
		Random randI = new Random();   
		long randomNumII = randI.nextInt(max-min)+min;
	    return randomNumII;
	}

}
