import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Client {

    public static Contact parseLine (String userInput) {
        String[] tokens = userInput.split(" ");

        if (tokens[3].equals("null")) tokens[3] = null;

        return new Contact(
                tokens[0],
                Integer.parseInt(tokens[1]),
                Long.parseLong(tokens[2]),
                tokens[3],
                new ArrayList<>(Arrays.asList(tokens).subList(4, tokens.length)));
    }


    public static void main (String[] args) throws IOException {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))
            String userInput;
        } {
            while ((userInput = in.readLine()) != null) {
                //Contact newContact = parseLine(userInput);
                ArrayList emails = new ArrayList<String>();
                emails.add("john@mail.com");
                emails.add("john.business@mail.com");
                Contact c = new Contact("John", 20, 253123456, "Company", emails);
                System.out.println(c.toString());
            }
        }

        socket.close();
    }
}
