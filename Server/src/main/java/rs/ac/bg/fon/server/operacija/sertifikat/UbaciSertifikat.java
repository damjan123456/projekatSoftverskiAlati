/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.operacija.sertifikat;

import rs.ac.bg.fon.zajednicki.model.Sertifikat;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author damja
 */
public class UbaciSertifikat extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Sertifikat)){
            throw new Exception("Sistem ne moze da zapamti sertifikat");
        }
        Sertifikat s = (Sertifikat) objekat;
        if (s.getInstitucija() == null || s.getInstitucija().isEmpty())
            throw new Exception("GRESKA INSTITUCIJA");
        if (s.getNaziv()== null || s.getNaziv().isEmpty())
            throw new Exception("GRESKA NAZIV");
    }

    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        broker.add((Sertifikat)objekat);
    }
    
}
