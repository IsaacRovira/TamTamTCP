/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;

/**
 *
 * @author Isaac
 */
public class Astm_low {
    
    int dato;
    Asciichars ascii;
    Astm_low(){
        this.dato = -1;
        ascii = new Asciichars();
    }
    
    public void Set_cadena(int dato){
        this.dato = dato;
    }
    public int Get_cadena(){
        return this.dato;
    }
    
    public int WaitENQ(){
        switch((int)dato){            
            case 5: return ascii.ACK;//ENQ
            default: return ascii.NAK;
        }
    }
    public void WaitACK(){
        switch((int)dato){
            case 6: break; //ACK
            case 25: break; //NAK
            default: break; //error
        }
    }
    
    public boolean CheckSum(){
        return true;
    }
    
}
class Asciichars{
    int NUL, SOH, STX, ETX, EOT, ENQ, ACK, BEL, BS, TAB, LF, VT, FF, CR, NAK;
    
    Asciichars(){
        this.NUL = 0x00;
        this.SOH = 0x01;
        this.STX = 0x02;
        this.ETX = 0x03;
        this.EOT = 0x04;
        this.ENQ = 0x05;
        this.ACK = 0x06;
        this.BEL = 0x07;
        this.BS = 0x08;
        this.TAB = 0x09;
        this.LF = 0x10;
        this.VT = 0x11;
        this.FF = 0x12;
        this.CR = 0x13;
        this.NAK = 0x25;
    }
}