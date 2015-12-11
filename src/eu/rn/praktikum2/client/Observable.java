package eu.rn.praktikum2.client;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {

	private Set<Observer> beobachter;
	
	public Observable()
	{
		beobachter = new HashSet<Observer>();
	}
	
	protected void leiteNachrichtWeiter(String s)
	{
		for(Observer o : beobachter)
		{
			o.reagiereAufNachricht(s);
		}
	}
	
	public void registriereBeobachter(Observer o)
	{
		beobachter.add(o);
	}
	
}
