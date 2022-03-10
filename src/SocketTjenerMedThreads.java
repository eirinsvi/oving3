import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
class SocketTjenerMedThreads {
    public static void main(String[] args) {
        Socket socketConnection = null;

        try {
            ServerSocket serverSocket = new ServerSocket(8082);
            //ServerSocket serverSocket = new ServerSocket(1250, 0, InetAddress.getByName(null));
            System.out.println("Log for the server side. Now we wait...");

            while (true) {
                socketConnection = serverSocket.accept();
                System.out.printf("New client connected %s : ", socketConnection);
                /* Assigns a thread to the new client */
                System.out.println("Assigning new thread");
                Thread newClient = new ThreadClass(socketConnection);
                newClient.start();
            }

        } catch (IOException e) {
            e.printStackTrace();

        }

        try {
            assert socketConnection != null;
            socketConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


