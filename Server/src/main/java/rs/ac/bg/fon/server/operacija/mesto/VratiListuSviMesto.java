/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.operacija.mesto;

import java.util.List;
import rs.ac.bg.fon.zajednicki.model.Mesto;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author damja
 */
public class VratiListuSviMesto extends ApstraktnaGenerickaOperacija{
    List<Mesto> mesta;
    @Override
    protected void preduslovi(Object objekat) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        mesta = broker.getAll(new Mesto(), null);
    }

    public List<Mesto> getMesta() {
        return mesta;
    }
    
    
    
}
