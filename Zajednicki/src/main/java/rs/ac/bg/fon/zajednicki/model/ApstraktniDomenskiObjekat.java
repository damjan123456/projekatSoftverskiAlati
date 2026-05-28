package rs.ac.bg.fon.zajednicki.model;

import java.io.Serializable;
import java.util.List;
import java.sql.ResultSet;

/**
 * Zajednički interfejs za sve domenske klase u aplikaciji.
 * Definiše metode neophodne za rad generičkog brokera baze podataka (vratiti
 * nazive tabela, kolone, vrednosti za upite i mapiranje iz rezultata baze).
 * * @author Damjan
 */
public interface ApstraktniDomenskiObjekat extends Serializable {
    
    /**
     * Vraća tačan naziv tabele u bazi podataka koja odgovara domenskoj klasi.
     * * @return Naziv tabele kao String.
     */
    public String vratiNazivTabele();
    
    /**
     * Mapira kompletan SQL ResultSet i pakuje ga u listu objekata.
     * Koristi se pri operacijama čitanja.
     * * @param rs Rezultat SQL upita koji sadrži redove iz baze podataka.
     * @return Lista objekata koji implementiraju ovaj interfejs.
     * @throws java.lang.Exception Ukoliko dođe do SQL greške pri čitanju podataka iz ResultSet-a.
     */
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception;
    
    /**
     * Vraća nazive kolona koje se koriste prilikom izvršavanja INSERT upita.
     * Kolone su odvojene zarezom.
     * * @return Nazivi kolona za unos kao String.
     */
    public String vratiKoloneZaUbacivanje();
    
    /**
     * Vraća vrednosti atributa objekta formatirane za potrebe SQL INSERT upita.
     * Vrednosti su smeštene unutar zagrada i prilagođene SQL sintaksi.
     * * @return Vrednosti za unos kao String.
     */
    public String vratiVrednostiZaUbacivanje();
    
    /**
     * Vraća SQL uslov zasnovanu na primarnom ključu objekta.
     * Koristi se za identifikaciju tačno određenog reda prilikom izmene, brisanja ili selekcije.
     * * @return SQL uslov za primarni ključ kao String.
     */
    public String vratiPrimarniKljuc();
    
    /**
     * Mapira trenutni red iz SQL ResultSet-a u jedan konkretan domenski objekat.
     * * @param rs Rezultat SQL upita pozicioniran na željeni red.
     * @return Mapirani objekat klase koja implementira interfejs.
     * @throws java.lang.Exception Ukoliko operacija nije podržana ili se desi SQL greška.
     */
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception;
    
    /**
     * Vraća deo SQL upita sa parovima kolona=vrednost koji se koristi unutar UPDATE naredbe.
     * * @return Vrednosti i kolone za izmenu kao String.
     */
    public String vratiVrednostiZaIzmenu();
}