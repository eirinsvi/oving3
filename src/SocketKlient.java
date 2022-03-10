/*
 * SocketKlient.java  - "Programmering i Java", 4.utgave - 2009-07-01
 *
 * Programmet kontakter et tjenerprogram som allerede kj�rer p� port 1250.
 * Linjer med tekst sendes til tjenerprogrammet. Det er laget slik at
 * det sender disse tekstene tilbake.
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
class SocketKlient {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 8082;
        String ipAdress = "127.0.0.1";

        Scanner leserFraKommandovindu = new Scanner(System.in);
        /* Bruker en scanner til å lese fra kommandovinduet

        System.out.print("Oppgi navnet på maskinen der tjenerprogrammet kjører: ");
        String tjenermaskin = leserFraKommandovindu.nextLine();

         */

        /* Setter opp forbindelsen til tjenerprogrammet */
        Socket forbindelse = new Socket(ipAdress, PORTNR);
        System.out.println("Nå er forbindelsen opprettet. Skriv inn regnestykkene dine, med linjeskift i mellom.");

        /* �pner en forbindelse for kommunikasjon med tjenerprogrammet */
        InputStreamReader leseforbindelse
                = new InputStreamReader(forbindelse.getInputStream());
        BufferedReader leseren = new BufferedReader(leseforbindelse);
        PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), true);


        /* Leser tekst fra kommandovinduet (brukeren) */
        String enLinje = leserFraKommandovindu.nextLine();
        while (!enLinje.equals("")) {
            skriveren.println(enLinje);  // sender teksten til tjeneren
            String respons = leseren.readLine();  // mottar respons fra tjeneren
            System.out.println("Fra tjenerprogrammet: " + respons);
            enLinje = leserFraKommandovindu.nextLine();
        }

        /* Lukker forbindelsen */
        leseren.close();
        skriveren.close();
        forbindelse.close();
    }
}
