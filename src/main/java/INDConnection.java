import java.io.*;
import java.net.Socket;

public class INDConnection {

    private String IP; // hostname of the server
    private int port = 1701; // port number to connect to

    public INDConnection() {

    }

    public INDConnection(String IP) {

        this.IP = IP;
    }

    public void indConnection () throws Exception {

        SQLconnection sqlConnection = new SQLconnection();
        MongoDBConnection mongoDBConnection = new MongoDBConnection();

        try (Socket socket = new Socket(IP, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            // Write a response back to the server
            writer.write("user data\n");
            writer.write("pass data\n");
            writer.write("printout\n");
            writer.flush();
            System.out.println("Sent response: ");

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received message from IND560: " + message);

                if (message.contains("<dprint>")){
                    System.out.println("31 digit Data Received from IND560: " + message.substring(15, 48).replace(":", ""));
                    sqlConnection.insertRow(message.substring(15, 48).replace(":", ""));
                    mongoDBConnection.writeCrateWeighing(message.substring(15, 48).replace(":", ""));
                }
            }

        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }


    /*last line*/
}
