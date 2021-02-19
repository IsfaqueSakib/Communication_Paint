package ServerSite;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.Signature;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import PaintPackage.ImageJTextArea;
import PaintPackage.PaintFrame;

public class Server {
	
	static ServerSocket serverSocket;
	static Socket socket;
	static DataInputStream dInputStream;
	static DataOutputStream dOutputStream;
	
	public static ImageJTextArea inbox,commentTextArea;
	 

	public static void main(String[] args) {
		
		 inbox=new ImageJTextArea(new File("src/Images/inbox.png"));
		 commentTextArea=new ImageJTextArea(new File("src/Images/chat.jpg"));
		 
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				PaintFrame paintFrame=new PaintFrame();
				paintFrame.setTitle("Server Site");
				paintFrame.setResizable(false);
				
				
				// Comment TextArea Portion Started
				
					  JPanel panel=new JPanel();
						
					    //commentTextArea=new JTextArea();
						commentTextArea.setToolTipText("  Write a Comment");
						commentTextArea.setBackground(Color.green);
						commentTextArea.setLineWrap(true);
						commentTextArea.setWrapStyleWord(true);
						commentTextArea.setFont(new Font("calibri", Font.PLAIN,20));
						commentTextArea.setForeground(Color.BLUE);
						
						JScrollPane scrollPane2=new JScrollPane(commentTextArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollPane2.setPreferredSize(new Dimension(500,150));
						panel.add(scrollPane2);
						//commentTextArea.setPreferredSize(new Dimension(500,80));
						//panel.add(commentTextArea);
						
						ImageIcon sendIcon=new ImageIcon("src/Images/send.png");
						JButton sendButton=new JButton(sendIcon);
						sendButton.setBackground(Color.white);
						panel.add(sendButton);
						
						
						paintFrame.getContentPane().add(panel,BorderLayout.SOUTH);
						
						JPanel panel2=new JPanel();
						
						
						inbox.setBackground(Color.YELLOW);
						inbox.setEditable(false);
						inbox.setLineWrap(true);
						inbox.setWrapStyleWord(true);
						inbox.setFont(new Font("calibri", Font.PLAIN,20));
						inbox.setForeground(Color.RED);
						inbox.setCaretColor(Color.white);
						
						JScrollPane scrollPane=new JScrollPane(inbox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						scrollPane.setPreferredSize(new Dimension(275, 500));
						panel2.add(scrollPane);
						
						paintFrame.getContentPane().add(panel2,BorderLayout.EAST);
						
					// Comment TextArea Portion Finished
						
						sendButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								if(commentTextArea.getText().equals("sign out")) {
									System.exit(0);
								}
								
								try {
									
									String msgout="";
									msgout=commentTextArea.getText().trim();
									dOutputStream.writeUTF(msgout);
									commentTextArea.setText("");
									
								} catch (Exception ex) {
									System.out.println("Something Wrong !!");
								}
								
								
							}
						});
				
				paintFrame.pack();
				paintFrame.setVisible(true);
				
			}
		});
		
		String msgin="";
		try {
			serverSocket=new ServerSocket(1201);
			socket=serverSocket.accept();
			
			dInputStream=new DataInputStream(socket.getInputStream());
			dOutputStream=new DataOutputStream(socket.getOutputStream());
			
			while(!msgin.equals("please sign out")) {
				msgin=dInputStream.readUTF();
				inbox.setText(inbox.getText().trim()+"\n"+" Client : "+msgin);
			}
			
			if(msgin.equals("please sign out")) {
				System.exit(0);
			}
			
		} catch (IOException e) {
			System.out.println("Something Wrong");
		}

	}

}

