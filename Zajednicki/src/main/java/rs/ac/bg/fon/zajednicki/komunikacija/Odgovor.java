/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.Serializable;

/**
 *
 * @author damja
 */
public class Odgovor implements Serializable {
    private Object odgovor;

    public Odgovor() {
    }

    public Odgovor(Object odgovor) {
        this.odgovor = odgovor;
    }

    public Object getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(Object odgovor) {
        this.odgovor = odgovor;
    }
    
    
    
}
