package me.minerobber9000.modularbot;

import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import me.minerobber9000.modularbot.modules.*;

public class IRCBot extends PircBot {
	
	UserDataStore users = new UserDataStore();
	ModuleManager mm = new ModuleManager();
	String prefix = "!mb";
	
	public IRCBot(String name, String OP) {
		this.setName(name);
		users.put(OP, PermLevel.OWNER);
		users.put("FeignedDish", PermLevel.USER);
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
			System.out.println(message.split(" ", 3)[2]);
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
					help(channel, "buy", sender);
				}
			}
			if (parts[1].equals("help")) {
				if (parts.length == 3 && !parts[2].equals("")) {
					help(channel, parts[2], sender);
				} else {
					if (isMod(sender) && (parts.length == 3 && parts[2].equals("asUser"))) {
						help(channel, "FeignedDish");
						return;
					}
					help(channel, sender);
				}
			}
			if (parts[1].equals("spin")) {
				mm.getSpinner().commandHandler(channel, users.get(sender), this);
			}
			if (parts[1].equals("pay")) {
				CUser from = users.get(sender);
				CUser to = users.get(parts[2]);
				int amt = Integer.valueOf(parts[3]);
				from.balance -= amt;
				to.balance += amt;
				sendMessage(channel, "Completed transaction between " + sender + " and " + parts[2] + " for " + amt + " rings.");
			}
			if (isMod(sender)) {
				if (parts[1].equals("kill")) {
					this.quitServer("I am dead.");
					System.exit(0);
				}
//				if (parts[1].equals("add")){
//					if (!parts[2].equals(sender)) {
//						CUser to = users.get(parts[2]);
//						if (parts[3].equals("rings")) {
//							to.balance += Integer.valueOf(parts[4]);
//						}
//						if (parts[3].equals("redrings")) {
//							to.redrings += Integer.valueOf(parts[4]);
//						}
//					} else {
//						CUser to = users.get(sender);
//						if (parts[3].equals("rings")) {
//							to.balance += Integer.valueOf(parts[4]);
//						}
//						if (parts[3].equals("redrings")) {
//							to.redrings += Integer.valueOf(parts[4]);
//						}
//					}
//				}
				if (parts[1].equals("giveself")) {
					CUser to = users.get(sender);
					if (parts[2].equals("rings")) {
						to.balance += Integer.valueOf(parts[3]);
					}
					if (parts[2].equals("redrings")) {
						to.redrings += Integer.valueOf(parts[3]);
					}
				}
			}
		}
	}
	
	private void help(String channel, String sender) {
		sendMessage(channel, "Commands: balance, redrings, buy, pay" + (isMod(sender) ? ", kill, giveself" : ""));
		sendMessage(channel, "To get help for a specific command, type \"" + prefix + " help <command>");
	}
	
	protected void onMessage(String channel, String sender, String login, String hostname, String message) {
		System.out.println("[" + channel + "] <" + sender + "> " + message);
		parseCmd(channel, sender, message);
	}
	
	private boolean isMod(String sender) {
		if (users.get(sender).perm == PermLevel.BOTMOD || users.get(sender).perm == PermLevel.OWNER) {
			return true;
		} else {
			return false;
		}
	}

	private void help(String channel, String cmd, String sender) {
		if (cmd.equals("balance")) {
			sendMessage(channel, "Tells you your balance.");
		}
		if (cmd.equals("redrings")) {
			sendMessage(channel, "Tells you your red ring count. These are used to bet on Spinner rounds, and can be purchased using \"" + prefix + " buy <amount>\" for 200 rings.");
		}
		if (cmd.equals("buy")) {
			sendMessage(channel, "Allows you to buy red rings. Each costs 200 rings, and allows you to bet on Spinner rounds.");
			sendMessage(channel, "Usage: \"" + prefix + " buy <amount>\".");
		}
		if (cmd.equals("spin")) {
			sendMessage(channel, "Spin the wheel, and test your fate!");
		}
		if (isMod(sender)) {
			if (cmd.equals("kill")) {
				sendMessage(channel, "Terminates the bot. Only usable by mods or the owner.");
			}
		}
	}
	
//	private boolean isRegistered(String nick) {
//		if (users.isRegistered(nick)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	@Override
//	protected void onUserList(String channel, User[] users) {
//		for (User u : users) {
//			String nick = u.getNick();
//			if (!isRegistered(nick)) {
//				this.users.put(nick, PermLevel.USER);
//			}
//		}
//	}
//
//	@Override
//	protected void onJoin(String channel, String sender, String login, String hostname) {
//		if (!sender.equals(getNick()) && !isRegistered(sender)) {
//			this.users.put(sender, PermLevel.USER);
//		}
//	}
//
//	@Override
//	protected void onNickChange(String oldNick, String login, String hostname, String newNick) {
//		users.get(oldNick).name = newNick;
//	}
}
