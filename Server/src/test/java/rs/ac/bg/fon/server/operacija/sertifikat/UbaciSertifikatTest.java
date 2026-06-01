package rs.ac.bg.fon.server.operacija.sertifikat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;
import rs.ac.bg.fon.zajednicki.model.Sertifikat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UbaciSertifikatTest {

    @Mock
    private DBBrokerInterfejs mockBroker;

    private UbaciSertifikat operacija;
    private Sertifikat ispravanSertifikat;

    @BeforeEach
    void setUp() {
        operacija = new UbaciSertifikat(mockBroker);

        ispravanSertifikat = new Sertifikat();
        ispravanSertifikat.setInstitucija("Oracle");
        ispravanSertifikat.setNaziv("AAA");
    }

    @Test
    void testUbaciSertifikatUspesno() throws Exception {
        operacija.izvrsi(ispravanSertifikat);

        verify(mockBroker).connect();
        verify(mockBroker).add(ispravanSertifikat);
        verify(mockBroker).commit();
        verify(mockBroker, never()).rollback();
    }

    @Test
    void testUbaciSertifikatNullObjekatBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(null));
        assertEquals("Sistem ne moze da zapamti sertifikat", ex.getMessage());
        
        verify(mockBroker).rollback();
    }

    @Test
    void testUbaciSertifikatPogresanTipObjektaBacaIzuzetak() throws Exception {
        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi("Nisam Sertifikat"));
        assertEquals("Sistem ne moze da zapamti sertifikat", ex.getMessage());
        
        verify(mockBroker).rollback();
    }

    @Test
    void testUbaciSertifikatPraznaInstitucijaBacaIzuzetak() throws Exception {
        ispravanSertifikat.setInstitucija("");

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanSertifikat));
        assertEquals("GRESKA INSTITUCIJA", ex.getMessage());

        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testUbaciSertifikatNazivNullBacaIzuzetak() throws Exception {
        ispravanSertifikat.setNaziv(null);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanSertifikat));
        assertEquals("GRESKA NAZIV", ex.getMessage());

        verify(mockBroker).rollback();
        verify(mockBroker, never()).connect();
    }

    @Test
    void testUbaciSertifikatGreskaBazePokreceRollback() throws Exception {
        doThrow(new Exception("Duplicate entry error")).when(mockBroker).add(ispravanSertifikat);

        Exception ex = assertThrows(Exception.class, () -> operacija.izvrsi(ispravanSertifikat));
        assertEquals("Duplicate entry error", ex.getMessage());

        verify(mockBroker).connect();
        verify(mockBroker).add(ispravanSertifikat);
        verify(mockBroker).rollback();
        verify(mockBroker, never()).commit();
    }
}