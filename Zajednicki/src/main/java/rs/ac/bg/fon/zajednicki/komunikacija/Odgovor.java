package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.Serializable;

/**
 * Predstavlja serverski odgovor koji se šalje klijentu kao rezultat izvršavanja
 * određene sistemske operacije.
 * * Sadrži podatak koji je rezultat operacije (ukoliko je uspešno izvršena)
 * ili tekstualnu poruku o grešci (ukoliko je došlo do izuzetka).
 * * @author Damjan
 */
public class Odgovor implements Serializable {
    
    /**
     * Podatak koji server vraća klijentu.
     */
    private Object odgovor;
    
    /**
     * Tekstualna poruka o grešci ukoliko obrada zahteva na serveru nije uspela.
     */
    private String greska;

    /**
     * Podrazumevani konstruktor koji kreira prazan objekat odgovora.
     */
    public Odgovor() {
    }

    /**
     * Konstruktor koji postavlja uslovno uspešan podatak u odgovor.
     * * @param odgovor Objekat koji predstavlja rezultat izvršene operacije.
     */
    public Odgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

    /**
     * Vraća podatak koji je sadržan u odgovoru servera.
     * * @return Rezultat operacije kao Object
     */
    public Object getOdgovor() {
        return odgovor;
    }

    /**
     * Postavlja podatak koji se šalje kao rezultat operacije.
     * * @param odgovor Rezultat operacije koji se pakuje u odgovor.
     */
    public void setOdgovor(Object odgovor) {
        this.odgovor = odgovor;
    }
    
    /**
     * Postavlja tekstualnu poruku o grešci na osnovu prosleđenog izuzetka.
     * Ako je prosleđeni izuzetak različit od null, uzima se njegova poruka.
     * * @param e Izuzetak koji se desio tokom izvršavanja operacije na serveru.
     */
    public void setGreska(Exception e){
        if (e != null)
            this.greska = e.getMessage();
    }
    
    /**
     * Rekonstruiše i vraća izuzetak na osnovu tekstualne poruke o grešci.
     * Ako poruka o grešci ne postoji, vraća null.
     * * @return java.lang.Exception objekat sa porukom o grešci, ili null ako greške nema.
     */
    public Exception getGreska(){
        if (greska != null)
            return new Exception(greska);
        return null;
    }
}