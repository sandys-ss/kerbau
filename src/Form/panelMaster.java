/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Form;

import Koneksi.Koneksi;
import Validasi.OnlyDigit;
import Validasi.ValidasiMaster;
import com.mysql.jdbc.Connection;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import net.sf.jasperreports.engine.util.JRLoader;
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
public class panelMaster extends javax.swing.JPanel {

    /**
     * Creates new form panelSupplier
     */
    Connection connection;
   
    
    public panelMaster() {
        initComponents();
        
        txthargabeli.setDocument(new OnlyDigit().getOnlyDigit());
        txthargajual.setDocument(new OnlyDigit().getOnlyDigit());
        txtstock.setDocument(new OnlyDigit().getOnlyDigit());
        
        isitabel();
    
    }
    
    public void addActionListenerCancel (ActionListener l) {
        btnCancel.addActionListener(l);
    }
    
    private void clear () {
        
        txtnopart.setText("");
        txtnamapart.setText("");
        txtstock.setText("");
        txthargabeli.setText("");
        txthargajual.setText("");
        cmbType.setSelectedIndex(-1);
        tabelMaster.clearSelection();
        
    }
    
     private void input () {
        
        String nopart = txtnopart.getText();
        String namapart = txtnamapart.getText();
        String stock = txtstock.getText();
        String type = (String) cmbType.getSelectedItem();
        String hargabeli = txthargabeli.getText();
        String hargajual  = txthargajual.getText();
        
        String insert = "INSERT INTO tbmaster (nopart,namapart,type,stock,"
            + "hargabeli, hargajual) VALUES (?,?,?,?,?,?);" ;
        
        ValidasiMaster valid = new ValidasiMaster();
        valid.validasi_part(nopart);
        
        if (valid.xno == "") {
            
            if (txtnopart.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "No Part masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnopart.requestFocus();
            } else if (txtnamapart.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nama Part masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnamapart.requestFocus();
            } else if (txtstock.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Stock masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtstock.requestFocus();
            } else if (type.equals("")) {
                JOptionPane.showMessageDialog(null, "Type masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            cmbType.requestFocus();
            } else if (txthargabeli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Harga Beli masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txthargabeli.requestFocus();
            } else if (txthargajual.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Harga jual masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txthargajual.requestFocus();
            } else {
                try {
                    connection = Koneksi.sambung();
                    PreparedStatement statement = null;
                    statement = connection.prepareStatement(insert);
                    statement.setString(1, nopart);
                    statement.setString(2, namapart);
                    statement.setString(3, type);
                    statement.setInt(4, Integer.valueOf(stock));
                    statement.setInt(5, Integer.valueOf(hargabeli));
                    statement.setInt(6, Integer.valueOf(hargajual));
                    statement.executeUpdate();
                    statement.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                    JOptionPane.showMessageDialog(null,"Data berhasil Disimpan",
                        "Informasi",JOptionPane.INFORMATION_MESSAGE);
                 clear();
                 //isitabel();
            }    
        } else {
            JOptionPane.showMessageDialog(null,"Data Sudah Ada",
                 "Informasi",JOptionPane.WARNING_MESSAGE);
            txtnopart.setText("");
            txtnopart.requestFocus();
        }
        
    }
     
     
     public void isitabel () {
        
        Object header [] = {"No Part", "Nama Part", "Type", "Stock",
                            "Harga Beli", "Harga Jual"};
   
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelMaster.setModel(model);
        
        String sql = "SELECT * FROM tbmaster ORDER BY nopart";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                String kolom5 = rs.getString(5);
                String kolom6 = rs.getString(6);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4,
                                    kolom5, kolom6};
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
     
     private void isifield () {
        int xrow = tabelMaster.getSelectedRow();
        String nopart = (String) tabelMaster.getValueAt(xrow, 0);
        String namapart = (String) tabelMaster.getValueAt(xrow, 1);
        String type = (String) tabelMaster.getValueAt(xrow, 2);
        String stock = (String) tabelMaster.getValueAt(xrow, 3);
        String hargabeli = (String) tabelMaster.getValueAt(xrow, 4);
        String hargajual = (String) tabelMaster.getValueAt(xrow, 5);
        
        txtnopart.setText(nopart);
        txtnamapart.setText(namapart);
        cmbType.setSelectedItem(type);
        txtstock.setText(stock);
        txthargabeli.setText(hargabeli);
        txthargajual.setText(hargajual);
    }
     
    private void update () {
        
        String sql = "UPDATE tbmaster SET namapart = '"+txtnamapart.getText()+"',"
                + "type= '"+cmbType.getSelectedItem()+"', stock = '"+txtstock.getText()+"',"
                + "hargabeli = '"+txthargabeli.getText()+"', hargajual = '"+txthargajual.getText()+"' "
                + "WHERE nopart = '"+txtnopart.getText()+"' ";
        
        if (txtnopart.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "No Part masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnopart.requestFocus();
            } else if (txtnamapart.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Nama Part masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnamapart.requestFocus();
            } else if (txtstock.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Stock masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtstock.requestFocus();
            } else if (cmbType.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Type masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            cmbType.requestFocus();
            } else if (txthargabeli.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Harga Beli masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txthargabeli.requestFocus();
            } else if (txthargajual.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Harga jual masih Kosong !", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txthargajual.requestFocus();
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
        
        String sql = "DELETE FROM tbmaster WHERE nopart = '"+txtnopart.getText()+"' ";
        
        if (txtnopart.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "No Part masih kosong", "Informasi",
                    JOptionPane.WARNING_MESSAGE);
            txtnopart.requestFocus();
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
        
        Object header [] = {"No Part", "Nama Part", "Type", "Stock",
                            "Harga Beli", "Harga Jual"};
   
        DefaultTableModel model = new DefaultTableModel(null, header) {
            public boolean isCellEditable(int row, int column) {
            return false;
            }
        };
        tabelMaster.setModel(model);
        
        String sql = "SELECT * FROM tbmaster WHERE nopart = '"+txtnopart.getText()+"' ";
        
        try {
            connection = Koneksi.sambung();
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                String kolom1 = rs.getString(1);
                String kolom2 = rs.getString(2);
                String kolom3 = rs.getString(3);
                String kolom4 = rs.getString(4);
                String kolom5 = rs.getString(5);
                String kolom6 = rs.getString(6);
                
                String kolom [] = {kolom1, kolom2, kolom3, kolom4,
                                    kolom5, kolom6};
                
                model.addRow(kolom);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void search () {
        
        ValidasiMaster caridata = new ValidasiMaster();
        caridata.validasi_part(txtnopart.getText());
        if (caridata.xno=="") {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan.!!!", "informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnopart.requestFocus();
        } else {
            isicari();
        }
        
    }
    
     private void export () {
        
        final String sql = "SELECT * FROM tbmaster ORDER BY nopart;" ;
        
        PreparedStatement statement = null;
        try {
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Tabel_Master");
            HSSFRow rowhead = sheet.createRow((short)0);
            
            HSSFCellStyle myStyle = workbook.createCellStyle();           
            myStyle.setFillPattern(HSSFCellStyle.BORDER_MEDIUM );
            myStyle.setFillForegroundColor(new HSSFColor.BLACK().getIndex());
             myStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
	    
	    HSSFFont font = workbook.createFont();
            font.setColor(IndexedColors.BLACK.getIndex());
            myStyle.setFont(font);
                        
            Cell c0 = rowhead.createCell(0);
            c0.setCellValue("NO.PART");
            c0.setCellStyle(myStyle);
            
            Cell c1 = rowhead.createCell(1);
            c1.setCellValue("NAMA.PART");
            c1.setCellStyle(myStyle);
            
            Cell c2 = rowhead.createCell(2);
            c2.setCellValue("TYPE");
            c2.setCellStyle(myStyle);
            
            Cell c3 = rowhead.createCell(3);
            c3.setCellValue("STOCK");
            c3.setCellStyle(myStyle);
            
            Cell c4 = rowhead.createCell(4);
            c4.setCellValue("HARGA.BELI");
            c4.setCellStyle(myStyle);
           
            Cell c5 = rowhead.createCell(5);
            c5.setCellValue("HARGA.JUAL");
            c5.setCellStyle(myStyle);
            
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int i = rs.getRow();
                HSSFRow row = sheet.createRow((short) i);
                row.createCell(0).setCellValue(rs.getString("nopart"));
                row.createCell(1).setCellValue(rs.getString("namapart"));
                row.createCell(2).setCellValue(rs.getString("type"));
                row.createCell(3).setCellValue(rs.getString("stock"));
                row.createCell(4).setCellValue(rs.getString("hargabeli"));
                row.createCell(5).setCellValue(rs.getString("hargajual"));
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
              File file = new File("src/Report/Data_Master.jrxml");
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
     
    public void print2 () {
         
        try {
            String namafile= "src/Report/Data_Master.jasper";
	    File report = new File(namafile);
            
            Map parameter = new HashMap();
            parameter.clear();
            
	    JasperReport jreprt = (JasperReport)JRLoader.loadObjectFromFile(report.getPath());
            JasperPrint jprintt = JasperFillManager.fillReport(jreprt,parameter,Koneksi.sambung());
            JasperViewer.viewReport(jprintt,false);

	} catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Membuka Laporan","Cetak Laporan",
                    JOptionPane.ERROR_MESSAGE);
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
        tabelMaster = new javax.swing.JTable();
        btnExport = new Component.Tombol_Master();
        tombol_Master1 = new Component.Tombol_Master();
        epart = new javax.swing.JLabel();
        panelMaster = new Component.PanelTransparan();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtstock = new javax.swing.JTextField();
        txtnopart = new javax.swing.JTextField();
        txtnamapart = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnCancel = new Component.Tombol_Master();
        btnInput = new Component.Tombol_Master();
        btnUpdate = new Component.Tombol_Master();
        btnDelete = new Component.Tombol_Master();
        btnSearch = new Component.Tombol_Master();
        btnClear = new Component.Tombol_Master();
        jLabel6 = new javax.swing.JLabel();
        txthargabeli = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txthargajual = new javax.swing.JTextField();
        cmbType = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 640));
        setPreferredSize(new java.awt.Dimension(800, 640));
        setLayout(new java.awt.BorderLayout());

        panelBackground.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelTabel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabelMaster.setBackground(new java.awt.Color(0, 153, 153));
        tabelMaster.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        tabelMaster.setForeground(new java.awt.Color(255, 255, 255));
        tabelMaster.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabelMaster.setSelectionBackground(new java.awt.Color(0, 255, 204));
        tabelMaster.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tabelMaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelMasterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelMaster);

        PanelTabel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 740, 290));

        btnExport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/xls-24.png"))); // NOI18N
        btnExport.setText("Export");
        btnExport.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });
        PanelTabel.add(btnExport, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 300, 110, 30));

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

        panelMaster.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Type :");
        panelMaster.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 80, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Nama Part :");
        panelMaster.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 80, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Stock :");
        panelMaster.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 80, -1));

        txtstock.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtstock.setOpaque(false);
        txtstock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtstockActionPerformed(evt);
            }
        });
        panelMaster.add(txtstock, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 60, 30));

        txtnopart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnopart.setOpaque(false);
        txtnopart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnopartActionPerformed(evt);
            }
        });
        txtnopart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtnopartKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnopartKeyTyped(evt);
            }
        });
        panelMaster.add(txtnopart, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 180, 30));

        txtnamapart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtnamapart.setOpaque(false);
        txtnamapart.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnamapartKeyTyped(evt);
            }
        });
        panelMaster.add(txtnamapart, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 300, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("No Part :");
        panelMaster.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 80, -1));

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cancel_file-24.png"))); // NOI18N
        btnCancel.setText("Cancel");
        btnCancel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        panelMaster.add(btnCancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 170, 110, 30));

        btnInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/add_file-24.png"))); // NOI18N
        btnInput.setText("Input");
        btnInput.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputActionPerformed(evt);
            }
        });
        panelMaster.add(btnInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 110, 30));

        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/edit_file-24.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        panelMaster.add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 170, 110, 30));

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/delete_file-24.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        panelMaster.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 110, 30));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/view_file-24.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        panelMaster.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 110, 30));

        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/eraser-24.png"))); // NOI18N
        btnClear.setText("Clear");
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        panelMaster.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 170, 110, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Harga Beli :");
        panelMaster.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 80, -1));

        txthargabeli.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txthargabeli.setOpaque(false);
        txthargabeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txthargabeliActionPerformed(evt);
            }
        });
        panelMaster.add(txthargabeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 150, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Harga Jual :");
        panelMaster.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, 80, -1));

        txthargajual.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txthargajual.setOpaque(false);
        panelMaster.add(txthargajual, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 110, 150, 30));

        cmbType.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        cmbType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "AVANZA", "INNOVA", "RUSH", "FORTUNER" }));
        cmbType.setSelectedIndex(-1);
        panelMaster.add(cmbType, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 30, 150, 30));

        panelBackground.add(panelMaster, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 740, 220));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Data Master");
        panelBackground.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 170, -1));

        add(panelBackground, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtstockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtstockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtstockActionPerformed

    private void txthargabeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txthargabeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txthargabeliActionPerformed

    private void txtnopartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnopartKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnopartKeyTyped

    private void txtnamapartKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnamapartKeyTyped
        evt.setKeyChar(Character.toUpperCase(evt.getKeyChar()));
    }//GEN-LAST:event_txtnamapartKeyTyped

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
       clear();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputActionPerformed
        input();
    }//GEN-LAST:event_btnInputActionPerformed

    private void tabelMasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelMasterMouseClicked
        int x = evt.getClickCount();
        if (x == 2) {
            isifield();
        }
    }//GEN-LAST:event_tabelMasterMouseClicked

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        update();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        search();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtnopartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnopartActionPerformed
        search();
    }//GEN-LAST:event_txtnopartActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        export();
    }//GEN-LAST:event_btnExportActionPerformed

    private void tombol_Master1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tombol_Master1ActionPerformed
        print();
    }//GEN-LAST:event_tombol_Master1ActionPerformed

    private void txtnopartKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnopartKeyReleased
         int x = txtnopart.getText().length();
        if (x > 15) {
            JOptionPane.showMessageDialog(null, "Input terlalu panjang", "Informasi",
                    JOptionPane.INFORMATION_MESSAGE);
            txtnopart.setText("");
        }
    }//GEN-LAST:event_txtnopartKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Component.PanelTransparan PanelTabel;
    private Component.Tombol_Master btnCancel;
    private Component.Tombol_Master btnClear;
    private Component.Tombol_Master btnDelete;
    private Component.Tombol_Master btnExport;
    private Component.Tombol_Master btnInput;
    private Component.Tombol_Master btnSearch;
    private Component.Tombol_Master btnUpdate;
    private javax.swing.JComboBox cmbType;
    private javax.swing.JLabel epart;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private Component.PanelBackground panelBackground;
    private Component.PanelTransparan panelMaster;
    private javax.swing.JTable tabelMaster;
    private Component.Tombol_Master tombol_Master1;
    private javax.swing.JTextField txthargabeli;
    private javax.swing.JTextField txthargajual;
    private javax.swing.JTextField txtnamapart;
    private javax.swing.JTextField txtnopart;
    private javax.swing.JTextField txtstock;
    // End of variables declaration//GEN-END:variables

   
}
