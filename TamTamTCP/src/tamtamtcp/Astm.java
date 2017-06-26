/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tamtamtcp;

import java.util.ArrayList;

/**
 *
 * @author Isaac
 */
public class Astm{
    
    ArrayList trama = new ArrayList();
    Asciichars ascii;
    conexion conn;    
    
    Astm(){
        trama = null;
        ascii = new Asciichars();
        this.conn = null;        
    }
    
    Astm(String direccion, int puerto){
        trama = null;
        ascii = new Asciichars();
        this.conn = new conexion(direccion, puerto);
        this.conn.Connectar();        
    }
    
    public void Nueva_trama(){
        trama.clear();
    }
    
    public ArrayList Get_trama(){
        return trama;
    }
    
    public void Add_trama(Object O){
        trama.add(O);
    }
    
    public void Guardar_frame(String cadena){
        trama.add(cadena);
    }
    
    public int Check_ENQ(){
        try{
            if(conn.inStream.available()==0){
                Thread.sleep(250);
                return -3;
            }
            int dato = conn.inStream.read();
            System.out.println(dato + " Leido durante ENQ check");
            if(dato == ascii.ENQ) return 1;
            return 0;
        }catch(Exception e){
            System.out.println(e.getClass() + " " + e.getMessage());
            return -1;
        }
    }
    
    public int RecibirResultado(){
        boolean eot = true;
        //data almacenara los datos del menaje.
        frame data;
        //NACKs is a variable that stores the number of consecutive NACKs
        int nacks = 0;
        //lineNum is a variable that stores the frame number for the NACK cases.
        int lineNum = -1;
        try{
            System.out.println("Enviar: ACK");
            Enviar_dato(ascii.ACK,1,1);
            do{
                int dato;
                data = new frame();
                int i = 0;
                switch(dato = LeerStream(30)){
                    case 4:
                        System.out.println("Recibir datos case 4 (eot)");
                        eot = false;
                        break;
                    case -1:
                        System.out.println("Recibir case -1");
                        return -1;
                    case -2:
                        System.out.println("Recibir case -2");
                        return 0;
                    case 2:
                        //<STX> the start text
                        System.out.println("Recibir datos case 2 (stx)");
                        
                        //Frame number.
                        FrameNum_IN(data);
                        
                        //Data frame
                        DataFrame_IN(data);                        

                        //Checksum
                        CheckSum_IN(data);
                        
                        //End of the frame
                        EndOfFrame_IN(data);
                        
                        
                        System.out.println("Verificar");               
                        if(Verificar_Datos(data, nacks, lineNum)){               //Verificamos que la trama recibida cumple los requisitos ASTM
                            Enviar_dato(ascii.ACK,1,1);
                            //trama.add(FrameToString(data));
                            nacks = 0;
                            lineNum = -1;
                        }else{
                            if(nacks > 6){                                       //6 errores consecutivos. Devolvemos NACK NACK NACK y salimos.
                                Enviar_dato(ascii.NACK, 3, 1);
                                return 0;                                       //Salimos de la función devolviendo 0. Proceso incompleto.
                            }else{                                              //Error en la trama devolvemos NACK, almacenamos el núm de trama y aumentamos en una unidad el valor NACKs.
                                nacks++;
                                lineNum = data.frameNum;
                                Enviar_dato(ascii.NACK,1,1);
                            }
                        }
                        break;
                    default:
                        System.out.print(dato + " Default ");
                        Enviar_dato(ascii.NACK,3,1);
                        return 0;                        
                }
            }while(eot);
            return 1;                                                           //Salimos de la función devolviendo 1; Proceso completado.
        }catch(Exception e){
            System.out.println(e.getClass() + " " + e.getMessage());
            return -1;
        }
    }
    
    public int EnviarResultado(int timeout){
        switch(LeerStream(timeout)){
            case 0: //Time out. Means nothing. Good one. We can send.
                Enviar_dato(ascii.ENQ,1,0);
                switch(LeerStream(30)){
                    case 0: //Time out. No answer. ¡mmmH! Wait 18 sec and try again.
                        return EnviarResultado(18);
                    case 5: //ENQ conflict. Wait 5 sec for ACK.
                        return EnviarResultado(5);                        
                    case -1: //Error reading. Abort.
                        return -1;
                    case 6: //ACK. Goooood! Let's go....
                        return Enviar_frame();
                    default: //What's that?. Send nak nak nak. Wait 30 sec. And try again.
                        Enviar_dato(ascii.NACK,3,0);
                        return EnviarResultado(30);                       
                }
            case -1: //Error reading. We abort.
                return -1;
            case 5: //ENQ. We abort.
                return 0;
            default: //Shit something is not working. Send nak nak nak. Wait 30 sec. And try again.
                Enviar_dato(ascii.NACK,3,0);
                return EnviarResultado(30);
        }
    }

    private int EndOfFrame_IN(frame data){
        int dato;
        System.out.println("End of the frame");
        for(int n = 0; n < data.crlf.length; n++){
            switch(dato=LeerStream(30)){
                case -1: //Error leyendo....
                    return -1;
                case 0: //Time out... cerrar comunicación.
                    return 0;
                case 4: //EOT.... comunicación cerrada.
                    return 4;
                default:
                    if(dato != data.crlf[n]){
                        System.out.println(n + " Error End of the frame " + dato);
                        Enviar_dato(ascii.NACK,3,1);
                        return 0;
                    }                    
            }
        }
        return 1;
    }
    private int CheckSum_IN(frame data){
        System.out.print("\nCheckSum: ");
        int dato;
        for(int n = 0; n < data.checksum.length; n++){
            switch(dato = LeerStream(30)){
                case -1: //Error al leer los datos....
                    return -1;
                case 0: //Timeout.... cerrar comunicación.
                    return 0;
                case 4: //EOT.... comunicación cortada.
                    return 4;
                default:
                    if(!((dato > 47 && dato < 58)||(dato > 64 && dato < 71))){
                        System.out.print("Error " + (char)dato+"\n");
                        Enviar_dato(ascii.NACK,3,1);
                        return 0;
                    }
                    System.out.print((char)dato);
                    data.checksum[n] = dato;
            }
        }
        System.out.println("");
        return 1;
    }
    private int DataFrame_IN(frame data){
        System.out.println("DataFrame");
        int dato, i;
        i=0;
        do{
            switch(dato = LeerStream(30)){
                case -1: //Error_lectura-
                    return -1;
                case 0: //Timeout... cerrar conexión.
                    return 0;
                case 4: //EOT.... cerrar conexión.
                    return 4;
                default:
                    System.out.print(dato + " ");                    
                    if(!(dato > 31 || dato < 127) && (dato != ascii.CR || dato != ascii.LF || dato != ascii.ETX)){
                        System.out.println((char)dato + " Data frame data incorrect");
                        Enviar_dato(ascii.NACK,3,1);
                        return 0;
                    }
                    data.framedata[i] = dato;
                    i++;
                    if(i > data.framedata.length){
                        Enviar_dato(ascii.NACK,3,1);
                        return 0;
                    }
            }
        }while(dato != ascii.ETX);
        return 1;
    }
    private int FrameNum_IN(frame data){
        System.out.print("Frame number: ");
        int dato;
        switch(dato = LeerStream(30)){
            case -1: //Error de lectura
                return -1;
            case 0: //Timeout.... cerrar conexión.
                return 0;
            case 4: //EOT----- cerrar conexión.
                return 4;
            default:
                if(dato < 48 || dato > 55){
                    System.out.print(" Error, dato = " + (char)dato + "\n");
                    Enviar_dato(ascii.NACK,3,1);
                    return 0;
                }else{
                    System.out.println((char)dato);
                    data.frameNum = dato;
                }
        }
        System.out.println("");
        return 1;
    }
    
    private int LeerStream(int timeout){
        int d;
        try{
            Thread.sleep(1);
            while(conn.inStream.available() == 0){
                Thread.sleep(1);
                //if(timeout == timeout) return -2;
            }
            d = conn.inStream.read();
        }catch(Exception e){
            System.out.println(e.getClass() + " " + e.getMessage());
            return -1;
        }
        return d;
    }    
    
    private void Enviar_dato(int dato, int veces, int modo){
        
        try{
            Thread.sleep(10);
            switch (modo){
            case 0:
                for(int n = 0; n < veces; n++){
                    this.conn.outStream.write(dato);
                    
                }
                break;
            case 1:
                for(int n = 0; n < veces; n++){
                    this.conn.outPrintStream.write(dato);
                }
                break;
            default:
                for(int n = 0; n < veces; n++){
                    this.conn.outStream.write(dato);
                }
                break;                
        }
        }catch(Exception e){
            System.out.println(e.getClass() + " " + e.getMessage());
        }
    }
    
    private boolean Verificar_Datos(frame datos, int nacks, int lineNum){        
        if(!Verificar_checksum(datos)) return false;        
        if(nacks > 0){
            return (lineNum == datos.frameNum);
        }else{
            return true;
        }
    }
    
    private boolean Verificar_checksum(frame datos){
        /*
        According to ASTM E-1381 frame cheksum (<STX>1...Data...<CR><ETX>xx<CR><LF>) is
        defined as modulo 256 of ASCII values sum between <STX> not included and <ETX>
        included characters: 1...Data...<CR><ETX>
        */        
        int suma, res;
        String a, b;
        a=b="";
        suma = datos.frameNum;        
        a += ((char)datos.checksum[0])+ "" + ((char)datos.checksum[1]);        
        
        for(int n = 0; n <= Find_Pos_Value(datos, ascii.ETX); n++){
            suma += datos.framedata[n];
        }
        
        res = suma % 256;
        b = String.format("%02X", res);
        
        System.out.println("Checksum: " + a + " / " + b);
        
        return b.equals(a);
    }
    
    private int[] Calcular_Checksum(frame datos){
        int suma, res;
        int[] num = new int[2];
        String a = null;
        suma = ascii.ETX + datos.frameNum;
        for(int n =0; n < Find_Pos_Value(datos, ascii.ETX); n++){
            suma += datos.framedata[n];
        }
        
        a = Integer.toHexString(suma);
        
        for(int n = 0; n < num.length; n++){
            num[n] = (int)a.charAt(n);
        }
        return num;
    }
    
    private frame Generate_frame(String cadena, int framenum){
        frame datos = new frame();
        for(int n=0; n < cadena.length(); n++){
            datos.framedata[n] = (int)cadena.charAt(n);
        }
        datos.checksum = Calcular_Checksum(datos);
        
        return datos;
    }
    
    private int Enviar_frame(){
        int nacks = 0;
        int framenum = 1;
        frame dato;
        for(Object S: trama){            
            dato = Generate_frame(S.toString(), framenum);
            do{
                EnviarFrame(dato);
                switch(LeerStream(30)){
                    case 0: //Time out. Abort sending and restart.
                        break;
                    case -1: //Error reading. Abort and restart.
                        break;
                    case 5: //ACK. Very good. We keep on sending....
                        if(framenum++ == 8){
                        framenum = 0;
                    }
                    nacks = 0;
                    break;
                    case 6: //NACK. Not good. Let's send again the frame. Max 6 times then abort.
                        if(nacks++ == 6) return 0;
                        break;
                    default: //Something went wrong. What's that?. Abort and restart....
                        break;                        
                }
            }while(nacks != 0);
        }
        Enviar_dato(ascii.EOT, 1, 0);
        return 1;        
    }
    
    private void EnviarFrame(frame dato){
        Enviar_dato(dato.stx,1,0);
            Enviar_dato(dato.frameNum,1,0);
            for(int n=0; n < Find_Pos_Value(dato, ascii.ETX); n++){
                Enviar_dato(dato.framedata[n],1,0);
            }
            Enviar_dato(dato.etx,1,0);
            Enviar_dato(dato.checksum[0],1,0);
            Enviar_dato(dato.checksum[1],1,0);
            Enviar_dato(dato.crlf[0],1,0);
            Enviar_dato(dato.crlf[1],1,0);
    }
    
    private int Find_Pos_Value(frame data, int value){
        for(int n = 0; n < data.framedata.length; n++){
            if(data.framedata[n]==value) return n;
        }
        return -1;
    }
    
    private String FrameToString(frame datos){
        String cadena = "";
        for(int n = 1; n < Find_Pos_Value(datos, ascii.ETX); n++){
            cadena += (char)datos.framedata[n];
        }
        return(cadena);
    }
}


class Asciichars{
    int NUL, SOH, STX, ETX, EOT, ENQ, ACK, BEL, BS, TAB, LF, VT, FF, CR, NACK;
    
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
        this.NACK = 0x21;
    }
}

class frame{
    int stx, frameNum, etx;
    int[] checksum, framedata, crlf;
    
    frame(){
        stx = 0;
        frameNum = 0;
        etx = 0;
        this.checksum = new int[2];
        this.framedata = new int[240];
        this.crlf = new int[]{13,10};
    }
}