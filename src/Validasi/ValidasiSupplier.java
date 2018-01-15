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
public class ValidasiSupplier {
    
    Connection connection;
    
    public String xkode="";
    public String xnamasupp="";
    public String xtelp="";
    public String xalamat="";

    public ValidasiSupplier() {
        connection = Koneksi.Koneksi.sambung();
    }
    
    public void validasi_supp (String xkodesupp) {
        try {
            String cari = "SELECT * FROM tbsupplier WHERE kodesupplier = '"+xkodesupp+"' " ;
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(cari);
            
            if (rs.next()) {
                xkode = rs.getString(1);
                xnamasupp = rs.getString(2);
                xtelp = rs.getString(3);
                xalamat = rs.getString(4);
            }
        } catch (SQLException ex) {
                System.out.println("SQLException: " +ex.getMessage());
                System.out.println("SQLState: " +ex.getSQLState());
                System.out.println("VendorError: " +ex.getErrorCode());
    }
    
    
    
    
    }
}