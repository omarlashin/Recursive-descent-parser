package compiler;
import java.awt.*;

public class StatementNode extends Node {
  protected StatementNode sibling;
  
  public StatementNode(String label){
    super(label);
    isRectangle = true;
  }
  
  public void map(){
    if(sibling != null){
      sibling.x = rIndex + space;
      sibling.y = y;
      rIndex = sibling.x + width;
      sibling.map();
    }
  }
  
  @Override
  public void paint(Graphics g){
    super.paint(g);
    if(sibling != null){
      g.drawLine(x + width, y + 25, sibling.x, y + 25);
      sibling.paint(g);
    }
  }
  
  public void setSibling(StatementNode sibling){
    this.sibling = sibling;
  }
  public StatementNode getSibling(){
    return sibling;
  }
}