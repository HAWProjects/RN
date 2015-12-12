package eu.rn.praktikum2.server;

import java.util.HashSet;
import java.util.Set;

public class Verbindungen {

	private Set<Verbindung> sockets;
	
	public Verbindungen()
	{
		sockets = new HashSet<Verbindung>();
	}
	
	public synchronized void fuegeHinzu(Verbindung con)
	{
		sockets.add(con);
	}
	
	public synchronized void entferne(Verbindung con)
	{
		sockets.remove(con);
	}
	
	public Set<Verbindung> getVerbindungen()
	{
		return sockets;
	}
}
