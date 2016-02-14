package me.minerobber9000.modularbot.modules;

import java.util.*;

import me.minerobber9000.modularbot.*;

public class Spinner{
	
	Random rng = null;
	String[] results = new String[] {"10 Rings", "20 Rings", "1 Red Ring", "5 Red Rings", "10 Spikes", "20 Spikes", "1 Giant Spike", "5 Giant Spikes"};

	public Spinner() {
		rng = new Random();
	}
	
	public Spinner(int seed) {
		rng = new Random((long) seed);
	}
	
	public void commandHandler(String channel, CUser sender, IRCBot bot) {
		int number = rng.nextInt(results.length);
		boolean lucky = false;
		String res = results[number];
		if (res.equals("10 Rings")) {
			sender.balance += 10;
		}
		if (res.equals("20 Rings")) {
			sender.balance += 20;
		}
		if (res.equals("5 Red Rings")) {
			sender.redrings += 5;
			lucky = true;
		}
		if (res.equals("10 Spikes")) {
			sender.balance -= 10;
		}
		if (res.equals("20 Spikes")) {
			sender.balance -= 20;
		}
		if (res.equals("1 Giant Spike")) {
			if (sender.redrings >= 1) {
				sender.redrings -= 1;
			}
		}
		if (res.equals("5 Giant Spikes")) {
			if (sender.redrings >= 5) {
				sender.redrings -= 5;
			}
		}
		bot.sendMessage(channel, "Result: " + res + "!" + (lucky ? " Lucky!" : ""));
	}

}
