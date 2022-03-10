import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThreadClass extends Thread {

    private final Socket socketConnection;

    public ThreadClass(Socket socketConnection) {
        this.socketConnection = socketConnection;
    }

    @Override
    public void run() {
        PrintWriter writer = null;
        InputStreamReader readConnection = null;
        try {
            readConnection = new InputStreamReader(socketConnection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert readConnection != null;
        BufferedReader reader = new BufferedReader(readConnection);
        try {
            writer = new PrintWriter(socketConnection.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            /* Waiting for input */
            String input = null;
            assert writer != null;
            //writer.println("Enter your equation");
            try {
                input = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Equation received from :" + socketConnection);
            System.out.println(input);
            /* This regular expression both checks if the input if correct, and also splits it by whitespaces. */
            while (input != null) {  // forbindelsen på klientsiden er lukket
                double tallEn = 0;
                double tallTo = 0;
                double resultat = 0;
                if(input.matches(".*[a-zA-Z]+.*")){
                    writer.println("Unable to solve your equation, due to the fact that it contains letters");
                } else {
                    input = input.replaceAll(",", ".");
                    input = input.replaceAll(" ", "");

                    int indexOperator = 0;
                    if (input.contains("+")) {
                        String[] linjeSplit = input.split("\\+", 2);
                        tallEn = Double.parseDouble(linjeSplit[0]);
                        tallTo = Double.parseDouble(linjeSplit[1]);
                        resultat = tallEn + tallTo;
                        writer.println("Svaret på regnestykket ditt " + input + "=" + resultat);  // sender svar til klienten
                    } else if (input.contains("-")) {
                        String[] linjeSplit = input.split("-", 2);
                        tallEn = Double.parseDouble(linjeSplit[0]);
                        tallTo = Double.parseDouble(linjeSplit[1]);
                        resultat = tallEn - tallTo;
                        writer.println("Svaret på regnestykket ditt " + input + "=" + resultat);  // sender svar til klienten
                    } else{
                        writer.println("Unable to solve your equation, due to the fact that you have not used operator + or -");
                    }
                    System.out.println("En klient skrev: " + input);

                }
                try {
                    input = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /* Lukker forbindelsen */
            try {
                reader.close();
                writer.close();
                socketConnection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
