package compiler;
import java.awt.*;

public class OperatorExpressionNode extends ExpressionNode {
  private ExpressionNode leftOperand;
  private ExpressionNode rightOperand;
  
  public OperatorExpressionNode(String operator){
    super("op\n(" + operator + ")");
  }
  
  @Override
  public void map(){
    leftOperand.y = y + height + space;
    leftOperand.x = x - (int)Math.ceil(space / Math.sqrt(3));
    if(leftOperand.y + height > btmIndex)
      btmIndex = leftOperand.y + height;
    if(leftOperand.x < lIndex)
      lIndex = leftOperand.x;
    else{
      leftOperand.update();
      rIndex += space;
    }
    leftOperand.map();
    rightOperand.y = y + height + space;
    rightOperand.x = x + (int)Math.ceil(space / Math.sqrt(3));
    if(rightOperand.y + height > btmIndex)
      btmIndex = rightOperand.y + height;
    if(rightOperand.x + width > rIndex)
      rIndex = rightOperand.x + width;
    rightOperand.map();
  }
  
  @Override
  public void paint(Graphics g){
    super.paint(g);
    leftOperand.paint(g);
    rightOperand.paint(g);
    g.drawLine(x + width / 2, y + height, leftOperand.x + width / 2, leftOperand.y);
    g.drawLine(x + width / 2, y + height, rightOperand.x + width / 2, rightOperand.y);
  }
  
  public void setLeftOperand(ExpressionNode leftOperand){
    this.leftOperand = leftOperand;
  }
  public void setRightOperand(ExpressionNode rightOperand){
    this.rightOperand = rightOperand;
  }
  public ExpressionNode getLeftOperand(){
    return leftOperand;
  }
  public ExpressionNode getRightOperand(){
    return rightOperand;
  }
}