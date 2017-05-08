package implementationB;
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

      board.x_matrix[0][0] = 31;
      int total = 31;
      int incre = 31;
      for(int x = 0; x < 8; x++)
      {
            for(int y = 0; y < 8; y++)
            {
              board.x_matrix[x][y] = total;
              board.y_matrix[y][x] = total; 
            }
        total += 62;
      }

      board.list.add("R1");
      board.list.add("R2");
      board.list.add("R3");
      board.list.add("R4");
      board.list.add("R5");
      board.list.add("R6");
      board.list.add("R7");
      board.list.add("R8");

      board.matrix[0][1] = "R1";
      board.matrix[0][3] = "R2";
      board.matrix[0][5] = "R3";
      board.matrix[0][7] = "R4";
      board.matrix[1][0] = "R5";
      board.matrix[1][2] = "R6";
      board.matrix[1][4] = "R7";
      board.matrix[1][6] = "R8";

      board.matrix[7][0] = "B1";
      board.matrix[7][2] = "B2";
      board.matrix[7][4] = "B3";
      board.matrix[7][6] = "B4";
      board.matrix[6][1] = "B5";
      board.matrix[6][3] = "B6";
      board.matrix[6][5] = "B7";
      board.matrix[6][7] = "B8";
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