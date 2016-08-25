/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package num;

/**
 *
 * @author Ondrej
 */
public class NUM{

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args){
          javax.swing.JFrame window = new javax.swing.JFrame("Numerické metody");
          window.setContentPane(new Menu());
          window.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
          window.setResizable(false);
          //window.pack();              // Arrange the components.
          window.setVisible(true);    // Make the window visible.
  }

}
