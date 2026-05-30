package rs.ac.bg.fon.klijent.forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.zajednicki.model.Citalac;
import rs.ac.bg.fon.zajednicki.model.Mesto;

/**
 * Model tabele za prikaz i filtriranje podataka o čitaocima unutar grafičkog interfejsa.
 * Nasleđuje AbstractTableModel i prilagođava listu objekata klase Citalac za prikaz u JTable komponenti.
 * * @author Damjan
 */
public class ModelTabeleCitaoci extends AbstractTableModel {
    
    /**
     * Lista čitalaca koji se trenutno prikazuju u tabeli.
     */
    List<Citalac> lista;
    
    /**
     * Nazivi kolona tabele koje se mapiraju na atribute čitaoca.
     */
    String[] kolone = {"Id", "Ime", "Prezime", "Broj telefona", "Mesto"};

    /**
     * Konstruktor koji inicijalizuje model tabele sa prosleđenom listom čitalaca.
     * @param lista Početna lista čitalaca za prikaz.
     */
    public ModelTabeleCitaoci(List<Citalac> lista) {
        this.lista = lista;
    }
    
    /**
     * Vraća ukupan broj redova u tabeli, što odgovara broju elemenata u listi.
     * @return Broj redova kao int.
     */
    @Override
    public int getRowCount() {
        return lista.size();
    }

    /**
     * Vraća ukupan broj kolona u tabeli na osnovu definisanog niza kolona.
     * @return Broj kolona kao int.
     */
    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    /**
     * Vraća naziv kolone na osnovu njenog indeksa.
     * @param column Indeks kolone.
     * @return Naziv kolone.
     */
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    /**
     * Vraća vrednost određene ćelije tabele na osnovu zadatog reda i kolone.
     * @param rowIndex Indeks reda iz kojeg se uzima objekat.
     * @param columnIndex Indeks kolone na osnovu koje se određuje atribut.
     * @return Vrednost atributa čitaoca za zadatu ćeliju.
     */
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

    /**
     * Vraća trenutnu listu čitalaca koja se nalazi u modelu tabele.
     * @return Lista objekata klase Citalac.
     */
    public List<Citalac> getLista() {
        return lista;
    }

    /**
     * Filtrira trenutnu listu čitalaca na osnovu zadatih kriterijuma (ime, prezime, mesto) i osvežava prikaz.
     * @param ime Kriterijum za pretragu po imenu čitaoca.
     * @param prezime Kriterijum za pretragu po prezimenu čitaoca.
     * @param mesto Kriterijum za pretragu po mestu čitaoca.
     * @return true ukoliko nakon filtriranja lista sadrži barem jednog čitaoca, inače false.
     */
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