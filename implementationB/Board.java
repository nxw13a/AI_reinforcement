package implementationB;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.*;
import java.security.SecureRandom;
import javax.swing.JComponent;

public class Board extends JComponent
{
   // dimension of checkerboard square (25% bigger than checker)

   private final static int SQUAREDIM = (int) (Checker.getDimension() * 1.25);

   // dimension of checkerboard (width of 8 squares)

   private final int BOARDDIM = 8 * SQUAREDIM;

   // preferred size of Board component

   private Dimension dimPrefSize;

   // dragging flag -- set to true when user presses mouse button over checker
   // and cleared to false when user releases mouse button

   private boolean inDrag = false;
   private boolean pick = true;
   private boolean still_eating = false;

   // displacement between drag start coordinates and checker center coordinates

   private int deltax, deltay;

   // reference to positioned checker at start of drag

   private PosCheck posCheck;

   // center location of checker at start of drag

   private int oldcx, oldcy;
   private String[] list = {"R1","R2","R3","R4","R5","R6","R7","R8"};
   private ArrayList<String> list_RED = new ArrayList<String>();
   private ArrayList<String> list_BLACK = new ArrayList<String>();
   private ArrayList<Integer> poss_moveX = new ArrayList<Integer>();
   private ArrayList<Integer> poss_moveY = new ArrayList<Integer>();


   // list of Checker objects and their initial positions

   private List<PosCheck> posChecks;

   public int[][] x_matrix = new int[8][8];
   public int[][] y_matrix = new int[8][8];

   public String[][] matrix = new String[8][8];

   private boolean check_move(int number)
   { 
      if(number < 1)
          return false;
      for(int x = 0 ; x < list_RED.size(); x++)
      {
         if(list_RED.get(x) == list[number - 1])
           return false;
      }
      String name = list[number - 1];
      int pos_Y = 0;
      int pos_X = 0;
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
            if(matrix[t][u] == name)
            {
                pos_X = u;
                pos_Y = t;
            }
        }
      }
      if(pos_X  + 1 < 8 && pos_Y + 1 < 8)
      {
         if(matrix[pos_Y + 1][pos_X + 1] == null)
         {
             return true;
          }
      }
      
      else if(pos_X  - 1 >= 0 && pos_Y + 1 < 8)
      {
         if(matrix[pos_Y + 1][pos_X - 1] == null)
         {
             return true;
        }
      }
      return false;
   }
  private int generateRandomMove() {
    Random a = new SecureRandom();
    int select = a.nextInt(8);
    while(!check_move(select))
    {
      select = a.nextInt(8);
    }
    return select;
  }
  private int gen_move(int size)
  {
    Random a = new SecureRandom();
    int select = a.nextInt(size);
    return select;
  }


   //this is where the AI move 


   private void AI_activate(List<PosCheck> posChecks2)
   {
      //print_matrix();
      String name = list[generateRandomMove() - 1];
      int pos_Y = 0;
      int pos_X = 0;
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
            if(matrix[t][u] == name)
            {
                pos_X = x_matrix[u][0];
                pos_Y = y_matrix[0][t];
            }
        }
      }
      //System.out.println(pos_X + " " + pos_Y);

      all_move(name);
      int move = gen_move(poss_moveX.size());
      change(x_matrix[poss_moveX.get(move)][0],y_matrix[0][poss_moveY.get(move)],name);
      int hold_X = x_matrix[poss_moveX.get(move)][0];
      int hold_Y = y_matrix[0][poss_moveY.get(move)];
      //System.out.println(poss_moveX);
      //System.out.println(poss_moveY);

      poss_moveX = new ArrayList<Integer>();
      poss_moveY = new ArrayList<Integer>();

      //print_matrix();
      for (PosCheck posCheck: posChecks2)
      {
        if (posCheck.cx == pos_X &&
            posCheck.cy == pos_Y )
         {
            posCheck.cx = hold_X;
            posCheck.cy = hold_Y;
         }
         //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
      }     
      if(find_eating("R", find(hold_Y,hold_X)) == false)
      {
          pick = true;
      }
      print_matrix();
      /*
      print_matrix();
      System.out.println(name);
      System.out.println(poss_moveX);
      System.out.println(poss_moveY);
      */
   }





   private void all_move(String name)
   {
      int pos_Y = 0;
      int pos_X = 0;
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
            if(matrix[t][u] == name)
            {
                pos_X = u;
                pos_Y = t;
            }
        }
      }
      if(pos_X  + 1 < 8 && pos_Y + 1 < 8)
      {
         if(matrix[pos_Y + 1][pos_X + 1] == null)
         {
             poss_moveX.add(pos_X+1);
             poss_moveY.add(pos_Y+1);
         }
      }
      
      if(pos_X  - 1 >= 0 && pos_Y + 1 < 8)
      {
         if(matrix[pos_Y + 1][pos_X - 1] == null)
         {
             poss_moveX.add(pos_X-1);
             poss_moveY.add(pos_Y+1);
        }
      }
   }

   public void print_matrix()
   {
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
          if(matrix[t][u] == null)
             System.out.print("  ");
          else
            System.out.print(matrix[t][u]);
        }
        System.out.println();
      }
   }
   private String opposite(String type)
   {
     return (type == "B") ? "R" : "B"; 
   }
   private boolean eating(int y, int x, String type)
   {
      if(y - 2 >= 0 && x + 2 < 8)
      {
        if(matrix[y - 1][x + 1] != null)
        {
           if(matrix[y - 1][x + 1].charAt(0) == opposite(type).charAt(0) && matrix[y - 2][x + 2] == null)
           {
              return true;
           }
        }
      }
      else if(y - 2 >= 0 && x - 2 >= 0)
      {
        if(matrix[y - 1][x - 1] != null)
        {
           if(matrix[y - 1][x - 1].charAt(0) == opposite(type).charAt(0) && matrix[y - 2][x - 2] == null)
           {
              return true;
           }
        }
      }
      return false;
   }
   private boolean find_eating(String type, String name)
   {
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
          if(matrix[t][u] != null)
          {
            if(matrix[t][u]== name)
            {
                 if(eating(t,u,type))
                    return true;
            }

          }
        }
      }
      return false;
   }
   private void remove(String name)
   {
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
          if(matrix[t][u] == name)
              matrix[t][u] = null;
        }
      }
   }
   private void change(int dom, int ran, String name)
   {
      int pos_X = 0;
      int pos_Y = 0;
      for(int x = 0; x < 8; x++)
      {
         if(dom == x_matrix[x][0])
            pos_X = x;

      }
      for(int y = 0; y < 8; y++)
      {
         if(ran == y_matrix[0][y])
            pos_Y = y;
      }
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
          if(matrix[t][u] == name)
              matrix[t][u] = null;
        }
      }
      matrix[pos_Y][pos_X] = name;
   }
   private String find(int dom, int ran)
   {
      int pos_X = 0;
      int pos_Y = 0;
      for(int x = 0; x < 8; x++)
      {
         if(dom == x_matrix[x][0])
            pos_X = x;
          //System.out.print(x_matrix[x][0]+ " ");
      }
     // System.out.println();
      for(int y = 0; y < 8; y++)
      {
         if(ran == y_matrix[0][y])
            pos_Y = y;
          //System.out.print(y_matrix[0][y]+ " ");
      }
      //System.out.println("\n"+ pos_X + " " + pos_Y);
      return matrix[pos_Y][pos_X];
   }
   private boolean containThis(List<PosCheck> posChecks2, int x, int y, CheckerType color)
   {
      for (PosCheck posCheck: posChecks2)
      {
        if (posCheck.cx == x &&
            posCheck.cy == y && posCheck.checker.checkerType == color)
         {
            return true;
         }
         //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
      }      

       return false;
   }
   {

   }
   public Board()
   {
      posChecks = new ArrayList<>();
      dimPrefSize = new Dimension(BOARDDIM, BOARDDIM);

      addMouseListener(new MouseAdapter()
                       {
                          @Override
                          public void mousePressed(MouseEvent me)
                          {
                             // Obtain mouse coordinates at time of press.
                              /*
                              for(int x = 0; x < 8; x++)
                              {
                                for(int y = 0; y < 8; y++)
                                {
                                  System.out.print(matrix[x][y]+" ");
                                }
                                System.out.println();
                              }
                              */

                             int x = me.getX();
                             int y = me.getY();

                             // Locate positioned checker under mouse press.

                             for (PosCheck posCheck: posChecks)
                                if (Checker.contains(x, y, posCheck.cx, 
                                                     posCheck.cy))
                                {
                                   Board.this.posCheck = posCheck;
                                   oldcx = posCheck.cx;
                                   oldcy = posCheck.cy;
                                   deltax = x - posCheck.cx;
                                   deltay = y - posCheck.cy;
                                   inDrag = true;
                                   return;
                                }
                          }

                          @Override
                          public void mouseReleased(MouseEvent me)
                          {
                            //print_matrix();
                             // When mouse released, clear inDrag (to
                             // indicate no drag in progress) if inDrag is
                             // already set.

                             if (inDrag)
                                inDrag = false;
                             else
                                return;

                             // Snap checker to center of square.
                             int x = me.getX();
                             int y = me.getY();
                             
                             posCheck.cx = (x - deltax) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;
                             posCheck.cy = (y - deltay) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;
                             //System.out.println(oldcx+" "+oldcy);
                             //System.out.println(find(oldcx,oldcy));
                             // Do not move checker onto an occupied square.
                             for (PosCheck posCheck: posChecks)
                                if (posCheck != Board.this.posCheck && 
                                    posCheck.cx == Board.this.posCheck.cx &&
                                    posCheck.cy == Board.this.posCheck.cy)
                                {
                                   Board.this.posCheck.cx = oldcx;
                                   Board.this.posCheck.cy = oldcy;
                                }
                              //Do not move out of the board
                              if(Board.this.posCheck.cx >= 527 || Board.this.posCheck.cy >= 527 || oldcy == Board.this.posCheck.cy || oldcx == Board.this.posCheck.cx)
                              {
                                 Board.this.posCheck.cx = oldcx;
                                 Board.this.posCheck.cy = oldcy;
                              }
                              //Do not jump or go back ward if not
                              if(Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR)
                                {
                                  Board.this.posCheck.cx = oldcx;
                                  Board.this.posCheck.cy = oldcy;
                                }
                              if(Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR )
                                {
                                   if(Board.this.posCheck.cy > oldcy)
                                  {
                                    Board.this.posCheck.cx = oldcx;
                                    Board.this.posCheck.cy = oldcy;
                                  }
                                  else if((oldcx - 124 == Board.this.posCheck.cx) && ( oldcy - 124 == Board.this.posCheck.cy) && containThis(posChecks, Board.this.posCheck.cx + 62, Board.this.posCheck.cy + 62,CheckerType.RED_REGULAR))
                                  { 
                                      //System.out.println("HELLO");
                                      for (PosCheck posCheck: posChecks)
                                        {
                                          if (posCheck.cx == Board.this.posCheck.cx + 62 &&
                                              posCheck.cy ==  Board.this.posCheck.cy + 62 && posCheck.checker.checkerType == CheckerType.RED_REGULAR)
                                           {
                                             change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                             list_RED.add(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));
                                             remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));

                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                              still_eating = true;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }  
                                  }
                                  else if((oldcx + 124 == Board.this.posCheck.cx) && ( oldcy - 124 == Board.this.posCheck.cy) && containThis(posChecks, Board.this.posCheck.cx - 62, Board.this.posCheck.cy + 62,CheckerType.RED_REGULAR))
                                  { 
                                      //System.out.println("HELLO");
                                      for (PosCheck posCheck: posChecks)
                                        {
                                          if (posCheck.cx == Board.this.posCheck.cx - 62 &&
                                              posCheck.cy ==  Board.this.posCheck.cy + 62 && posCheck.checker.checkerType == CheckerType.RED_REGULAR)
                                           {
                                              change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                              list_RED.add(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                              remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                              still_eating = true;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }  
                                  }
                                  else if((oldcx - Board.this.posCheck.cx != 62 && oldcy - Board.this.posCheck.cy  != 62) ||  (Board.this.posCheck.cx - oldcx != 62 && oldcy - Board.this.posCheck.cy != 62 ))
                                  {
                                    //System.out.println("HOLA");
                                    Board.this.posCheck.cx = oldcx;
                                    Board.this.posCheck.cy = oldcy;
                                  }
                                  else if(!pick || Board.this.posCheck.cx == oldcx && Board.this.posCheck.cy == oldcy)
                                  {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                  }
                                  else if(Board.this.posCheck.cx != oldcx && Board.this.posCheck.cy != oldcy)
                                  {
                                    change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                    if(find_eating("B", find(oldcx,oldcy)) == false)
                                    {
                                        pick = false;
                                    }
                                    if(pick == false)
                                    {
                                        AI_activate(posChecks);
                                    }
                                    //print_matrix();
                                
                                  }
                                   if(find_eating("B", find(oldcx,oldcy)) == false && still_eating)
                                    {
                                        pick = false;
                                        still_eating = false;
                                    }
                                    if(pick == false)
                                    {
                                        AI_activate(posChecks);
                                    }
                                    System.out.println();
                                    print_matrix();
                                }
                          

                             posCheck = null;
                             repaint();
                          
                        }

                       });

      // Attach a mouse motion listener to the applet. That listener listens
      // for mouse drag events.

      addMouseMotionListener(new MouseMotionAdapter()
                             {
                                @Override
                                public void mouseDragged(MouseEvent me)
                                {
                                   if (inDrag)
                                   {
                                      // Update location of checker center.

                                      posCheck.cx = me.getX() - deltax;
                                      posCheck.cy = me.getY() - deltay;
                                      repaint();
                                   }
                                }
                             });

   }

   public void add(Checker checker, int row, int col)
   {
      if (row < 1 || row > 8)
         throw new IllegalArgumentException("row out of range: " + row);
      if (col < 1 || col > 8)
         throw new IllegalArgumentException("col out of range: " + col);
      PosCheck posCheck = new PosCheck();
      posCheck.checker = checker;
      posCheck.cx = (col - 1) * SQUAREDIM + SQUAREDIM / 2;
      posCheck.cy = (row - 1) * SQUAREDIM + SQUAREDIM / 2;
      for (PosCheck _posCheck: posChecks)
         if (posCheck.cx == _posCheck.cx && posCheck.cy == _posCheck.cy)
            throw new AlreadyOccupiedException("square at (" + row + "," +
                                               col + ") is occupied");
      posChecks.add(posCheck);
   }

   @Override
   public Dimension getPreferredSize()
   {
      return dimPrefSize;
   }

   @Override
   protected void paintComponent(Graphics g)
   {
      paintCheckerBoard(g);
      for (PosCheck posCheck: posChecks)
         if (posCheck != Board.this.posCheck)
            posCheck.checker.draw(g, posCheck.cx, posCheck.cy);

      // Draw dragged checker last so that it appears over any underlying 
      // checker.

      if (posCheck != null)
         posCheck.checker.draw(g, posCheck.cx, posCheck.cy);
   }

   private void paintCheckerBoard(Graphics g)
   {
      ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON);

      // Paint checkerboard.

      for (int row = 0; row < 8; row++)
      {
         g.setColor(((row & 1) != 0) ? Color.BLACK : Color.WHITE);
         for (int col = 0; col < 8; col++)
         {
            g.fillRect(col * SQUAREDIM, row * SQUAREDIM, SQUAREDIM, SQUAREDIM);
            g.setColor((g.getColor() == Color.BLACK) ? Color.WHITE : Color.BLACK);
         }
      }
   }

   // positioned checker helper class

   private class PosCheck
   {
      public Checker checker;
      public int cx;
      public int cy;
   }
}