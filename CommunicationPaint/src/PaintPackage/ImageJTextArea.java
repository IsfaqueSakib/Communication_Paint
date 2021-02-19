package PaintPackage;

import java.awt.Graphics;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class ImageJTextArea extends JTextArea
{
File image;
    public ImageJTextArea(File image)
    {
        setOpaque(false);
        this.image=image;
    }
    
    public void paintComponent(final Graphics g)
    {
        try
        {
        // Scale the image to fit by specifying width,height
        g.drawImage(new ImageIcon(image.getAbsolutePath()).getImage(),0,0,getWidth(),getHeight(),this);
        super.paintComponent(g);
        }catch(Exception e){}
    }
}