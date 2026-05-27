/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Mesto;

/**
 *
 * @author damja
 */
public class ModelTabeleCitaoci extends AbstractTableModel{
    List<Citalac> lista;
    String[] kolone = {"Id","Ime","Prezime","Broj telefona","Mesto"};

    public ModelTabeleCitaoci(List<Citalac> lista) {
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
        Citalac citalac = lista.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> citalac.getIdCitalac();
            case 1 -> citalac.getIme();
            case 2 -> citalac.getPrezime();
            case 3 -> citalac.getBrojTel();
            case 4 -> citalac.getMesto().getNaziv();
            default -> "NA";
        };
    }

    public List<Citalac> getLista() {
        return lista;
    }

    public boolean pretrazi(String ime, String prezime, Mesto mesto) {
        List<Citalac> filtrirani = lista.stream()
            .filter(c -> ime == null || ime.isEmpty() || c.getIme().toLowerCase().contains(ime.toLowerCase()))
            .filter(c -> prezime == null || prezime.isEmpty() || c.getPrezime().toLowerCase().contains(prezime.toLowerCase()))
            .filter(c -> mesto == null || c.getMesto().getNaziv().equalsIgnoreCase(mesto.getNaziv()))
            .toList();
        lista = filtrirani;
        fireTableDataChanged();
        if (lista.isEmpty())
            return false;
        return true;
    }

    
}
