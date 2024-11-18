package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import events.ElectionEvent;
import events.ElectionListener;
import voting.Election;
import voting.Vote;

public class Client implements Runnable {
    
    private Socket clientSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Vote currentVote;
    private Election election;
    private boolean canVote;
    private boolean connected;
    private ArrayList<ElectionListener> listeners;
    private String serverIP = "192.168.2.50";
    private int serverPort = 9999;
   
    public Client() {
    	listeners = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            clientSocket = new Socket(serverIP, serverPort);
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            connected = true;
            
            InputHandler handler = new InputHandler();
            Thread handlerThread = new Thread(handler);
            
            election = (Election) in.readObject();
            canVote = true;
            
            handlerThread.start();
            notifyElectionRecieved(election);
        } 
        catch(IOException e) { 
        	JOptionPane.showMessageDialog(null, "Ocorreu um erro ao se conectar com o servidor: \n" + e.getMessage());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void shutdown() {
        canVote = false;
        connected = false;
        try {
            in.close();
            out.close();
            if(!clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addElectionListener(ElectionListener listener) {
    	if(!listeners.contains(listener)) {
    		listeners.add(listener);
    	}
    }
    public void sendVote(String cpf, String option) {
        currentVote = new Vote(cpf, option);
    }
    
    public boolean isConnected() {
    	return connected;
    }
    
    private void notifyElectionRecieved(Election election) {
    	for(ElectionListener listener : listeners) {
    		listener.onElectionRecieved(new ElectionEvent(this), election);
    	}
    }
    
    private void notifyServerMessage(String serverMessage) {
    	for(ElectionListener listener : listeners) {
    		listener.serverMessageRecieved(new ElectionEvent(this), serverMessage);
    	}
    }
    
    class InputHandler implements Runnable {
        
        @Override
        public void run() {
            try {
                while (connected) {
                    if (canVote && currentVote != null) {
                        out.writeObject(currentVote);
                        out.flush();
                        out.reset();
                        String serverMessage = (String) in.readObject();
                        notifyServerMessage(serverMessage);
                        currentVote = null;
                        connected = false;
                    }
                    Thread.sleep(100);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
            	shutdown();
            }
        }
    }
}
