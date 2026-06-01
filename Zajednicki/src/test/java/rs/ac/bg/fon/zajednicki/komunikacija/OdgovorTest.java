/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package rs.ac.bg.fon.zajednicki.komunikacija;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 *
 * @author Damjan
 */
public class OdgovorTest {
    Odgovor o;
    
    @BeforeEach
    public void setUp() throws Exception{
      o = new Odgovor();
    }
    
    @AfterEach
    public void tearDown() throws Exception {
      o = null;
    }

    @Test
    public void testOdgovor(){
        o = new Odgovor("odgovor");
        assertEquals("odgovor", o.getOdgovor());
        assertNull(o.getGreska());
    }

    @Test
    public void testSetOdgovor() {
        o.setOdgovor("odgovor");
        assertEquals("odgovor", o.getOdgovor());
    }
    
    @Test
    void testSetOdgovorObjekat() {
        Object pomocniObjekat = new Object();
        o.setOdgovor(pomocniObjekat);
        
        assertEquals(pomocniObjekat, o.getOdgovor());
    }
    
    @Test
    void testSetOdgovorNull() {
        o.setOdgovor(null);
        assertNull(o.getOdgovor());
    }

    @Test
    public void testSetGreska() {
        Exception e = new Exception("poruka sa greskom");
        o.setGreska(e);
        
        assertNotNull(e);
        assertEquals("poruka sa greskom", o.getGreska().getMessage());
    }

    @Test
    void testSetGreskaNull() {
        o.setGreska(null);
        assertNull(o.getGreska());
    }

    @ParameterizedTest
    @CsvSource({
        "Greska pri povezivanju sa bazom, Greska pri povezivanju sa bazom",
        "Nevalidni podaci za autentifikaciju, Nevalidni podaci za autentifikaciju",
        "Citalac vec postoji u sistemu, Citalac vec postoji u sistemu"
    })
    void testSetGreskaRazlicitePoruke(String porukaIzuzetka, String ocekivanaPoruka) {
        Exception e = new Exception(porukaIzuzetka);
        o.setGreska(e);
        
        assertNotNull(o.getGreska());
        assertEquals(ocekivanaPoruka, o.getGreska().getMessage());
    }
    
}
