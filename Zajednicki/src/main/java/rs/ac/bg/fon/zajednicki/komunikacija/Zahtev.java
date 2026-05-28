package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.Serializable;

/**
 * Predstavlja klijentski zahtev koji se šalje serveru radi izvršavanja neke akcije.
 * * Sadrži informaciju o operaciji koju treba pokrenuti, kao i parametar
 * koji je neophodan za izvršavanje te operacije.
 * * @author damja
 */
public class Zahtev implements Serializable {
    
    /**
     * Vrsta operacije koju klijent zahteva od servera.
     */
    private Operacija operacija;
    
    /**
     * Podaci potrebni za izvršenje operacije.
     */
    private Object parametar;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat zahteva.
     */
    public Zahtev() {
    }

    /**
     * Konstruktor koji inicijalizuje zahtev sa tačno definisanom operacijom i parametrom.
     * @param operacija Konstanta iz enuma Operacija koja definiše akciju.
     * @param parametar Objekat koji se šalje serveru kao ulazni podatak za operaciju.
     */
    public Zahtev(Operacija operacija, Object parametar) {
        this.operacija = operacija;
        this.parametar = parametar;
    }
    
    /**
     * Vraća operaciju koja je zatražena ovim zahtevom.
     * * @return Operacija koja se izvršava.
     */
    public Operacija getOperacija() {
        return operacija;
    }

    /**
     * Postavlja operaciju koju server treba da obradi.
     * * @param operacija Operacija koju klijent zahteva.
     */
    public void setOperacija(Operacija operacija) {
        this.operacija = operacija;
    }

    /**
     * Vraća parametar prosleđen uz klijentski zahtev.
     * * @return Ulazni podatak operacije kao Object.
     */
    public Object getParametar() {
        return parametar;
    }

    /**
     * Postavlja parametar koji se šalje serveru na obradu.
     * * @param parametar Ulazni objekat potreban za izvršenje operacije.
     */
    public void setParametar(Object parametar) {
        this.parametar = parametar;
    }
}