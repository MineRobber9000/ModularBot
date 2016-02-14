package me.minerobber9000.modularbot.modules;

public class ModuleManager {
	
	Spinner spinner;

	public ModuleManager() {
		spinner = new Spinner();
	}
	
	public void registerSpinner(Spinner m) {
		this.spinner = m;
	}
	
	public Spinner getSpinner() {
		return this.spinner;
	}

}
