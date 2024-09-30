/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package quickordersystem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DELL
 */
public class QuickOrderSystem {

    /**
     * @param args the command line arguments
     */
    private static boolean isOrderValidated = false;
    private static boolean isPaymentProcessed = false;
    
    public static void main(String[] args) {
        // TODO code application logic here
            Thread orderValidationThread =new OrderValidationThread();
            Thread paymentProcessingThread  = new PaymentProcessingThread();
            Thread shippingThread = new ShippingThread();
            
            orderValidationThread.start();
            paymentProcessingThread.start();
            shippingThread.start();
        
    }
    private static class OrderValidationThread  extends Thread{
    
    @Override
    public void run(){
        
        synchronized (QuickOrderSystem.class) {
            System.err.println("Order validation started.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
              ex.printStackTrace();
            }
            isOrderValidated =true;
            System.err.println("Order validtaion complete.");
            QuickOrderSystem.class.notifyAll();
        }   
        
    }
    
 }
    

 private static  class PaymentProcessingThread extends Thread {

    @Override
    public void run(){
        synchronized (QuickOrderSystem.class) {
            
            try {
                while(!isOrderValidated){
                    System.err.println("Waiting for the order validtaion...");
                    QuickOrderSystem.class.wait();
           
                }
                System.err.println("Payment processing started");
                Thread.sleep(1000);
                isPaymentProcessed =true;
                System.out.println("Payment processing complete.");
                QuickOrderSystem.class.notifyAll();
                } catch (InterruptedException ex) {
                ex.printStackTrace();
                }
        }
        
    }

  }
 private static class ShippingThread extends  Thread{
 
 @Override
 public void run(){
 
     synchronized (QuickOrderSystem.class) {
         
         try {
             while(!isPaymentProcessed){
                 System.err.println("Waiting for payment processing ....");
                 QuickOrderSystem.class.wait();
         
             }
             System.out.println("Shipping order....");
             Thread.sleep(1000);
             System.err.println("Order shipped successfully.");
         } catch (InterruptedException ex) {
             ex.printStackTrace();
         }
     }
 
 
 }
 
 
 
 }
 
}