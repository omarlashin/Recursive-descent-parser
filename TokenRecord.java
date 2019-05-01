package compiler;

enum TokenType {IF, THEN, ELSE, END, REPEAT, UNTIL, READ, WRITE, PLUS, MINUS, MULTIPLY, DIVIDE, EQUAL, LESSTHAN, LEFTBRACKET, RIGHTBRACKET, SEMICOLON, ASSIGNMENT, ID, NUM, ERROR};

public class TokenRecord {
  public TokenType type;
  public String stringValue;
  public int numberValue;
  public int stPointer;
  public int ltPointer;
  
  public TokenRecord(TokenType type, String stringValue){
    this.type = type;
    this.stringValue = stringValue;
  }
  public TokenRecord(String stringValue, int stPointer){
    type = TokenType.ID;
    this.stringValue = stringValue;
    this.stPointer = stPointer;
  }
  public TokenRecord(int numberValue, int ltPointer){
    type = TokenType.NUM;
    this.numberValue = numberValue;
    this.ltPointer = ltPointer;
  }
  
  @Override
  public String toString(){
    if(type == TokenType.ID)
      return "ID: " + stringValue + "\nSymbol Table Pointer: " + stPointer;
    else if(type == TokenType.NUM)
      return "NUM: " + numberValue + "\nLiteral Table Pointer: " + ltPointer;
    else if(Scanner.isReserved(stringValue))
        return type + ": Reserved Word" + ": " + stringValue;
    else if(isSpecialSymbol(type))
      return type + ": Special Symbol" + ": " + stringValue;
    return type + ": " + stringValue;
  }
  
  private boolean isSpecialSymbol(TokenType type){
    TokenType special[] = TokenType.values();
    for(int i = 8; i < 18; i++)
      if(type == special[i])
        return true;
    return false;
  }
}