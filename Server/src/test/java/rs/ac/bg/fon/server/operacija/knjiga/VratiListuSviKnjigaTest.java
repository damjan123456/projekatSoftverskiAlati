package rs.ac.bg.fon.server.operacija.knjiga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Knjiga;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VratiListuSviKnjigaTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private VratiListuSviKnjiga operacija;

    @BeforeEach
    void setUp() {
        operacija = new VratiListuSviKnjiga(mockBroker);
    }

    @Test
    void testVratiListuSviKnjigaUspesno() throws Exception {
        List<Knjiga> lazneKnjige = new ArrayList<>();
        Knjiga k1 = new Knjiga();
        k1.setNaslov("Na Drini cuprija");
        Knjiga k2 = new Knjiga();
        k2.setNaslov("Prokleta avlija");
        lazneKnjige.add(k1);
        lazneKnjige.add(k2);

        when(mockBroker.getAll(any(Knjiga.class), isNull())).thenReturn(lazneKnjige);

        operacija.izvrsi(null);

        List<Knjiga> rezultat = operacija.getKnjige();
        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());
        assertEquals("Na Drini cuprija", rezultat.get(0).getNaslov());
        assertEquals("Prokleta avlija", rezultat.get(1).getNaslov());

        verify(mockBroker).connect();
        verify(mockBroker).commit();
    }

    @Test
    void testVratiListuSviKnjigaGreskaBazePokreceRollback() throws Exception {
        when(mockBroker.getAll(any(Knjiga.class), isNull())).thenThrow(new Exception("Greška čitanja knjiga"));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Greška čitanja knjiga", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}