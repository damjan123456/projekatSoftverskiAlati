package rs.ac.bg.fon.klijent.forme;

/**
 * Definiše stanja u kojima grafičke forme unutar klijentske aplikacije mogu biti otvorene.
 * <p>
 * Koristi se od strane kontrolera kako bi se dinamički prilagodio izgled i funkcionalnost formi 
 * </p>
 *
 * @author Damjan
 */
public enum FormaModovi {
    
    /**
     * Režim rada namenjen za unos i kreiranje novog zapisa/objekta u sistemu.
     * Sva polja su uglavnom prazna i omogućena za unos.
     */
    DODAJ,
    
    /**
     * Režim rada namenjen za ažuriranje i izmenu podataka postojećeg zapisa/objekta.
     * Polja su popunjena trenutnim podacima i omogućena za izmenu.
     */
    IZMENI,
    
    /**
     * Režim rada namenjen isključivo za pregled detaljnih informacija o izabranom zapisu/objektu.
     * Sva tekstualna polja i komponente su zaključane za izmene.
     */
    DETALJI
}