package me.minerobber9000.modularbot;

import java.util.ArrayList;

public class UserDataStore {
	
	ArrayList<CUser> users = new ArrayList<CUser>();

	public UserDataStore() {
		System.out.println("UserDataStore initialized.");
	}
	
	public void put(String user, PermLevel perm) {
		users.add(new CUser(user, perm));
	}
	
	public CUser get(String user) {
		CUser u = null;
		for (CUser iter : users) {
			if (iter.name.equals(user)) {
				u = iter;
				break;
			}
		}
		return u;
	}
	
	public ArrayList<CUser> getByPermLevel(PermLevel perm) {
		ArrayList<CUser> u = new ArrayList<CUser>();
		for (CUser iter : users) {
			if (iter.perm == perm) {
				u.add(iter);
				break;
			}
		}
		return u;
	}

}
