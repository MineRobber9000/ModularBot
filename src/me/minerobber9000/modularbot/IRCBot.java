package me.minerobber9000.modularbot;

import org.jibble.pircbot.PircBot;
import me.minerobber9000.modularbot.modules.*;

public class IRCBot extends PircBot {
	
	UserDataStore users = new UserDataStore();
	ModuleManager mm = new ModuleManager();
	String prefix = "!mb";
	
	public IRCBot(String name, String OP) {
		this.setName(name);
		users.put(OP, PermLevel.OWNER);
	}
	
	public IRCBot(String name, String OP, String pre) {
		this.setName(name);
		users.put(OP, PermLevel.OWNER);
		this.prefix = pre;
	}
	
	private void parseCmd(String channel, String sender, String message) {
		message = message.toLowerCase();
		if (message.startsWith(prefix)) {
			String[] parts = message.split(" ");
			if (parts[1].equals("balance")) {
				int balance = users.get(sender).balance;
				sendMessage(channel, sender + " has " + balance + " rings." + ((balance >= 200) ? " They can buy a Red Ring." : ""));
			}
			if (parts[1].equals("redrings")) {
				int redrings = users.get(sender).redrings;
				sendMessage(channel, sender + " has " + redrings + " red rings.");
			}
			if (parts[1].equals("buy")) {
				if (parts.length == 3 && !parts[2].equals("")) {
					int bought = Integer.valueOf(parts[2]);
					int price = bought * 200;
					int bal = users.get(sender).balance;
					if (bal >= price) {
						users.get(sender).balance = bal - price;
						users.get(sender).redrings += bought;
						sendMessage(channel, "Bought " + Integer.toString(bought) + " red rings for " + Integer.toString(price) + "rings.");
					} else {
						sendMessage(channel, "You don't have enough rings!");
					}
				} else {
					help(channel, "buy");
				}
			}
		}
	}
	
	private void help(String channel) {
		sendMessage(channel, "Commands: balance, redrings, buy");
		sendMessage(channel, "To get help for a specific command, type \"" + prefix + " help <command>");
	}
	
	private void help (String channel, String cmd) {
		//stuff
		help(channel);
	}
}
