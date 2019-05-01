package compiler;
import java.awt.*;

public class AssignStatementNode extends StatementNode {
  private ExpressionNode expression;
  
  public AssignStatementNode(String identifier){
    super("assign\n(" + identifier + ")");
  }
  
  @Override
  public void map(){
    expression.x = x;
    expression.y = y + space;
    if(expression.y + height > btmIndex)
      btmIndex = expression.y + height;
    expression.map();
    super.map();
  }
  
  @Override
  public void paint(Graphics g){
    super.paint(g);
    expression.paint(g);
    g.drawLine(x + width / 2, y + height, expression.x + width / 2, expression.y);
  }
  
  public void setExpression(ExpressionNode expression){
    this.expression = expression;
  }
  public ExpressionNode getExpression(){
    return expression;
  }
}