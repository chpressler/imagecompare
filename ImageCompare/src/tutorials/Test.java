package tutorials;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * To reproduce the problem: run this class with an image on the command line.
 * It should display the image at natural size.  Then, resize the image.  A
 * scroll bar should appear; the image will fill the resized window as much as
 * possible.  Finally, bring the image back to the natural size.  Java gets
 * stuck in a loop trying to figure out which scrollbar to add.
 */
public class Test {
    public static void main(String[] args) {
        try {
            JFrame f = new JFrame();
            ImageDisplay id =
                new ImageDisplay
                (   ImageIO.read(new FileInputStream(new File("test.jpg")))
                );
            f.getContentPane().add(new JScrollPane(id));
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.pack();
            f.show();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
class ImageDisplay extends JComponent {
    BufferedImage image;
    float ratio = 0;
    int myWidth = 0, myHeight = 0;
    public ImageDisplay(BufferedImage bi) {
        myWidth = bi.getWidth();
        myHeight = bi.getHeight();
        image = bi;
        ratio = ((float) myWidth) / myHeight;
        this.setPreferredSize(new Dimension(myWidth,myHeight));
    }
    public void paintComponent( Graphics g ) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle r = this.getParent().getBounds();
        float rectangleRatio = ((float) r.width)/r.height;
        if ( ratio <= rectangleRatio ) {
            myWidth = r.width;
            myHeight = Math.round( ((float) (r.width*image.getWidth()) )
                    / image.getHeight());
        } else {
            myHeight = r.height;
            myWidth = Math.round( ((float) (r.height*image.getWidth()) )
                    / image.getHeight() );
        }
        g2d.drawImage(image,0,0,myWidth,myHeight,this);
        this.setPreferredSize(new Dimension(myWidth,myHeight));
        this.revalidate();
    }
}
