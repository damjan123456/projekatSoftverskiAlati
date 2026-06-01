package rs.ac.bg.fon.server.operacija.bibliotekar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Bibliotekar;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VratiListuSviBibliotekarTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private VratiListuSviBibliotekar operacija;

    @BeforeEach
    void setUp() {
        operacija = new VratiListuSviBibliotekar(mockBroker);
    }

    @Test
    void testVratiListuSviBibliotekarUspesno() throws Exception {
        List<Bibliotekar> laznaLista = new ArrayList<>();
        Bibliotekar b1 = new Bibliotekar();
        b1.setIme("Marko");
        Bibliotekar b2 = new Bibliotekar();
        b2.setIme("Ana");
        laznaLista.add(b1);
        laznaLista.add(b2);

        when(mockBroker.getAll(any(Bibliotekar.class), any())).thenReturn(laznaLista);

        operacija.izvrsi(null);

        List<Bibliotekar> rezultat = operacija.getBibliotekari();
        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());
        assertEquals("Marko", rezultat.get(0).getIme());

        verify(mockBroker).connect();
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testVratiListuSviBibliotekarGreskaBazePokreceRollback() throws Exception {
        when(mockBroker.getAll(any(Bibliotekar.class), any())).thenThrow(new Exception("Baza nedostupna"));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Baza nedostupna", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}