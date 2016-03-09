package io.syntx.socketServer;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class SocketClientHandler implements Runnable {

	private Socket client;
	//code that i wrote
	private List<String> listOfStrings = new LinkedList<String>();

	public SocketClientHandler(Socket client) {
		this.client = client;
	}


    //code that i wrote
	public List<String> getListOfStrings (){
		return listOfStrings;
	}
	public void setListOfStrings(String s){
        listOfStrings.add(s);
    }

	@Override
	public void run() {
		try {
			System.out.println("Thread started with name:"+Thread.currentThread().getName());
			Thread.sleep(2000);
            sendToClient();
            readResponse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void readResponse() throws IOException, InterruptedException {
        String clientInput="";
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
        //while ((clientInput = stdIn.readLine()) != null) {
                System.out.println("REQUEST TO SEND TIME RECEIVED. SENDING CURRENT TIME");
               setListOfStrings(clientInput);
                //break;

        //}
	}
    private void sendToClient() throws IOException, InterruptedException {
        setListOfStrings("First string");
        setListOfStrings("Second string");
        try {
            sendList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //code that i wrote
	private void sendList() throws IOException, InterruptedException {
		ObjectOutputStream writer = new ObjectOutputStream(client.getOutputStream());
		writer.writeObject(getListOfStrings());
		writer.flush();
		writer.close();
	}

}
