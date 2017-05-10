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
   private String must_eat = "";
   private boolean king_jump = false;
   private boolean red_jump = false;

   private Queue<String> queue = new LinkedList<String>();

   // displacement between drag start coordinates and checker center coordinates

   private int deltax, deltay;

   // reference to positioned checker at start of drag

   private PosCheck posCheck;

   // center location of checker at start of drag

   private int oldcx, oldcy;
   private int[] pos_index_holder = new int[2];
   public ArrayList<String> list = new ArrayList<String>();
   private ArrayList<String> list_RED = new ArrayList<String>();
   private ArrayList<String> list_BLACK = new ArrayList<String>();
   private ArrayList<Integer> poss_moveX = new ArrayList<Integer>();
   private ArrayList<Integer> poss_moveY = new ArrayList<Integer>();
   ArrayList<Integer> newPosition = new ArrayList<Integer>();
   private Manager manager = new Manager();


   // list of Checker objects and their initial positions

   private List<PosCheck> posChecks;

   public int[][] x_matrix = new int[8][8];
   public int[][] y_matrix = new int[8][8];

   public String[][] matrix = new String[8][8];

   public void printout_queue()
   {
      Iterator iterator = queue.iterator();
      while(iterator.hasNext()){
        String element = (String) iterator.next();
        System.out.print(element + " ");
      }
      System.out.println();
   }

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
           if(matrix[pos_Y - 1][pos_X - 1] == null)
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
    //System.out.println(copy);
    //System.out.println(list +"\n");
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
                         king_jump = true;
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
                  System.out.println(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62) + " " + Board.this.posCheck.cx);
                  
                  remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62));                                    
              }
             //still_eating = false;
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
                         king_jump = true;
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
                  System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62) + " " + Board.this.posCheck.cx);
                  
                  
                  
                  remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62));   
                     
              }
             //still_eating = false;
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
                           king_jump = true;
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
                  System.out.println(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                  
                  
                  
                 remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62)); 
                     
              }
             //still_eating = false;
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
                          king_jump = true;
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
                  System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                  
                  
                  
                  remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));  
                     
              }
             //still_eating = false;
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
  private void containUR(int x, int y)
   {
     while(x < 8 && y >= 0)
     {
       x += 1;
       y -= 1;
       if(x >= 8 || y < 0)
          return;
       else if(matrix[y][x] != null && (x + 1 < 8 && y - 1 >= 0))
       {
          if(matrix[y][x].charAt(0) == 'B' && matrix[y - 1][x + 1] == null)
          {
             poss_moveX.add(x + 1);
             poss_moveY.add(y - 1);
          }
          return;
       }
     }
   }
private void containUL(int x, int y)
   {
     while(x >= 0 && y >= 0)
     {
       x -= 1;
       y -= 1;
       if(x < 0 || y < 0)
          return;
       else if(matrix[y][x] != null && (x - 1 >= 0 && y - 1 >= 0))
       {
          if(matrix[y][x].charAt(0) == 'B' && matrix[y - 1][x - 1] == null)
          {
             poss_moveX.add(x - 1);
             poss_moveY.add(y - 1);
          }
          return;
       }
     }
   }
  private void containLR(int x, int y)
   {
     while(x < 8 && y < 8)
     {
       x += 1;
       y += 1;
       if(x >= 8 || y >= 8)
          return;
       else if(matrix[y][x] != null && (x + 1 < 8 && y + 1 < 8))
       {
          if(matrix[y][x].charAt(0) == 'B' && matrix[y + 1][x + 1] == null)
          {
             poss_moveX.add(x + 1);
             poss_moveY.add(y + 1);
          }
          return;
       }
     }
   }
  private void containLL(int x, int y)
   {
     while(x >= 0 && y < 8)
     {
       x -= 1;
       y += 1;
       if(x < 0 || y >= 8)
          return;
       else if(matrix[y][x] != null && (x - 1 >= 0 && y + 1 < 8))
       {
          if(matrix[y][x].charAt(0) == 'B' && matrix[y + 1][x - 1] == null)
          {
             poss_moveX.add(x - 1);
             poss_moveY.add(y + 1);
          }
          return;
       }
     }
   }
  private void find_move(String name)
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
    if(name.length() < 3)
    {
       if(pos_Y + 2 < 8 && pos_X + 2 < 8)
       {
          if(matrix[pos_Y + 1][pos_X + 1] != null)
          { 
            if(matrix[pos_Y + 1][pos_X + 1].charAt(0) == 'B' && matrix[pos_Y + 2][pos_X + 2] == null )
            {
                poss_moveX.add(pos_X + 2);
                poss_moveY.add(pos_Y + 2);
            }
          }
       }
       if(pos_Y + 2 < 8 && pos_X - 2 >= 0)
       {
          if(matrix[pos_Y + 1][pos_X - 1] != null)
          { 
            if(matrix[pos_Y + 1][pos_X - 1].charAt(0) == 'B' && matrix[pos_Y + 2][pos_X - 2] == null )
            {
                poss_moveX.add(pos_X - 2);
                poss_moveY.add(pos_Y + 2);
            }
          }
       }
    }
    else
    {
       containLL(pos_X,pos_Y);
       containLR(pos_X,pos_Y);
       containUL(pos_X,pos_Y);
       containUR(pos_X,pos_Y);
    }
  }

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
     System.out.println(name);
      all_move(name);
      if(poss_moveX.size() != 0 && poss_moveY.size() != 0)
      {
        int newX, newY, move, hold_X, hold_Y;

        // try {
          
        //   double[][] returnedScore = manager.stateDoesExist(matrix);

        //   // returnedScore != null: current state exists 
        //   // manager.getHighScore(returnedScore) != null: The score of the current state is not equal
        //   if (returnedScore != null && manager.getHighScore(returnedScore) != null){
        //       System.out.println("ENTERED INTO THE DUNGEON...");
        //       // Determine the indices with the highest score: newX and newY
        //       // With the Queue find the piece that has the possible move newX and newY
        //       // Move that piece and update variables
        //       newPosition.clear();
        //       newPosition = manager.getHighScore(returnedScore);

        //       boolean flag = false;
        //       int counter = 0;
        //       // if (!queue.isEmpty()) {
        //       //   for(Object item : queue){
        //       //     if (legalCheckFunction(item.toString())) {
        //       //       poss_moveY.clear();
        //       //       poss_moveX.clear();

        //       //       all_move(item.toString());
        //       //       counter = 0;
        //       //       while (counter < poss_moveX.size() || counter < poss_moveY.size()){


        //       //           if (poss_moveX.get(counter) == newPosition.get(1) && poss_moveY.get(counter) == newPosition.get(0)){
        //       //               System.out.println(item.toString());
        //       //               System.out.println(poss_moveX.get(counter) + " : " + newPosition.get(1));
        //       //               System.out.println(poss_moveY.get(counter) + " : " + newPosition.get(0));
        //       // //             name = item.toString();
        //       // //             flag = true;
        //       //             break;
        //       //           }else {
        //       //             counter++;
        //       //           }
        //       //       }
        //       //     }
        //       //   }
        //       // }                
              
        //       // change(x_matrix[newX][0],y_matrix[0][newY],name);
        //       // hold_X = x_matrix[newX][0];
        //       // hold_Y = y_matrix[0][newY];
        //   } else {

        //       // Choose a random piece, move and update variable if the current state does not exist
        //       // move = gen_move(poss_moveX.size());
        //       // change(x_matrix[poss_moveX.get(move)][0],y_matrix[0][poss_moveY.get(move)],name);
        //       // hold_X = x_matrix[poss_moveX.get(move)][0];
        //       // hold_Y = y_matrix[0][poss_moveY.get(move)];
        //   } 

        // } catch (IOException e) {
        //     System.err.println("Caught IOException: " + e.getMessage());
        // }

        move = gen_move(poss_moveX.size());
        change(x_matrix[poss_moveX.get(move)][0],y_matrix[0][poss_moveY.get(move)],name);
        hold_X = x_matrix[poss_moveX.get(move)][0];
        hold_Y = y_matrix[0][poss_moveY.get(move)];
        System.out.println(name);
        System.out.println(poss_moveX);
        System.out.println(poss_moveY + "\n");

        queue.add(name);

        try {
          if (manager.stateDoesExist(matrix) != null) {
            System.err.println("Matrix Only ");
          }

          if (manager.stateDoesExist(matrix, queue) != null) {
            System.err.println("Matrix and Q ");
          }
          saveThings();
          } catch (IOException e) {
              System.err.println("Caught IOException: " + e.getMessage());
          }

        poss_moveX = new ArrayList<Integer>();
        poss_moveY = new ArrayList<Integer>();

        print_matrix();
        for (PosCheck posCheck: posChecks2)
        {
          if (posCheck.cx == pos_X &&
              posCheck.cy == pos_Y )
           {
              posCheck.cx = hold_X;
              posCheck.cy = hold_Y;

              
              clear_p(posChecks2, hold_X, hold_Y, pos_X, pos_Y,name.length());

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
        if(red_jump)
        {
          while(true)
          {
            find_move(name);
            if(poss_moveX.size() == 0 && poss_moveY.size() == 0)
            {
              //System.out.println("HI");
              poss_moveX = new ArrayList<Integer>();
              poss_moveY = new ArrayList<Integer>();
               break;
            }
            int org_Y = hold_Y;
            int org_X = hold_X;
            //System.out.println(name);
            //System.out.println(poss_moveX);
            //System.out.println(poss_moveY + "\n");
            move = gen_move(poss_moveX.size());
            queue.add(name);

            change(x_matrix[poss_moveX.get(move)][0],y_matrix[0][poss_moveY.get(move)],name);
            hold_X = x_matrix[poss_moveX.get(move)][0];
            hold_Y = y_matrix[0][poss_moveY.get(move)];
            //print_matrix();
            //System.out.println(poss_moveX);
            //System.out.println(poss_moveY + "\n");

            try {
              saveThings();
            } catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            }

            poss_moveX = new ArrayList<Integer>();
            poss_moveY = new ArrayList<Integer>();

            //print_matrix();
            for (PosCheck posCheck: posChecks2)
            {
               //System.out.println("HI");
              if (posCheck.cx == org_X &&
                  posCheck.cy == org_Y )
               {
                  //System.out.println("HI2");
                  posCheck.cx = hold_X;
                  posCheck.cy = hold_Y;
                  clear_p(posChecks2, hold_X, hold_Y, org_X, org_Y,name.length());

                  if(hold_Y == 465 && posCheck.checker.checkerType != CheckerType.RED_KING)
                  {
                    //System.out.println("HI3");
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
          red_jump = false;
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

   private void clear_p(List<PosCheck> posChecks2, int hold_X, int hold_Y, int pos_X, int pos_Y,int length)
   {
    if(length < 3)
    {
      for (PosCheck posCheck: posChecks2) {
          if (posCheck.cx == hold_X - 62 && pos_X == hold_X - 124 &&
              posCheck.cy == hold_Y - 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
           {
              
              remove(find(posCheck.cx,posCheck.cy));
              //System.out.println(find(posCheck.cx,posCheck.cy));

              red_jump = true;
              posCheck.cx = 527;
              posCheck.cy = 403;
           }
           else if(posCheck.cx == hold_X + 62 && pos_X == hold_X + 124 &&
              posCheck.cy == hold_Y - 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
           {  
              
              remove(find(posCheck.cx,posCheck.cy));
              //System.out.println(find(posCheck.cx,posCheck.cy));
              
              red_jump = true;
              posCheck.cx = 527;
              posCheck.cy = 403;
           }
               //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
      }
    }
    else
    {
      for (PosCheck posCheck: posChecks2) {
          if (posCheck.cx == hold_X - 62  && go_back(hold_X, hold_Y, pos_X, pos_Y,-62,-62) &&
              posCheck.cy == hold_Y - 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
           {
              
              remove(find(posCheck.cx,posCheck.cy));
              //System.out.println(find(posCheck.cx,posCheck.cy));
              
              red_jump = true;
              posCheck.cx = 527;
              posCheck.cy = 403;
           }
           else if(posCheck.cx == hold_X + 62  && go_back(hold_X, hold_Y, pos_X, pos_Y,62,-62) &&
              posCheck.cy == hold_Y - 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
           {
              
              remove(find(posCheck.cx,posCheck.cy));
              //System.out.println(find(posCheck.cx,posCheck.cy));
              
              red_jump = true;
              posCheck.cx = 527;
              posCheck.cy = 403;
           }
           else if(posCheck.cx == hold_X + 62  && go_back(hold_X, hold_Y, pos_X, pos_Y,62,62) &&
              posCheck.cy == hold_Y + 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
           {
              
              remove(find(posCheck.cx,posCheck.cy));
              //System.out.println(find(posCheck.cx,posCheck.cy));
              
              red_jump = true;
              posCheck.cx = 527;
              posCheck.cy = 403;
           }
           else if(posCheck.cx == hold_X - 62  && go_back(hold_X, hold_Y, pos_X, pos_Y,-62,62) &&
              posCheck.cy == hold_Y + 62 && (posCheck.checker.checkerType == CheckerType.BLACK_REGULAR || posCheck.checker.checkerType == CheckerType.BLACK_KING))
           {
              
              remove(find(posCheck.cx,posCheck.cy));
              
              red_jump = true;
              //System.out.println(find(posCheck.cx,posCheck.cy));
              posCheck.cx = 527;
              posCheck.cy = 403;
           }
           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
        }
    } 
   }
   private boolean go_back(int x, int y, int oldx, int oldy, int x_in, int y_in)
   {
      while((x < 466 && x >= 31) && (y < 466 && y >= 31))
      {
        //System.out.println(x + " " + y + " = " + oldx + " " + oldy);
         if(x == oldx && y == oldy)
            return true;
          x += x_in;
          y += y_in;
      }
      return false;
   }
   private void move_LR(int x, int y)
   {
      //System.out.println(x + " " + y);
      while(true)
         {
            y += 1;
            x += 1;
                //System.out.println(x + " " + y);
            if(x >= 8 || y >= 8)
                break;
            if(matrix[y][x] == null)
             {
                poss_moveX.add(x);
                poss_moveY.add(y);
             }
            else 
            {  
              if(y + 1 < 8 && x + 1 < 8)
              {
                if(matrix[y][x].charAt(0) == 'B' && matrix[y + 1][x + 1] == null)
                {
                  poss_moveX.add(x + 1);
                  poss_moveY.add(y + 1);
                }
              }
              break;
            }
         }
   }
   private void move_LL(int x, int y)
   {
      //System.out.println(x + " " + y);
      while(true)
         {
            y += 1;
            x -= 1;
               // System.out.println(x + " " + y);
            if(x < 0 || y >= 8)
                break;
            if(matrix[y][x] == null)
             {
                poss_moveX.add(x);
                poss_moveY.add(y);
             }
            else 
            {  
              if((y + 1 < 8) && (x - 1 >= 0))
              {
                if(matrix[y][x].charAt(0) == 'B' && matrix[y + 1][x - 1] == null)
                {
                  poss_moveX.add(x - 1);
                  poss_moveY.add(y + 1);
                }
              }
              break;
            }
         }
   }
    private void move_UR(int x, int y)
   {
      //System.out.println(x + " " + y);
      while(true)
         {
            y -= 1;
            x += 1;
               // System.out.println(x + " " + y);
            if(x >= 8 || y < 0)
                break;
            if(matrix[y][x] == null)
             {
                poss_moveX.add(x);
                poss_moveY.add(y);
             }
            else 
            {  
              if((y - 1 >= 0) && (x + 1 < 8))
              {
                if(matrix[y][x].charAt(0) == 'B' && matrix[y - 1][x + 1] == null)
                {
                  poss_moveX.add(x + 1);
                  poss_moveY.add(y - 1);
                }
                
              }
                break;
            }
         }
   }
       private void move_UL(int x, int y)
   {
      //System.out.println(x + " " + y);
      while(true)
         {
            y -= 1;
            x -= 1;
            //System.out.println(x + " " + y);
            if(x < 0 || y < 0)
                break;
            if(matrix[y][x] == null)
             {
                poss_moveX.add(x);
                poss_moveY.add(y);
             }
            else 
            { 
              if((y - 1 >= 0) && (x - 1 >= 0))
              { 
                if((matrix[y][x].charAt(0) == 'B') && matrix[y - 1][x - 1] == null)
                {
                  poss_moveX.add(x - 1);
                  poss_moveY.add(y - 1);
                }
              }
              break;
            }
         }
   }


   private void all_move(String name)
   {
        poss_moveX = new ArrayList<Integer>();
        poss_moveY = new ArrayList<Integer>();
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
          move_LR(pos_X,pos_Y);
          move_LL(pos_X,pos_Y);
          move_UR(pos_X,pos_Y);
          move_UL(pos_X,pos_Y);

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
    System.out.println("function: eating called.");
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
      System.out.println("function: find_eating called.");
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
    // System.out.println("function: removed " + name);

    int[] indices = findIndexOfEatenName(name);
    String printName = matrix[indices[0]][indices[1]];
    boolean flagger = legalCheckFunction(printName);
    // System.out.println("\nflagger: " + flagger);
    
    if (flagger) {
      System.out.println("negative");
      try {
        if (manager.stateDoesExist(matrix, queue) == null) {
          saveThings();
          manager.learn(indices, matrix, queue, "neg");
        }
      } catch (IOException e) { }
      

      
    }
    else {
      System.out.println("positive");
      manager.learn(indices, matrix, queue, "pos");
    }

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

      manager.cacheCurrentState(matrix);
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
      // System.out.println("\n"+ pos_X + " " + pos_Y);

      // System.out.println("\n"+ matrix[pos_Y][pos_X]);

      return matrix[pos_Y][pos_X];
   }

   private int[] findIndexOfEatenPieces(int dom, int ran)
   {
      int[] pos = new int[2];
      
      for(int x = 0; x < 8; x++)
      {
         if(dom == x_matrix[x][0])
            pos[0] = x;
          //System.out.print(x_matrix[x][0]+ " ");
      }
     // System.out.println();
      for(int y = 0; y < 8; y++)
      {
         if(ran == y_matrix[0][y])
            pos[1] = y;
          //System.out.print(y_matrix[0][y]+ " ");
      }
      // System.out.println("\n"+ pos_X + " " + pos_Y);
      return pos;
   }

   private int[] findIndexOfEatenName(String getName)
   {
      int[] pos = new int[2];
      
      for(int t = 0; t < 8; t++)
      {
        for(int u = 0; u < 8; u++)
        {
          if(matrix[t][u] == getName){
            pos[0] = t;
            pos[1] = u;
          }
        }
      }

      return pos;
   }

   private boolean containThis(List<PosCheck> posChecks2, int x, int y, CheckerType color)
   {
      if(x > 466 || x < 31 || y > 466 || y < 31)
        return false;
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
    private boolean containempt(List<PosCheck> posChecks2, int x, int y)
   {
     if(x > 466 || x < 31 || y > 466 || y < 31)
        return true;
      for (PosCheck posCheck: posChecks2)
      {
        if (posCheck.cx == x &&
            posCheck.cy == y)
         {
            return true;
         }
         //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
      }      

       return false;
   }
   private boolean containUR(List<PosCheck> posChecks2, int x, int y)
   {
     while(x < 466 && y >= 31)
     {
       x += 62;
       y -= 62;
       if(x > 466 && y < 31)
          break;
       if((x - 62 >= 31 && y + 62 < 466) && (containThis(posChecks2,x,y,CheckerType.RED_REGULAR) || containThis(posChecks2,x,y,CheckerType.RED_KING)))
       {
          if(!containempt(posChecks2,x + 62,y - 62))
          {
            return true;
          }
       }
     }
     return false;
   }
  private boolean containUL(List<PosCheck> posChecks2, int x, int y)
   {
     while(x >= 31 && y >= 31)
     {
       x -= 62;
       y -= 62;
      if(x < 31 && y < 31)
          break;
       if((x - 62 >= 31 && y + 62 < 466) && (containThis(posChecks2,x,y,CheckerType.RED_REGULAR) || containThis(posChecks2,x,y,CheckerType.RED_KING)))
       {
          if(!containempt(posChecks2,x - 62,y - 62))
          {
            return true;
          }
       }
     }
     return false;
   }
  private boolean containLR(List<PosCheck> posChecks2, int x, int y)
   {
     while(x < 466 && y < 466)
     {
       x += 62;
       y += 62;
       if(x > 466 && y > 466)
          break;
       if((x + 62 < 466 && y + 62 < 466) && (containThis(posChecks2,x,y,CheckerType.RED_REGULAR) || containThis(posChecks2,x,y,CheckerType.RED_KING)))
       {
          if(!containempt(posChecks2,x + 62,y + 62))
          {
            return true;
          }
       }
     }
     return false;
   }
  private boolean containLL(List<PosCheck> posChecks2, int x, int y)
   {
     while(x >= 31 && y < 466)
     {
       x -= 62;
       y += 62;
       if(x < 31 && y > 466)
          break;
       if((x - 62 >= 31 && y + 62 < 466) && (containThis(posChecks2,x,y,CheckerType.RED_REGULAR) || containThis(posChecks2,x,y,CheckerType.RED_KING)))
       {
          if(!containempt(posChecks2,x - 62,y + 62))
          {
            return true;
          }
       }
     }
     return false;
   }
   private boolean containking(List<PosCheck> posChecks2,int x, int y)
   {
     if(containUR(posChecks2,x,y))
     {
      System.out.println("OUT1");
       return true;   
     }
     else if(containUL(posChecks2,x,y))
     {
        System.out.println("OUT2");
       return true;
     }
     else if(containLR(posChecks2,x,y))
     {
        System.out.println("OUT3");
       return true;
     }
     else if(containLL(posChecks2,x,y))
     {
        System.out.println("OUT4");
       return true;
     }
     return false;
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
                             System.out.println(posCheck.cx+" "+posCheck.cy);
                             //System.out.println(find(oldcx,oldcy));
                            // System.out.println(must_eat);
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
                              else if(must_eat != "")
                              {
                                  if(must_eat.equals(find(oldcx,oldcy)))
                                  {
                                    if(Board.this.posCheck.checker.checkerType == CheckerType.BLACK_REGULAR )
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
                                  else if((oldcx - 124 == Board.this.posCheck.cx) && ( oldcy - 124 == Board.this.posCheck.cy) && (containThis(posChecks, Board.this.posCheck.cx + 62, Board.this.posCheck.cy + 62,CheckerType.RED_REGULAR) || 
                                    containThis(posChecks, Board.this.posCheck.cx + 62, Board.this.posCheck.cy + 62,CheckerType.RED_KING)))
                                  { 
                                      //System.out.println("HELLO");
                                      for (PosCheck posCheck: posChecks)
                                        {
                                          if (posCheck.cx == Board.this.posCheck.cx + 62 &&
                                              posCheck.cy ==  Board.this.posCheck.cy + 62 && (posCheck.checker.checkerType == CheckerType.RED_REGULAR || posCheck.checker.checkerType == CheckerType.RED_KING))
                                           {
                                              //print_matrix();
                                              queue.add(find(oldcx,oldcy));
                                             change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                             
                                             list_RED.add(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));
                                               for(int p = 0; p < list.size(); p++)
                                             {
                                                 if(list.get(p) == find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62))
                                                 {
                                                    list.remove(p);
                                                 }
                                             }
                                             System.out.println(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                                             
                                             
                                              //System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                                             remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));
                                             //print_matrix();
                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }
                                        if((Board.this.posCheck.cx + 124 < 466 && Board.this.posCheck.cy - 124 >= 31) && (containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)) )
                                        {
                                          if(containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            if(!containempt(posChecks,Board.this.posCheck.cx + 124,Board.this.posCheck.cy - 124))
                                            {
                                                still_eating = true;                              
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                               still_eating = false;
                                                must_eat = "";
                                            }                                            
                                          }
                                          else
                                          {
                                            still_eating = false;
                                            must_eat = "";
                                          }
                                          
                                        }
                                        else if((Board.this.posCheck.cx - 124 >= 31 && Board.this.posCheck.cy - 124 >= 31) && (containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)))
                                        {
                                          if(containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            if(!containempt(posChecks,Board.this.posCheck.cx - 124,Board.this.posCheck.cy - 124))
                                            {
                                                 still_eating = true;
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                               still_eating = false;
                                                must_eat = "";
                                            }                                              
                                          }
                                          else
                                          { 
                                            must_eat = "";
                                            still_eating = false;
                                          }
                                        }
                                        else
                                        {  
                                          still_eating = false;
                                          must_eat = "";
                                        }
                                  }
                                  else if((oldcx + 124 == Board.this.posCheck.cx) && ( oldcy - 124 == Board.this.posCheck.cy) && (containThis(posChecks, Board.this.posCheck.cx - 62, Board.this.posCheck.cy + 62,CheckerType.RED_REGULAR) 
                                    || containThis(posChecks, Board.this.posCheck.cx - 62, Board.this.posCheck.cy + 62,CheckerType.RED_KING)))
                                  { 
                                      //System.out.println("HELLO2");
                                      for (PosCheck posCheck: posChecks)
                                        {
                                          if (posCheck.cx == Board.this.posCheck.cx - 62 &&
                                              posCheck.cy ==  Board.this.posCheck.cy + 62 && (posCheck.checker.checkerType == CheckerType.RED_REGULAR || posCheck.checker.checkerType == CheckerType.RED_KING))
                                           {
                                              queue.add(find(oldcx,oldcy));
                                              change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                              
                                              list_RED.add(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                                for(int p = 0; p < list.size(); p++)
                                               {
                                                   if(list.get(p) == find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62))
                                                   {
                                                      list.remove(p);
                                                   }
                                               }
                                               System.out.println(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                                               
                                               
                                              //System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                              remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                              
                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }
                                        if((Board.this.posCheck.cx + 124 < 466 && Board.this.posCheck.cy - 124 >= 31) && (containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)))
                                        {
                                          if(containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            if(!containempt(posChecks,Board.this.posCheck.cx + 124,Board.this.posCheck.cy - 124))
                                            {
                                                still_eating = true;
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                               still_eating = false;
                                                must_eat = "";
                                            }
                                          }
                                          else
                                          {
                                            still_eating = false;
                                            must_eat = "";
                                          }
                                          
                                        }
                                        else if((Board.this.posCheck.cx - 124 >= 31 && Board.this.posCheck.cy - 124 >= 31) && (containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)))
                                        {
                                          if(containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            if(!containempt(posChecks,Board.this.posCheck.cx - 124,Board.this.posCheck.cy - 124))
                                            {
                                                still_eating = true;
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                               still_eating = false;
                                                must_eat = "";
                                            }
                                          }
                                          else
                                          {
                                            still_eating = false;
                                            must_eat = "";
                                          }
                                        }
                                        else
                                        {  
                                          still_eating = false;
                                          must_eat = "";
                                        }
                                  }
                  
                                  else if(Board.this.posCheck.cx == oldcx && Board.this.posCheck.cy == oldcy)
                                  {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                  }
                                  else if(Board.this.posCheck.cx != oldcx && Board.this.posCheck.cy != oldcy)
                                  {
                                    //System.out.println(Board.this.posCheck.cy + " " + oldcy);
                                    if(Board.this.posCheck.cy + 62 == oldcy)
                                    {
                                      queue.add(find(oldcx,oldcy));
                                      change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                      
                                      still_eating = false;
                                       must_eat = "";
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
                                      king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                }
                                else if(!check_kingLR(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx < Board.this.posCheck.cx) && (oldcy < Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                      king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                }
                                else if(!check_kingUL(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx > Board.this.posCheck.cx) && (oldcy > Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                      king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                }
                               else if(!check_kingUR(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx < Board.this.posCheck.cx) && (oldcy > Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                      king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                }
                                else
                                {
                                  queue.add(find(oldcx,oldcy));
                                  change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                  
                                  if(containking(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy) && king_jump)
                                  {
                                    king_jump = false;
                                    still_eating = true;
                                     must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                  }
                                  else
                                  {
                                     king_jump = false;
                                    still_eating = false;
                                    must_eat = "";
                                  }
                                }
                              }
                                  }
                                  else
                                  {
                                    System.out.println("YOU MUST PICK " + must_eat );
                                    Board.this.posCheck.cx = oldcx;
                                    Board.this.posCheck.cy = oldcy;
                                  }
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
                                  /*eating from the left */

                                  else if((oldcx - 124 == Board.this.posCheck.cx) && ( oldcy - 124 == Board.this.posCheck.cy) && (containThis(posChecks, Board.this.posCheck.cx + 62, Board.this.posCheck.cy + 62,CheckerType.RED_REGULAR) || 
                                    containThis(posChecks, Board.this.posCheck.cx + 62, Board.this.posCheck.cy + 62,CheckerType.RED_KING)))
                                  { 
                                      //System.out.println("HELLO");
                                      for (PosCheck posCheck: posChecks)
                                        {
                                          if (posCheck.cx == Board.this.posCheck.cx + 62 &&
                                              posCheck.cy ==  Board.this.posCheck.cy + 62 && (posCheck.checker.checkerType == CheckerType.RED_REGULAR || posCheck.checker.checkerType == CheckerType.RED_KING))
                                           {
                                              //print_matrix();
                                            queue.add(find(oldcx,oldcy));
                                             change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));

                                             
                                             
                                             list_RED.add(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));
                                               for(int p = 0; p < list.size(); p++)
                                             {
                                                 if(list.get(p) == find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62))
                                                 {
                                                    list.remove(p);
                                                 }
                                             }
                                             System.out.println(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                                             
                                             
                                             remove(find(Board.this.posCheck.cx + 62,Board.this.posCheck.cy + 62));

                                             //print_matrix();
                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }
                                        if((Board.this.posCheck.cx + 124 < 466 && Board.this.posCheck.cy - 124 >= 31) && (containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)) )
                                        {
       
                                          if(containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            //System.out.println("HI34");
                                            if(!containempt(posChecks,Board.this.posCheck.cx + 124,Board.this.posCheck.cy - 124))
                                            {
                                              //System.out.println("HI36");
                                                 still_eating = true;
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                              //System.out.println("HI35");
                                               still_eating = false;
                                                must_eat = "";
                                            }                                            
                                          }
                                          else
                                          {
                                            still_eating = false;
                                            must_eat = "";
                                          }
                                          
                                        }
                                        else if((Board.this.posCheck.cx - 124 >= 31 && Board.this.posCheck.cy - 124 >= 31) && (containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)))
                                        {
                                          if(containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            if(!containempt(posChecks,Board.this.posCheck.cx - 124,Board.this.posCheck.cy - 124))
                                            {
                                                still_eating = true;
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                               still_eating = false;
                                                must_eat = "";
                                            }                                              
                                          }
                                          else
                                          { 
                                            must_eat = "";
                                            still_eating = false;
                                          }
                                        }
                                        else
                                        {  
                                          still_eating = false;
                                          must_eat = "";
                                        }
                                  }

                                  /*eating from the right  */
                                  else if((oldcx + 124 == Board.this.posCheck.cx) && ( oldcy - 124 == Board.this.posCheck.cy) && (containThis(posChecks, Board.this.posCheck.cx - 62, Board.this.posCheck.cy + 62,CheckerType.RED_REGULAR) 
                                    || containThis(posChecks, Board.this.posCheck.cx - 62, Board.this.posCheck.cy + 62,CheckerType.RED_KING)))
                                  { 
                                      //System.out.println("HELLO2");
                                      for (PosCheck posCheck: posChecks)
                                        {
                                          if (posCheck.cx == Board.this.posCheck.cx - 62 &&
                                              posCheck.cy ==  Board.this.posCheck.cy + 62 && (posCheck.checker.checkerType == CheckerType.RED_REGULAR || posCheck.checker.checkerType == CheckerType.RED_KING))
                                           {
                                              queue.add(find(oldcx,oldcy));
                                              change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                              
                                              list_RED.add(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                                for(int p = 0; p < list.size(); p++)
                                               {
                                                   if(list.get(p) == find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62))
                                                   {
                                                      list.remove(p);
                                                   }
                                               }
                                               System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62) + " " + Board.this.posCheck.cx);
                                               
                                               
                                              //System.out.println(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));
                                              remove(find(Board.this.posCheck.cx - 62,Board.this.posCheck.cy + 62));

                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
                                           }
                                           //System.out.println(x + " = " + posCheck.cx + " , " + y  + " = " + posCheck.cy);
                                        }
                                        if((Board.this.posCheck.cx + 124 < 466 && Board.this.posCheck.cy - 124 >= 31) &&
                                          (containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)))
                                        {
                                          //System.out.println("OUTPUT20");
                                          if(containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx + 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            //System.out.println("HI2");
                                            if(!containempt(posChecks,Board.this.posCheck.cx + 124,Board.this.posCheck.cy - 124))
                                            {
                                               //System.out.println("HELELE");
                                               still_eating = true;
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                               still_eating = false;
                                                must_eat = "";
                                            }
                                          }
                                          else
                                          {
                                            still_eating = false;
                                            must_eat = "";
                                          }
                                          
                                        }
                                        else if((Board.this.posCheck.cx - 124 >= 31 && Board.this.posCheck.cy - 124 >= 31) && (containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING)))
                                        {
                                           //System.out.println("OUTPUT21");
                                          if(containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_REGULAR) || containThis(posChecks,Board.this.posCheck.cx - 62,Board.this.posCheck.cy - 62,CheckerType.RED_KING))
                                          {
                                            if(!containempt(posChecks,Board.this.posCheck.cx - 124,Board.this.posCheck.cy - 124))
                                            {
                                                 still_eating = true;
                                                must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                            }
                                            else
                                            {
                                               still_eating = false;
                                                must_eat = "";
                                            }
                                          }
                                          else
                                          {
                                            still_eating = false;
                                            must_eat = "";
                                          }
                                        }
                                        else
                                        {  
                                          still_eating = false;
                                          must_eat = "";
                                        }
                                  }
                  
                                  else if(Board.this.posCheck.cx == oldcx && Board.this.posCheck.cy == oldcy)
                                  {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                  }
                                  else if(Board.this.posCheck.cx != oldcx && Board.this.posCheck.cy != oldcy)
                                  {
                                    //System.out.println(Board.this.posCheck.cy + " " + oldcy);
                                    if(Board.this.posCheck.cy + 62 == oldcy)
                                    {
                                      queue.add(find(oldcx,oldcy));
                                      change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));

                                      still_eating = false;
                                       must_eat = "";
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
                                     king_jump = false;
                                    still_eating = false;
                                     must_eat = "";

                                }
                                else if(!check_kingLR(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx < Board.this.posCheck.cx) && (oldcy < Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                      king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                }
                                else if(!check_kingUL(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx > Board.this.posCheck.cx) && (oldcy > Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                      king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                }
                               else if(!check_kingUR(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy,oldcx,oldcy) && (oldcx < Board.this.posCheck.cx) && (oldcy > Board.this.posCheck.cy))
                                {
                                      Board.this.posCheck.cx = oldcx;
                                      Board.this.posCheck.cy = oldcy;
                                      king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                }
                                else
                                {
                                  queue.add(find(oldcx,oldcy));
                                  change(Board.this.posCheck.cx,Board.this.posCheck.cy,find(oldcx,oldcy));
                                  if(containking(posChecks,Board.this.posCheck.cx,Board.this.posCheck.cy) && king_jump)
                                  {
                                    king_jump = false;
                                    still_eating = true;
                                    System.out.println("PROBLEM");
                                     must_eat = find(Board.this.posCheck.cx,Board.this.posCheck.cy);
                                  }
                                  else
                                  {
                                    king_jump = false;
                                    still_eating = false;
                                     must_eat = "";
                                  }
                                }
                              }

                            if(still_eating == false)
                            {
                              AI_activate(posChecks);
                              still_eating = true;
                            }
                            //printout_queue(); 
                            //print_matrix();
                            //System.out.println(queue.element());
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

      try {
        saveThings();
      } catch (IOException e) {
          System.err.println("Caught IOException: " + e.getMessage());
      }

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

   private void saveThings() throws IOException{
   try {
        if (manager.stateDoesExist(matrix, queue) == null){
              manager.setMatrix(matrix);

              poss_moveX.clear();
              poss_moveY.clear();

              for(int t = 0; t < 8; t++)
              {
                for(int u = 0; u < 8; u++)
                {
                    // System.out.println(copy.get(j));
                    if(legalCheckFunction(matrix[t][u])) {
                      all_move(matrix[t][u]);
                    }
                }
              }

              manager.setScoreOnPossibleMoves(poss_moveY, poss_moveX);
              manager.setQueueOrder(queue);
              System.out.println(queue);

              manager.printToFile();
            } else {
                System.out.println(queue);
                System.err.println("State Exists..");    
            }

      } catch (IOException e) {
          System.err.println("Caught IOException: " + e.getMessage());
      }
  }

  private boolean legalCheckFunction(String name) {
      if(name == "R1")
        return true;
      if(name == "R2")
        return true;
      if(name == "R3")
        return true;
      if(name == "R4")
        return true;
      if(name == "R5")
        return true;
      if(name == "R6")
        return true;
      if(name == "R7")
        return true;
      if(name == "R8")
        return true;

      return false;
  }
      
}