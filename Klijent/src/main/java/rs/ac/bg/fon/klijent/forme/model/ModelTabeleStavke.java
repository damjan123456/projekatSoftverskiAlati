/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.forme.model;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;

/**
 *
 * @author damja
 */
public class ModelTabeleStavke extends AbstractTableModel {
    
    List<StavkaZapisaOIznajmljivanju> lista;
    String[] kolone = {"rb","Datum vracanja","Max datum vracanja","Kolicina","Iznos","Cena za nepovracaj","Vraceno na vreme", "Knjiga"};

    public ModelTabeleStavke(List<StavkaZapisaOIznajmljivanju> lista) {
        this.lista = lista;
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaZapisaOIznajmljivanju stavka = lista.get(rowIndex);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        return switch (columnIndex) {
            case 0 -> stavka.getRb();
            case 1 -> sdf.format(stavka.getDatumVracanja());
            case 2 -> sdf.format(stavka.getMaxDatumVracanja());
            case 3 -> stavka.getKolicina();
            case 4 -> stavka.getIznos();
            case 5 -> stavka.getCenaZaNepovracaj();
            case 6 -> stavka.isVracenoNaVreme() ? "DA" : "NE";
            case 7 -> stavka.getKnjiga().getNaslov();
            default -> "NA";
        };
    }

    public List<StavkaZapisaOIznajmljivanju> getLista() {
        return lista;
    }

    public void dodajStavku(StavkaZapisaOIznajmljivanju stavka) {
        int maxrb = 0;
        for (StavkaZapisaOIznajmljivanju s : lista) {
            if (s.getRb() > maxrb)
                maxrb = s.getRb();
        }
        stavka.setRb(maxrb + 1);
        lista.add(stavka);
        fireTableDataChanged();
    }

    public void obrisiStavku(int red) {
        lista.remove(red);
        fireTableDataChanged();
    }

    public void setLista(List<StavkaZapisaOIznajmljivanju> lista) {
        this.lista = lista;
    }
    
    
    
}
