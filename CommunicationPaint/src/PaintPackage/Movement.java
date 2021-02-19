package PaintPackage;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Movement implements MouseMotionListener,MouseListener{
	
	int x,y;
	
    public Movement(Component... component) {
		
    	for(Component component2 : component) {
    		component2.addMouseListener(this);
    		component2.addMouseMotionListener(this);
    	}
    	
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		
		event.getComponent().setLocation(event.getX()+event.getComponent().getX(), event.getY()+event.getComponent().getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent event) {
		
		x=event.getX();
		y=event.getY();
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}

