package Comparator;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.event.ActionEvent;

public class App_UI {

	private JFrame frame;
	private JTextField pathField1;
	private JTextField pathField2;
	private JTextField pathField3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App_UI window = new App_UI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public App_UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		/*Main window */
		frame = new JFrame();
		frame.setBounds(100, 100, 584, 323);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		/*Create field for input path for Folder №1*/
		pathField1 = new JTextField();
		pathField1.setBounds(12, 50, 433, 22);
		frame.getContentPane().add(pathField1);
		pathField1.setColumns(10);
		/*Create field for input path for Folder №2*/
		pathField2 = new JTextField();
		pathField2.setBounds(12, 114, 433, 22);
		frame.getContentPane().add(pathField2);
		pathField2.setColumns(10);
		/*Create field for input path for result folder */
		pathField3 = new JTextField();
		pathField3.setBounds(12, 178, 433, 22);
		frame.getContentPane().add(pathField3);
		pathField3.setColumns(10);
		
		/*UI description of pathField1*/
		JLabel lblNewLabel = new JLabel("Folder №1:");
		lblNewLabel.setBounds(12, 29, 71, 16);
		frame.getContentPane().add(lblNewLabel);
		/*UI description of pathField2*/
		JLabel lblNewLabel_1 = new JLabel("Folder №2:");
		lblNewLabel_1.setBounds(12, 85, 71, 16);
		frame.getContentPane().add(lblNewLabel_1);
		/*UI description of pathField3*/
		JLabel lblNewLabel_2 = new JLabel("Folder with results:");
		lblNewLabel_2.setBounds(12, 149, 150, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		/*
		 * Create button for choosing folder №1 path  */
		JButton explorerBtn1 = new JButton("Select");
		explorerBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser();
		        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int ret = dialog.showDialog(null, "OK");
		        if (ret == JFileChooser.APPROVE_OPTION) {
		            pathField1.setText(dialog.getSelectedFile().getAbsolutePath());		            
		        }
			}
		});
		explorerBtn1.setBounds(457, 49, 97, 25);
		frame.getContentPane().add(explorerBtn1);
		
		
		/*
		 * Create button for choosing folder №2 path  */
		JButton explorerBtn2 = new JButton("Select");
		explorerBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser();
		        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int ret = dialog.showDialog(null, "OK");
		        if (ret == JFileChooser.APPROVE_OPTION) {
		            pathField2.setText(dialog.getSelectedFile().getAbsolutePath());		            
		        }				
			}
		});
		explorerBtn2.setBounds(457, 113, 97, 25);
		frame.getContentPane().add(explorerBtn2);
		
		
		
		/*
		 * Create button for choosing folder №3 path  */
		JButton explorerBtn3 = new JButton("Select");
		explorerBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser();
		        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int ret = dialog.showDialog(null, "OK");
		        if (ret == JFileChooser.APPROVE_OPTION) {
		            pathField3.setText(dialog.getSelectedFile().getAbsolutePath());		            
		        }				
			}
		});
		explorerBtn3.setBounds(457, 177, 97, 25);
		frame.getContentPane().add(explorerBtn3);
		
		
		/*Create button for starting comparing. Check to approving folders path. */
		JButton compareBtn = new JButton("Compare");
		compareBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isOK = true;				
				File directory1 = new File(pathField1.getText());					
				if(!directory1.exists()) {
					JOptionPane.showMessageDialog(null, "Folder №1 is incorrect");
					isOK = false;
				}		
				
				File directory2 = new File(pathField2.getText());					
				if(!directory2.exists()) {
					JOptionPane.showMessageDialog(null, "Folder №2 is incorrect");
					isOK = false;
				}
				
				File directory3 = new File(pathField3.getText());
				
				if(!directory3.getParentFile().exists()) {					
					JOptionPane.showMessageDialog(null, "Result folder is incorrect");
					isOK = false;
				}
				if(directory3.toString().equals(directory2.toString())||directory3.toString().equals(directory1.toString())) {
					JOptionPane.showMessageDialog(null, "Result folder must be not \"Folder №1\" or \"Folder №2\"");
					isOK = false;
				}
				
				if (isOK) {
					directory3.mkdir();
					try {
						compare(directory1, directory2, pathField3.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        JOptionPane.showMessageDialog(null, "Done!");
				}				
			}
		});
		compareBtn.setBounds(457, 240, 97, 25);
		frame.getContentPane().add(compareBtn);
	}
	
	/* 
	 * Compare files and push it in result folder
	 * **/
	public void compare (File folder1, File folder2, String foldResult) throws IOException {
		/*Create changers for files list*/
		ArrayList <File> filesArray = new ArrayList <File>();
		Stack <File> filesStack = new Stack<File>();
		
		Path destinationFolder = Paths.get(foldResult);
		
		File[] files1 = folder1.listFiles();
		File[] files2 = folder2.listFiles();
		
		String folderNum1 = "(1)";
		String folderNum2 = "(2)";		
		
		
		for(File file: files1) {
			filesStack.push(file);
			}
		for(File file: files2) {
			filesArray.add(file);
		}
		
		/*Compare files from two arrays and put files to result folder if they have differences by name, size or changing time*/
		while (!filesStack.empty()) {
			File file1 = filesStack.pop();
			boolean isCoincided = false;			
			long sizeF1 = file1.length();
			long dateF1 = file1.lastModified();			
			
			for(int i =0; i<filesArray.size();i++) {	
				File file2 = filesArray.get(i);
				if(file1.getName().equals(file2.getName())){
					if(sizeF1 == file2.length()) {
						if(dateF1 == file2.lastModified()) {
							isCoincided = true;
							filesArray.remove(i);
							}
						else {
							if (file2.isDirectory())
								copyFolder(file2, foldResult, folderNum2);
							else 
								Files.copy(file2.toPath(), destinationFolder.resolve(folderNum2+file2.getName()), StandardCopyOption.REPLACE_EXISTING);								
								filesArray.remove(i);						
						}
					}
					else {	
						if (file2.isDirectory())
							copyFolder(file2, foldResult, folderNum2);
						else 
							Files.copy(file2.toPath(), destinationFolder.resolve(folderNum2+file2.getName()), StandardCopyOption.REPLACE_EXISTING);							
							filesArray.remove(i);						
					}
				}				
			}
			
			if(isCoincided) continue;
			
			if (file1.isDirectory())
				copyFolder(file1, foldResult, folderNum1);
			else
				Files.copy(file1.toPath(), destinationFolder.resolve(folderNum1 + file1.getName()), StandardCopyOption.REPLACE_EXISTING);
		}			
		
		/*if files remain in second folder, they puted in result folder*/
		for(File file: filesArray) {
			if (file.isDirectory())
				copyFolder(file, foldResult , folderNum2);
			else
				Files.copy(file.toPath(), destinationFolder.resolve(folderNum2 + file.getName()), StandardCopyOption.REPLACE_EXISTING);			
		}
	}
	
	/* 
	 * Recursive copying for folders and folders in folders
	 * **/
	public void copyFolder (File fold, String path, String foldNum) throws IOException {
		File[] files = fold.listFiles();
		if(!new File(path+"\\"+foldNum+fold.getName()).exists()) {
			Files.copy(fold.toPath(), Paths.get(path).resolve(foldNum+fold.getName()), StandardCopyOption.REPLACE_EXISTING);	
			Path destDir = Paths.get(path+"\\"+foldNum+fold.getName());		
			for(File file: files) {			
				if(file.isDirectory()) {
					copyFolder(file, destDir.toString(), "");
				}
				else {				
					Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);				
				}
			}
		}
	}
}