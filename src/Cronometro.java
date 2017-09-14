/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CARLOS
 */

 import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

public class Cronometro implements Runnable
{
    public static int onoff = 0;
    JLabel tiempo;
    Thread hilo;
    boolean cronometroActivo,cronometroInactivo;
    public Cronometro()
    {    
        tiempo = new JLabel( "00:00" );
        tiempo.setFont( new Font( Font.SERIF, Font.BOLD, 30 ) );
        tiempo.setHorizontalAlignment( JLabel.CENTER );
        tiempo.setForeground( Color.BLUE );
        tiempo.setOpaque( true );
    }

    public void run(){
        Integer minutos = 0 , segundos = 0, milesimas = 0;
        String min="", seg="";
        try
        {
            
            while( cronometroActivo )
            {
                Thread.sleep( 4 );
                
                milesimas += 4;

                if( milesimas == 1000 )
                {
                    milesimas = 0;
                    segundos += 1;
                    
                    if( segundos == 60 )
                    {
                        segundos = 0;
                        minutos++;
                    }
                }
           
                if( minutos < 10 ) min = "0" + minutos;
                else min = minutos.toString();
                if( segundos < 10 ) seg = "0" + segundos;
                else seg = segundos.toString();
                          
                tiempo.setText( min + ":" + seg  );
            }
        }catch(Exception e){}
    
        tiempo.setText( "00:00" );
      
         if(cronometroInactivo ==false){
         tiempo.setText( "00:00" );
         cronometroActivo = true;
        }
    }

    public void iniciarCronometro() {
        cronometroActivo = true;
        hilo = new Thread( this );
        hilo.start();
    }
    
    public void pararCronometro(){
       
        cronometroActivo = true;   
        hilo.suspend();
    }

    public JLabel getTiempo() {
        return tiempo;
    }

    public void setTiempo(JLabel tiempo) {
        this.tiempo = tiempo;
    }

   
}
