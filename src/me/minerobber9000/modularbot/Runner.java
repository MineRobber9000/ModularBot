package me.minerobber9000.modularbot;

import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

public class Runner {
	
	IRCBot bot;
	
	public static void main(String[] args) throws Exception {
		Runner r = new Runner();
		r.run(new String[] {"#Lil-G|bot"});
	}
	
	public Runner() {
		this.bot = new IRCBot("ModularBot", "ImANoob", "!test");
	}
	
	public void run(String[] channels) throws NickAlreadyInUseException, IOException, IrcException {
		bot.connect("irc.badnik.net");
		for (String chan : channels) {
			bot.joinChannel(chan);
		}
	}
	
}
