package compiler;
import java.util.ArrayList;

enum State {START, INCOMMENT, INID, INNUM, INASSIGNMENT, DONE, ERROR};

public class Scanner {
  private char text[];
  private int pointer;
  private ArrayList<String> symbolTable;
  private ArrayList<Integer> literalTable;
  private static String reserved[];
  
  public Scanner(String text){
    this.text = text.toCharArray();
    pointer = 0;
    symbolTable = new ArrayList<>();
    literalTable = new ArrayList<>();
    reserved = new String[]{"if", "then", "else", "end", "repeat", "until", "read", "write"};
  }
  
  public TokenRecord getToken(){
    State state = State.START;
    char c = '\0';
    String s = "";
    TokenRecord record = null;
    while(state != State.DONE && state != State.ERROR){
      if(pointer == text.length)
        return null;
      c = text[pointer];
      switch(state){
        case START:
          if(Character.isWhitespace(c)){
            pointer++;
            state = State.START;
          }
          else if(c == '{'){
            pointer++;
            state = State.INCOMMENT;
          }
          else if(Character.isDigit(c)){
            pointer++;
            state = State.INNUM;
            s += c;
          }
          else if(Character.isLetter(c)){
            pointer++;
            state = State.INID;
            s += c;
          }
          else if(c == ':'){
            pointer++;
            state = State.INASSIGNMENT;
            s += c;
          }
          else if(c == '+' || c == '-' || c == '*' || c == '/' ||
                  c == '=' || c == '<' || c == '(' || c == ')' ||
                  c == ';'){
            pointer++;
            state = State.DONE;
            s += c;
          }
          else{
            pointer++;
            state = State.ERROR;
            s += c;
          }
          break;
        case INCOMMENT:
          if(c == '}'){
            pointer++;
            state = State.START;
          }
          else{
            pointer++;
            state = State.INCOMMENT;
          }
          break;
        case INID:
          if(Character.isLetter(c)){
            pointer++;
            state = State.INID;
            s += c;
          }
          else{
            state = State.DONE;
          }
          break;
        case INNUM:
          if(Character.isDigit(c)){
            pointer++;
            state = State.INNUM;
            s += c;
          }
          else{
            state = State.DONE;
          }
          break;
        case INASSIGNMENT:
          if(c == '='){
            pointer++;
            state = State.DONE;
            s += c;
          }
          else{
            state = State.ERROR;
          }
          break;
      }
    }
    
    if(state == State.DONE){
      if(s.charAt(0) == '+')
        record = new TokenRecord(TokenType.PLUS, s);
      else if(s.charAt(0) == '-')
        record = new TokenRecord(TokenType.MINUS, s);
      else if(s.charAt(0) == '*')
        record = new TokenRecord(TokenType.MULTIPLY, s);
      else if(s.charAt(0) == '/')
        record = new TokenRecord(TokenType.DIVIDE, s);
      else if(s.charAt(0) == '=')
        record = new TokenRecord(TokenType.EQUAL, s);
      else if(s.charAt(0) == '<')
        record = new TokenRecord(TokenType.LESSTHAN, s);
      else if(s.charAt(0) == '(')
        record = new TokenRecord(TokenType.LEFTBRACKET, s);
      else if(s.charAt(0) == ')')
        record = new TokenRecord(TokenType.RIGHTBRACKET, s);
      else if(s.charAt(0) == ';')
        record = new TokenRecord(TokenType.SEMICOLON, s);
      else if(s.charAt(0) == ':')
        record = new TokenRecord(TokenType.ASSIGNMENT, s);
      else if(Character.isDigit(s.charAt(0))){
        record = new TokenRecord(Integer.parseInt(s), literalTable.size());
        literalTable.add(Integer.parseInt(s));
      }
      else{
        if(isReserved(s))
          record = new TokenRecord(TokenType.valueOf(s.toUpperCase()), s);
        else if(symbolTable.contains(s))
          record = new TokenRecord(s, symbolTable.indexOf(s));
        else{
          record = new TokenRecord(s, symbolTable.size());
          symbolTable.add(s);
        }
      }
    }
    else
      record = new TokenRecord(TokenType.ERROR, s);
    
    return record;
  }
  
  public static boolean isReserved(String value){
    for(int i = 0; i < reserved.length; i++)
      if(value.equalsIgnoreCase(reserved[i]))
        return true;
    return false;
  }
}