import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        int portNR = 8082;

        System.out.print("Programmet kjører! \n");
        Socket connection = new Socket("172.20.10.2", portNR);

        InputStreamReader readConnetion = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(readConnetion);
        PrintWriter writer = new PrintWriter(connection.getOutputStream(), true);


        Scanner readCMD = new Scanner(System.in);
        String line = readCMD.nextLine();

        while(!line.equals("")){
            writer.println(line);
            System.out.println(reader.readLine());
            line = readCMD.nextLine();
        }

        reader.close();
        writer.close();
        connection.close();
    }
}
