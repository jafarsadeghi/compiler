
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class main {


    static int ID = 0;
    static int STRING = 1;
    static int INTEGER = 2;
    static int DOUBLE = 3;
    static int BOOLEAN = 4;
    static int CHARACTER = 5;


    public static void main(String[] args) throws IOException {


        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        int state = 0;
        int i = 0;
        Character[] alphabet = {'+', '-', '(', ')'};
        ArrayList<Character> keyCharacters = new ArrayList<Character>(Arrays.asList(alphabet));


        StringBuilder idBuilder = new StringBuilder();
        while (i <= code.length()) {
            char ch = code.charAt(i);
            if (Character.isLetter(ch)) {
                idBuilder.append(ch);
                i++;
                while (code.length() > i && Character.isLetterOrDigit(code.charAt(i)) || code.charAt(i) == '_') {
                    idBuilder.append(code.charAt(i));
                    i++;
                }
                resolver(idBuilder.toString());
            } else if (ch == '/') {

            } else if (keyCharacters.contains(ch)) {
                receiver(CHARACTER, String.valueOf(ch));
                i++;
            } else if (Character.isDigit(ch)) {
                i = numberDetector(code, i);
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
    }

    private static int numberDetector(String code, int i) {
        boolean hasDecimal = false;
        if (code.length() > i + 2 && code.charAt(i) == '0' && (code.charAt(i + 1) == 'x' || code.charAt(i + 1) == 'X') && (Character.isDigit(code.charAt(i + 2)) || code.charAt(i) >= 'a' && code.charAt(i) <= 'f') || (code.charAt(i) >= 'A' && code.charAt(i) <= 'F')) {
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
                } else if (Character.isDigit(code.charAt(i))){
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

    }

    private static void receiver(int type, String keyWord) {
        System.out.println(type + "  " + keyWord);
    }
}