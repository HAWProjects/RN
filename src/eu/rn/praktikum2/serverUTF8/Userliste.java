package eu.rn.praktikum2.serverUTF8;

import java.util.HashSet;
import java.util.Set;

public class Userliste {
	
	private Set<String> names;
	
	public Userliste()
    {
        names = new HashSet<String>();
    }

	
	public synchronized void fuegeHinzu(String name)
	{
		names.add(name);
	}
	
	public synchronized void entferne(String name)
	{
		names.remove(name);
	}
	
	public Set<String> getNames()
	{
		return names;
	}
}
