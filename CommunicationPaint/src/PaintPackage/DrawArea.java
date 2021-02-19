package PaintPackage;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class DrawArea extends JComponent{
	
	Graphics2D graphics2d;
	private Image image;
	int currX,currY,prevX,prevY;
	
	public DrawArea() {
		
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				
				prevX=e.getX();
				prevY=e.getY();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				currX=e.getX();
				currY=e.getY();
				//System.out.println("Mouse dragged");
				
				if(graphics2d != null) {
					if(PaintFrame.slelectedShape==1 && PaintFrame.eraser!=1 ) {
					    graphics2d.drawOval(prevX, prevY, 20, 20);
					    graphics2d.setStroke(new BasicStroke(PaintFrame.thickValue));
					}
					else if(PaintFrame.slelectedShape==2 && PaintFrame.eraser!=1) {
						graphics2d.drawRect(prevX, prevY, 20, 20);
						 graphics2d.setStroke(new BasicStroke(PaintFrame.thickValue));
					}
					else if(PaintFrame.slelectedShape==3 && PaintFrame.eraser!=1) {
						graphics2d.drawLine(prevX, prevY, currX, currY);
						graphics2d.setStroke(new BasicStroke(PaintFrame.thickValue));
						
					}
					else if(PaintFrame.slelectedShape==4 && PaintFrame.eraser!=1) {
						graphics2d.drawArc(prevX, prevY, currX/2, currY/2, 60, -60);
						graphics2d.setStroke(new BasicStroke(PaintFrame.thickValue));
						
					}
					else if(PaintFrame.slelectedShape==5 && PaintFrame.eraser!=1) {
						graphics2d.draw3DRect(prevX, prevY, 20, 20, false);
						graphics2d.setStroke(new BasicStroke(PaintFrame.thickValue));
						
					}
					
					else if(PaintFrame.slelectedShape==6 && PaintFrame.eraser!=1) {
						drawStar();
						graphics2d.setStroke(new BasicStroke(PaintFrame.thickValue));
						
					}
					else if(PaintFrame.slelectedShape==7 && PaintFrame.eraser!=1) {
						Ellipse2D.Double circle=new Ellipse2D.Double(prevX,prevY,40,20);
						graphics2d.draw(circle);
						graphics2d.setStroke(new BasicStroke(PaintFrame.thickValue));
						
					}
					
					if(PaintFrame.eraser==1) {
						erase();
					}
					
					repaint();
					
					prevX=currX;
					prevY=currY;
				}
				
			}
			
			
			
			
		});
	}
	
	protected void paintComponent(Graphics g) {
	    if (image == null) {
	      // image to draw null ==> we create
	      //image = createImage(getSize().width, getSize().height);
	    	image = createImage(500, 500);
	      graphics2d = (Graphics2D) image.getGraphics();
	      // enable antialiasing
	      graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	      // clear draw area
	      clear();
	    }
	 
	    g.drawImage(image, 0, 0, null);
	  }
	
	public void clear() {
	    graphics2d.setPaint(Color.white);
	    // draw white on entire draw area to clear
	    graphics2d.fillRect(0, 0, getSize().width, getSize().height);
	    graphics2d.setPaint(Color.black);
	    repaint();
	  }
	 
	
	  public void choosedColor(Color c) {
		  graphics2d.setPaint(c); 
	  }
	  
	  public void erase() {
		    Color c = graphics2d.getColor();
		    graphics2d.setColor(Color.white);
		    graphics2d.drawRect(currX, currY, 10, 10);
		    graphics2d.fillRect(currX, currY, 10, 10);
		    graphics2d.setColor(c);
	        repaint();
	}
	  
	  public void drawStar() {
	        int xPoints[] = {9, 15, 0, 18, 3};
	        int yPoints[] = {0, 18, 6, 6, 18};

	        //Graphics2D g2d = (Graphics2D) g;
	        GeneralPath star = new GeneralPath();

	        star.moveTo(xPoints[0] + currX, yPoints[0] + currY);
	        for (int i = 1; i < xPoints.length; i++) {
	            star.lineTo(xPoints[i] + currX, yPoints[i] + currY);
	        }
	        star.closePath();

	        graphics2d.draw(star);
	    }
	  
	  public void drawShape(int x,int y) {
		  graphics2d.drawRect(x, y, 40, 40);
	  }

}
