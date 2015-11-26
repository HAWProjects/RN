package eu.rn.praktikum2;

import java.util.Set;

public class Userliste {
	
	private Set<String> names;

	
	public synchronized void fuegeHinzu(String name)
	{
		names.add(name);
	}
	
	public synchronized void entferne(String name)
	{
		names.remove(name);
	}
	
	public String getNames()
	{
		return names.toString();
	}
}
