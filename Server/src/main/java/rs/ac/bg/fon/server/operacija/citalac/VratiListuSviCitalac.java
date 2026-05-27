/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.operacija.citalac;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author damja
 */
public class VratiListuSviCitalac extends ApstraktnaGenerickaOperacija {
    List<Citalac> citaoci;
    @Override
    protected void preduslovi(Object objekat) {
    }

    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        String uslov = " JOIN mesto ON citalac.idMesto=mesto.idMesto";
        citaoci = broker.getAll(new Citalac(), uslov);
    }

    public List<Citalac> getCitaoci() {
        return citaoci;
    }
    
}
