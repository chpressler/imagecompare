package tutorials;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
 
public class ChessBoard extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener
{
    JLayeredPane layeredPane;
    JPanel chessBoard;
    JLabel chessPiece;
    int xAdjustment;
    int yAdjustment;
    
    BufferedImage image;
    int width;
    int height;
 
    public ChessBoard()
    {
        Dimension boardSize = new Dimension(600, 600);
 
        //  Use a Layered Pane for this this application
 
        layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);
        layeredPane.setPreferredSize( boardSize );
        layeredPane.addMouseListener( this );
        layeredPane.addMouseMotionListener( this );
        layeredPane.addMouseWheelListener(this);
 
        //  Add a chess board to the Layered Pane
 
        chessBoard = new JPanel();
        layeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);
        chessBoard.setLayout( new GridLayout(8, 8) );
        chessBoard.setPreferredSize( boardSize );
        chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
 
        for (int i = 0; i < 64; i++)
        {
            JPanel square = new JPanel( new BorderLayout() );
            chessBoard.add( new JPanel() );
            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground( i % 2 == 0 ? Color.red : Color.white );
            else
                square.setBackground( i % 2 == 0 ? Color.white : Color.red );
        }
 
        // Add a few pieces to the board
 
        JLabel piece = new JLabel( new ImageIcon("test.jpg") );
        JPanel panel = (JPanel)chessBoard.getComponent( 0 );
        panel.add( piece );
        piece = new JLabel( new ImageIcon("test.jpg") );
        panel = (JPanel)chessBoard.getComponent( 0 );
        panel.add( piece );
    }
 
    /*
    **  Add the selected chess piece to the dragging layer so it can be moved
    */
    public void mousePressed(MouseEvent e)
    {
        chessPiece = null;
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
 
        if (c instanceof JPanel) return;
 
        Point parentLocation = c.getParent().getLocation();
        System.out.println("sfdgdfgdf");
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        chessPiece = (JLabel)c;
        chessPiece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        chessPiece.setSize(chessPiece.getWidth(), chessPiece.getHeight());
        layeredPane.add(chessPiece, JLayeredPane.DRAG_LAYER);
    }
 
    /*
    **  Move the chess piece around
    */
    public void mouseDragged(MouseEvent me)
    {
        if (chessPiece == null) return;
 
        chessPiece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
     }
 
    /*
    **  Drop the chess piece back onto the chess board
    */
    public void mouseReleased(MouseEvent e)
    {
        if (chessPiece == null) return;
 
        chessPiece.setVisible(false);
        Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
 
        if (c instanceof JLabel)
        {
            Container parent = c.getParent();
            parent.remove(0);
            parent.add( chessPiece );
        }
        else
        {
            Container parent = (Container)c;
            parent.add( chessPiece );
        }
 
        chessPiece.setVisible(true);
    }
 
    public void mouseClicked(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
 
    public static void main(String[] args)
    {
        JFrame frame = new ChessBoard();
        frame.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable( false );
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
     }

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
	 
	    if (c instanceof JPanel) return;
	        
		if(e.getWheelRotation() > 0) {
			width /= 1.15;
			height /= 1.15;
		}
		else if(e.getWheelRotation() < 0) {
			width *= 1.15;
			height *= 1.15;
		}
		
		c.setSize(width, height);
		
		new Thread(new Runnable() {
			public void run() {
				chessPiece.setBounds(width, height, width, height);
				
				if (((Graphics2D) chessPiece.getGraphics()) != null) {
		
					chessPiece.setPreferredSize(new Dimension(width,
							height));
					((Graphics2D) chessPiece.getGraphics()).drawImage(image, 0, 0, width, height, 0, 0, image
							.getWidth(), image.getHeight(), null);

				}
			}}).start();
	};
}

