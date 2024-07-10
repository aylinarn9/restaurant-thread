/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.threadproject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aylin
 */
public class Restoran {
     private static final int toplamMasaSayisi = 6;
    private static final int toplamGarsonSayisi = 3;
    private static final int AsciSayisi = 2;
    private static final int maksimumMusteriSay = 10;
    Musteri musteri;
     private final List<Integer> masalardakiMusteriler = new ArrayList<>();
    private final List<Integer> beklemeListesi = new ArrayList<>();
Queue<String> masayaOturanMusteri=new LinkedList<>();
ArrayList<Musteri>musteriler=new ArrayList<>();

    public ArrayList<Musteri> getMusteriler() {
        return musteriler;
    }

    public void setMusteriler(ArrayList<Musteri> musteriler) {
        this.musteriler = musteriler;
    }

  
    
      /* private static final int garsonSayisi=3;
    private int siparisAlanGarsonS=0;

    // Restoran restoran=new Restoran();

   
    private final Lock masalarLock = new ReentrantLock();
   /* public void SiparisAl(int garsonNumarasi, int musteriNumarasi){


        try {

            // System.out.println(garsonNumarasi+ " siparis almaya çalışıyor.");
            // Semaphore'dan izin al
            // semaphore.acquire();
            //int doluMasaSayisi=restoran.getDoluMasa();
            garsonSemafor.acquire();
            masalarLock.lock();
            try {

                if (siparisAlanGarsonS < garsonSayisi) {
                    System.out.println("Garson" + garsonNumarasi + "Musteri" + musteriNumarasi+ " in siparişini aldı");
                   // siparisİlet.offer(musteriNumarasi);
                    System.out.println("siparisialınankuyruk: " + siparisİlet);
                    //Thread.sleep(1000);
                    siparisAlanGarsonS++;

                } else {
                    System.out.println("Musteri" + musteriNumarasi + " in siparişi beklemede");
                }


            } finally {

                masalarLock.unlock();

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            garsonSemafor.release();

        }







    }*/
ExecutorService executorAna = Executors.newFixedThreadPool(maksimumMusteriSay);
private int garsonNo;
private int asciNo;

    private int garsonSirasiniKontrolEtme() {

        garsonNo = garsonSira;
        garsonSira = (garsonSira + 1) % toplamGarsonSayisi;
        return garsonNo + 1;
    }
private int asciSirasiniKontrolEtme(){
        asciNo=asciSira;
    asciSira = (asciSira + 1) % AsciSayisi;
    return asciNo + 1;
}

    public int getGarsonNo() {
        return garsonNo;
    }

    public void setGarsonNo(int garsonNo) {
        this.garsonNo = garsonNo;
    }

    public int getAsciNo() {
        return asciNo;
    }

    public void setAsciNo(int asciNo) {
        this.asciNo = asciNo;
    }
  private Semaphore masaSem = new Semaphore(toplamMasaSayisi, true);
    private Semaphore garsonSem = new Semaphore(toplamGarsonSayisi, true);
      private int garsonSira = 0;
    private int asciSira=0;
    private Semaphore asci1Sem = new Semaphore(AsciSayisi, true);
    private Semaphore asci2Sem = new Semaphore(AsciSayisi, true);
    private Semaphore kasaSem = new Semaphore(1, true);

////////////////////////////
public void onceliklimusteriyiEkle(OncelikliMusteri musteri) {
    executorAna.submit(() -> {
        try {
            masaSem.acquire();
            if (onceliklimusteriMasaYerlestirme(musteri)) {
                int garsonNo = garsonSirasiniKontrolEtme();
                garsonSem.acquire();
                oncelikligarsonSiparisAlma(garsonNo, musteri);
                garsonSem.release();

                int asciNo=asciSirasiniKontrolEtme();
                  if (asciNo == 1) {
                        asci1Sem.acquire(); 
                      oncelikliasciyemekHazirlama(asciNo,musteri);
                        asci1Sem.release(); 
                    } else {
                        asci2Sem.acquire(); 
                        oncelikliasciyemekHazirlama(asciNo,musteri);
                        asci2Sem.release(); 
                    }
             
                    garsonSem.acquire();
                    onceliklisiparisiYe(musteri);
                    garsonSem.release();

                    kasaSem.acquire();
                    oncelikliodemeyiAl(musteri);
                    kasaSem.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            masaSem.release();
        }
    });
}

    private boolean onceliklimusteriMasaYerlestirme(OncelikliMusteri musteri) {
        // Masa yerlesme

        System.out.println(musteri.getAd() + " restorana geldi.");
        if (onceliklimasaDoluMu()) {
            System.out.println("Masalar dolu, " + musteri.getAd() + " bekliyor...");
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(musteri.getAd() + " beklemenin ardından restorandan ayrıldı.");
            return false;
        } else {
            System.out.println(musteri.getAd() + " masaya oturdu.");
            masayaOturanMusteri.offer(musteri.getAd());
           // System.out.println("kuyruk"+masayaOturanMusteri);
            return true;
        }

    }
    public boolean onceliklimasaDoluMu() {

        return false;
    }
    public void oncelikligarsonSiparisAlma(int garsonNo,OncelikliMusteri musteri) throws InterruptedException {
        // Garson sipariş
        System.out.println("Garson-" + garsonNo +  musteri.getAd()+ "nin siparisini aldı");
        Thread.sleep(2000); 
        System.out.println("Garson-" + garsonNo + musteri.getAd() + "nin siparişini aşçiya iletti");
    }

    public void oncelikliasciyemekHazirlama(int asciNo,OncelikliMusteri musteri) throws InterruptedException {
        // asci yemek hazirlama
        System.out.println("Asci-" + asciNo + musteri.getAd() +  "nin siparişini hazırlamaya başladı");
        Thread.sleep(3000); 
        System.out.println(musteri.getAd() + " yemeği hazır.");
    }

    public void onceliklisiparisiYe(OncelikliMusteri musteri)  {
        // yemeği yeme kısmı
        System.out.println(musteri.getAd() + " nin siparişi masaya geldi");
        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(musteri.getAd() + " siparişini yedi.");
    }

    public void oncelikliodemeyiAl(OncelikliMusteri musteri)  {
        // bu kısımda ödeme islemi yapıyorum

        System.out.println(musteri.getAd() + " ödemeyi gerçekleştiriyor.");
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(musteri.getAd() + " ödemeyi tamamladı. Masadan ayrıldı.");
    }

/////////////////////////////////////
public void musteriAnaGiris(Musteri musteri) {
    executorAna.submit(() -> {
        try {
            masaSem.acquire();
            if (musteriMasaYerlestirme(musteri)) {
                int garsonNo = garsonSirasiniKontrolEtme();
                garsonSem.acquire();
                garsonSiparisAlma(garsonNo, musteri);
                garsonSem.release();

                int asciNo=asciSirasiniKontrolEtme();
                if(asciNo==1){
                asci1Sem.acquire();
                asciYemekHazirlama(asciNo,musteri);
                asci1Sem.release();
                }else{
                asci2Sem.acquire();
                asciYemekHazirlama(asciNo,musteri);
                asci2Sem.release(); 
                }
     

                garsonSem.acquire();
                siparisiYe(musteri);
                garsonSem.release();


                kasaSem.acquire();
                odemeAl(musteri);
                kasaSem.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            masaSem.release();
        }
    });
}




    public boolean musteriMasaYerlestirme(Musteri musteri)  {
         
        // Masa yerlesme
        System.out.println(musteri.getAd() + " restorana geldi.");
        if (masaDoluMu()) {
            System.out.println("Masalar dolu, " + musteri.getAd() + " bekliyor...");
           // bekleyenKuyruk.offer(musteri.getAd());
           // System.out.println(""+bekleyenKuyruk);
            
            try {
                Thread.sleep(20000);
                
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(musteri.getAd() + " beklemenin ardından restorandan ayrıldı.");
            return false;
        } else {
            System.out.println(musteri.getAd() + " masaya oturdu.");
            masayaOturanMusteri.offer(musteri.getAd());
            
           // System.out.println("kuyruk"+masayaOturanMusteri);
            return true;
        }

    }

    public Queue<String> getMasayaOturanMusteri() {
        return masayaOturanMusteri;
    }

    public void setMasayaOturanMusteri(Queue<String> masayaOturanMusteri) {
        this.masayaOturanMusteri = masayaOturanMusteri;
    }
    public boolean masaDoluMu() {

        return false;
    }
    
    public void garsonSiparisAlma(int garsonNo,Musteri musteri) throws InterruptedException {
        // Garson sipariş
        System.out.println("Garson-" + garsonNo + musteri.getAd()+ "nin siparisini aldı");
        musteriler.add(musteri);
        
        Thread.sleep(2000); // Sipariş alma süresi
        System.out.println("Garson-" + garsonNo + musteri.getAd() + " nin siparişini aşçıya iletti ");
    }



    public void asciYemekHazirlama(int asciNo,Musteri musteri)  {
          try {
              // asci yemek hazirlama
              System.out.println("Asci-" + asciNo + musteri.getAd() + "nin siparişini hazırlamaya başladı");
              Thread.sleep(3000); // Yemek hazırlama süresi
              System.out.println(musteri.getAd() + " nin yemeği hazır.");
          } catch (InterruptedException ex) {
              Logger.getLogger(Restoran.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    public void siparisiYe(Musteri musteri)  {
        // yemeği yeme kısmı
        System.out.println(musteri.getAd() + " nin siparişi masaya geldi");
        try {
            Thread.sleep(3000); // Yemek yeme süresi
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(musteri.getAd() + " siparişini yedi");
    }

    public void odemeAl(Musteri musteri) {
        // bu kısımda ödeme islemi yapıyorum
        System.out.println(musteri.getAd() + " ödemeyi gerçekleştiriyor.");
        try {
            Thread.sleep(1000); // Ödeme alma süresi
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(musteri.getAd() + " ödemeyi tamamladı. Masadan ayrıldı.");
    }
}
