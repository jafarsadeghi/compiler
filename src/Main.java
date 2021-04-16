
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {


    final static int ID = 0;
    final static int STRING = 1;
    final static int INTEGER = 2;
    final static int DOUBLE = 3;
    final static int BOOLEAN = 4;
    final static int KEYWORD = 5;
    static int i = 0;


    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        StringBuilder code = new StringBuilder("");
        Character[] alphabet = {'+', '-', '(', ')', '*', '/', '%', '<', '>', '=', '!', '&', '|', ';', ',', '.', '[', ']', '{', '}'};
        ArrayList<Character> keyCharacters = new ArrayList<Character>(Arrays.asList(alphabet));

        while (true) {
            String string = scanner.nextLine();
            if (string.equals("$")) {
                break;
            } else
                code.append(string).append("\n");
        }

        while (code.length() > i) {
            char ch = code.charAt(i);
            if (Character.isLetter(ch)) {
                StringBuilder idBuilder = new StringBuilder("");
                idBuilder.append(ch);
                i++;
                while (code.length() > i && Character.isLetterOrDigit(code.charAt(i)) || code.charAt(i) == '_') {
                    idBuilder.append(code.charAt(i));
                    i++;
                }
                resolver(idBuilder.toString());
            } else if (keyCharacters.contains(ch)) {
                String str = String.valueOf(ch);
                if (ch == '+' || ch == '-' || ch == '*' || ch == '!' || ch == '<' || ch == '=' || ch == '>') {
                    if (code.length() == i + 1) {
                        receiver(KEYWORD, str);
                    } else {
                        if (code.charAt(i + 1) == '=') {
                            receiver(KEYWORD, str.concat("="));
                            i++;
                        } else
                            receiver(KEYWORD, str);
                    }
                    i++;
                } else if (ch == '/') {
                    if (code.length() == i + 1) {
                        receiver(KEYWORD, "/");
                    } else {
                        switch (code.charAt(++i)) {
                            case '/':
                                i++;
                                while (code.charAt(i) != '\n') {
                                    i++;
                                }
                                i++;
                                break;
                            case '*':
                                i++;
                                while (code.length() > i + 1 && !(code.charAt(i) == '*' && code.charAt(i + 1) == '/')) {
                                    i++;
                                }
                                i += 2;
                                break;
                            case '=':
                                receiver(KEYWORD, "/=");
                                i++;
                                break;
                            default:
                                receiver(KEYWORD, "/");
                                break;
                        }
                    }
                } else if (ch == '&' || ch == '|') {
                    if (code.charAt(++i) == ch) {
                        receiver(KEYWORD, str.concat(str));
                    }
                    i++;
                } else {
                    receiver(KEYWORD, str);
                    i++;
                }
            } else if (Character.isDigit(ch)) {
                i = numberDetector(code.toString(), i);
            } else if (ch == '\"') {
                i++;
                StringBuilder string = new StringBuilder("\"");
                if (code.charAt(i) == '\"') {
                    receiver(STRING, "");
                } else {
                    while (code.charAt(i) != '\"') {
                        string.append(code.charAt(i));
                        i++;
                    }
                    receiver(STRING, string.append("\"").toString());
                    i++;
                }
            } else if (ch == ' ' || ch == '\n') {
                i++;
            } else {
                System.out.println("ino yadetoon raft: " + ch);
                i++;
            }
        }
    }

    private static Hashtable<String, String> initializeKeyWords() {

        Hashtable<String, String> result = new Hashtable<>();
        result.put("void", "void");
        result.put("int", "int");
        result.put("double", "double");
        result.put("bool", "bool");
        result.put("string", "string");
        result.put("class", "class");
        result.put("null", "null");
        result.put("this", "this");
        result.put("for", "for");
        result.put("while", "while");
        result.put("if", "if");
        result.put("else", "else");
        result.put("return", "return");
        result.put("break", "break");
        result.put("continue", "continue");
        result.put("new", "new");
        result.put("NewArray", "NewArray");
        result.put("getArrVal", "getArrVal");
        result.put("Print", "Print");
        result.put("ReadInteger", "ReadInteger");
        result.put("ReadLine", "ReadLine");
        result.put("itod", "itod");
        result.put("dtoi", "dtoi");
        result.put("btoi", "btoi");
        result.put("itob", "itob");
        result.put("private", "private");
        result.put("public", "public");
        result.put("true", "true");
        result.put("false", "false");
        return result;
    }

    private static int numberDetector(String code, int i) {
        boolean hasDecimal = false;
        if (code.length() > i + 2 && code.charAt(i) == '0' && (code.charAt(i + 1) == 'x' || code.charAt(i + 1) == 'X') && (Character.isDigit(code.charAt(i + 2)) || (code.charAt(i + 2) >= 'a' && code.charAt(i + 2) <= 'f') || (code.charAt(i + 2) >= 'A' && code.charAt(i + 2) <= 'F'))) {
            StringBuilder hex = new StringBuilder("0").append(code.charAt(++i));
            i++;
            while (code.length() > i && (Character.isDigit(code.charAt(i)) || (code.charAt(i) >= 'a' && code.charAt(i) <= 'f') || (code.charAt(i) >= 'A' && code.charAt(i) <= 'F'))) {
                hex.append(code.charAt(i));
                i++;
            }
            receiver(INTEGER, hex.toString());
        } else {
            StringBuilder number = new StringBuilder(String.valueOf(code.charAt(i)));
            i++;

            while (code.length() > i && (Character.isDigit(code.charAt(i)) || code.charAt(i) == '.' || code.charAt(i) == 'e' || code.charAt(i) == 'E')) {
                if (code.length() > i + 2 && code.charAt(i) == 'e' || code.charAt(i) == 'E' && (code.charAt(i + 1) == '+' || code.charAt(i + 1) == '-') && Character.isDigit(code.charAt(i + 2))) {
                    number.append(code.charAt(i)).append(code.charAt(++i));
                    i++;
                    while (code.length() > i && (Character.isDigit(code.charAt(i)))) {
                        number.append(code.charAt(i));
                        i++;
                    }
                    break;
                } else if (code.charAt(i) == '.') {
                    if (hasDecimal) {
                        break;
                    } else {
                        number.append('.');
                        hasDecimal = true;
                        i++;
                    }
                } else if (Character.isDigit(code.charAt(i))) {
                    number.append(code.charAt(i));
                    i++;
                } else {
                    break;
                }
            }
            if (hasDecimal)
                receiver(DOUBLE, number.toString());
            else
                receiver(INTEGER, number.toString());
        }
        return i;
    }

    private static void resolver(String keyWord) {
        Hashtable<String, String> keywords = initializeKeyWords();

        if (keywords.contains(keyWord)) {
            if (keyWord.equals("true") || keyWord.equals("false")) {
                receiver(BOOLEAN, keyWord);
            } else {
                receiver(KEYWORD, keyWord);
            }
        } else {
            receiver(ID, keyWord);
        }

    }

    private static void receiver(int type, String keyWord) {
        switch (type) {
            case ID:
                System.out.println("T_ID " + keyWord);
                break;
            case INTEGER:
                System.out.println("T_INTLITERAL " + keyWord);
                break;
            case DOUBLE:
                System.out.println("T_DOUBLELITERAL " + keyWord);
                break;
            case STRING:
                System.out.println("T_STRINGLITERAL " + keyWord);
                break;
            case BOOLEAN:
                System.out.println("T_BOOLEANLITERAL " + keyWord);
                break;
            case KEYWORD:
                System.out.println(keyWord);
                break;

        }
    }
}




 /* try {
            String inputFileName = null;
            String outputFileName = null;
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i].equals("-i")) {
                        inputFileName = args[i + 1];
                    }
                    if (args[i].equals("-o")) {
                        outputFileName = args[i + 1];
                    }
                }
            }
            Reader reader = null;
            Writer writer = null;
            if (inputFileName != null)
                reader = new FileReader("tests/" + inputFileName);
            int j;
            while ((j=reader.read())!=-1)
                System.out.println((char)j);

            if (outputFileName != null)
                writer = new FileWriter( "out/" + outputFileName);

            // Read with reader and write the output with writer.

        }
        catch(Exception e) {
            return;
        }*/