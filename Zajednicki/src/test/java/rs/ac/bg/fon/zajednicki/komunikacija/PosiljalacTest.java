package rs.ac.bg.fon.zajednicki.komunikacija;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PosiljalacTest {

    private Posiljalac posiljalac;
    private ByteArrayOutputStream memoryStream;

    @Mock
    private Socket mockedSocket;

    @BeforeEach
    void setUp() throws Exception {
        memoryStream = new ByteArrayOutputStream();
        
        when(mockedSocket.getOutputStream()).thenReturn(memoryStream);
        
        posiljalac = new Posiljalac(mockedSocket);
    }

    @AfterEach
    void tearDown() throws Exception {
        posiljalac = null;
        memoryStream.close();
        memoryStream = null;
    }

    @Test
    void testKonstruktorUspesnoKreiranje() throws IOException {
        verify(mockedSocket, times(1)).getOutputStream();
        assertNotNull(posiljalac);
    }

    @Test
    void testPosaljiObjekatUspesno() throws Exception {
        String testPoruka = "test poruka";

        posiljalac.posalji(testPoruka);

        byte[] poslatiPodaci = memoryStream.toByteArray();
        assertTrue(poslatiPodaci.length > 0, "Strim ne bi trebalo da bude prazan nakon slanja.");

        ByteArrayInputStream bif = new ByteArrayInputStream(poslatiPodaci);
        ObjectInputStream ois = new ObjectInputStream(bif);
        Object primljeniObjekat = ois.readObject();

        assertEquals(testPoruka, primljeniObjekat);
        ois.close();
    }

    @Test
    void testPosaljiNullObjekat() throws Exception {
        posiljalac.posalji(null);

        byte[] poslatiPodaci = memoryStream.toByteArray();
        assertTrue(poslatiPodaci.length > 0);

        ByteArrayInputStream bif = new ByteArrayInputStream(poslatiPodaci);
        ObjectInputStream ois = new ObjectInputStream(bif);
        Object primljeniObjekat = ois.readObject();

        assertNull(primljeniObjekat);
        ois.close();
    }

    @Test
    void testPosaljiKadaSoketBaciIzuzetak() throws Exception {
        memoryStream.close();
        
        assertDoesNotThrow(() -> {
            posiljalac.posalji("Podatak koji ce izazvati IOException");
        }, "Metoda posalji interno hvata IOException, tako da ne sme da ga prosledi dalje.");
    }
}