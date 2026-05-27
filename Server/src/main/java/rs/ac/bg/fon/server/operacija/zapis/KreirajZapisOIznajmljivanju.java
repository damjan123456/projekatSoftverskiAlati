/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.operacija.zapis;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author damja
 */
public class KreirajZapisOIznajmljivanju extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof ZapisOIznajmljivanju)){
            throw new Exception("Sistem ne moze da doda zapis o iznajmljivanju");
        } 
        ZapisOIznajmljivanju z = (ZapisOIznajmljivanju) objekat;
        if (z.getDatumIznajmljivanja()== null)
            throw new Exception("GRESKA DATUM");
        if (z.getUkupanIznos() <0)
            throw new Exception("GRESKA UKUPAN IZNOS");
        if (z.getBibliotekar()== null || z.getBibliotekar().getIdBibliotekar() < 0)
            throw new Exception("GRESKA BIBLIOTEKAR");
        if (z.getCitalac()== null || z.getCitalac().getIdCitalac() < 0)
            throw new Exception("GRESKA CITALAC");
    }

    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        ZapisOIznajmljivanju zapis = (ZapisOIznajmljivanju)objekat;
        int idZapis = broker.addReturnKey(objekat);
        
        List<StavkaZapisaOIznajmljivanju> stavke = zapis.getStavke();
        
        for (StavkaZapisaOIznajmljivanju s : stavke) {
            s.setZapis(idZapis);
            broker.add(s);
        }
    }
    
}
