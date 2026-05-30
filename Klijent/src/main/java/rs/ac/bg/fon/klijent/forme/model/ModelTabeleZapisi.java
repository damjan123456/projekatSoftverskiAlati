package rs.ac.bg.fon.klijent.forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;

/**
 * Model tabele namenjen pregledu, pretrazi i filtriranju svih kreiranih zapisa o iznajmljivanju knjiga.
 * Povezuje i vizuelno spaja podatke o datumu, iznosu, pripadajućem čitaocu i zaduženom bibliotekaru.
 * * @author Damjan
 */
public class ModelTabeleZapisi extends AbstractTableModel {
    
    /**
     * Lista svih glavnih zapisa o iznajmljivanju unutar modela.
     */
    List<ZapisOIznajmljivanju> lista;
    
    /**
     * Nazivi kolona tabele za prikaz podataka o zaglavljima zapisa.
     */
    String[] kolone = {"ID", "Datum iznajmljivanja", "Ukupan iznos", "Citalac", "Bibliotekar"};

    /**
     * Konstruktor koji postavlja početni skup zapisa o iznajmljivanju za vizuelni prikaz.
     * @param lista Lista zapisa o iznajmljivanju.
     */
    public ModelTabeleZapisi(List<ZapisOIznajmljivanju> lista) {
        this.lista = lista;
    }
    
    /**
     * Vraća broj zapisa koji su trenutno filtrirani i dostupni u tabeli.
     * @return Broj redova kao int.
     */
    @Override
    public int getRowCount() {
        return lista.size();
    }

    /**
     * Vraća broj stubaca tabele sa zapisima.
     * @return Broj kolona kao int.
     */
    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    /**
     * Vraća naslov kolone na osnovu njenog pozicionog indeksa.
     * @param column Indeks kolone.
     * @return Naziv kolone.
     */
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    /**
     * Mapira atribute objekta klase ZapisOIznajmljivanju na adekvatne kolone u JTable komponenti, 
     * spajajući ime i prezime za aktere.
     * @param rowIndex Indeks selektovanog reda.
     * @param columnIndex Indeks kolone za mapiranje podatka.
     * @return Vrednost ćelije u zavisnosti od izabranog stubca.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ZapisOIznajmljivanju zapis = lista.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> zapis.getIdZapis();
            case 1 -> {
                if (zapis.getDatumIznajmljivanja() != null) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy.");
                    yield sdf.format(zapis.getDatumIznajmljivanja());
                }
                yield "";
            }
            case 2 -> zapis.getUkupanIznos();
            case 3 -> zapis.getCitalac().getIme() + " " + zapis.getCitalac().getPrezime();
            case 4 -> zapis.getBibliotekar().getIme() + " " + zapis.getBibliotekar().getPrezime();
            default -> "NA";
        };
    }

    /**
     * Vraća listu zapisa o iznajmljivanju sadržanu u ovom modelu tabele.
     * @return Lista objekata klase ZapisOIznajmljivanju.
     */
    public List<ZapisOIznajmljivanju> getLista() {
        return lista;
    }

    /**
     * Izvršava filtriranje i pretragu svih zapisa na osnovu delimičnog imena ili prezimena čitaoca i bibliotekara.
     * Rezultat pretrage se trenutno primenjuje i osvežava vizuelnu komponentu.
     * @param filterCitalac Tekstualni filter za ime ili prezime čitaoca.
     * @param filterBibliotekar Tekstualni filter za ime ili prezime bibliotekara.
     * @return true ukoliko nakon filtriranja postoji barem jedan zapis koji ispunjava uslove, inače false.
     */
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