package Checkers;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import java.util.ArrayList;
import java.util.List;

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

   // displacement between drag start coordinates and checker center coordinates

   private int deltax, deltay;

   // reference to positioned checker at start of drag

   private PosCheck posCheck;

   // center location of checker at start of drag

   private int oldcx, oldcy;

   // list of Checker objects and their initial positions

   private List<PosCheck> posChecks;

   public int[][] x_matrix = new int[8][8];
   public int[][] y_matrix = new int[8][8];

   public String[][] matrix = new String[8][8];

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
                              else if(Board.this.posCheck.checker.checkerType != CheckerType.BLACK_KING)
                              {
                    
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
                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
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
                                              posCheck.cx = 527;
                                              posCheck.cy = 403;
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
                                }
                                

                              
                                if(Board.this.posCheck.checker.checkerType == CheckerType.RED_REGULAR)
                                {
                                  Board.this.posCheck.cx = oldcx;
                                  Board.this.posCheck.cy = oldcy;
                                }
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