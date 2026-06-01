package rs.ac.bg.fon.zajednicki.komunikacija;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrimalacTest {

    private Primalac primalac;
    private ByteArrayInputStream memoryStream;

    @Mock
    private Socket mockedSocket;

    private byte[] kreirajSerijalizovanePodatke(Object objekat) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(objekat);
        oos.flush();
        return baos.toByteArray();
    }

    @BeforeEach
    void setUp() throws Exception {
        byte[] podaci = kreirajSerijalizovanePodatke("Inicijalna poruka");
        memoryStream = new ByteArrayInputStream(podaci);
        
        when(mockedSocket.getInputStream()).thenReturn(memoryStream);
        
        primalac = new Primalac(mockedSocket);
    }

    @AfterEach
    void tearDown() throws Exception {
        primalac = null;
        if (memoryStream != null) {
            memoryStream.close();
        }
        memoryStream = null;
    }

    @Test
    void testKonstruktorUspesnoKreiranje() throws IOException {
        verify(mockedSocket, times(1)).getInputStream();
        assertNotNull(primalac);
    }

    @Test
    void testPrimiObjekatUspesno() throws Exception {
        String ocekivaniString = "Test podatak za primanje";
        
        byte[] noviPodaci = kreirajSerijalizovanePodatke(ocekivaniString);
        ByteArrayInputStream noviStrim = new ByteArrayInputStream(noviPodaci);
        
        when(mockedSocket.getInputStream()).thenReturn(noviStrim);
        Primalac lokalniPrimalac = new Primalac(mockedSocket);

        Object primljeniObjekat = lokalniPrimalac.primi();

        assertNotNull(primljeniObjekat);
        assertEquals(ocekivaniString, primljeniObjekat);
    }

    @Test
    void testPrimiNullObjekat() throws Exception {
        byte[] nullPodaci = kreirajSerijalizovanePodatke(null);
        ByteArrayInputStream nullStrim = new ByteArrayInputStream(nullPodaci);
        
        when(mockedSocket.getInputStream()).thenReturn(nullStrim);
        Primalac lokalniPrimalac = new Primalac(mockedSocket);

        Object primljeniObjekat = lokalniPrimalac.primi();

        assertNull(primljeniObjekat);
    }

    @Test
void testPrimiKadaKlijentPrekineVezu() throws Exception {
    ByteArrayInputStream prazanStrim = new ByteArrayInputStream(new byte[0]);
    
    when(mockedSocket.getInputStream()).thenReturn(prazanStrim);
    Primalac lokalniPrimalac = new Primalac(mockedSocket);

    assertDoesNotThrow(() -> {
        Object rezultat = lokalniPrimalac.primi();
        assertNull(rezultat, "Ukoliko se desi izuzetak, metoda primi() treba da vrati null.");
    }, "Metoda primi() interno hvata izuzetke, tako da test ne sme da krahira.");
}
}