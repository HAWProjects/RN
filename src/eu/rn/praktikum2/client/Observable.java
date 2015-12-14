package eu.rn.praktikum2.client;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {

	private Set<Observer> beobachter;
	
	public Observable()
	{
		beobachter = new HashSet<Observer>();
	}
	
	protected void benachrichtigeTexteingabe()
	{
		for(Observer o : beobachter)
		{
			o.reagiereAufTexteingabe();
		}
	}
	
	protected void benachrichtigeVerbinden()
	{
		for(Observer o : beobachter)
		{
			o.reagiereAufVerbinden();
		}
	}
	
	public void registriereBeobachter(Observer o)
	{
		beobachter.add(o);
	}
	
}
