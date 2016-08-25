/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import num.Menu;

/**
 *
 * @author Ondrej
 */
public class MonteCarlo{

  private DecimalFormat fmt = new DecimalFormat("0.################");
  private double precision = 0.000_0001;
  private double l = 0, r = Math.PI;
  private double result = 2;
  private double approx = 0;
  private int iteration = 0;

  public MonteCarlo(double precision){
    this.precision = precision;
  }

  //vraci hodnotu funkce
  private double getValue(double x){
    return Math.sin(x);
  }

  public double mc(long n, JTextArea txtBox, JLabel vysledek, JLabel iterac){
    double a = this.l;
    double b = this.r;
    double x;
    double sum = 0;
    for(long i = 0; i < n; i++){
      x = Math.random() * (b - a) + a;
      sum += this.getValue(x);
    }
    double approx = (b - a) * sum / n;
    System.out.println("\nMonte Carlo with " + n + " points: " + fmt.format(approx));
    txtBox.append("Monte Carlo s " + n + " body: " + fmt.format(approx) + "\n");
    vysledek.setText(fmt.format(approx));
    iterac.setText(Long.toString(n));
    return approx;
  }

  public void doMonteCarlo(JTextArea txtBox, JLabel vysledek, JLabel iterac){
    do{
      this.iteration++;
      this.approx = this.mc(this.iteration, txtBox, vysledek, iterac);
      try{
        Thread.sleep(100);
      }catch(InterruptedException ex){
        Logger.getLogger(MonteCarlo.class.getName()).log(Level.SEVERE, null, ex);
      }
    }while(Math.abs(this.result - this.approx) > this.precision);
    vysledek.setText(Double.toString(this.approx));
    iterac.setText(Integer.toString(this.iteration));
    System.out.println(this.iteration);
    System.out.println(this.approx);
    Menu.third = true;
  }

  public static void main(String[] args){
    //new MonteCarlo().doMonteCarlo();
  }
}
