/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import View.ViewLogin;
import java.io.BufferedReader;
import java.io.FileReader;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import java.sql.*;
import javax.swing.JOptionPane;
import View.ViewUpload;
import View.ViewRegister;
import java.io.IOException;

/**
 *
 * @author mfaja
 */
public class MainModel {
    Update update;
    String file;
    public Connection koneksi;
    public Statement statement;
    
    private String username;
    private String password;
    
    
    
    private String DBurl = "jdbc:mysql://localhost/projectakhir";
    private String DBusername = "root";
    private String DBpassword = "";
    
    String filepath = "C:\\Users\\mfaja\\OneDrive\\Documents\\Prak PBO\\Project Akhir\\DataPenjualan.csv";
    
    int batchSize = 20;
    
    
    public MainModel(String file) {
        this.file = file;
    }

    public MainModel() {
        
    }
    
    public void setUpdate(Update up){
        this.update = up;
    }
    
    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void clearUsername(String username) {
        this.username = username;
        update.clearUser(this);
    }

    public void clearPassword(String password) {
        this.password = password;
        update.clearPass(this);
    }
    
    public void clearUser(){
        clearUsername("");
    }
    
    public void clearPass(){
        clearPassword("");
    }

    public void Connector() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            koneksi = (Connection) DriverManager.getConnection(DBurl,DBusername,DBpassword);
            System.out.println("Koneksi Berhasil");
        }catch(Exception ex){
            System.out.println("Koneksi gagal");
        }
    }
    
    public void loginData(Controller cl,ViewLogin lg){
        if(this.username.isEmpty() || this.password.isEmpty()){
            JOptionPane.showMessageDialog(null,  "Anda belum memasukkan Input!", "Empty Field", JOptionPane.WARNING_MESSAGE);
        } else if(this.username.isBlank() || this.password.isBlank()){
            JOptionPane.showMessageDialog(null,  "Input tidak boleh blank (Space)!", "Blank Field", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                String pass="";
                String user="";
                String query = "SELECT * FROM `users` WHERE `username` = '"+this.username+"'";
                Connector();
                statement = koneksi.createStatement();
                ResultSet resultSet  = statement.executeQuery(query);
                while (resultSet.next()){
                    user = resultSet.getString("username");
                    pass = resultSet.getString("password");
                }

                if (!user.equals("")){
                    if(this.password.equals(pass)){
                        lg.setVisible(false);
                        ViewUpload h = new ViewUpload();
                        h.setVisible(true);
                        h.pack();
                        h.setLocationRelativeTo(null);
                        h.setDefaultCloseOperation(ViewUpload.EXIT_ON_CLOSE);
                    } else  {
                        JOptionPane.showMessageDialog(null,  "Password Salah!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null,  "Username Tidak Ditemukan", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            } catch (SQLException ex){
                JOptionPane.showMessageDialog(null,ex,"Warning!", JOptionPane.WARNING_MESSAGE);

            } catch (NullPointerException ex){
                JOptionPane.showMessageDialog(null,  "Input yang dimasukkan salah!", "Input Error", JOptionPane.WARNING_MESSAGE);

            }catch (Exception ex){
                System.out.println(ex);
            }finally {
                cl.clearInput();
            }
        }
    }
    
    
    
    public void inputData(Controller cl){
        try{
            this.Connector();
            koneksi.setAutoCommit(false);
            String sql = "INSERT INTO `datapenjualan`(`tanggal`, `kode_bln`, `NoInvoice`, `NoDeliveryOrder`, `totalpenjualan`, `Discount`, `PPN`, `Total`) VALUES (?,?,?,?,?,?,?,?);";
            
            PreparedStatement statement = koneksi.prepareStatement(sql);
            
            statement = koneksi.prepareStatement(sql);
            
            BufferedReader lineReader = new BufferedReader(new FileReader(filepath));
            String lineText = null;
            int count = 0;
            
            lineReader.readLine();
            while((lineText = lineReader.readLine()) != null){
                String[] data = lineText.split(",");
                String tanggal = data[0];
                String kode = data[1];
                String NoInvoice = data[2];
                String NoDeliveryOrder = data[3];
                String totalpenjualan = data[4];
                String Discount = data[5];
                String PPN = data[6];
                String Total = data[7];
                
                statement.setString(1,tanggal);
                statement.setString(2,kode);
                statement.setString(3,NoInvoice);
                statement.setString(4,NoDeliveryOrder);
                statement.setString(5,totalpenjualan);
                statement.setString(6,Discount);
                statement.setString(7,PPN);
                statement.setString(8,Total);
                
                statement.addBatch();
                if(count%batchSize==0){
                    statement.executeBatch();
                }
            }
            lineReader.close();
            statement.executeBatch();
            koneksi.commit();
            koneksi.close();
            
        }catch(Exception ex){
            System.out.println(ex);
        } finally{
            cl.clearInput();
        }
    }

    public void registerData(Controller cl){
        try {
            if(this.username.isEmpty() || this.password.isEmpty()){
                JOptionPane.showMessageDialog(null,  "Anda belum memasukkan Input!", "Empty Field", JOptionPane.WARNING_MESSAGE);
            } else if(this.username.isBlank() || this.password.isBlank()){
                JOptionPane.showMessageDialog(null,  "Input tidak boleh blank (Space)!", "Blank Field", JOptionPane.WARNING_MESSAGE);
            } else {
                String query = "INSERT INTO `users`(`id`, `username`, `password`) VALUES (NULL, '"+this.username+"', '"+this.password+"')";
                this.Connector();
                statement = koneksi.createStatement();
                statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null,"Registration Successful");}
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null,ex, "Warning", JOptionPane.WARNING_MESSAGE);
        } finally {
            cl.clearInput();
        }   
    }    
    
        
}
