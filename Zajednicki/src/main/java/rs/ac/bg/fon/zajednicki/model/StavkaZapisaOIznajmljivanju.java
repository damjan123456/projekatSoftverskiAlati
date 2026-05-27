/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author damja
 */
public class StavkaZapisaOIznajmljivanju implements ApstraktniDomenskiObjekat {
    private int rb;
    private int idZapis;
    private int kolicina;
    private double iznos;
    private Date datumVracanja;
    private Date maxDatumVracanja;
    private boolean vracenoNaVreme;
    private double cenaZaNepovracaj;
    private Knjiga knjiga;

    public StavkaZapisaOIznajmljivanju() {
    }

    public StavkaZapisaOIznajmljivanju(int idZapis, int rb, int kolicina, double iznos, Date datumVracanja, 
            Date maxDatumVracanja, boolean vracenoNaVreme, double cenaZaNepovracaj, Knjiga knjiga) {
        this.rb = rb;
        this.kolicina = kolicina;
        this.iznos = iznos;
        this.datumVracanja = datumVracanja;
        this.maxDatumVracanja = maxDatumVracanja;
        this.vracenoNaVreme = vracenoNaVreme;
        this.cenaZaNepovracaj = cenaZaNepovracaj;
        this.knjiga = knjiga;
        this.idZapis = idZapis;
    }

    public int getZapis() {
        return idZapis;
    }

    public void setZapis(int zapis) {
        this.idZapis = zapis;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public Date getDatumVracanja() {
        return datumVracanja;
    }

    public void setDatumVracanja(Date datumVracanja) {
        this.datumVracanja = datumVracanja;
    }

    public Date getMaxDatumVracanja() {
        return maxDatumVracanja;
    }

    public void setMaxDatumVracanja(Date maxDatumVracanja) {
        this.maxDatumVracanja = maxDatumVracanja;
    }

    public boolean isVracenoNaVreme() {
        return vracenoNaVreme;
    }

    public void setVracenoNaVreme(boolean vracenoNaVreme) {
        this.vracenoNaVreme = vracenoNaVreme;
    }

    public double getCenaZaNepovracaj() {
        return cenaZaNepovracaj;
    }

    public void setCenaZaNepovracaj(double cenaZaNepovracaj) {
        this.cenaZaNepovracaj = cenaZaNepovracaj;
    }

    public Knjiga getKnjiga() {
        return knjiga;
    }

    public void setKnjiga(Knjiga knjiga) {
        this.knjiga = knjiga;
    }

    @Override
    public String toString() {
        return knjiga.toString();
    }

    @Override
    public String vratiNazivTabele() {
        return "stavkazapisaoiznajmljivanju";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()){
            StavkaZapisaOIznajmljivanju stavka = new StavkaZapisaOIznajmljivanju();
            stavka.setZapis(rs.getInt("stavkazapisaoiznajmljivanju.idZapis"));
            stavka.setRb(rs.getInt("stavkazapisaoiznajmljivanju.rb"));
            stavka.setDatumVracanja(rs.getDate("stavkazapisaoiznajmljivanju.datumVracanja"));
            stavka.setMaxDatumVracanja(rs.getDate("stavkazapisaoiznajmljivanju.maxDatumVracanja"));
            stavka.setKolicina(rs.getInt("stavkazapisaoiznajmljivanju.kolicina"));
            stavka.setIznos(rs.getDouble("stavkazapisaoiznajmljivanju.iznos"));
            stavka.setCenaZaNepovracaj(rs.getDouble("stavkazapisaoiznajmljivanju.cenaZaNepovracaj"));
            stavka.setVracenoNaVreme(rs.getBoolean("stavkazapisaoiznajmljivanju.vracenoNaVreme"));
            
            Knjiga k = new Knjiga();
            k.setIdKnjiga(rs.getInt("knjiga.idKnjiga"));
            k.setNaslov(rs.getString("knjiga.naslov"));
            k.setAutor(rs.getString("knjiga.autor"));
            k.setCenaZaNepovracaj(rs.getDouble("knjiga.cenaZaNepovracaj"));
            
            stavka.setKnjiga(k);
            
            lista.add(stavka);
        }
        
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "rb,idZapis,datumVracanja,maxDatumVracanja,kolicina,iznos,cenaZaNepovracaj,vracenoNaVreme,idKnjiga";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        java.sql.Date sqlDatum1 =new java.sql.Date(datumVracanja.getTime());
        java.sql.Date sqlDatum2 =new java.sql.Date(maxDatumVracanja.getTime());


        return "(" + rb + "," + idZapis + ",'" + sqlDatum1 + "','" + sqlDatum2 + "'," + kolicina + "," + iznos + "," + cenaZaNepovracaj + "," + vracenoNaVreme + "," + knjiga.getIdKnjiga() +")";    
        //return "(" + rb + ",'" + datumVracanja + "','" + maxDatumVracanja + "'," + kolicina + "," + iznos + "," + cenaZaNepovracaj + "," + vracenoNaVreme + ")";    
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "rb=" + rb + " AND idZapis=" + idZapis;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        java.sql.Date sqlDatum1 =new java.sql.Date(datumVracanja.getTime());
        java.sql.Date sqlDatum2 =new java.sql.Date(maxDatumVracanja.getTime());
        return "rb=" + rb + ",idZapis=" + idZapis + ",datumVracanja='" + sqlDatum1 + "',maxDatumVracanja='" + sqlDatum2 + "',kolicina=" + kolicina + ",iznos=" + iznos + ",cenaZaNepovracaj=" + cenaZaNepovracaj + ",vracenoNaVreme=" + vracenoNaVreme;    
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final StavkaZapisaOIznajmljivanju other = (StavkaZapisaOIznajmljivanju) obj;
        if (this.rb != other.rb) {
            return false;
        }
        return this.idZapis == other.idZapis;
    }
    
    
}
