/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectakhir;

import View.LoadingApp;
import View.ViewLogin;

/**
 *
 * @author mfaja
 */
public class Main extends ViewLogin {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Connector cn = new Connector();
//        cn.inputData();
//        Dashboard d = new Dashboard();
        
        LoadingApp l = new LoadingApp();
        l.setVisible(true);
        l.pack();
        l.requestFocusInWindow();
        l.setLocationRelativeTo(null);
        try{
            for(int x = 0; x<=100; x++){
                Thread.sleep(35);
                LoadingApp.labelbar.setText(Integer.toString(x)+ "%");
                LoadingApp.bar.setValue(x);
            }
        } catch(Exception ex){
            System.out.println(ex);
        } finally{
            l.dispose();
        }
        
        ViewLogin v = new ViewLogin();
        v.setVisible(true);
        v.pack();
        v.requestFocusInWindow();
        v.setLocationRelativeTo(null);
        v.setDefaultCloseOperation(ViewLogin.EXIT_ON_CLOSE);
        
    }
    
}
