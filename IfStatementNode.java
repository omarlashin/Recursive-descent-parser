package compiler;
import java.awt.*;

public class IfStatementNode extends StatementNode {
  private ExpressionNode test;
  private StatementNode thenPart;
  private StatementNode elsePart;
  
  public IfStatementNode(){
    super("if");
  }
  
  @Override
  public void map(){
    if(elsePart == null){
      test.y = y + height + space;
      test.x = x - space;
      if(test.y + height > btmIndex)
        btmIndex = test.y + height;
      if(test.x < lIndex)
        lIndex = test.x;
      else{
        test.update();
        rIndex += space;
      }
      test.map();
      thenPart.y = y + height + space;
      thenPart.x = rIndex + space;
      if(thenPart.y + height > btmIndex)
        btmIndex = thenPart.y + height;
      if(thenPart.x + width > rIndex)
        rIndex = thenPart.x + width;
      thenPart.map();
    }
    else{
      test.y = y + height + space;
      test.x = x - space;
      if(test.y + height > btmIndex)
        btmIndex = test.y + height;
      if(test.x < lIndex)
        lIndex = test.x;
      else{
        test.update();
        rIndex += space;
      }
      test.map();
      thenPart.y = y + height + space;
      thenPart.x = rIndex;
      if(thenPart.y + height > btmIndex)
        btmIndex = thenPart.y + height;
      if(thenPart.x + width > rIndex)
        rIndex = thenPart.x + width;
      thenPart.map();
      elsePart.y = y + height + space;
      elsePart.x = rIndex + space;
      if(elsePart.y + height > btmIndex)
        btmIndex = elsePart.y + height;
      if(elsePart.x + width > rIndex)
        rIndex = elsePart.x + width;
      elsePart.map();
    }
    super.map();
  }
  
  @Override
  public void paint(Graphics g){
    super.paint(g);
    test.paint(g);
    thenPart.paint(g);
    g.drawLine(x + width / 2, y + height, test.x + width / 2, test.y);
    g.drawLine(x + width / 2, y + height, thenPart.x + width / 2, thenPart.y);
    if(elsePart != null){
      elsePart.paint(g);
      g.drawLine(x + width / 2, y + height, elsePart.x + width / 2, elsePart.y);
    }
  }
  
  public void setTest(ExpressionNode test){
    this.test = test;
  }
  public void setThenPart(StatementNode thenPart){
    this.thenPart = thenPart;
  }
  public void setElsePart(StatementNode elsePart){
    this.elsePart = elsePart;
  }
  public ExpressionNode getTest(){
    return test;
  }
  public StatementNode getThenPart(){
    return thenPart;
  }
  public StatementNode getElsePart(){
    return elsePart;
  }
}