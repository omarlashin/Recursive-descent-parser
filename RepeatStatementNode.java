package compiler;
import java.awt.*;

public class RepeatStatementNode extends StatementNode {
  private StatementNode body;
  private ExpressionNode test;
  
  public RepeatStatementNode(){
    super("repeat");
  }
  
  @Override
  public void map(){
    body.y = y + height + space;
    body.x = x - space;
    if(body.y + height > btmIndex)
      btmIndex = body.y + height;
    if(body.x < lIndex)
      lIndex = body.x;
    else{
      body.update();
      rIndex += space;
    }
    body.map();
    test.y = y + height + space;
    test.x = rIndex + space;
    if(test.y + height > btmIndex)
      btmIndex = test.y + height;
    if(test.x + width > rIndex)
      rIndex = test.x + width;
    test.map();
    super.map();
  }
  
  @Override
  public void paint(Graphics g){
    super.paint(g);
    body.paint(g);
    test.paint(g);
    g.drawLine(x + width / 2, y + height, body.x + width / 2, body.y);
    g.drawLine(x + width / 2, y + height, test.x + width / 2, test.y);
  }
  
  public void setBody(StatementNode body){
    this.body = body;
  }
  public void setTest(ExpressionNode test){
    this.test = test;
  }
  public StatementNode getBody(){
    return body;
  }
  public ExpressionNode getTest(){
    return test;
  }
}