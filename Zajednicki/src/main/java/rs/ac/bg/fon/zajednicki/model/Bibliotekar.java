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
public class Bibliotekar implements ApstraktniDomenskiObjekat {
    private int idBibliotekar;
    private String ime;
    private String prezime;
    private String brojTel;
    private String korisnickoIme;
    private String sifra;

    public Bibliotekar() {
    }

    public Bibliotekar(String korisnickoIme, String sifra) {
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }
 
    public Bibliotekar(int idBibliotekar, String ime, String prezime, String brojTel, String korisnickoIme, String sifra) {
        this.idBibliotekar = idBibliotekar;
        this.ime = ime;
        this.prezime = prezime;
        this.brojTel = brojTel;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    public int getIdBibliotekar() {
        return idBibliotekar;
    }

    public void setIdBibliotekar(int idBibliotekar) {
        this.idBibliotekar = idBibliotekar;
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

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Bibliotekar other = (Bibliotekar) obj;
        if (!Objects.equals(this.korisnickoIme, other.korisnickoIme)) {
            return false;
        }
        return Objects.equals(this.sifra, other.sifra);
    }

    @Override
    public String vratiNazivTabele() {
        return "bibliotekar";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Bibliotekar b = new Bibliotekar();
            b.setIdBibliotekar(rs.getInt("bibliotekar.idBibliotekar"));
            b.setIme(rs.getString("bibliotekar.ime"));
            b.setPrezime(rs.getString("bibliotekar.prezime"));
            b.setBrojTel(rs.getString("bibliotekar.brojTel"));
            b.setKorisnickoIme(rs.getString("bibliotekar.korisnickoIme"));
            b.setSifra(rs.getString("bibliotekar.sifra"));
            
            lista.add(b);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ime,prezime,brojTel,korisnickoIme,sifra";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + ime + "','" + prezime + "','" + brojTel + "','" + korisnickoIme + "','" + sifra + "')";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "bibliotekar.idBibliotekar=" + idBibliotekar;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ime='" + ime + "',prezime='" + prezime + "',brojTel='" + brojTel + "',korisnickoIme='" + korisnickoIme + "',sifra='" + sifra + "'";

    }

   
    
}
