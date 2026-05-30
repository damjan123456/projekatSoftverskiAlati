package rs.ac.bg.fon.server.broker;

import java.util.List;

/**
 * Generički interfejs koji definiše ugovor za operacije nad bazom podataka (CRUD).
 * Sadrži podrazumevane (default) metode za upravljanje konekcijom i transakcijama,
 * kao i apstraktne metode za rad sa objektima perzistencije.
 *
 * @author Damjan
 * @param <T> Tip objekta koji predstavlja domensku klasu mapiranu na tabelu baze podataka.
 */
public interface DBBrokerInterfejs<T> {

    /**
     * Otvara konekciju sa bazom podataka preko jedinstvene instance klase zadužene za konekciju.
     *
     * @throws java.lang.Exception Ako dođe do greške prilikom uspostavljanja veze sa bazom.
     */
    default public void connect() throws Exception {
        DbKonekcija.getInstance().getConnection();
    }

    /**
     * Zatvara aktivnu konekciju sa bazom podataka.
     *
     * @throws java.lang.Exception Ako dođe do greške prilikom zatvaranja veze.
     */
    default public void disconnect() throws Exception {
        DbKonekcija.getInstance().getConnection().close();
    }

    /**
     * Potvrđuje trenutnu transakciju i trajno upisuje sve izmene u bazu podataka.
     *
     * @throws java.lang.Exception Ako dođe do greške prilikom izvršavanja commit operacije.
     */
    default public void commit() throws Exception {
        DbKonekcija.getInstance().getConnection().commit();
    }

    /**
     * Poništava sve izmene izvršene u okviru trenutne transakcije i vraća bazu u prethodno stanje.
     *
     * @throws java.lang.Exception Ako dođe do greške prilikom izvršavanja rollback operacije.
     */
    default public void rollback() throws Exception {
        DbKonekcija.getInstance().getConnection().rollback();
    }

    /**
     * Vraća listu objekata iz baze podataka koji ispunjavaju zadati SQL uslov.
     *
     * @param param Objekat koji nosi informacije o strukturi tabele i načinu mapiranja rezultata.
     * @param uslov Dodatni SQL uslov. Može biti null.
     * @return List lista objekata preuzetih i parsiranih iz baze podataka.
     * @throws java.lang.Exception Ako dođe do greške prilikom izvršavanja SQL upita.
     */
    List<T> getAll(T param, String uslov) throws Exception;

    /**
     * Ubacuje novi zapis u odgovarajuću tabelu baze podataka na osnovu prosleđenog objekta.
     *
     * @param param Domenski objekat čiji se podaci upisuju u bazu.
     * @throws java.langException Ako dođe do greške prilikom izvršavanja INSERT upita.
     */
    void add(T param) throws Exception;

    /**
     * Ažurira postojeći zapis u bazi podataka na osnovu primarnog ključa prosleđenog objekta.
     *
     * @param param Domenski objekat koji sadrži nove vrednosti i identifikator zapisa koji se menja.
     * @throws java.lang.Exception Ako dođe do greške prilikom izvršavanja UPDATE upita.
     */
    void edit(T param) throws Exception;

    /**
     * Briše zapis iz baze podataka na osnovu primarnog ključa prosleđenog objekta.
     *
     * @param param Domenski objekat koji identifikuje zapis namenjen za brisanje.
     * @throws java.lang.Exception Ako dođe do greške prilikom izvršavanja DELETE upita.
     */
    void delete(T param) throws Exception;

    /**
     * Ubacuje novi zapis u bazu podataka i vraća generisani primarni ključ.
     *
     * @param param Domenski objekat čiji se podaci upisuju u bazu.
     * @return int Generisani identifikator iz baze podataka, ili -1 ako ključ nije generisan.
     * @throws java.lang.Exception Ako dođe do greške prilikom izvršavanja upita ili preuzimanja ključa.
     */
    int addReturnKey(T param) throws Exception;
}