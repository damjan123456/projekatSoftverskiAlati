package rs.ac.bg.fon.klijent.forme.model;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.zajednicki.model.StavkaZapisaOIznajmljivanju;

/**
 * Model tabele namenjen za manipulaciju i prikaz stavki pojedinačnog zapisa o iznajmljivanju knjiga.
 * Omogućava dinamičko dodavanje, brisanje i formatiranje prikaza atributa stavki u JTable komponenti.
 * * @author Damjan
 */
public class ModelTabeleStavke extends AbstractTableModel {
    
    /**
     * Lista stavki zapisa o iznajmljivanju koje su vezane za ovaj model.
     */
    List<StavkaZapisaOIznajmljivanju> lista;
    
    /**
     * Nazivi kolona tabele koje prikazuju specifičnosti pojedinačne stavke.
     */
    String[] kolone = {"rb", "Datum vracanja", "Max datum vracanja", "Kolicina", "Iznos", "Cena za nepovracaj", "Vraceno na vreme", "Knjiga"};

    /**
     * Konstruktor koji povezuje model tabele sa prosleđenom listom stavki zapisa.
     * @param lista Lista stavki za prikaz.
     */
    public ModelTabeleStavke(List<StavkaZapisaOIznajmljivanju> lista) {
        this.lista = lista;
    }
    
    /**
     * Vraća broj stavki koje se trenutno nalaze u tabeli.
     * @return Broj stavki kao int.
     */
    @Override
    public int getRowCount() {
        return lista.size();
    }

    /**
     * Vraća ukupan broj kolona modela stavki.
     * @return Broj kolona kao int.
     */
    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    /**
     * Vraća naziv kolone na osnovu njenog indeksa.
     * @param column Indeks kolone.
     * @return Naziv iz niza kolona.
     */
    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }
    
    /**
     * Formira i vraća vrednost za prikaz u konkretnoj ćeliji tabele, uz formatiranje datuma i logičkih stanja.
     * @param rowIndex Indeks reda stavke.
     * @param columnIndex Indeks kolone za traženi podatak.
     * @return Vrednost prilagođena tipu podatka za odgovarajući stubac.
     */
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

    /**
     * Vraća unutrašnju listu svih stavki zapisa.
     * @return Lista objekata klase StavkaZapisaOIznajmljivanju.
     */
    public List<StavkaZapisaOIznajmljivanju> getLista() {
        return lista;
    }

    /**
     * Dodaje novu stavku u tabelu, pri čemu automatski izračunava naredni redni broj stavke.
     * Nakon dodavanja, obaveštava tabelu o promeni strukture podataka.
     * @param stavka Objekat stavke koji se dodaje u listu.
     */
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

    /**
     * Uklanja stavku iz tabele na osnovu prosleđenog indeksa reda i osvežava prikaz komponente.
     * @param red Indeks reda koji se briše.
     */
    public void obrisiStavku(int red) {
        lista.remove(red);
        fireTableDataChanged();
    }

    /**
     * Postavlja novu listu stavki za model i zamenjuje postojeću.
     * @param lista Nova lista stavki zapisa o iznajmljivanju.
     */
    public void setLista(List<StavkaZapisaOIznajmljivanju> lista) {
        this.lista = lista;
    }  
}