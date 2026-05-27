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
public class Knjiga implements ApstraktniDomenskiObjekat {
    private int idKnjiga;
    private String naslov;
    private String autor;
    private double cenaZaNepovracaj;

    public Knjiga() {
    }

    public Knjiga(int idKnjiga, String naslov, String autor, double cenaZaNepovracaj) {
        this.idKnjiga = idKnjiga;
        this.naslov = naslov;
        this.autor = autor;
        this.cenaZaNepovracaj = cenaZaNepovracaj;
    }

    public int getIdKnjiga() {
        return idKnjiga;
    }

    public void setIdKnjiga(int idKnjiga) {
        this.idKnjiga = idKnjiga;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public double getCenaZaNepovracaj() {
        return cenaZaNepovracaj;
    }

    public void setCenaZaNepovracaj(double cenaZaNepovracaj) {
        this.cenaZaNepovracaj = cenaZaNepovracaj;
    }

    @Override
    public String toString() {
        return naslov + " " + autor;
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
        final Knjiga other = (Knjiga) obj;
        if (!Objects.equals(this.naslov, other.naslov)) {
            return false;
        }
        return Objects.equals(this.autor, other.autor);
    }

    @Override
    public String vratiNazivTabele() {
        return "knjiga";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            Knjiga k = new Knjiga();
            k.setIdKnjiga(rs.getInt("knjiga.idKnjiga"));
            k.setNaslov(rs.getString("knjiga.naslov"));
            k.setAutor(rs.getString("knjiga.autor"));
            k.setCenaZaNepovracaj(rs.getDouble("knjiga.cenaZaNepovracaj"));
            
            lista.add(k);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naslov,autor,cenaZaNepovracaj";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "('" + naslov + "','" + autor + "'," + cenaZaNepovracaj + ")";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "knjiga.idKnjiga=" + idKnjiga;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "naslov='" + naslov + "',autor='" + autor + "',cenaZaNepovracaj" + cenaZaNepovracaj;
    }
    
    
    
    
}
