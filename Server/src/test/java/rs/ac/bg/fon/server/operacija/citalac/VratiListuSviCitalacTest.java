package rs.ac.bg.fon.server.operacija.citalac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Citalac;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VratiListuSviCitalacTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private VratiListuSviCitalac operacija;

    @BeforeEach
    void setUp() {
        operacija = new VratiListuSviCitalac(mockBroker);
    }

    @Test
    void testVratiListuSviCitalacUspesno() throws Exception {
        List<Citalac> lazniCitaoci = new ArrayList<>();
        Citalac c1 = new Citalac();
        c1.setIme("Marko");
        Citalac c2 = new Citalac();
        c2.setIme("Jovana");
        lazniCitaoci.add(c1);
        lazniCitaoci.add(c2);

        String ocekivaniUslov = " JOIN mesto ON citalac.idMesto=mesto.idMesto";

        when(mockBroker.getAll(any(Citalac.class), eq(ocekivaniUslov))).thenReturn(lazniCitaoci);

        operacija.izvrsi(null);

        List<Citalac> rezultat = operacija.getCitaoci();
        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());
        assertEquals("Marko", rezultat.get(0).getIme());

        verify(mockBroker).connect();
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testVratiListuSviCitalacGreskaBazePokreceRollback() throws Exception {
        String ocekivaniUslov = " JOIN mesto ON citalac.idMesto=mesto.idMesto";
        when(mockBroker.getAll(any(Citalac.class), eq(ocekivaniUslov))).thenThrow(new Exception("SQL Error"));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("SQL Error", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}