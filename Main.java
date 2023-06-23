import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

class TextEditor extends JFrame implements ActionListener{

 JTextArea textArea;
 JScrollPane scrollPane;
 JLabel fontLabel;
 JSpinner fontSizeSpinner;
 JButton fontColorButton;
 JComboBox<String> fontBox;
 
 JMenuBar menuBar;
 JMenu fileMenu;
 JMenuItem openItem;
 JMenuItem saveItem;
 JMenuItem exitItem;

 TextEditor(){
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setTitle("Bro text Editor");
  this.setSize(500, 500);
  this.setLayout(new FlowLayout());
  this.setLocationRelativeTo(null);
  
  textArea = new JTextArea();
  textArea.setLineWrap(true);
  textArea.setWrapStyleWord(true);
  textArea.setFont(new Font("Arial",Font.PLAIN,20));
  
  scrollPane = new JScrollPane(textArea);
  scrollPane.setPreferredSize(new Dimension(450,450));
  scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
  
  fontLabel = new JLabel("Font: ");
  
  fontSizeSpinner = new JSpinner();
  fontSizeSpinner.setPreferredSize(new Dimension(50,25));
  fontSizeSpinner.setValue(20);
  fontSizeSpinner.addChangeListener(new ChangeListener() {

   @Override
   public void stateChanged(final ChangeEvent e) {
    
    textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue())); 
   }
   
  });
  
  fontColorButton = new JButton("Color");
  fontColorButton.addActionListener(this);
  
  final String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  
  fontBox = new JComboBox<>(fonts);
  fontBox.addActionListener(this);
  fontBox.setSelectedItem("Arial");
  
  // ----- menubar -----
  
   menuBar = new JMenuBar();
   fileMenu = new JMenu("File");
   openItem = new JMenuItem("Open");
   saveItem = new JMenuItem("Save");
   exitItem = new JMenuItem("Exit");
   
   openItem.addActionListener(this);
   saveItem.addActionListener(this);
   exitItem.addActionListener(this);
   
   fileMenu.add(openItem);
   fileMenu.add(saveItem);
   fileMenu.add(exitItem);
   menuBar.add(fileMenu);
  
  // ----- /menubar -----
   
  this.setJMenuBar(menuBar);
  this.add(fontLabel);
  this.add(fontSizeSpinner);
  this.add(fontColorButton);
  this.add(fontBox);
  this.add(scrollPane);
  this.setVisible(true);
 }
 
 @Override
 public void actionPerformed(final ActionEvent e) {
  
  if(e.getSource()==fontColorButton) {
   final Color color = JColorChooser.showDialog(null, "Choose a color", Color.black);
   
   textArea.setForeground(color);
  }
  
  if(e.getSource()==fontBox) {
   textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
  }
  
  if(e.getSource()==openItem) {
   final JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   final FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
   fileChooser.setFileFilter(filter);
   
   final int response = fileChooser.showOpenDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    final File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    Scanner fileIn = null;
    
    try {
     fileIn = new Scanner(file);
     if(file.isFile()) {
      while(fileIn.hasNextLine()) {
       final String line = fileIn.nextLine()+"\n";
       textArea.append(line);
      }
     }
    } catch (final FileNotFoundException e1) {

     e1.printStackTrace();
    }
    finally {
     fileIn.close();
    }
   }
  }
  if(e.getSource()==saveItem) {
   final JFileChooser fileChooser = new JFileChooser();
   fileChooser.setCurrentDirectory(new File("."));
   
   final int response = fileChooser.showSaveDialog(null);
   
   if(response == JFileChooser.APPROVE_OPTION) {
    File file;
    PrintWriter fileOut = null;
    
    file = new File(fileChooser.getSelectedFile().getAbsolutePath());
    try {
     fileOut = new PrintWriter(file);
     fileOut.println(textArea.getText());
    } 
    catch (final FileNotFoundException e1) {
     e1.printStackTrace();
    }
    finally {
     fileOut.close();
    }   
   }
  }
  if(e.getSource()==exitItem) {
   System.exit(0);
  }  
 }
}


public class Main {

 public static void main(final String[] args) {
  
  new TextEditor();

 }
}
