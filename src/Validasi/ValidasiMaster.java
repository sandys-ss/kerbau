/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validasi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author SANDYS
 */
public class ValidasiMaster {
    
    Connection connection;
    public String xno="";
    public String xnamapart="";
    public String xstock="";
    public String xhargabeli="";
    public String xhargajual="";
    public String xtype="";

    public ValidasiMaster() {
        
        connection = Koneksi.Koneksi.sambung();
        
    }
    
    public void validasi_part (String xnopart) {
        
        try {
            String cari = "SELECT * From tbmaster WHERE nopart = '" +xnopart+"' ";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(cari);
            
            if (rs.next()) {
                xno = rs.getString(1);
                xnamapart = rs.getString(2);
                xtype = rs.getString(3);
                xstock = rs.getString(4);
                xhargabeli = rs.getString(5);
                xhargajual = rs.getString(6);
            } 
            
        } catch (SQLException ex) {
                System.out.println("SQLException: " +ex.getMessage());
                System.out.println("SQLState: " +ex.getSQLState());
                System.out.println("VendorError: " +ex.getErrorCode());
        }
    }   
}
