/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Koneksi;
import Validasi.OnlyDigit;
import Validasi.ValidasiCustomer;
import Validasi.ValidasiSupplier;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author SANDYS
 */
public class panelSupplier extends javax.swing.JPanel {

    /**
     * Creates new form panelSupplier
     */
    private Connection connection;
    
    public panelSupplier() {
        initComponents();
        
        txttelp.setDocument(new OnlyDigit().getOnlyDigit());
        isitabel();
        
    }
    
    public void addActionListenerCancel (ActionListener l) {
        btnCancel.addActionListener(l);
    }
    
    public void clear () {
        txtkodesupp.setText("");
        txtnamasupp.setText("");
        txttelp.setText("");
        txtalamat.setText("");
        tabelSupplier.clearSelection();
        isitabel();
    }
    
    private void input () {
        
        String kodesupp = txtkodesupp.getText();
        String namasupp = txtnamasupp.getText();
        String telp =  txttelp.getText();
        String alamat = txtalamat.getText();
        
        String insert = "INSERT INTO tbsupplier (kodesupplier,namasupplier,telp,"
            + "alamat) VALUES (?,?,?,?);" ;
        
        ValidasiSupplier valid = new ValidasiSupplier();
        valid.validasi_supp(kodesupp);
        
        if (valid.xkode == "") {
            
            if (txtkodesupp.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Kode Supplier masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtkodesupp.requestFocus();
            } else if (txtnamasupp.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nama Supplier masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnamasupp.requestFocus();
            } else if (txttelp.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Telp masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txttelp.requestFocus();
            } else if (txtalamat.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Alamat masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtalamat.requestFocus();
            } else {
                try {
                    connection = Koneksi.sambung();
                    PreparedStatement statement = null;
                    statement = connection.prepareStatement(insert);
                    statement.setString(1, kodesupp);
                    statement.setString(2, namasupp);
                    statement.setString(3, telp);
                    statement.setString(4, alamat);
                    statement.executeUpdate();
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
            txtkodesupp.setText("");
            txtkodesupp.requestFocus();
        }
        
    }

    private void isitabel () {
        
        Object header [] = {"Kode Supplier", "Nama Supplier", "Telp", "Alamat"};
        
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelSupplier.setModel(model);
        
        String sql = "SELECT * FROM tbsupplier ORDER BY kodesupplier";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4 };
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
     private void isifield () {
        int xrow = tabelSupplier.getSelectedRow();
        String kodesupp = (String) tabelSupplier.getValueAt(xrow, 0);
        String namasupp = (String) tabelSupplier.getValueAt(xrow, 1);
        String telp = (String) tabelSupplier.getValueAt(xrow, 2);
        String alamat = (String) tabelSupplier.getValueAt(xrow, 3);
        
        txtkodesupp.setText(kodesupp);
        txtnamasupp.setText(namasupp);
        txttelp.setText(telp);
        txtalamat.setText(alamat);
    }
    
    private void update () {
        
        String sql = "UPDATE tbsupplier SET namasupplier = '"+txtnamasupp.getText()+"',"
                + " telp= '"+txttelp.getText()+"', alamat = '"+txtalamat.getText()+"'"
                + "WHERE kodesupplier = '"+txtkodesupp.getText()+"' ";
        
        if (txtkodesupp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Kode Supplier masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtkodesupp.requestFocus();
        } else if (txtnamasupp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Nama Supplier masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnamasupp.requestFocus();
        } else if (txttelp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Telp masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txttelp.requestFocus();
        } else if (txtalamat.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Alamat masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtalamat.requestFocus();
        } else {
            try {
                connection = Koneksi.sambung();
                PreparedStatement statement = null;
                statement = connection.prepareStatement(sql);
                Statement stm = connection.createStatement();
                stm.executeUpdate(sql);
                stm.close();
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
            JOptionPane.showMessageDialog(null,"Data berhasil Di Update",
                        "Informasi",JOptionPane.INFORMATION_MESSAGE);
            clear();
            isitabel();

        }
    }
    
    private void delete () {
        
        String sql = "DELETE FROM tbsupplier WHERE kodesupplier = '"+txtkodesupp.getText()+"' ";
        
        if (txtkodesupp.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Kode Supplier masih kosong", "Informasi",
                    JOptionPane.WARNING_MESSAGE);
            txtkodesupp.requestFocus();
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
    
    private void isicari () {
        
        Object header [] = {"Kode Supplier", "Nama Supplier", "Telp", "Alamat"};
        Object row = new Object();
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelSupplier.setModel(model);
        
        String sql = "SELECT * FROM tbsupplier WHERE kodesupplier = '"+txtkodesupp.getText()+"' ";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4 };
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void search () {
        
        ValidasiSupplier caridata = new ValidasiSupplier();
        caridata.validasi_supp(txtkodesupp.getText());
        if (caridata.xkode=="") {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan.!!!", "informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtkodesupp.requestFocus();
        } else {
            isicari();
        }
        
    }
    
    private void export () {
        
        final String sql = "SELECT * FROM tbsupplier ORDER BY kodesupplier;" ;
        
        PreparedStatement statement = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Tabel_Supplier");
            HSSFRow rowhead = sheet.createRow((short)0);
            
            HSSFCellStyle myStyle = workbook.createCellStyle();           
            myStyle.setFillPattern(HSSFCellStyle.BORDER_MEDIUM );
            myStyle.setFillForegroundColor(new HSSFColor.BLACK().getIndex());
             myStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
	    
	    HSSFFont font = workbook.createFont();
            font.setColor(IndexedColors.BLACK.getIndex());
            myStyle.setFont(font);
                        
            Cell c0 = rowhead.createCell(0);
            c0.setCellValue("KODE.SUPPLIER");
            c0.setCellStyle(myStyle);
            
            Cell c1 = rowhead.createCell(1);
            c1.setCellValue("NAMA.SUPPLIER");
            c1.setCellStyle(myStyle);
            
            Cell c2 = rowhead.createCell(2);
            c2.setCellValue("TELP");
            c2.setCellStyle(myStyle);
            
            Cell c3 = rowhead.createCell(3);
            c3.setCellValue("ALAMAT");
            c3.setCellStyle(myStyle);      
                       
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int i = rs.getRow();
                HSSFRow row = sheet.createRow((short) i);
                row.createCell(0).setCellValue(rs.getString("kodesupplier"));
                row.createCell(1).setCellValue(rs.getString("namasupplier"));
                row.createCell(2).setCellValue(rs.getString("telp"));
                row.createCell(3).setCellValue(rs.getString("alamat"));
                i++;
            }
                JFileChooser pilih = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Excel File", "xls");
                pilih.setFileFilter(filter);
                int value = pilih.showSaveDialog(null);
                if (value == JFileChooser.APPROVE_OPTION) {
                    File file = new File(pilih.getSelectedFile()+".xls");
                    String yemi = file.getPath();
                    FileOutputStream fileOut = new FileOutputStream(yemi);
                    workbook.write(fileOut);
                    fileOut.close();
                        JOptionPane.showMessageDialog(null, "Data Berhasil Di Export",
                            "Informasi", JOptionPane.INFORMATION_MESSAGE);
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(panelCustomer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(panelCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void print () {
         
          try { 
              File file = new File("src/Report/Data_Supplier.jrxml");
              
              JasperDesign jasperDesign = new JasperDesign();
              jasperDesign = JRXmlLoader.load(file);
              
              Map parameter = new HashMap();
              parameter.clear();
              
              JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
              JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                      parameter, Koneksi.sambung());
              JasperViewer.viewReport(jasperPrint, false); 
          } catch (Exception e) { 
              e.printStackTrace(); 
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

        panelBackground = new Component.PanelBackground();
        PanelTabel = new Component.PanelTransparan();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelSupplier = new javax.swing.JTable();
        btnexport = new Component.Tombol_Master();
        tombol_Master1 = new Component.Tombol_Master();
        epart = new javax.swing.JLabel();
        panelSupp = new Component.PanelTransparan();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txttelp = new javax.swing.JTextField();
        txtkodesupp = new javax.swing.JTextField();
        txtalamat = new javax.swing.JTextField();
        txtnamasupp = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnCancel = new Component.Tombol_Master();
        btnInput = new Component.Tombol_Master();
        btnUpdate = new Component.Tombol_Master();
        btnDelete = new Component.Tombol_Master();
        btnSearch = new Component.Tombol_Master();
        btnClear = new Component.Tombol_Master();
        jLabel4 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 640));
        setPreferredSize(new java.awt.Dimension(800, 640));
        setLayout(new java.awt.BorderLayout());

        panelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelTabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelSupplier.setBackground(new java.awt.Color(0, 153, 153));
        tabelSupplier.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tabelSupplier.setForeground(new java.awt.Color(255, 255, 255));
        tabelSupplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelSupplier.setSelectionBackground(new java.awt.Color(0, 255, 204));
        tabelSupplier.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tabelSupplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSupplierMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelSupplier);

        PanelTabel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 290));

        btnexport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/xls-24.png"))); // NOI18N
        btnexport.setText("Export");
        btnexport.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnexport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnexportActionPerformed(evt);
            }
        });
        PanelTabel.add(btnexport, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 110, 30));

        tombol_Master1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/print-24.png"))); // NOI18N
        tombol_Master1.setText("Print");
        tombol_Master1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tombol_Master1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tombol_Master1ActionPerformed(evt);
            }
        });
        PanelTabel.add(tombol_Master1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 100, 30));

        panelBackground.add(PanelTabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 740, 340));

        epart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/1Epart.png"))); // NOI18N
        panelBackground.add(epart, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 0, -1, -1));

        panelSupp.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Alamat :");
        panelSupp.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 100, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nama Supplier :");
        panelSupp.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 100, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Telp :");
        panelSupp.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 100, -1));

        txttelp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txttelp.setOpaque(false);
        txttelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txttelpActionPerformed(evt);
            }
        });
        panelSupp.add(txttelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 230, 30));

        txtkodesupp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtkodesupp.setOpaque(false);
        txtkodesupp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtkodesuppActionPerformed(evt);
            }
        });
        txtkodesupp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtkodesuppKeyTyped(evt);
            }
        });
        panelSupp.add(txtkodesupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 230, 30));

        txtalamat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtalamat.setOpaque(false);
        txtalamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtalamatKeyTyped(evt);
            }
        });
        panelSupp.add(txtalamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 450, 30));

        txtnamasupp.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnamasupp.setOpaque(false);
        txtnamasupp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnamasuppKeyTyped(evt);
            }
        });
        panelSupp.add(txtnamasupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 370, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Kode Supplier :");
        panelSupp.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, -1));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel_file-24.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelSupp.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 110, 30));

        btnInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_file-24.png"))); // NOI18N
        btnInput.setText("Input");
        btnInput.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputActionPerformed(evt);
            }
        });
        panelSupp.add(btnInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 20, 110, 30));

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit_file-24.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        panelSupp.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 110, 30));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_file-24.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        panelSupp.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 110, 30));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/view_file-24.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        panelSupp.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, 110, 30));

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser-24.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        panelSupp.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 110, 30));

        panelBackground.add(panelSupp, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 740, 220));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Data Supplier");
        panelBackground.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 170, -1));

        add(panelBackground, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txttelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txttelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txttelpActionPerformed

    private void btnexportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnexportActionPerformed
        export();
    }//GEN-LAST:event_btnexportActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputActionPerformed
        input();
    }//GEN-LAST:event_btnInputActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void txtkodesuppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtkodesuppActionPerformed
        search();
    }//GEN-LAST:event_txtkodesuppActionPerformed

    private void tabelSupplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSupplierMouseClicked
        int x = evt.getClickCount();
        if (x==2) {
            isifield();
        }
    }//GEN-LAST:event_tabelSupplierMouseClicked

    private void txtkodesuppKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtkodesuppKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtkodesuppKeyTyped

    private void txtnamasuppKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamasuppKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnamasuppKeyTyped

    private void txtalamatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtalamatKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtalamatKeyTyped

    private void tombol_Master1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombol_Master1ActionPerformed
        print();
    }//GEN-LAST:event_tombol_Master1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Component.PanelTransparan PanelTabel;
    private Component.Tombol_Master btnCancel;
    private Component.Tombol_Master btnClear;
    private Component.Tombol_Master btnDelete;
    private Component.Tombol_Master btnInput;
    private Component.Tombol_Master btnSearch;
    private Component.Tombol_Master btnUpdate;
    private Component.Tombol_Master btnexport;
    private javax.swing.JLabel epart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private Component.PanelBackground panelBackground;
    private Component.PanelTransparan panelSupp;
    private javax.swing.JTable tabelSupplier;
    private Component.Tombol_Master tombol_Master1;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtkodesupp;
    private javax.swing.JTextField txtnamasupp;
    private javax.swing.JTextField txttelp;
    // End of variables declaration//GEN-END:variables
}
