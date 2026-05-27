/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.operacija.citalac;

import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.server.operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author damja
 */
public class ObrisiCitalac extends ApstraktnaGenerickaOperacija{

    @Override
    protected void preduslovi(Object param) throws Exception{
        if (param == null || !(param instanceof Citalac)){
            throw new Exception("Sistem ne moze da obrise citaoca");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat) throws Exception {
        broker.delete((Citalac)objekat);
    }
    
}
