package io.syntx.socketClient;

        import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

        import java.io.*;
        import java.net.Socket;
        import java.net.UnknownHostException;
        import java.util.LinkedList;

/**
 * A Simple Socket client that connects to our socket server
 *
 */
public class SocketClient2 {

    private String hostname;
    private int port;
    Socket socketClient;

    public SocketClient2(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    public void connect() throws UnknownHostException, IOException{
        System.out.println("Attempting to connect to "+hostname+":"+port);
        socketClient = new Socket(hostname,port);
        System.out.println("Connection Established");
    }

    public void readResponse() throws IOException{
        int sizeOfListOfStrings = 0;
        ObjectInputStream reader = new ObjectInputStream(socketClient.getInputStream());
        try {
            System.out.println("List On server cosists of: " + ((LinkedList<String>)(reader.readObject())).size()+ " elements");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.print( " + sizeOfListOfStrings);
        // while ((userInput = stdIn.readLine()) != null) {
        //     System.out.println(userInput);
        // }
    }

    public void writeNewString() throws IOException{
        ObjectOutputStream writer = new ObjectOutputStream(socketClient.getOutputStream());
        writer.writeBytes("New String from Client 2");
        writer.flush();
    }

    public static void main(String arg[]){
        //Creating a SocketClient object
        SocketClient client = new SocketClient ("localhost",9991);
        try {
            //trying to establish connection to the server
            client.connect();
            //asking server for time
            client.writeNewString();
            //waiting to read response from server
            client.readResponse();

        } catch (UnknownHostException e) {
            System.err.println("Host unknown. Cannot establish connection");
        } catch (IOException e) {
            System.err.println("Cannot establish connection. Server may not be up."+e.getMessage());
        }
    }
}