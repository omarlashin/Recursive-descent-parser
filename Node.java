package compiler;
import java.awt.*;
import java.util.ArrayList;

public class Node {
  public final String label;
  protected int x;
  protected int y;
  protected boolean isRectangle;
  protected int width = 100;
  protected int height = 50;
  protected int space = 100;
  protected static ArrayList<Node> tree = new ArrayList<>();
  protected static int lIndex = 50;
  protected static int rIndex = 150;
  protected static int topIndex = 50;
  protected static int btmIndex = 100;
  
  public Node(String label){
    this.label = label;
    x = 50;
    y = 50;
    tree.add(this);
  }
  
  protected void update(){
    x += space;
    Node parent = null;
    for(Node node : tree){
      if(node instanceof IfStatementNode){
        IfStatementNode casted = (IfStatementNode)node;
        if(casted.getTest() == this){
          parent = node;
          break;
        }
      }
      else if(node instanceof RepeatStatementNode){
        RepeatStatementNode casted = (RepeatStatementNode)node;
        if(casted.getBody() == this){
          parent = node;
          break;
        }
      }
      else if(node instanceof AssignStatementNode){
        AssignStatementNode casted = (AssignStatementNode)node;
        if(casted.getExpression() == this){
          parent = node;
          break;
        }
      }
      else if(node instanceof WriteStatementNode){
        WriteStatementNode casted = (WriteStatementNode)node;
        if(casted.getExpression() == this){
          parent = node;
          break;
        }
      }
      else if(node instanceof OperatorExpressionNode){
        OperatorExpressionNode casted = (OperatorExpressionNode)node;
        if(casted.getLeftOperand() == this){
          parent = node;
          break;
        }
      }
    }
    if(parent != null)
      parent.update();
  }
  
  public void paint(Graphics g){
    if(isRectangle)
      g.drawRect(x, y, width, height);
    else
      g.drawOval(x, y, width, height);
    drawString(g, x, y);
  }
  
  private void drawString(Graphics g, int x, int y){
    for(String line : label.split("\n")){
      if(line.length() > 10)
        line = line.substring(0, 10) + "...)";
      g.drawString(line, x + (width - g.getFontMetrics().stringWidth(line)) / 2, y += g.getFontMetrics().getHeight());
    }
  }
  
  public static Dimension getDimensions(){
    topIndex -= 50;
    btmIndex += 50;
    rIndex += 50;
    lIndex -= 50;
    int offset = Math.abs(lIndex);
    for(Node node : tree)
      node.x += offset;
    rIndex += offset;
    return new Dimension(rIndex - lIndex, btmIndex - topIndex);
  }
  public void setX(int x){
    this.x = x;
  }
  public void setY(int y){
    this.y = y;
  }
}