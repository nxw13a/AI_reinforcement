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

import java.io.*;

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
   private boolean still_eating = true;
   private boolean gameover = false;

   // displacement between drag start coordinates and checker center coordinates

   private int deltax, deltay;

   // reference to positioned checker at start of drag

   private PosCheck posCheck;

   // center location of checker at start of drag

   private int oldcx, oldcy;
   public ArrayList<String> list = new ArrayList<String>();
   private ArrayList<String> list_RED = new ArrayList<String>();
   private ArrayList<String> list_BLACK = new ArrayList<String>();
   private ArrayList<Integer> poss_moveX = new ArrayList<Integer>();
   private ArrayList<Integer> poss_moveY = new ArrayList<Integer>();
   private Manager manager = new Manager();


   // list of Checker objects and their initial positions

   private List<PosCheck> posChecks;

   public int[][] x_matrix = new int[8][8];
   public int[][] y_matrix = new int[8][8];

   public String[][] matrix = new String[8][8];
   private String turn_king(String a)
   {
      if(a == "R1")
        return "R1K";
      if(a == "R2")
        return "R2K";
      if(a == "R3")
        return "R3K";
      if(a == "R4")
        return "R4K";
      if(a == "R5")
        return "R5K";
      if(a == "R6")
        return "R6K";
      if(a == "R7")
        return "R7K";
      return "R8K";
   }
   private boolean check_move( String name)
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
      //System.out.println("HI");
      if(name.length() < 3)
      {
        if(pos_X  + 1 < 8 && pos_Y + 1 < 8)
        {
           //System.out.println(matrix[pos_Y + 1][pos_X + 1]);
           if(matrix[pos_Y + 1][pos_X + 1] == null)
           {
              //System.out.println("HI");
               return true;
           }
           else if(pos_X  + 2 < 8 && pos_Y + 2 < 8)
           {
            if(matrix[pos_Y + 1][pos_X + 1].charAt(0) == 'B' && matrix[pos_Y + 2][pos_X + 2] == null)
            {
              return true;
            }
          }
        }
        
        if(pos_X  - 1 >= 0 && pos_Y + 1 < 8)
        {
           if(matrix[pos_Y + 1][pos_X - 1] == null)
           {
            //System.out.println("HI");
              return true;
          }
           else if(pos_X  - 2 >= 0 && pos_Y + 2 < 8)
           {
            if(matrix[pos_Y + 1][pos_X - 1].charAt(0) == 'B' && matrix[pos_Y + 2][pos_X - 2] == null)
            {
              return true;
            }
          }
 
        }
      }
      else
      {
        if(pos_X  + 1 < 8 && pos_Y + 1 < 8)
        {
           //System.out.println(matrix[pos_Y + 1][pos_X + 1]);
           if(matrix[pos_Y + 1][pos_X + 1] == null)
           {
              //System.out.println("HI");
               return true;
           }
           else if(pos_X  + 2 < 8 && pos_Y + 2 < 8)
           {
            if(matrix[pos_Y + 1][pos_X + 1].charAt(0) == 'B' && matrix[pos_Y + 2][pos_X + 2] == null)
            {
              return true;
            }
          }
        }
        
        if(pos_X  - 1 >= 0 && pos_Y + 1 < 8)
        {
           if(matrix[pos_Y + 1][pos_X - 1] == null)
           {
            //System.out.println("HI");
              return true;
           }
            else if(pos_X  - 2 >= 0 && pos_Y + 2 < 8)
           {
            if(matrix[pos_Y + 1][pos_X - 1].charAt(0) == 'B' && matrix[pos_Y + 2][pos_X - 2] == null)
            {
              return true;
            }
          }
        }

        if(pos_X  - 1 >= 0 && pos_Y - 1 >= 0)
        {
           if(matrix[pos_Y - 1][pos_X + 1] == null)
           {
            //System.out.println("HI");
              return true;
           }
            else if(pos_X  - 2 >= 0 && pos_Y - 2 >= 0)
           {
            if(matrix[pos_Y - 1][pos_X - 1].charAt(0) == 'B' && matrix[pos_Y - 2][pos_X - 2] == null)
            {
              return true;
            }
          }
        }
        if(pos_X  + 1 < 8 && pos_Y - 1 >= 0)
        {
           if(matrix[pos_Y - 1][pos_X + 1] == null)
           {
            //System.out.println("HI");
              return true;
          }
           else if(pos_X  + 2 < 8 && pos_Y - 2 >= 0)
           {
            if(matrix[pos_Y - 1][pos_X + 1].charAt(0) == 'B' && matrix[pos_Y - 2][pos_X + 2] == null)
            {
              return true;
            }
          }
        }
      }
      return false;
   }
  private String generateRandomMove() {
    Random a = new SecureRandom();
    ArrayList<String> copy = new ArrayList<String>(list);
    for(int t = 0; t < copy.size(); t++)
      {
         if(!check_move(copy.get(t)))
         {
            copy.remove(t);
            t--;
         }
      }
    System.out.println(copy);
    System.out.println(list +"\n");
    int select = a.nextInt(copy.size());
    return copy.get(select);
    
  }
  private int gen_move(int size)
  {
    Random a = new SecureRandom();
    int select = a.nextInt(size);
    return select;
  }
  private boolean occupied_space(List<PosCheck> posChecks2, int x, int y)
  {
     for (PosCheck posCheck: posChecks2)
        if (posCheck.cx == x &&
            posCheck.cy == y)
          {
            return true;
          }
     return false;
  }
  private boolean check_kingLL(List<PosCheck> posChecks2, int x, int y, int orgX, int orgY)
  {
      int or_X = x;
      int or_Y = y;
      int count = 0;
      x += 62;
      y -= 62;
       //System.out.println(x + " " + orgX + " " + y + " " + orgY);

      if(x == orgX && y == orgY)
          return true;
      while(y > 0 && x < 500)
      {
        //System.out.println(x + " " + orgX + " " + y + " " + orgY);
          if(count >= 2)
          {
            //System.out.println("HI " + count);
            return false;
          }
          if(x == orgX && y == orgY)
          {
              if( (containThis(posChecks2, or_X + 62, or_Y - 62, CheckerType.RED_REGULAR) || containThis(posChecks2, or_X + 62, or_Y - 62, CheckerType.RED_KING)) && count == 1)
              {
                  for (PosCheck posCheck: posChecks2)
                    {
                      if (posCheck.cx == or_X + 62 &&
                          posCheck.cy == or_Y - 62)
                       {
                          posCheck.cx = 527;
                          posCheck.cy = 403;
                       }
                       //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                    } 
                  for(int p = 0; p < list.size(); p++)
                  {
                    if(list.get(p) == find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62))
                    {
                       list.remove(p);
                    }
                  }
                  remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62));                                    
              }
             still_eating = false;
             return true;
          }
          else if(occupied_space(posChecks2,x,y))
          {
            //System.out.println(count);
             count++;
             //System.out.println(count);
          }
          x += 62;
          y -= 62;
      }
      //System.out.println("OOO");
      return false;
  }
   private boolean check_kingLR(List<PosCheck> posChecks2, int x, int y, int orgX, int orgY)
  {
      int or_X = x;
      int or_Y = y;
      int count = 0;
      x -= 62;
      y -= 62;
      if(x == orgX && y == orgY)
          return true;
      while(y > 0 && x > 0)
      {
         //System.out.println(+ " " + orgX + " " + y + " " + orgY);
          if(count >= 2)
          {
            //System.out.println("HI " + count);
            return false;
          }
          if(x == orgX && y == orgY)
          {
              if( (containThis(posChecks2, or_X - 62, or_Y - 62, CheckerType.RED_REGULAR) || containThis(posChecks2, or_X - 62, or_Y - 62, CheckerType.RED_KING)) && count == 1)
              {
                  for (PosCheck posCheck: posChecks2)
                    {
                      if (posCheck.cx == or_X - 62 &&
                          posCheck.cy == or_Y - 62)
                       {
                          posCheck.cx = 527;
                          posCheck.cy = 403;
                       }
                       //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                    } 
                  for(int p = 0; p < list.size(); p++)
                  {
                    if(list.get(p) == find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62))
                    {
                       list.remove(p);
                    }
                  }
                  remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62));        
              }
             still_eating = false;
             return true;
          }
          else if(occupied_space(posChecks2,x,y))
          {
            //System.out.println(count);
             count++;
             //System.out.println(count);
          }
          x -= 62;
          y -= 62;
      }
      //System.out.println("OOO");
      return false;
  }
    private boolean check_kingUL(List<PosCheck> posChecks2, int x, int y, int orgX, int orgY)
  {
      int or_X = x;
      int or_Y = y;
      int count = 0;
      x += 62;
      y += 62;
      if(x == orgX && y == orgY)
          return true;
      while(y < 500 && x < 500)
      {
         //System.out.println(+ " " + orgX + " " + y + " " + orgY);
          if(count >= 2)
          {
            //System.out.println("HI " + count);
            return false;
          }
          if(x == orgX && y == orgY)
          {
              if( (containThis(posChecks2, or_X + 62, or_Y + 62, CheckerType.RED_REGULAR) || containThis(posChecks2, or_X + 62, or_Y + 62, CheckerType.RED_KING)) && count == 1)
              {
                  for (PosCheck posCheck: posChecks2)
                    {
                      if (posCheck.cx == or_X + 62 &&
                          posCheck.cy == or_Y + 62)
                       {
                          posCheck.cx = 527;
                          posCheck.cy = 403;
                       }
                       //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                    } 
                  for(int p = 0; p < list.size(); p++)
                  {
                    if(list.get(p) == find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62))
                    {
                       list.remove(p);
                    }
                  }
                 remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));        
              }
             still_eating = false;
             return true;
          }
          else if(occupied_space(posChecks2,x,y))
          {
            //System.out.println(count);
             count++;
             //System.out.println(count);
          }
          x += 62;
          y += 62;
      }
      return false;
  }
   private boolean check_kingUR(List<PosCheck> posChecks2, int x, int y, int orgX, int orgY)
  {
      int or_X = x;
      int or_Y = y;
      int count = 0;
      x -= 62;
      y += 62;
      if(x == orgX && y == orgY)
          return true;
      while(y < 500 && x > 0)
      {
         //System.out.println(+ " " + orgX + " " + y + " " + orgY);
          if(count >= 2)
          {
            //System.out.println("HI " + count);
            return false;
          }
          if(x == orgX && y == orgY)
          {
              if( (containThis(posChecks2, or_X - 62, or_Y + 62, CheckerType.RED_REGULAR) || containThis(posChecks2, or_X - 62, or_Y + 62, CheckerType.RED_KING)) && count == 1)
              {
                  for (PosCheck posCheck: posChecks2)
                    {
                      if (posCheck.cx == or_X - 62 &&
                          posCheck.cy == or_Y + 62)
                       {
                          posCheck.cx = 527;
                          posCheck.cy = 403;
                       }
                       //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                    }  
                  for(int p = 0; p < list.size(); p++)
                  {
                    if(list.get(p) == find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62))
                    {
                       list.remove(p);
                    }
                  }
                  remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));       
              }
             still_eating = false;
             return true;
          }
          else if(occupied_space(posChecks2,x,y))
          {
            //System.out.println(count);
             count++;
             //System.out.println(count);
          }
          x -= 62;
          y += 62;
      }
      return false;
  }
   //this is where the AI move 


   private void AI_activate(List<PosCheck> posChecks2)
   {
      //print_matrix();
    if(list.size() != 0)
    {
      String name;
      if(list.size() == 1)
      {
         name = list.get(0);
      }
      else
      {
        name = generateRandomMove();
      }
      int pos_Y = 0;
      int pos_X = 0;
      //System.out.println(list);
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
     //System.out.println(name);
      all_move(name);
      if(poss_moveX.size() != 0 && poss_moveY.size() != 0)
      {
        int newX, newY, move, hold_X = 0, hold_Y = 0;
        try {
          double[][] returnedScore = manager.stateDoesExist(matrix);
          if (returnedScore != null && manager.getHighScore(returnedScore) != null){
            newX = manager.getHighScore(returnedScore).get(0);
            newY = manager.getHighScore(returnedScore).get(1);
            change(x_matrix[newX][0],y_matrix[0][newY],name);
            hold_X = x_matrix[newX][0];
            hold_Y = y_matrix[0][newY];
          } else {
            move = gen_move(poss_moveX.size());
            change(x_matrix[poss_moveX.get(move)][0],y_matrix[0][poss_moveY.get(move)],name);
            hold_X = x_matrix[poss_moveX.get(move)][0];
            hold_Y = y_matrix[0][poss_moveY.get(move)];
          }

        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

        //System.out.println(poss_moveX);
        //System.out.println(poss_moveY);

        try {
          if (manager.stateDoesExist(matrix) == null){
            manager.setMatrix(matrix);
            manager.setScoreOnPossibleMoves(poss_moveY, poss_moveX);
            manager.printToFile();
          }

        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

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

              if(hold_Y - pos_Y == 124)
              {
                  //System.out.println("OHLLLLLLLLLLLLLLLLLLA");
                  clear_p(posChecks2, hold_X, hold_Y, pos_X, pos_Y);
                  
              }

              if(hold_Y == 465 && posCheck.checker.checkerType != CheckerType.RED_KING)
              {
                posCheck.checker.checkerType = CheckerType.RED_KING;
                for(int x = 0; x <  list.size();x++)
                {
                   if(list.get(x) == find(hold_X,hold_Y))
                   {
                      String h = list.get(x);
                      list.set(x,turn_king(h));
                   }
                 }
                for(int t = 0; t < 8; t++)
                {
                  for(int u = 0; u < 8; u++)
                  {
                    if( (hold_Y == x_matrix[u][0]) && (hold_X == y_matrix[0][t]) )
                    {
                      String hold = matrix[u][t];
                      matrix[u][t] = turn_king(hold);
                    }
                  }
                }
                 //System.out.println(list);
                 //print_matrix();
              }
           }
           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
        }
      }
      else
      {
        System.out.println("YOU WIN");
        gameover = true;
      }     
    }
    else
    {
        System.out.println("YOU WIN");
        gameover = true;

    }
      //print_matrix();
      /*
      print_matrix();
      System.out.println(name);
      System.out.println(poss_moveX);
      System.out.println(poss_moveY);
      */
   }


   private void clear_p(List<PosCheck> posChecks2, int hold_X, int hold_Y, int pos_X, int pos_Y)
   {
    for (PosCheck posCheck: posChecks2)
                {
                  if (posCheck.cx == hold_X - 62 && pos_X == hold_X - 124 &&
                      posCheck.cy == hold_Y - 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
                   {
                      remove(find(posCheck.cx,posCheck.cy));
                      //System.out.println(find(posCheck.cx,posCheck.cy));
                      posCheck.cx = 527;
                      posCheck.cy = 403;
                   }
                   else if(posCheck.cx == hold_X + 62 && pos_X == hold_X + 124 &&
                      posCheck.cy == hold_Y - 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
                   {
                      remove(find(posCheck.cx,posCheck.cy));
                      //System.out.println(find(posCheck.cx,posCheck.cy));
                      posCheck.cx = 527;
                      posCheck.cy = 403;
                   }
                   //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                }      
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
      //System.out.println(name.length());
      if(name.length() < 3)
      {
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
        if(pos_X + 2 < 8 && pos_Y + 2 < 8)
        {
           if(matrix[pos_Y + 2][pos_X + 2] == null && matrix[pos_Y + 1][pos_X + 1] != null)
           {
              if(matrix[pos_Y + 1][pos_X + 1].charAt(0) == 'B')
              {
               poss_moveX.add(pos_X+2);
               poss_moveY.add(pos_Y+2);
             }
           }
        }
         if(pos_X - 2 >= 0 && pos_Y + 2 < 8)
        {
           if(matrix[pos_Y + 2][pos_X - 2] == null && matrix[pos_Y + 1][pos_X - 1] != null)
           {  
              if(matrix[pos_Y + 1][pos_X - 1].charAt(0) == 'B')
              {
               poss_moveX.add(pos_X-2);
               poss_moveY.add(pos_Y+2);
             }
           }
        }
      }
      else /* *******************************************************************************************************************************************************************************/
      {
         int oldY = pos_Y;
         int oldX = pos_X;
         System.out.println(oldX + " " + oldY);
         while(oldX < 8 || oldY < 8)
         {
            if(matrix[oldY][oldX] == null)
             {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
             }
            else 
            {  
              if(matrix[oldY][oldX].charAt(0) == 'B' && matrix[oldY + 1][oldX + 1] == null)
              {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
              }
              else
              {
                break;
              }       
            }
            oldY += 1;
            oldX += 1;
         }

         oldX = pos_X;
         oldY = pos_Y;

         while(oldX >= 0 || oldY >= 0)
         {
          if(matrix[oldY][oldX] == null)
             {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
             }
            else 
            {
              if(matrix[oldY][oldX].charAt(0) == 'B' && matrix[oldY - 1][oldX - 1] == null)
              {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
              }
              break;
            }
            System.out.println(oldX + " " + oldY);
            oldX -= 1;
            oldY -= 1; 
         }

         oldX = pos_X;
         oldY = pos_Y;

         while(oldX >= 0 || oldY < 8)
         {
            if(matrix[oldY][oldX] == null)
             {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
             }
            else 
            {
              if(matrix[oldY][oldX].charAt(0) == 'B' && matrix[oldY + 1][oldX - 1] == null)
              {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
              }
              break;
            }

            oldX -= 1;
            oldY += 1;
         }

         oldX = pos_X;
         oldY = pos_Y;

         while(oldX < 8 || oldY >= 0)
         {
            if(matrix[oldY][oldX] == null)
             {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
             }
            else 
            {
              if(matrix[oldY][oldX].charAt(0) == 'B' && matrix[oldY - 1][oldX + 1] == null)
              {
                poss_moveX.add(oldX);
                poss_moveY.add(oldY);
              }
              break;
            }
            System.out.println(oldX + " " + oldY);
            oldX += 1;
            oldY -= 1;
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
                              print_matrix();
                             // Snap checker to center of square.
                             int x = me.getX();
                             int y = me.getY();
                             
                             posCheck.cx = (x - deltax) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;
                             posCheck.cy = (y - deltay) / SQUAREDIM * SQUAREDIM + 
                                           SQUAREDIM / 2;
                             //System.out.println(posCheck.cx+" "+posCheck.cy);
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
                              if(gameover)
                              {
                                Board.this.posCheck.cx = oldcx;
                                 Board.this.posCheck.cy = oldcy;
                              }
                              else if(Board.this.posCheck.cx >= 527 || Board.this.posCheck.cy >= 527 || oldcy == Board.this.posCheck.cy || oldcx == Board.this.posCheck.cx)
                              {
                                 Board.this.posCheck.cx = oldcx;
                                 Board.this.posCheck.cy = oldcy;
                              }
                              //Do not jump or go back ward if not
                              else if(Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR || Board.this.posCheck.checker.checkerType == CheckerType.RED_KING )
                                {
                                  //System.out.println(find(oldcx,oldcy));
                                  Board.this.posCheck.cx = oldcx;
                                  Board.this.posCheck.cy = oldcy;
                                }

                  
                              else if(Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR )
                                {
                                  //System.out.println(find(oldcx,oldcy));
                                   if(Board.this.posCheck.cy > oldcy)
                                  {
                                    Board.this.posCheck.cx = oldcx;
                                    Board.this.posCheck.cy = oldcy;
                                  }
                                  else if(((oldcx - Board.this.posCheck.cx) != (oldcy - Board.this.posCheck.cy)) && (oldcx - Board.this.posCheck.cx > 0) )
                                  {
                                    //System.out.println((oldcx - Board.this.posCheck.cx) + " " + (oldcy - Board.this.posCheck.cy));
                                    Board.this.posCheck.cx = oldcx;
                                    Board.this.posCheck.cy = oldcy;
                                  }
                                  else if(((Board.this.posCheck.cx - oldcx) != (oldcy - Board.this.posCheck.cy)) && (Board.this.posCheck.cx - oldcx > 0) )
                                  {
                                    //System.out.println((oldcx - Board.this.posCheck.cx) + " " + (oldcy - Board.this.posCheck.cy));
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
                                              //print_matrix();
                                             change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                             list_RED.add(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));
                                               for(int p = 0; p < list.size(); p++)
                                             {
                                                 if(list.get(p) == find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62))
                                                 {
                                                    list.remove(p);
                                                 }
                                             }
                                              //System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                                             remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));
                                             //print_matrix();
                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }  
                                        still_eating = false;
                                  }
                                  else if((oldcx + 124 == Board.this.posCheck.cx) && ( oldcy - 124 == Board.this.posCheck.cy) && containThis(posChecks, Board.this.posCheck.cx - 62, Board.this.posCheck.cy + 62,CheckerType.RED_REGULAR))
                                  { 
                                      //System.out.println("HELLO2");
                                      for (PosCheck posCheck: posChecks)
                                        {
                                          if (posCheck.cx == Board.this.posCheck.cx - 62 &&
                                              posCheck.cy ==  Board.this.posCheck.cy + 62 && posCheck.checker.checkerType == CheckerType.RED_REGULAR)
                                           {
                                              change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                              list_RED.add(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                                for(int p = 0; p < list.size(); p++)
                                               {
                                                   if(list.get(p) == find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62))
                                                   {
                                                      list.remove(p);
                                                   }
                                               }
                                              //System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                              remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));

                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }
                                        still_eating = false;  
                                  }
                  
                                  else if(Board.this.posCheck.cx == oldcx && Board.this.posCheck.cy == oldcy)
                                  {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                  }
                                  else if(Board.this.posCheck.cx != oldcx && Board.this.posCheck.cy != oldcy)
                                  {
                                    System.out.println(Board.this.posCheck.cy + " " + oldcy);
                                    if(Board.this.posCheck.cy + 62 == oldcy)
                                    {
                                      change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                      still_eating = false;
                                    }
                                    else
                                    {
                                        Board.this.posCheck.cx = oldcx;
                                        Board.this.posCheck.cy = oldcy;
                                    }
    
                                    //print_matrix();
                                
                                  }
                                  if(Board.this.posCheck.cy == 31 && Board.this.posCheck.checker.checkerType != CheckerType.BLACK_KING)
                                  {
                                    Board.this.posCheck.checker.checkerType = CheckerType.BLACK_KING;
                                    //print_matrix();
                                          for(int t = 0; t < 8; t++)
                                          {
                                            for(int u = 0; u < 8; u++)
                                            {
                                                if( (Board.this.posCheck.cy == x_matrix[u][0]) && (Board.this.posCheck.cx == y_matrix[0][t]) )
                                                {
                                                    String hold = matrix[u][t];
                                                    matrix[u][t] = hold + "K";
                                                }
                                            }
                                          }
                                    //print_matrix();

                                  }
                                            //print_matrix();
                                }
                              else if(Board.this.posCheck.checker.checkerType == CheckerType.BLACK_KING)
                              {
                                if(!check_kingLL(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx > Board.this.posCheck.cx) && (oldcy < Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                }
                                else if(!check_kingLR(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx < Board.this.posCheck.cx) && (oldcy < Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                }
                                else if(!check_kingUL(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx > Board.this.posCheck.cx) && (oldcy > Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                }
                               else if(!check_kingUR(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx < Board.this.posCheck.cx) && (oldcy > Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                }
                                else
                                {
                                  change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                  still_eating = false;
                                }
                              }

                            if(still_eating == false)
                            {
                              AI_activate(posChecks);
                              still_eating = true;
                            }
                            //print_matrix();
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