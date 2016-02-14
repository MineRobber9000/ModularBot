package me.minerobber9000.modularbot.modules;

import java.util.*;

public class Spinner{
	
	Random rng = null;
	String[] results = new String[] {"10 Rings", "20 Rings"};

	public Spinner() {
		rng = new Random();
	}
	
	public Spinner(int seed) {
		rng = new Random((long) seed);
	}
	
	public void commandHandler() {
		int number = rng.nextInt(results.length);
	}

}
