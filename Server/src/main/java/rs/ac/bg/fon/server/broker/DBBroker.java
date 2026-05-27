 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.server.broker;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.zajednicki.model.ApstraktniDomenskiObjekat;

/**
 *
 * @author damja
 */
public class DBBroker implements DBBrokerInterfejs<ApstraktniDomenskiObjekat> {

    @Override
    public List<ApstraktniDomenskiObjekat> getAll(ApstraktniDomenskiObjekat param,String uslov) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        
        String upit = "SELECT * FROM " + param.vratiNazivTabele();
        if (uslov!=null){
            upit += uslov;
        }
        System.out.println(upit);
        Statement st = DbKonekcija.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(upit);
        lista = param.vratiListu(rs);
        rs.close();
        st.close();
        return lista;
    }

    @Override
    public void add(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaUbacivanje() + 
                      ") VALUES " + param.vratiVrednostiZaUbacivanje();
        System.out.println(upit);
        Statement st = DbKonekcija.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    @Override
    public void edit(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "UPDATE " + param.vratiNazivTabele() + " SET " + param.vratiVrednostiZaIzmenu() + " WHERE " + param.vratiPrimarniKljuc();
        System.out.println(upit);
        Statement st = DbKonekcija.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    @Override
    public void delete(ApstraktniDomenskiObjekat param) throws Exception {
        String upit = "DELETE FROM " + param.vratiNazivTabele() + " WHERE " + param.vratiPrimarniKljuc();
        System.out.println(upit);
        Statement st = DbKonekcija.getInstance().getConnection().createStatement();
        st.executeUpdate(upit);
        st.close();
    }

    @Override
    public int addReturnKey(ApstraktniDomenskiObjekat param) throws Exception{
        String upit = "INSERT INTO " + param.vratiNazivTabele() + " (" + param.vratiKoloneZaUbacivanje() + 
                      ") VALUES " + param.vratiVrednostiZaUbacivanje();
        System.out.println(upit);
        PreparedStatement ps = DbKonekcija.getInstance().getConnection().prepareStatement(upit,Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        int id = -1;
        if (rs.next())
            id = rs.getInt(1);
        rs.close();
        ps.close();
        
        return id;
        
    }

    

    
    
}
