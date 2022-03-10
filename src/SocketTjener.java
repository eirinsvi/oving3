/**
 * SocketTjener.java  - "Programmering i Java", 4.utgave - 2009-07-01
 *
 * Programmet �pner en socket og venter p� at en klient skal ta kontakt.
 * Programmet leser tekster som klienten sender over, og returnerer disse.
 */

import java.io.*;
import java.net.*;

import static java.lang.Math.round;

class SocketTjener {
    public static void main(String[] args) throws Exception {
        final int PORTNR = 1250;

        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Logg for tjenersiden. Nå venter vi...");
        Socket forbindelse = tjener.accept();  // venter inntil noen tar kontakt

        /* �pner str�mmer for kommunikasjon med klientprogrammet */
        InputStreamReader leseforbindelse
                = new InputStreamReader(forbindelse.getInputStream());
        BufferedReader leseren = new BufferedReader(leseforbindelse);
        PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), true);

        /* Sender innledning til klienten */
        //TODO returner error om det brukes andre tegn enn tall, + eller -
        skriveren.println("Hei, du har kontakt med tjenersiden! Nå kan du addere og subtrahere tall! Skriv regnestykkene dine på denne formen: 'x+y' eller 'x-y', så avslutt med linjeskift");

        String enLinje = leseren.readLine();  // mottar en linje med tekst
        while (enLinje != null) {  // forbindelsen på klientsiden er lukket
            double tallEn = 0;
            double tallTo = 0;
            double resultat = 0;
            System.out.println("En klient skrev: " + enLinje);
            if(enLinje.matches(".*[a-zA-Z]+.*")){
                skriveren.println("Unable to solve your equation, due to the fact that it contains letters");
            } else {
                enLinje = enLinje.replaceAll(",", ".");
                enLinje = enLinje.replaceAll(" ", "");

                int indexOperator = 0;
                if (enLinje.contains("+")) {
                    String[] linjeSplit = enLinje.split("\\+", 2);
                    tallEn = Double.parseDouble(linjeSplit[0]);
                    tallTo = Double.parseDouble(linjeSplit[1]);
                    resultat = tallEn + tallTo;
                    skriveren.println("Svaret på regnestykket ditt " + enLinje + "=" + resultat);  // sender svar til klienten
                } else if (enLinje.contains("-")) {
                    String[] linjeSplit = enLinje.split("-", 2);
                    tallEn = Double.parseDouble(linjeSplit[0]);
                    tallTo = Double.parseDouble(linjeSplit[1]);
                    resultat = tallEn - tallTo;
                    skriveren.println("Svaret på regnestykket ditt " + enLinje + "=" + resultat);  // sender svar til klienten
                } else{
                    skriveren.println("Unable to solve your equation, due to the fact that you have not used operator + or -");
                }
                System.out.println("En klient skrev: " + enLinje);

            }
            enLinje = leseren.readLine();
        }
        /* Lukker forbindelsen */
        leseren.close();
        skriveren.close();
        forbindelse.close();
    }
}

