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
	private JTextField path1;
	private JTextField path2;
	private JTextField path3;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 584, 323);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		path1 = new JTextField();
		path1.setBounds(12, 50, 433, 22);
		frame.getContentPane().add(path1);
		path1.setColumns(10);
		
		path2 = new JTextField();
		path2.setBounds(12, 114, 433, 22);
		frame.getContentPane().add(path2);
		path2.setColumns(10);
		
		path3 = new JTextField();
		path3.setBounds(12, 178, 433, 22);
		frame.getContentPane().add(path3);
		path3.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Folder №1:");
		lblNewLabel.setBounds(12, 29, 71, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Folder №2:");
		lblNewLabel_1.setBounds(12, 85, 71, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Folder with results:");
		lblNewLabel_2.setBounds(12, 149, 150, 16);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		
		JButton explorerBtn1 = new JButton("Select");
		explorerBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser();
		        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int ret = dialog.showDialog(null, "OK");
		        if (ret == JFileChooser.APPROVE_OPTION) {
		            path1.setText(dialog.getSelectedFile().getAbsolutePath());		            
		        }
			}
		});
		explorerBtn1.setBounds(457, 49, 97, 25);
		frame.getContentPane().add(explorerBtn1);
		
		
		
		JButton explorerBtn2 = new JButton("Select");
		explorerBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser();
		        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int ret = dialog.showDialog(null, "OK");
		        if (ret == JFileChooser.APPROVE_OPTION) {
		            path2.setText(dialog.getSelectedFile().getAbsolutePath());		            
		        }				
			}
		});
		explorerBtn2.setBounds(457, 113, 97, 25);
		frame.getContentPane().add(explorerBtn2);
		
		
		
		
		JButton explorerBtn3 = new JButton("Select");
		explorerBtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser();
		        dialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		        int ret = dialog.showDialog(null, "OK");
		        if (ret == JFileChooser.APPROVE_OPTION) {
		            path3.setText(dialog.getSelectedFile().getAbsolutePath());		            
		        }				
			}
		});
		explorerBtn3.setBounds(457, 177, 97, 25);
		frame.getContentPane().add(explorerBtn3);
		
		
		
		JButton compareBtn = new JButton("Compare");
		compareBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isOK = true;				
				File directory1 = new File(path1.getText());					
				if(!directory1.exists()) {
					JOptionPane.showMessageDialog(null, "Folder №1 is incorrect");
					isOK = false;
				}		
				
				File directory2 = new File(path2.getText());					
				if(!directory2.exists()) {
					JOptionPane.showMessageDialog(null, "Folder №2 is incorrect");
					isOK = false;
				}
				
				File directory3 = new File(path3.getText());					
				if(!directory3.exists()) {
					JOptionPane.showMessageDialog(null, "Result folder is incorrect");
					isOK = false;
				}
				
				if (isOK) {
					try {
						compare(directory1, directory2, path3.getText());
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
	public void compare (File fold1, File fold2, String foldResult) throws IOException {
		ArrayList <File> filesArr = new ArrayList <File>();
		Path destDir = Paths.get(foldResult);
		File[] files1 = fold1.listFiles();
		File[] files2 = fold2.listFiles();
		
		String foldNum1 = "(1)";
		String foldNum2 = "(2)";		
		Stack <File> filesStack = new Stack<File>();
		
		if (files2.length>files1.length) {
			foldNum1 = "(2)";
			foldNum2 = "(1)";
			for(File file2: files2) {
				filesStack.push(file2);
				}
			files2 = files1;
		}
		else {
			for(File file1: files1) {
				filesStack.push(file1);
				}
		}
		
		for(File file: files2) {
			filesArr.add(file);
		}
		
		
		while (!filesStack.empty()) {
			File file1 = filesStack.pop();
			boolean isEqual = false;			
			long sizeF1 = file1.length();
			long dateF1 = file1.lastModified();			
			
			for(int i =0; i<filesArr.size();i++) {	
				File file2 = filesArr.get(i);
				if(file1.getName().equals(file2.getName())){
					if(sizeF1 == file2.length()) {
						if(dateF1 == file2.lastModified()) {
							isEqual = true;
							filesArr.remove(i);
							}
						else {
							if (file2.isDirectory())
								copyFolder(file2, foldResult, foldNum2);
							else 
								Files.copy(file2.toPath(), destDir.resolve(foldNum2+file2.getName()), StandardCopyOption.REPLACE_EXISTING);
								
								filesArr.remove(i);						
						}
					}
					else {	
						if (file2.isDirectory())
							copyFolder(file2, foldResult, foldNum2);
						else 
							Files.copy(file2.toPath(), destDir.resolve(foldNum2+file2.getName()), StandardCopyOption.REPLACE_EXISTING);
							
							filesArr.remove(i);						
					}
				}				
			}
			if(isEqual) continue;
			if (file1.isDirectory())
				copyFolder(file1, foldResult, foldNum1);
			else
				Files.copy(file1.toPath(), destDir.resolve(foldNum1 + file1.getName()), StandardCopyOption.REPLACE_EXISTING);
		}			
		for(File file: filesArr) {
			if (file.isDirectory())
				copyFolder(file, foldResult , foldNum2);
			else
				Files.copy(file.toPath(), destDir.resolve(foldNum2 + file.getName()), StandardCopyOption.REPLACE_EXISTING);			
		}
	}
	
	/* 
	 * recursive copying of folder
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