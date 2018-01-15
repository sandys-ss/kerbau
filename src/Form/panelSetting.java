/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Koneksi;
import Validasi.ValidasiMaster;
import Validasi.ValidasiUser;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SANDYS
 */
public class panelSetting extends javax.swing.JPanel {

    /**
     * Creates new form panelReport
     */
    
    Connection connection;
    
    public panelSetting() {
        initComponents();
        
        isitabel();
    }
    
    public void addActionListenerCancel (ActionListener l) {
        btnCancel.addActionListener(l);
    }
    
         private void input () {
        
        String user = txtuser.getText();
        char[] passwd = txtpass.getPassword();
        String pass = "";
        for (int i = 0; i < passwd.length; i++) {
            pass = pass + passwd[i];
        }
        
        String insert = "INSERT INTO tbuser VALUES ('"+user+"', '"+pass+"')";
        
        ValidasiUser valid = new ValidasiUser();
        valid.validasi_user(user);
        
        if (valid.xuser == "") {
            
            if (txtuser.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "User Name masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtuser.requestFocus();
            } else if (txtpass.getPassword().toString().equals("")) {
                JOptionPane.showMessageDialog(null, "password masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                try {
                    connection = Koneksi.sambung();
                   // Statement statement = null;
                    Statement statement = connection.createStatement();
                    //statement = connection.prepareStatement(insert);
                    //statement.setString(1, user);
                    //statement.setString(2, passwd);
                    statement.executeUpdate(insert);
                    statement.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                    JOptionPane.showMessageDialog(null,"Data berhasil Disimpan",
                        "Informasi",JOptionPane.INFORMATION_MESSAGE);
                 clear();
                 isitabel();
               
            }
            
        } else {
            JOptionPane.showMessageDialog(null,"Data Sudah Ada",
                 "Informasi",JOptionPane.WARNING_MESSAGE);
            txtuser.setText("");
            txtuser.requestFocus();
        }
        
    }
         
    private void clear () {
        
        txtuser.setText("");
        txtpass.setText("");
        tabelUser.clearSelection();
        
    }
    
    public void isitabel () {
        
        Object header [] = {"Username"};
   
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelUser.setModel(model);
        
        String sql = "SELECT * FROM tbuser ORDER BY user";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                
                String kolom [] = {kolom1};
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void isifield () {
        int xrow = tabelUser.getSelectedRow();
        String user = (String) tabelUser.getValueAt(xrow, 0);
        
        txtuser.setText(user);
    
    }
    
        private void delete () {
        
        String sql = "DELETE FROM tbuser WHERE user = '"+txtuser.getText()+"' ";
        
        if (txtuser.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Kode Supplier masih kosong", "Informasi",
                    JOptionPane.WARNING_MESSAGE);
            txtuser.requestFocus();
        } else {
            int pilih = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus Data ini ?",
                "Warning", JOptionPane.YES_NO_OPTION);
        
        if (pilih == JOptionPane.YES_OPTION) {
           try {
               connection = Koneksi.sambung();
               PreparedStatement statement = null;
                statement = connection.prepareStatement(sql);
                Statement stm = connection.createStatement();
                stm.execute(sql);
                stm.close();
           } catch (Exception e) {
               System.out.println(e.getMessage());
           } 
        }
            clear();
            isitabel();

        }
            
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground1 = new Component.PanelBackground();
        panelTransparan1 = new Component.PanelTransparan();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtuser = new javax.swing.JTextField();
        btnclear = new Component.Tombol_Master();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelUser = new javax.swing.JTable();
        btnadd = new Component.Tombol_Master();
        btndelete = new Component.Tombol_Master();
        txtpass = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        epart = new javax.swing.JLabel();
        btnCancel = new Component.Tombol_Master();

        setMinimumSize(new java.awt.Dimension(800, 640));
        setLayout(new java.awt.BorderLayout());

        panelBackground1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelTransparan1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("Password :");
        panelTransparan1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 80, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("User Name :");
        panelTransparan1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 80, -1));

        txtuser.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtuser.setForeground(new java.awt.Color(255, 255, 255));
        txtuser.setOpaque(false);
        panelTransparan1.add(txtuser, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 150, -1));

        btnclear.setForeground(new java.awt.Color(255, 255, 255));
        btnclear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_file-24.png"))); // NOI18N
        btnclear.setText("Clear");
        btnclear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnclear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnclearActionPerformed(evt);
            }
        });
        panelTransparan1.add(btnclear, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 40, 100, 30));

        tabelUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelUser);

        panelTransparan1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 570, 210));

        btnadd.setForeground(new java.awt.Color(255, 255, 255));
        btnadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_file-24.png"))); // NOI18N
        btnadd.setText("Add");
        btnadd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });
        panelTransparan1.add(btnadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 100, 30));

        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_file-24.png"))); // NOI18N
        btndelete.setText("Delete");
        btndelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btndelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndeleteActionPerformed(evt);
            }
        });
        panelTransparan1.add(btndelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 40, 100, 30));

        txtpass.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtpass.setForeground(new java.awt.Color(255, 255, 255));
        txtpass.setOpaque(false);
        panelTransparan1.add(txtpass, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 150, -1));

        panelBackground1.add(panelTransparan1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 750, 320));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("User Management");
        panelBackground1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 250, -1));

        epart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1Epart.png"))); // NOI18N
        panelBackground1.add(epart, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, -1, -1));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel_file-24.png"))); // NOI18N
        btnCancel.setText("Cancel");
        panelBackground1.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 390, 110, -1));

        add(panelBackground1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        input();
    }//GEN-LAST:event_btnaddActionPerformed

    private void tabelUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelUserMouseClicked
        int x = evt.getClickCount();
        if (x ==2) {
            isifield();
        }
    }//GEN-LAST:event_tabelUserMouseClicked

    private void btnclearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnclearActionPerformed
        clear();
    }//GEN-LAST:event_btnclearActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        delete();
    }//GEN-LAST:event_btndeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Component.Tombol_Master btnCancel;
    private Component.Tombol_Master btnadd;
    private Component.Tombol_Master btnclear;
    private Component.Tombol_Master btndelete;
    private javax.swing.JLabel epart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private Component.PanelBackground panelBackground1;
    private Component.PanelTransparan panelTransparan1;
    private javax.swing.JTable tabelUser;
    private javax.swing.JPasswordField txtpass;
    private javax.swing.JTextField txtuser;
    // End of variables declaration//GEN-END:variables
}
