package Checkers;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Checkers extends JFrame
{
   public Checkers(String title)
   {
      super(title);
      setDefaultCloseOperation(EXIT_ON_CLOSE);

      Board board = new Board();
      board.add(new Checker(CheckerType.RED_REGULAR), 1, 2);
      board.add(new Checker(CheckerType.RED_REGULAR), 1, 4);
      board.add(new Checker(CheckerType.RED_REGULAR), 1, 6);
      board.add(new Checker(CheckerType.RED_REGULAR), 2, 1);
      board.add(new Checker(CheckerType.RED_REGULAR), 2, 3);
      board.add(new Checker(CheckerType.RED_REGULAR), 2, 5);
      board.add(new Checker(CheckerType.RED_REGULAR), 2, 7);
      board.add(new Checker(CheckerType.RED_REGULAR), 1, 8);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 1);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 3);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 5);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 8, 7);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 2);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 4);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 6);
      board.add(new Checker(CheckerType.BLACK_REGULAR), 7, 8);

      board.matrix[0][1] = "R1";
      board.matrix[0][3] = "R2";
      board.x_matrix[0][0] = 31;
      board.y_matrix[0][0] = 31;
      for(int x = 0; x < 8; x++)
      {
            for(int y = 0; y < 8; y++)
            {
                  
            }
            System.out.println();
      }
      board.matrix[0][5] = "R3";
      board.matrix[0][7] = "R4";
      board.matrix[1][0] = "R5";
      board.matrix[1][2] = "R6";
      board.matrix[1][4] = "R7";
      board.matrix[1][6] = "R8";

      board.matrix[7][0] = "R1";
      board.matrix[7][2] = "R1";
      board.matrix[7][4] = "R1";
      board.matrix[7][6] = "R1";
      board.matrix[6][1] = "R1";
      board.matrix[6][3] = "R1";
      board.matrix[6][5] = "R1";
      board.matrix[6][7] = "R1";
      setContentPane(board);
      pack();
      setVisible(true);
   }
      public static void main(String[] args)
   {
      Runnable r = new Runnable()
                   {
                      @Override
                      public void run()
                      {
                         new Checkers("Checkers");
                      }
                   };
      EventQueue.invokeLater(r);
   }
}