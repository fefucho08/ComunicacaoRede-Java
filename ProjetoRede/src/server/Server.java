package server;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import voting.Election;
import voting.ElectionController;
import voting.Vote;

import java.io.*;

public class Server implements Runnable {
    private ArrayList<ClientHandler> connections;
    private ElectionController electionController;
    private ServerSocket serverSocket;
    private boolean acceptingConnections;
    private ExecutorService pool;
    
    public Server(ElectionController electionController) {
        connections = new ArrayList<>();
        acceptingConnections = true;
        pool = Executors.newCachedThreadPool();
        this.electionController = electionController;
    }
    
    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("Server running on port 9999...");
            while(acceptingConnections && !serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                ClientHandler handler = new ClientHandler(client);
                connections.add(handler);
                pool.execute(handler);
                System.out.println("New client connected!");
            }
        } catch (IOException e) {
        	if(!serverSocket.isClosed()) {
        		e.printStackTrace();
        	}
        }
    }
    
    public void shutdown() {
        try {
            acceptingConnections = false;
            pool.shutdown();
            
            if(serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            for(ClientHandler client : connections) {
                if(client != null) {
                    client.shutdown();
                }
            }
            electionController.generateElectionReport();
            System.out.println("Server shutdown!");
        } catch(IOException e) {
        	// ignore
        }
    }
    
    class ClientHandler implements Runnable {
        
        private Socket client;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        
        public ClientHandler(Socket client) {
            this.client = client;
        }
        
        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(client.getOutputStream());
                in = new ObjectInputStream(client.getInputStream());
                
                if(electionController != null) {
                    sendElection(electionController.getElection());
                }

                try {
                	Vote clientVote = (Vote) in.readObject();

                	if(electionController.addVote(clientVote)) {
                		out.writeObject("Voto computado com sucesso!");
                	}
                	else {
                		out.writeObject("CPF já votou!");
                	}
                	out.flush();
                	out.reset();
                } catch (ClassNotFoundException e) {
                	e.printStackTrace();
                }
            } catch(IOException e) {
            	// ignore
            } finally {
            	shutdown();
            }
        }
        
        public void sendElection(Election election) throws IOException {
            out.writeObject(election);
            out.flush();
            out.reset();
        }
        
        public void sendMessage(String message) throws IOException {
        	out.writeObject(message);
        	out.flush();
        	out.reset();
        }
        
        public void shutdown() {
            try {
                in.close();
                out.close();
                if(!client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
            	// ignore
            } finally {
            	synchronized(connections) {
            		connections.remove(this);
            	}
            }
        }
       
    }
}
