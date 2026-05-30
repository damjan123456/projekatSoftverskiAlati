package rs.ac.bg.fon.klijent.main;

import rs.ac.bg.fon.klijent.glavnikontroler.GlavniKontroler;

/**
 * Glavna pokretačka klasa klijentskog dela aplikacije.
 * Služi kao ulazna tačka programa koja inicira izvršavanje i pokreće korisnički interfejs.
 * * @author Damjan
 */
public class Main {
    
    /**
     * Podrazumevani konstruktor klase Main.
     */
    public Main() {
    }

    /**
     * Glavna metoda koja se poziva prilikom startovanja klijentske aplikacije.
     * Preko jedinstvene instance GlavnogKontrolera inicira otvaranje forme za prijavu korisnika.
     * * @param args Argumenti komandne linije prosleđeni prilikom pokretanja aplikacije (ne koriste se).
     */
    public static void main(String[] args) {
        GlavniKontroler.getInstanca().otvoriLoginFormu();
    }
}