import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class XMLParser {

    enum State {
        TEXT,
        OPEN_TAG,
        TAG_NAME,
    }

    public void parse(String filePath) {
        State currentState = State.TEXT;
        StringBuilder currentToken = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int ch;
            while ((ch = reader.read()) != -1) {
                char c = (char) ch;

                switch (currentState) {
                    case TEXT:
                        if (c == '<') {
                            if (!currentToken.isEmpty()) {
                                System.out.println("Text: " + currentToken.toString().trim());
                                currentToken.setLength(0);
                            }
                            currentState = State.OPEN_TAG;
                        } else {
                            currentToken.append(c);
                        }
                        break;

                    case OPEN_TAG:
                        if (Character.isLetter(c)) {
                            currentState = State.TAG_NAME;
                            currentToken.setLength(0);
                            currentToken.append(c);
                        } else {
                            // За сега, по-късно символи които не започват с char
                        }
                        break;

                    case TAG_NAME:
                        if (Character.isLetterOrDigit(c)) {
                            currentToken.append(c);
                        } else if (c == ' ' || c == '>') {
                            String tagName = currentToken.toString();
                            System.out.println("Tag name: " + tagName);

                            if (c == '>') {
                                currentState = State.TEXT;
                            } else {
                                // За атрибути в бъдеще
                                currentState = State.TEXT; // Само към текст засега
                            }
                            currentToken.setLength(0); // Зачистване на токена
                        }
                        break;

                    default:
                        break;
                }
            }
            if (!currentToken.isEmpty() && currentState == State.TEXT) {
                System.out.println("Text: " + currentToken.toString().trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

