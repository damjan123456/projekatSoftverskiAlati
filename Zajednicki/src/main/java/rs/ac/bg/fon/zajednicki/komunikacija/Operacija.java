package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.Serializable;

/**
 * Enumeracija koja definiše sve podržane sistemske operacije u aplikaciji.
 * Koristi se unutar objekta Zahtev kako bi server prepoznao akciju
 * koju klijent želi da izvrši.
 * * @author Damjan
 */
public enum Operacija implements Serializable {
    
    /**
     * Operacija za autentifikaciju bibliotekara na sistem.
     */
    LOGIN, 
    
    /**
     * Operacija za brisanje postojećeg čitaoca iz baze podataka.
     */
    OBRISI_CITAOCA, 
    
    /**
     * Operacija za unos i evidentiranje novog sertifikata.
     */
    UNESI_SERTIFIKAT, 
    
    /**
     * Operacija za dobijanje liste svih čitalaca iz baze podataka.
     */
    VRATI_CITAOCE, 
    
    /**
     * Operacija za dobijanje liste svih geografskih mesta iz baze podataka.
     */
    VRATI_MESTA, 
    
    /**
     * Operacija za unos i kreiranje novog čitaoca u sistemu.
     */
    UNESI_CITAOCA, 
    
    /**
     * Operacija za izmenu podataka o postojećem čitaocu.
     */
    IZMENI_CITAOCA, 
    
    /**
     * Operacija za dobijanje liste svih zapisa o iznajmljivanjima knjiga.
     */
    VRATI_ZAPISE, 
    
    /**
     * Operacija za dobijanje liste svih knjiga iz bibliotečkog fonda.
     */
    VRATI_KNJIGE, 
    
    /**
     * Operacija za kreiranje novog zapisa o iznajmljivanju knjiga.
     */
    KREIRAJ_ZAPIS, 
    
    /**
     * Operacija za izmenu postojećeg zapisa o iznajmljivanju.
     */
    IZMENI_ZAPIS, 
    
    /**
     * Operacija za dobijanje liste svih registrovanih bibliotekara.
     */
    VRATI_BIBLIOTEKARE, 
    
    /**
     * Operacija za pretragu i vraćanje jednog specifičnog zapisa o iznajmljivanju.
     */
    VRATI_ZAPIS
}