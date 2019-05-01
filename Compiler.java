package compiler;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Compiler {
  
  public static void main(String[] args) {
    try{
      String text;
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      JFileChooser fc = new JFileChooser();
      fc.setAcceptAllFileFilterUsed(false);
      fc.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
      int choice = fc.showOpenDialog(null);
      if(choice == JFileChooser.APPROVE_OPTION){
        text = readFile(fc.getSelectedFile()) + " ";
        Scanner scanner = new Scanner(text);
        RecursiveDescentParser parser = new RecursiveDescentParser(scanner);
        StatementNode output = parser.parse();
        TreeFrame tf = new TreeFrame(output);
        tf.setVisible(true);
      }
    }
    catch(FileNotFoundException ex){
      JOptionPane.showMessageDialog(null, "File specified not found.", "Program Error", JOptionPane.ERROR_MESSAGE);
    }
    catch(IOException ex){
      JOptionPane.showMessageDialog(null, "Couldn't read specified file.", "Program Error", JOptionPane.ERROR_MESSAGE);
    }
    catch(RuntimeException ex){
      JOptionPane.showMessageDialog(null, "Couldn't produce syntax tree. " + ex.getMessage(), "Parsing Error", JOptionPane.ERROR_MESSAGE);
    }
    catch(Exception ex){
      JOptionPane.showMessageDialog(null, "An error has occured.", "Program Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  public static String readFile(File file) throws FileNotFoundException, IOException{
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String text = "";
    int x = reader.read();
    while(x != -1){
      text += (char)x;
      x = reader.read();
    }
    return text;
  }
  
}