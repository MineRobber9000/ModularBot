package me.minerobber9000.modularbot;

public class CUser {

	public String name;
	public PermLevel perm;
	public int balance;
	public int redrings = 0;

	public CUser(String user, PermLevel perm) {
		this.name = user;
		this.perm = perm;
		this.balance = 100;
	}

}
