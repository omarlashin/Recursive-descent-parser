package compiler;
import java.awt.*;
import javax.swing.*;

public class TreeFrame extends JFrame {
  private final TreePanel pnlTree;
  private final JScrollPane pneTree;
  
  public TreeFrame(StatementNode root){
    pnlTree = new TreePanel(root);
    pneTree = new JScrollPane(pnlTree);
    
    setTitle("Syntax Tree");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    add(pneTree);
    pneTree.setPreferredSize(new Dimension(1000, 800));
    pack();
    Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((ss.width - getWidth()) / 2, (ss.height - getHeight()) / 2);
  }
}

class TreePanel extends JPanel {
  private final StatementNode root;
  
  public TreePanel(StatementNode root){
    this.root = root;
    root.map();
    setPreferredSize(Node.getDimensions());
    setBackground(Color.WHITE);
  }
  
  @Override
  public void paint(Graphics g){
    super.paint(g);
    g.setFont(new Font("Calibri", Font.BOLD, 15));
    ((Graphics2D)g).setStroke(new BasicStroke(2));
    root.paint(g);
  }
}