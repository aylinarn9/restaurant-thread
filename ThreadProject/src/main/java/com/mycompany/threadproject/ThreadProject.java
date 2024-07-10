/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.threadproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Aylin
 */
public class ThreadProject extends JFrame {

    JButton OnayButon;
  JButton masakontrolButton;
  //Restoran restoran;
    public static void main(String[] args) {
           SwingUtilities.invokeLater(new Runnable() {
            @Override
           public void run() {
              ThreadProject yazlabproje = new ThreadProject();
            }
        });
        
        
     
    }
   public static void anaSecim(){
         int i;
        Random random=new Random();
        int randomSayi= random.nextInt(10)+1;

        System.out.println("toplam"+randomSayi+"kişi");
        int randomSayi2= random.nextInt(randomSayi)+1;
        Restoran restoran = new Restoran();

        System.out.println(""+ randomSayi2+" normal musteri " +(randomSayi-randomSayi2)+"oncelikliMusteri restorana giris yaptı");

        // System.out.println(""+ randomSayi+" normal musteri " +(10-randomSayi)+"oncelikliMusteri restorana giris yaptı");

        for ( i = randomSayi2+1; i <=randomSayi; i++) {

            OncelikliMusteri onceliklimusteri = new OncelikliMusteri("OncelikliMusteri" + i);
            restoran.onceliklimusteriyiEkle(onceliklimusteri);


        }

        for ( i = 1; i <=randomSayi2; i++) {

            Musteri musteri = new Musteri("Musteri" + i);
            restoran.musteriAnaGiris(musteri);
        }

        restoran.executorAna.shutdown();
   } 
    
    public ThreadProject() {
        JFrame f = new JFrame();
         
        JButton garsonbuton;
        garsonbuton = new JButton("GARSON");
        
        JButton ascibuton;
        ascibuton = new JButton("AŞÇI");
        
        JButton kasabuton;
        kasabuton = new JButton("KASA");
        
  JButton OnayButon;
  OnayButon=new JButton("OnayButon");

        garsonbuton.setBounds(50, 50, 200, 50);
        ascibuton.setBounds(300, 50, 200, 50);
        kasabuton.setBounds(550, 50, 200, 50);
        OnayButon.setBounds(300, 250, 200, 50);

        f.add(garsonbuton);
        f.add(ascibuton);
        f.add(kasabuton);
         f.add(OnayButon);
        f.setSize(800, 500);
        f.setLayout(null);
        f.setVisible(true);

       
        setTitle("Thread");
        setSize(900, 600);
        setLocationRelativeTo(null);                                                
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
       //  OnayButon = new JButton("OnayButon");
       //  masakontrolButton = new JButton("MasaKontrol");
      
       // add(OnayButon);
      //  add(masakontrolButton);
 

       
       // OnayButon.setBounds(600, 50, 200, 50);
       // masakontrolButton.setBounds(300, 50, 200, 50);
      

        OnayButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anaSecim();
              
            }
        });
        
           garsonbuton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
       Restoran rs=new Restoran();
                 //anaSecim();
                 if(rs.masaDoluMu()==true){
                     while(!rs.masayaOturanMusteri.isEmpty()){
                          System.out.println("masalardaki musteriler:"+rs.masayaOturanMusteri.poll());
                     }
                    
                 }
                 
               
            }
        });
            
           /* masakontrolButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
            
              

            }
        });  
           
               while(!rs.getOturanMusteriList().isEmpty()){
        
          
        for(int j=1;j<4;j++){
               Masa ms=new Masa(3);
               Musteri eleman=rs.getOturanMusteriList().pop();
          int n= eleman.getMusteriNumarasi();
                 Garson garson=new Garson(j,n,ms);
                 Thread garsonthread=new Thread(garson);
               garsonthread.start();
   
        } 
    }
      
            */
        

    }
  
}
