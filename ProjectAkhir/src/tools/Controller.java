/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import tools.MainModel;
import View.ViewLogin;
import View.ViewRegister;

/**
 *
 * @author mfaja
 */
public class Controller {
    MainModel MM ;
    String user, pass;
    
    public void setMo(MainModel mo){
        this.MM = mo;
    }

    public Controller() {
//    MM = new MainModel();
    }
    
    public void clearInput(){
        MM.clearUser();
        MM.clearPass();
    }
    
    public void clearUser(){
        MM.clearUser();
    }
    
    public void clearPass(){
        MM.clearPass();
    }
    
    public void inputUser(ViewRegister vr){
        this.user = vr.getUser();
        this.pass = vr.getPass();
        MM.setUsername(this.user);
        MM.setPassword(this.pass);
        MM.registerData(this);
    }
    
    public void loginData(ViewLogin vl){
        this.user = vl.getUser();
        this.pass = vl.getPass();
        MM.setUsername(this.user);
        MM.setPassword(this.pass);
        MM.loginData(this, vl);
    }
}
