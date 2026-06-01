package rs.ac.bg.fon.server.operacija.zapis;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 * Konkretna sistemska operacija zadužena za preuzimanje liste svih zapisa o iznajmljivanju.
 * Koristi kompleksan SQL JOIN mehanizam za povezivanje zapisa sa čitaocem, bibliotekarom i mestom.
 *
 * @author Damjan
 */
public class VratiListuZapisOIznajmljivanju extends ApstraktnaGenerickaOperacija {
    
    /**
     * Interna lista u koju se smeštaju učitani zapisi o iznajmljivanju.
     */
    private List<ZapisOIznajmljivanju> zapisi;

    /**
     * Podrazumevani konstruktor klase VratiListuZapisOIznajmljivanju.
     */
    public VratiListuZapisOIznajmljivanju() {
    }

    /**
     * Konstruktor za potrebe testiranja
     */
    public VratiListuZapisOIznajmljivanju(rs.ac.bg.fon.server.broker.DBBrokerInterfejs broker) {
        super(broker);
    }

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        // Operacija preuzimanja svih zapisa nema predefinisane strukturne preduslove
    }

    /**
     * Formira složeni JOIN uslov i poziva db brokera da preuzme sve zapise sa spojenim podacima povezane strukture.
     */
    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        String uslov = " JOIN citalac ON zapisoiznajmljivanju.idCitalac=citalac.idCitalac JOIN bibliotekar ON zapisoiznajmljivanju.idBibliotekar=bibliotekar.idBibliotekar JOIN mesto ON citalac.idMesto=mesto.idMesto";
        zapisi = broker.getAll(new ZapisOIznajmljivanju(), uslov);
    }

    /**
     * Vraća listu svih zapisa o iznajmljivanju preuzetih iz baze podataka nakon izvršenja operacije.
     *
     * @return List Lista objekata tipa ZapisOIznajmljivanju.
     */
    public List<ZapisOIznajmljivanju> getZapisi() {
        return zapisi;
    }
}