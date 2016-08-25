package model;

import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 *
 * @author Ondrej
 */
public class Solver{

  private double tolerance; //nejmensi interval pro hledani
  private int maxIterations; //maximalni pocet iteraci
  private int perfIter = 0; //provedeny pocet kroku

  /*=================== FUNKCE PROGRAMU ============================*/
  public Solver(){
    this.tolerance = 0.000_001;
    this.maxIterations = 50;
  }

  public Solver(double tolerance){
    this.tolerance = tolerance;
    this.maxIterations = 50;
  }

  public Solver(int maxIterations){
    this.maxIterations = maxIterations;
    this.tolerance = 0.000_001;
  }

  public Solver(double tolerance, int maxIterations){
    this.tolerance = tolerance;
    this.maxIterations = maxIterations;
  }

  public int getPerfIter(){
    return perfIter;
  }

  public void setPerfIter(int perfIter){
    this.perfIter = perfIter;
  }

  public double getTolerance(){
    return tolerance;
  }

  public int getMaxIterations(){
    return maxIterations;
  }

  public void setTolerance(double tolerance){
    if(tolerance > 0){
      this.tolerance = tolerance;
    }
  }

  public void setMaxIterations(int maxIterations){
    if(maxIterations > 0){
      this.maxIterations = maxIterations;
    }
  }

  /*=================== FUNKCE MATEMATICKE ============================*/
  //vraci hodnotu funkce
  private double getValue(double x){
    double exp;
    exp = Math.pow(3, x) + Math.pow(x, 3) - 200;
    return exp;
  }

  public double doBisection(double x1, double x2, JTextArea txtBox, JLabel vysledek, JLabel iterace) throws Exception{
    int iterNum = 1;
    /* kolikrat byla provedena iterace bisekce */
    double f1, f2, fmid;
    /* funkcni hodnoty */
    double mid = 0;
    /* novy bod pro vyhodnoceni */
 /* definice formatu pro vypis */
    DecimalFormat df = new DecimalFormat("0.0000000");
    System.out.println("Iterace #\tL\tP\t\tSTRED\t\tTOLERANCE");
    txtBox.append("Iterace #\tL\tP\tSTRED\tTOLERANCE\n");

    do{
      f1 = getValue(x1); // vyhodnoceni funkce v krajnich bodech
      f2 = getValue(x2);
      if(f1 * f2 > 0){   // nemuze provadet bisekci
        System.out.println("V zadanem intervalu se nenachazi reseni");
        throw new Exception("V zadanem intervalu se nenachazi reseni");
      }
      mid = (x1 + x2) / 2;  // prostredni hodnota intervalu
      fmid = getValue(mid); // vyhodnoceni funkce v prostrednim bode
      String tmp = iterNum + "\t" + df.format(x1) + "\t" + df.format(x2) + "\t" + df.format(mid) + "\t" + df.format(fmid);
      System.out.println(tmp);
      txtBox.append(tmp + "\n");
      vysledek.setText(Double.toString(mid));
      iterace.setText(Integer.toString(iterNum));
      if(fmid * f1 < 0) // urceni dalsiho intervalu pro bisekci
      {
        x2 = mid;
      }else{
        x1 = mid;
      }
      Thread.sleep(200);
      iterNum++;          // pricteni iterace
    }while(Math.abs(x1 - x2) / 2 >= tolerance && Math.abs(fmid) > tolerance && iterNum <= maxIterations);
    // kontrola dosazeni tolerance a poctu iteraci
    this.perfIter = iterNum - 1;
    vysledek.setText(Double.toString(mid));
    iterace.setText(Integer.toString(this.perfIter));
    num.Menu.first = true;
    return mid;
  }

  public double doSectant(double x0, double x1, JTextArea txtBox, JLabel vysledek, JLabel iterace) throws Exception{
// Local variables
    double x, // Calculated value of x at each iteration
            f0, // Function value at x0
            f1, // Function value at x1
            fx, // Function value at calculated value of x
            root; // Root, if within desired tolerance
// Set initial function values
    f0 = this.getValue(x0);
    f1 = this.getValue(x1);
    System.out.println("\n" + "n  \t"
            + " xn         \t"
            + " f(xn)         \t"
            + " xn+1         \t"
            + " f(xn+1)     \t"
            + " xn+1 - xn     \t"
            + " f(xn+1) - f(xn)");

    txtBox.append("n  \t"
            + " xn         \t"
            + " f(xn)         \t"
            + " xn+1         \t"
            + " f(xn+1)     \t"
            + " xn+1 - xn     \t"
            + " f(xn+1) - f(xn)\n");

    //System.out.println("---------------------------------------------------------------------------------------------------------------");
    DecimalFormat df = new DecimalFormat("0.00000");

// Loop for finding root using Secant Method
    for(int i = 0; i < this.maxIterations; i++){
      x = x1 - f1 * ((x1 - x0) / (f1 - f0));
      fx = this.getValue(x);
      x0 = x1;
      x1 = x;
      f0 = f1;
      f1 = fx;

      System.out.println(i + "\t "
              + df.format(x0) + "\t "
              + df.format(this.getValue(x0)) + "\t "
              + df.format(x1) + "\t "
              + df.format(this.getValue(x1)) + "\t "
              + df.format(x1 - x0) + "\t "
              + df.format(this.getValue(x1) - this.getValue(x0)) + "\t ");

      txtBox.append(i + "\t "
              + df.format(x0) + "\t "
              + df.format(this.getValue(x0)) + "\t "
              + df.format(x1) + "\t "
              + df.format(this.getValue(x1)) + "\t "
              + df.format(x1 - x0) + "\t "
              + df.format(this.getValue(x1) - this.getValue(x0)) + "\t \n");
      vysledek.setText(Double.toString(x1));
      iterace.setText(Integer.toString(i));
      Thread.sleep(200);
// Check whether calculated value is within tolerance
      if(Math.abs(x1 - x0) < this.tolerance){
        root = x1;
        vysledek.setText(Double.toString(root));
        iterace.setText(Integer.toString(i));
        num.Menu.second = true;
        return root;
      } // end if
    } // end for
    return x1;
  } // end SecantMethod()

  public static void main(String[] args) throws Exception{
    /*
    Solver s = new Solver();
    double result = s.doBisection(0, 5);
    System.out.println();
    System.out.println("===================================================================================");
    System.out.println();
    System.out.print("3^x=200-x^3");
    System.out.print("\t x = " + result);
    System.out.println("\t Pocet iteraci: " + s.getPerfIter());
    System.out.println();
    System.out.println("===================================================================================");

    System.out.println(s.doSectant(0, 3));
     */

  }

}
