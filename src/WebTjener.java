import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class WebTjener {
    final static int PORTNR = 80;

    public static void main(String[] args) throws IOException {
        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Venter på en forbindelse...");
        Socket forbindelse = tjener.accept();
        System.out.println("Du har nå en forbindelse");
        BufferedReader in = new BufferedReader(new InputStreamReader(forbindelse.getInputStream()));
        String alt = "<ul>";
        String inputLine;
        while (!(inputLine = in.readLine()).equals("")) {
            alt += "<li>" + inputLine + "</li>";
        }
        alt += "</ul>";
        PrintWriter out = new PrintWriter(forbindelse.getOutputStream(), true);

        //out.println("HTTP/1.0 200 OK");
        //out.println("Content-Type: text/html; charset=utf-8 (linjeskift)");
        //out.println("\r\n");
        //out.println("<h1> Hilsen. du har koblet deg opp til min enkle web-tjener</h1>");
        out.println("HTTP/1.0 200 OK \n" +
                "Content-Type: text/html; charset=utf-8 \n" +
                "\r\n" +
                "<html> <body>" +
                "<h1> Hilsen. Du har koblet deg opp til min enkle web-tjener</h1>" +
                "<p>Header fra Klient er: </p>" +
                "<ul>" +
                "<li>HTTP/1.0 200 OK </li>" +
                "<li>Content-Type: text/html; charset=utf-8</li></ul> annen info: "+ alt + "</html></body>");

        forbindelse.close();
        tjener.close();
        out.close();
        in.close();
    }
}