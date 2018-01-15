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
public class ValidasiUser {
    
    Connection connection;
    public String xuser="";
    public String xpassword="";
   

    public ValidasiUser() {
        
        connection = Koneksi.Koneksi.sambung();
        
    }
    
    public void validasi_user (String xid) {
        
        try {
            String cari = "SELECT * From tbuser WHERE user = '" +xid+"' ";
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(cari);
            
            if (rs.next()) {
                xuser = rs.getString(1);
                xpassword = rs.getString(2);
                
            } 
            
        } catch (SQLException ex) {
                System.out.println("SQLException: " +ex.getMessage());
                System.out.println("SQLState: " +ex.getSQLState());
                System.out.println("VendorError: " +ex.getErrorCode());
        }
        
    }
    
    
    
}
