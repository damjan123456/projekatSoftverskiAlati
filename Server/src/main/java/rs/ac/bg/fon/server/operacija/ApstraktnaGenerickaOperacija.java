/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.operacija;

import rs.ac.bg.fon.server.broker.DBBroker;
import rs.ac.bg.fon.server.broker.DBBrokerInterfejs;

/**
 *
 * @author damja
 */
public abstract class ApstraktnaGenerickaOperacija {
    protected final DBBrokerInterfejs broker;

    public ApstraktnaGenerickaOperacija() {
        this.broker = new DBBroker();
    }
    
    public final void izvrsi(Object objekat) throws Exception{
        try{
            preduslovi(objekat);
            zaponcniTransakciju();
            izvrsiOperaciju(objekat);
            potvrdiTransakciju();  
        }catch(Exception e){
            ponistiTransakciju();
            throw e;
        }
    }

    protected abstract void preduslovi(Object objekat) throws Exception;
    protected abstract void izvrsiOperaciju(Object objekat) throws Exception;

    private void zaponcniTransakciju() throws Exception {
        broker.connect();
    }

    private void potvrdiTransakciju() throws Exception {
        broker.commit();
    }

    private void ponistiTransakciju() throws Exception {
        broker.rollback();
    }

    
}
