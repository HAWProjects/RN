package eu.rn.praktikum2.client;

public interface ChatProtokoll
{
    void verbinden();
    void verbindungBeenden();
    void datenVonServerVerarbeiten();
    void nachrichtSenden(String text);
    void nachrichtEmpfangen();
    void userlisteAusgeben();
    void nachrichtVerarbeiten(String s);
}
