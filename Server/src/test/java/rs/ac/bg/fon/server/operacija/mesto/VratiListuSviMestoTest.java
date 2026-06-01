package rs.ac.bg.fon.server.operacija.mesto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Mesto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VratiListuSviMestoTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private VratiListuSviMesto operacija;

    @BeforeEach
    void setUp() {
        operacija = new VratiListuSviMesto(mockBroker);
    }

    @Test
    void testVratiListuSviMestoUspesno() throws Exception {
        List<Mesto> laznaLista = new ArrayList<>();
        Mesto m1 = new Mesto();
        m1.setNaziv("Beograd");
        Mesto m2 = new Mesto();
        m2.setNaziv("Novi Sad");
        laznaLista.add(m1);
        laznaLista.add(m2);

        when(mockBroker.getAll(any(Mesto.class), isNull())).thenReturn(laznaLista);

        operacija.izvrsi(null);

        List<Mesto> rezultat = operacija.getMesta();
        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());
        assertEquals("Beograd", rezultat.get(0).getNaziv());
        assertEquals("Novi Sad", rezultat.get(1).getNaziv());

        verify(mockBroker).connect();
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testVratiListuSviMestoGreskaBazePokreceRollback() throws Exception {
        when(mockBroker.getAll(any(Mesto.class), isNull())).thenThrow(new Exception("Greška konekcije"));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Greška konekcije", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}