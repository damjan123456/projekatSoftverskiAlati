package rs.ac.bg.fon.server.operacija.zapis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.ZapisOIznajmljivanju;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VratiListuZapisOIznajmljivanjuTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private VratiListuZapisOIznajmljivanju operacija;

    @BeforeEach
    void setUp() {
        operacija = new VratiListuZapisOIznajmljivanju(mockBroker);
    }

    @Test
    void testVratiListuZapisOIznajmljivanjuUspesno() throws Exception {
        List<ZapisOIznajmljivanju> lazniZapisi = new ArrayList<>();
        lazniZapisi.add(new ZapisOIznajmljivanju());
        lazniZapisi.add(new ZapisOIznajmljivanju());

        String ocekivaniSlozeniUslov = " JOIN citalac ON zapisoiznajmljivanju.idCitalac=citalac.idCitalac JOIN bibliotekar ON zapisoiznajmljivanju.idBibliotekar=bibliotekar.idBibliotekar JOIN mesto ON citalac.idMesto=mesto.idMesto";

        when(mockBroker.getAll(any(ZapisOIznajmljivanju.class), eq(ocekivaniSlozeniUslov))).thenReturn(lazniZapisi);

        operacija.izvrsi(null);

        List<ZapisOIznajmljivanju> rezultat = operacija.getZapisi();
        assertNotNull(rezultat);
        assertEquals(2, rezultat.size());

        verify(mockBroker).connect();
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testVratiListuZapisOIznajmljivanjuGreskaBazePokreceRollback() throws Exception {
        String ocekivaniSlozeniUslov = " JOIN citalac ON zapisoiznajmljivanju.idCitalac=citalac.idCitalac JOIN bibliotekar ON zapisoiznajmljivanju.idBibliotekar=bibliotekar.idBibliotekar JOIN mesto ON citalac.idMesto=mesto.idMesto";
        
        when(mockBroker.getAll(any(ZapisOIznajmljivanju.class), eq(ocekivaniSlozeniUslov))).thenThrow(new Exception("Fatal SQL Error"));

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Fatal SQL Error", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}