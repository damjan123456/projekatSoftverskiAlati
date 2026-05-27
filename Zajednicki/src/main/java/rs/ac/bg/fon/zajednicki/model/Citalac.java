/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author damja
 */
public class Citalac implements ApstraktniDomenskiObjekat {
    private int idCitalac;
    private String ime;
    private String prezime;
    private String brojTel;
    private Mesto mesto;

    public Citalac() {
    }

    public Citalac(String ime, String prezime, String brojTel, Mesto mesto) {
        this.ime = ime;
        this.prezime = prezime;
        this.brojTel = brojTel;
        this.mesto = mesto;
    }

    public int getIdCitalac() {
        return idCitalac;
    }

    public void setIdCitalac(int idCitalac) {
        this.idCitalac = idCitalac;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getBrojTel() {
        return brojTel;
    }

    public void setBrojTel(String brojTel) {
        this.brojTel = brojTel;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Citalac other = (Citalac) obj;
        return Objects.equals(this.brojTel, other.brojTel);
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String vratiNazivTabele() {
        return "citalac";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Citalac c = new Citalac();
            c.setIdCitalac(rs.getInt("citalac.idCitalac"));
            c.setIme(rs.getString("citalac.ime"));
            c.setPrezime(rs.getString("citalac.prezime"));
            c.setBrojTel(rs.getString("citalac.brojTel"));
            
            Mesto m = new Mesto();
            m.setIdMesto(rs.getInt("mesto.idMesto"));
            m.setNaziv(rs.getString("mesto.naziv"));
            c.setMesto(m);
            
            lista.add(c);
            
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,brojTel,idMesto";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + ime + "','" + prezime + "','" + brojTel + "'," + mesto.getIdMesto() + ")";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "citalac.idCitalac=" + idCitalac;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "',prezime='" + prezime + "',brojTel='" + brojTel + "',idMesto=" + mesto.getIdMesto();
    }
    
    
}
