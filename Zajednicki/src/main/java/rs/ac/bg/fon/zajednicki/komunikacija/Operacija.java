/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.komunikacija;

import java.io.Serializable;

/**
 *
 * @author damja
 */
public enum Operacija implements Serializable {
    LOGIN, 
    OBRISI_CITAOCA, 
    UNESI_SERTIFIKAT, 
    VRATI_CITAOCE, 
    VRATI_MESTA, 
    UNESI_CITAOCA, 
    IZMENI_CITAOCA, 
    VRATI_ZAPISE, 
    VRATI_KNJIGE, 
    KREIRAJ_ZAPIS, 
    IZMENI_ZAPIS, 
    VRATI_BIBLIOTEKARE, VRATI_ZAPIS
}
