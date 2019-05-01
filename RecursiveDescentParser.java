package compiler;

public class RecursiveDescentParser {
  private Scanner scanner;
  private TokenRecord token;
  
  public RecursiveDescentParser(Scanner scanner){
    this.scanner = scanner;
    token = scanner.getToken();
  }
  
  public StatementNode parse() throws RuntimeException{
    return program();
  }
  
  private void match(TokenType expected) throws RuntimeException{
    if(token.type == expected)
      token = scanner.getToken();
    else
      throw new RuntimeException("Invalid token found.");
  }
  
  private StatementNode program() throws RuntimeException{
    return stmtSequence();
  }
  
  private StatementNode stmtSequence() throws RuntimeException{
    StatementNode result = statement();
    StatementNode temp = result;
    while(token != null && token.type == TokenType.SEMICOLON){
      match(TokenType.SEMICOLON);
      temp.sibling = statement();
      temp = temp.sibling;
    }
    return result;
  }
  
  private StatementNode statement() throws RuntimeException{
    switch(token.type){
      case IF:
        return ifStmt();
      case REPEAT:
        return repeatStmt();
      case ID:
        return assignStmt();
      case READ:
        return readStmt();
      case WRITE:
        return writeStmt();
      default:
        throw new RuntimeException("Invalid statement found.");
    }
  }
  
  private StatementNode ifStmt() throws RuntimeException{
    match(TokenType.IF);
    IfStatementNode result = new IfStatementNode();
    result.setTest(exp());
    match(TokenType.THEN);
    result.setThenPart(stmtSequence());
    if(token.type == TokenType.ELSE){
      match(TokenType.ELSE);
      result.setElsePart(stmtSequence());
    }
    else
      result.setElsePart(null);
    match(TokenType.END);
    return result;
  }
  
  private StatementNode repeatStmt() throws RuntimeException{
    match(TokenType.REPEAT);
    RepeatStatementNode result = new RepeatStatementNode();
    result.setBody(stmtSequence());
    match(TokenType.UNTIL);
    result.setTest(exp());
    return result;
  }
  
  private StatementNode assignStmt() throws RuntimeException{
    AssignStatementNode result = new AssignStatementNode(token.stringValue);
    match(TokenType.ID);
    match(TokenType.ASSIGNMENT);
    result.setExpression(exp());
    return result;
  }
  
  private StatementNode readStmt() throws RuntimeException{
    match(TokenType.READ);
    StatementNode result = new StatementNode("read\n(" + token.stringValue + ")");
    match(TokenType.ID);
    return result;
  }
  
  private StatementNode writeStmt() throws RuntimeException{
    match(TokenType.WRITE);
    WriteStatementNode result = new WriteStatementNode();
    result.setExpression(exp());
    return result;
  }
  
  private ExpressionNode exp() throws RuntimeException{
    ExpressionNode result = simpleExp();
    if(token != null && (token.type == TokenType.LESSTHAN || token.type == TokenType.EQUAL)){
      OperatorExpressionNode temp = new OperatorExpressionNode(token.stringValue);
      match(token.type);
      temp.setLeftOperand(result);
      temp.setRightOperand(simpleExp());
      result = temp;
    }
    return result;
  }
  
  private ExpressionNode simpleExp() throws RuntimeException{
    ExpressionNode result = term();
    OperatorExpressionNode temp;
    while(token != null && (token.type == TokenType.PLUS || token.type == TokenType.MINUS)){
      temp = new OperatorExpressionNode(token.stringValue);
      match(token.type);
      temp.setLeftOperand(result);
      temp.setRightOperand(term());
      result = temp;
    }
    return result;
  }
  
  private ExpressionNode term() throws RuntimeException{
    ExpressionNode result = factor();
    OperatorExpressionNode temp;
    while(token != null && (token.type == TokenType.MULTIPLY || token.type == TokenType.DIVIDE)){
      temp = new OperatorExpressionNode(token.stringValue);
      match(token.type);
      temp.setLeftOperand(result);
      temp.setRightOperand(factor());
      result = temp;
    }
    return result;
  }
  
  private ExpressionNode factor() throws RuntimeException{
    ExpressionNode result;
    switch(token.type){
      case LEFTBRACKET:
        match(TokenType.LEFTBRACKET);
        result = exp();
        match(TokenType.RIGHTBRACKET);
        return result;
      case NUM:
        result = new ExpressionNode("const\n(" + token.numberValue + ")");
        match(TokenType.NUM);
        return result;
      case ID:
        result = new ExpressionNode("id\n(" + token.stringValue + ")");
        match(TokenType.ID);
        return result;
      default:
        throw new RuntimeException("Invalid token found.");
    }
  }
}