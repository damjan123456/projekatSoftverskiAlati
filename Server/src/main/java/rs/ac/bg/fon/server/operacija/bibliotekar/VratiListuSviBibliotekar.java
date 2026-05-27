/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.operacija.bibliotekar;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author damja
 */
public class VratiListuSviBibliotekar extends ApstraktnaGenerickaOperacija {
    List<Bibliotekar> bibliotekari;
    @Override
    protected void preduslovi(Object objekat) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        bibliotekari = broker.getAll(new Bibliotekar(), null);
    }

    public List<Bibliotekar> getBibliotekari() {
        return bibliotekari;
    }
}
