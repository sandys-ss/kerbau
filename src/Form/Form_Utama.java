/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Koneksi;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Depo2
 */
public class Form_Utama extends javax.swing.JFrame {

    /**
     * Creates new form Form_Utama
     */
    Connection connection;
    
    public Form_Utama() {
        initComponents();
        icon();
        card();
        aksi();
        
        this.setResizable(false);
        
       
    }
    
    private void icon () {
        
        ImageIcon gmr = new ImageIcon ("src/Images/icon.png");
        setIconImage(gmr.getImage());     
        
    }
    
    private void card () {
        
        panelCard.add(panelUtama, "panelutama");
        panelCard.add(panelAbout, "panelabout");
        panelCard.add(panelMaster, "Panelmaster");
        panelCard.add(panelSupplier, "panelsupplier");
        panelCard.add(panelCustomer, "panelcustomer");
        panelCard.add(panelBuy, "panelbuy");
        panelCard.add(panelSell, "panelsell");
        panelCard.add(panelSetting, "panelsetting");
        panelCard.add(panelLogin, "panellogin");
        
        CardLayout c1 = (CardLayout)panelCard.getLayout();
        c1.show(panelCard, "panellogin");
        
        setGlassPane(panelAbout);
        
    }


    private void aksi () {
        
        panelUtama.addActionListenerAboutUs(new Aksi_menuUtama_about());
        panelUtama.addActionListenerMaster(new Aksi_menuUtama_master());
        panelUtama.addActionListenerCustomer(new Aksi_menuUtama_customer());
        panelUtama.addActionListenerSupplier(new Aksi_menuUtama_supplier());
        panelUtama.addActionListenerBuy(new Aksi_menuUtama_buy());
        panelUtama.addActionListenerSell(new Aksi_menuUtama_sell());
        panelUtama.addActionListenerSetting(new Aksi_menuUtama_setting());
        panelUtama.addActionListenerLogout(new Aksi_menuUtama_logout());
        
        panelAbout.addActionListenerOk(new Aksi_menuAbout_ok());
        panelCustomer.addActionListenerCancel(new Aksi_menuCancel_back());
        panelMaster.addActionListenerCancel(new Aksi_menuCancel_back());
        panelSupplier.addActionListenerCancel(new Aksi_menuCancel_back());
        panelBuy.addActionListenerCancel(new Aksi_menuCancel_back());
        panelSell.addActionListenerCancel(new Aksi_menuCancel_back());
        panelSetting.addActionListenerCancel(new Aksi_menuCancel_back());
        panelLogin.addActionListenerLogin(new Aksi_menuLogin_login());
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCard = new javax.swing.JPanel();
        panelAbout = new Form.panelAbout();
        panelUtama = new Form.panelUtama();
        panelCustomer = new Form.panelCustomer();
        panelMaster = new Form.panelMaster();
        panelSupplier = new Form.panelSupplier();
        panelBuy = new Form.PanelBuy();
        panelSell = new Form.PanelSell();
        panelSetting = new Form.panelSetting();
        panelLogin = new Form.panelLogin();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("S.I.P - ePart v.1.0");

        panelCard.setLayout(new java.awt.CardLayout());
        panelCard.add(panelAbout, "card2");
        panelCard.add(panelUtama, "card3");
        panelCard.add(panelCustomer, "card4");
        panelCard.add(panelMaster, "card5");
        panelCard.add(panelSupplier, "card6");
        panelCard.add(panelBuy, "card7");
        panelCard.add(panelSell, "card8");
        panelCard.add(panelSetting, "card9");
        panelCard.add(panelLogin, "card10");

        getContentPane().add(panelCard, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(816, 678));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Form_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Utama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Utama().setVisible(true);
            }
        });
    }
    
    class Aksi_menuUtama_about implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            getGlassPane().setVisible(true);
        }
        
    }
    
    class Aksi_menuUtama_master implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardLayout c1 = (CardLayout)panelCard.getLayout();
            c1.show(panelCard, "Panelmaster");            
        }
        
    }
    
    class Aksi_menuUtama_customer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardLayout c1 = (CardLayout)panelCard.getLayout();
            c1.show(panelCard, "panelcustomer");
        }
        
    }
    
    class Aksi_menuUtama_supplier implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardLayout c1 = (CardLayout)panelCard.getLayout();
            c1.show(panelCard, "panelsupplier");
        }
        
    }
    
    class Aksi_menuUtama_buy implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardLayout c1 = (CardLayout)panelCard.getLayout();
            c1.show(panelCard, "panelbuy");
        }
        
    }
    
    class Aksi_menuUtama_sell implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardLayout c1 = (CardLayout)panelCard.getLayout();
            c1.show(panelCard, "panelsell");
        }
        
    }
    
    class Aksi_menuUtama_setting implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
           CardLayout c1 = (CardLayout)panelCard.getLayout();
           c1.show(panelCard, "panelsetting"); 
        }
        
    }
    
    class Aksi_menuUtama_logout implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardLayout c1 = (CardLayout)panelCard.getLayout();
           c1.show(panelCard, "panellogin"); 
        }
        
    }
    
    
    class Aksi_menuAbout_ok implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            getGlassPane().setVisible(false);
        }
        
    }
    
    class Aksi_menuCancel_back implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            CardLayout c1 = (CardLayout)panelCard.getLayout();
            c1.show(panelCard, "panelutama");
            
            //refresh panelbuy
            panelBuy.isitabelmaster();
            panelBuy.getCmbkodesupp().removeAllItems();
            panelBuy.isicombo();
            
            //refresh tabelmaster
            panelMaster.isitabel();
            
            //refresh panelsell
            panelSell.isitabelmaster();
            panelSell.getCmbkodecust().removeAllItems();
            panelSell.isicombo();
            
        }
        
    }
    
    class Aksi_menuLogin_login implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            
        
        try {
            String user = panelLogin.getTxtusername().getText().trim();
            String pass = String.valueOf(panelLogin.getTxtpassword().getPassword()).trim();
            
            String sql = "select password from tbuser where user = '"+user+"' ";
            
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            
            if (rs.next()) {
                String pass2 = rs.getString("password");
                if (pass2.equals(pass)) {
                    CardLayout c1 = (CardLayout)panelCard.getLayout();
                    c1.show(panelCard, "panelutama");
                    panelLogin.getTxtusername().setText("");
                    panelLogin.getTxtpassword().setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "password salah", "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    panelLogin.getTxtusername().setText("");
                    panelLogin.getTxtpassword().setText(""); 
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username Tidak ditemukan", "Warning",
                        JOptionPane.WARNING_MESSAGE);
                panelLogin.getTxtusername().setText("");
                    panelLogin.getTxtpassword().setText(""); 
                stm.close();
            }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
    }
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Form.panelAbout panelAbout;
    private Form.PanelBuy panelBuy;
    private javax.swing.JPanel panelCard;
    private Form.panelCustomer panelCustomer;
    private Form.panelLogin panelLogin;
    private Form.panelMaster panelMaster;
    private Form.PanelSell panelSell;
    private Form.panelSetting panelSetting;
    private Form.panelSupplier panelSupplier;
    private Form.panelUtama panelUtama;
    // End of variables declaration//GEN-END:variables
}