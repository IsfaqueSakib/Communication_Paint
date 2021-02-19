package PaintPackage;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.omg.CORBA.PUBLIC_MEMBER;

import ClientSite.Client;
import ServerSite.Server;

public class PaintFrame extends JFrame implements MenuListener,ActionListener,ItemListener,MouseListener,MouseMotionListener{
	
	Container container;
	JMenuBar menuBar;
	Graphics2D g2;
	public static Color color=Color.black;
	int eraseX,eraseY;
	JCheckBoxMenuItem shapeItemsOval,shapeItemsRect,shapeItemsLine,shapeItemsArc,shapeitem3DRect,shapeItemsStar,shapeItemsEllipse;
	ButtonGroup buttonGroup;
	public static int slelectedShape=3,thickValue=2,eraser=0,x=30,y=30,insertOption=1;
	JSlider thicknessSlider;
	JPanel controls;
	transient JFrame frame;
	JMenuItem menuItemRect,menuItemOval,menuItemLine,menuItemFreeHand;
	JMenuItem menuItemActionsDraw,menuItemActionsMove,menuItemActionsResize,menuItemActionsDel;
	JMenuItem bgColor,paintColor;
	JTextArea textArea;
	DrawArea drawArea=new DrawArea();
	
	public PaintFrame(){
		initComponents();
	}
	
	void initComponents() {
		
		this.setTitle("Communication Paint");
		this.setBounds(200, 10, 800, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//this.setResizable(false);
		
		container=this.getContentPane();
		container.setLayout(new BorderLayout());
		super.setPreferredSize(new Dimension(800,700));
		

		
//	// Comment TextArea Portion Started
//		
//		JPanel panel=new JPanel();
//		panel.setBounds(0, 520, 500, 140);
//		//panel.setBackground(Color.white);
//		
//		JTextArea commentTextArea=new JTextArea();
//		commentTextArea.setText("  Write a Comment");
//		commentTextArea.setBackground(Color.white);
//		commentTextArea.setPreferredSize(new Dimension(500,80));
//		panel.add(commentTextArea);
//		
//		JButton sendButton=new JButton("Send");
//		sendButton.setBackground(Color.GREEN);
//		panel.add(sendButton);
//		
//		container.add(panel);
//		
//	// Comment TextArea Portion Finished
		
		container.add(drawArea,BorderLayout.CENTER);
	    controls=new JPanel(new BorderLayout());
		container.add(controls,BorderLayout.NORTH);
		container.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		
		
		menuBar=new JMenuBar();
		menuBar.setBackground(Color.BLACK);
		this.setJMenuBar(menuBar);
		JMenu file = new JMenu("File");
		file.setForeground(Color.RED);
		menuBar.add(file);
		menuBar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		
    	JMenuItem newpage=new JMenuItem("New");
    	file.add(newpage);
    	newpage.setIcon(new ImageIcon("src/Images/add.png"));
		
		JMenuItem open=new JMenuItem("Open");
		file.add(open);
		open.setIcon(new ImageIcon("src/Images/folder.png"));
		
		JMenuItem save=new JMenuItem("Save");
		file.add(save);
		save.setIcon(new ImageIcon("src/Images/save.png"));
		
		JMenuItem saveas=new JMenuItem("Save As");
		file.add(saveas);
		saveas.setIcon(new ImageIcon("src/Images/saveas.png"));
		
		JMenuItem exit=new JMenuItem("Exit");
		file.add(exit);
		exit.setIcon(new ImageIcon("src/Images/exit.png"));
		
		JMenu erase = new JMenu("Eraser");
		erase.setForeground(Color.WHITE);
		menuBar.add(erase);
		
		JMenu color = new JMenu("Color");
		color.setForeground(Color.RED);
		menuBar.add(color);
		
		bgColor=new JMenuItem("Background");
		bgColor.setIcon(new ImageIcon("src/Images/background.png"));
		color.add(bgColor);
		
		paintColor=new JMenuItem("Paint");
		paintColor.setIcon(new ImageIcon("src/Images/bgColorPaint.png"));
		color.add(paintColor);
		
		JMenu shape = new JMenu("Shape");
		shape.setForeground(Color.WHITE);
		menuBar.add(shape);
		
	   shapeItemsOval=new JCheckBoxMenuItem("Oval");
	   shapeItemsOval.setIcon(new ImageIcon("src/Images/oval.png"));
	   shapeItemsOval.setBackground(Color.green);
	   shapeItemsOval.setSelected(false);
	   shape.add(shapeItemsOval);
	   
	   shapeItemsRect=new JCheckBoxMenuItem("Rectangle");
	   shapeItemsRect.setIcon(new ImageIcon("src/Images/rectangle.png"));
	   shapeItemsRect.setBackground(Color.yellow);
	   shapeItemsRect.setSelected(false);
	   shape.add(shapeItemsRect);
	   
	   shapeItemsLine=new JCheckBoxMenuItem("Line");
	   shapeItemsLine.setIcon(new ImageIcon("src/Images/line.png"));
	   shapeItemsLine.setBackground(Color.green);
	   shapeItemsLine.setSelected(false);
	   shape.add(shapeItemsLine);
	   
	   shapeItemsArc=new JCheckBoxMenuItem("Arc");
	   shapeItemsArc.setIcon(new ImageIcon("src/Images/arc.png"));
	   shapeItemsArc.setBackground(Color.yellow);
	   shapeItemsArc.setSelected(false);
	   shape.add(shapeItemsArc);
	   
	   shapeitem3DRect=new JCheckBoxMenuItem("3D Rectangle");
	   shapeitem3DRect.setIcon(new ImageIcon("src/Images/3D_rectangle.png"));
	   shapeitem3DRect.setBackground(Color.green);
	   shapeitem3DRect.setSelected(false);
	   shape.add(shapeitem3DRect);
	   
	   shapeItemsStar=new JCheckBoxMenuItem("Star");
	   shapeItemsStar.setIcon(new ImageIcon("src/Images/star.png"));
	   shapeItemsStar.setBackground(Color.yellow);
	   shapeItemsStar.setSelected(false);
	   shape.add(shapeItemsStar);
	   
	   shapeItemsEllipse=new JCheckBoxMenuItem("Ellipse");
	   shapeItemsEllipse.setIcon(new ImageIcon("src/Images/ellipse.png"));
	   shapeItemsEllipse.setBackground(Color.green);
	   shapeItemsEllipse.setSelected(false);
	   shape.add(shapeItemsEllipse);
	   
	   buttonGroup=new ButtonGroup();
	   buttonGroup.add(shapeItemsOval);
	   buttonGroup.add(shapeItemsRect);
	   buttonGroup.add(shapeItemsLine);
	   buttonGroup.add(shapeItemsArc);
	   buttonGroup.add(shapeitem3DRect);
	   buttonGroup.add(shapeItemsStar);
	   buttonGroup.add(shapeItemsEllipse);
	   
	   JMenu thickness = new JMenu("Thickness");
	   thickness.setForeground(Color.RED);
	   menuBar.add(thickness);
		
	   JMenuItem thick=new JMenuItem("Choose Thickness");
	   thickness.add(thick);
	   thicknessSlider=new JSlider(0, 1, 8, 1);
	   thickness.add(thicknessSlider);
	   
	   thicknessSlider.addChangeListener(new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider js=(JSlider) e.getSource();
			thickValue=js.getValue();
		}
	});
	   
	  JMenu insertShape = new JMenu("Insert Shape");
	   //menuBar.add(insertShape);
	   
	   JMenu actions=new JMenu("Action");
	   
	   menuItemActionsDraw = new JMenuItem("Draw");
	  // actions.add(menuItemActionsDraw);
	   menuItemActionsMove = new JMenuItem("Move");
	 //  actions.add(menuItemActionsMove);
	   menuItemActionsResize = new JMenuItem("Resize");
	 //  actions.add(menuItemActionsResize);
	   menuItemActionsDel = new JMenuItem("Delete");
	 //  actions.add(menuItemActionsDel);
	   
	   insertShape.add(actions);
	   
	   menuItemRect = new JMenuItem("Rect");
	   insertShape.add(menuItemRect);
	   menuItemOval = new JMenuItem("Oval");
	   insertShape.add(menuItemOval);
	   menuItemLine = new JMenuItem("Line");
	   insertShape.add(menuItemLine);
	   menuItemFreeHand= new JMenuItem("FreeHand");
	   insertShape.add(menuItemFreeHand);
		
	   shapeItemsOval.addItemListener(this);
	   shapeItemsRect.addItemListener(this);
	   shapeItemsLine.addItemListener(this);
	   shapeItemsArc.addItemListener(this);
	   shapeitem3DRect.addItemListener(this);
	   shapeItemsStar.addItemListener(this);
	   shapeItemsEllipse.addItemListener(this);
		
		file.addMenuListener(this);
		erase.addMenuListener(this);
		
		exit.addActionListener(this);
		save.addActionListener(this);
		open.addActionListener(this);
		//color.addMenuListener(this);
		bgColor.addActionListener(this);
		paintColor.addActionListener(this);
		
		 menuItemActionsDraw.addActionListener(this);
	     menuItemActionsMove.addActionListener(this);
	     menuItemActionsResize.addActionListener(this);
   	     menuItemActionsDel.addActionListener(this);
   	     
   	    menuItemRect.addActionListener(this);
        menuItemOval.addActionListener(this);
        menuItemLine.addActionListener(this);
        menuItemFreeHand.addActionListener(this);
        
       /* JPanel southPannel=new JPanel(new BorderLayout());
        //controls.add(southPannel,BorderLayout.SOUTH);
        southPannel.setSize(100, 100);
        container.add(southPannel,BorderLayout.PAGE_END);
        
        
        textArea=new JTextArea();
        //textArea.setBounds(100, 600, 100, 300);
        textArea.setBackground(Color.YELLOW);
        southPannel.add(textArea);*/
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Exit")) {
			System.exit(0);
		}
		
		if(e.getActionCommand().equals("Background")) {
		
			JColorChooser colorChooser=new JColorChooser();
	    	  color=colorChooser.showDialog(null, "Choose Color", Color.black);
	    	 //drawArea.changeBGcolor(color);
	    	 container.setBackground(color);
			
		}
		
		if(e.getActionCommand().equals("Paint")) {
			
			JColorChooser colorChooser=new JColorChooser();
	    	  color=colorChooser.showDialog(null, "Choose Color", Color.black);
	    	 drawArea.choosedColor(color);
			
		}
		
		if(e.getActionCommand().equals("Open")) {
	    	System.out.println("Open");
	    	JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Select an image");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG and GIF images", "png", "gif");
			jfc.addChoosableFileFilter(filter);

			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				System.out.println(jfc.getSelectedFile().getPath());
			}
	    }
		
		if(e.getActionCommand().equals("Save")) {
			
			 String path = System.getProperty("user.home") + "/Desktop".replace("\\", "/");
	    	 JFileChooser fileChooser = new JFileChooser(path);
	    	 int returnVal = fileChooser.showSaveDialog(frame);
	    	 //If user chooses to save the file
	    	 if (returnVal == JFileChooser.APPROVE_OPTION) {
	    	 File file = fileChooser.getSelectedFile();
	    	 try {
	    	 FileOutputStream fs = new FileOutputStream(file);
	    	 ObjectOutputStream os = new ObjectOutputStream(fs);
	    	 BufferedImage bi = new BufferedImage(getWidth(), getHeight(),
	    	 BufferedImage.TYPE_INT_BGR);
	    	 Graphics2D g = bi.createGraphics();
	    	// ByteArrayOutputStream bout = new ByteArrayOutputStream();
	    	    // write buffered image to output stream
	    	 ImageIO.write(bi, "jpg", os);
	    	// os.writeObject(panel);
	    	 os.close();
	    	 // XMLEncoder saveFile = new XMLEncoder ( new BufferedOutputStream ( new FileOutputStream(file)) );
	    	 // saveFile.writeObject(sp);
	    	 // saveFile.close();
	    	 } catch (IOException ex) {
	    	 ex.printStackTrace();
	    	 }
	     } 	 
			
		}
		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuSelected(MenuEvent e) {
		 JMenu myMenu = (JMenu) e.getSource();
	     
	      if(myMenu.getText().equals("Eraser")) {
	    	  //drawArea.clear(); 
	    	  eraser=1;
	    	  //drawArea.drawShape(x, y);
	      }
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(e.getItem()==shapeItemsOval) {
			slelectedShape=1;
			eraser=0;
		}
		if(e.getItem()==shapeItemsRect) {
			slelectedShape=2;
			eraser=0;
		}
		if(e.getItem()==shapeItemsLine) {
			slelectedShape=3;
			eraser=0;
		}
		if(e.getItem()==shapeItemsArc) {
			slelectedShape=4;
			eraser=0;
		}
		if(e.getItem()==shapeitem3DRect) {
			slelectedShape=5;
			eraser=0;
		}
		if(e.getItem()==shapeItemsStar) {
			slelectedShape=6;
			eraser=0;
		}
		
		if(e.getItem()==shapeItemsEllipse) {
			slelectedShape=7;
			eraser=0;
		}
		
		if(e.getItem()==menuItemRect) {
			insertOption=1;
		}
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x=e.getX();
		y=e.getY();
		//System.out.println("X = "+x+"    Y = "+y);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse moved");
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse entered");
		//drawArea.drawShape(20, 20);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse exited");
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("Mouse pressed");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse released");
		
	}
	
}