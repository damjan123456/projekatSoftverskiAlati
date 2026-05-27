/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.klijent.forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;

/**
 *
 * @author damja
 */
public class ModelTabeleZapisi extends AbstractTableModel {
    List<ZapisOIznajmljivanju> lista;
    String[] kolone = {"ID","Datum iznajmljivanja","Ukupan iznos","Citalac","Bibliotekar"};

    public ModelTabeleZapisi(List<ZapisOIznajmljivanju> lista) {
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
        ZapisOIznajmljivanju zapis = lista.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> zapis.getIdZapis();
            case 1 -> zapis.getDatumIznajmljivanja();
            case 2 -> zapis.getUkupanIznos();
            case 3 -> zapis.getCitalac().getIme() + " " + zapis.getCitalac().getPrezime();
            case 4 -> zapis.getBibliotekar().getIme() + " " + zapis.getBibliotekar().getPrezime();
            default -> "NA";
        };
    }

    public List<ZapisOIznajmljivanju> getLista() {
        return lista;
    }

//    public boolean pretrazi(String ime, String prezime, Mesto mesto) {
//        List<ZapisOIznajmljivanju> filtrirani = lista.stream()
//            .filter(c -> ime == null || ime.isEmpty() || c.getIme().toLowerCase().contains(ime.toLowerCase()))
//            .filter(c -> prezime == null || prezime.isEmpty() || c.getPrezime().toLowerCase().contains(prezime.toLowerCase()))
//            .filter(c -> mesto == null || c.getMesto().getNaziv().equalsIgnoreCase(mesto.getNaziv()))
//            .toList();
//        lista = filtrirani;
//        fireTableDataChanged();
//        if (lista.isEmpty())
//            return false;
//        return true;
//    }

    public boolean pretrazi(String filterCitalac, String filterBibliotekar) {
    
    List<ZapisOIznajmljivanju> filtrirani = lista.stream()
    .filter(z -> (filterCitalac == null || filterCitalac.isEmpty() ||
                  z.getCitalac().getIme().toLowerCase().contains(filterCitalac.toLowerCase()) ||
                  z.getCitalac().getPrezime().toLowerCase().contains(filterCitalac.toLowerCase())))
    .filter(z -> (filterBibliotekar == null || filterBibliotekar.isEmpty() ||
                  z.getBibliotekar().getIme().toLowerCase().contains(filterBibliotekar.toLowerCase()) ||
                  z.getBibliotekar().getPrezime().toLowerCase().contains(filterBibliotekar.toLowerCase())))
    .toList();

    lista = filtrirani;
    fireTableDataChanged();
    return !lista.isEmpty();
}


    
}
