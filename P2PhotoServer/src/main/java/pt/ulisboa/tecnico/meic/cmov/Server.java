package pt.ulisboa.tecnico.meic.cmov;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Server {

    private List<User> users;
    private List<Pair<String, String>> loggedInUsers;

    /** Socket related **/
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private static final int SERVER_PORT = 10000;

    public Server() {
        this.users = new ArrayList<>();
        this.loggedInUsers = new ArrayList<>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public boolean usernameExists(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public String usernameIsLoggedOn(String username) {
        for (Pair<String, String> user: this.loggedInUsers) {
            if (user.getKey().equals(username))
                return user.getValue();
        }
        return null;
    }

    public User getUserByUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    /** === SOCKET RELATED === **/

    public void initSocket() {
        try {
            this.serverSocket = new ServerSocket(SERVER_PORT);
            this.clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            List<String> args;

            while(true) {
                message = in.readLine();

                args = parseInstruction(message);

                out.println(processInstruction(args));

            }

        } catch(IOException e) {
            System.err.println("IOException!");
        }
    }

    public void stopSocket() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch(IOException e) {
            System.err.println("IOException!");
        }
    }


    private List<String> parseInstruction(String instruction) {
        List<String> args = null;
        if (instruction.startsWith("LOGIN") || instruction.startsWith("SIGNUP") || (instruction.startsWith("LOGOUT")) || (instruction.startsWith("ALB-AUP"))) {
            args = Arrays.asList(instruction.split(" "));
        }
        return args;
    }

    private String processInstruction(List<String> args) {

        try {
            String instruction = args.get(0);
            String username;
            String password;
            String sessionId;
            User user;

            switch (instruction) {
                case "LOGIN":
                    username = args.get(1);
                    password = args.get(2);

                    if (!usernameExists(username)) {
                        System.out.println("** LOGIN: User " + username + " does not exists");
                        return "NOK 1";
                    } else {
                        user = getUserByUsername(username);
                        if (!user.getPassword().equals(password)) {
                            System.out.println("** LOGIN: User " + username + " failed!");
                            return "NOK 2";
                        } else {

                            sessionId = usernameIsLoggedOn(username);

                            if (sessionId == null) {
                                sessionId = Long.toHexString(Double.doubleToLongBits(Math.random()));
                                this.loggedInUsers.add(new Pair<>(username, sessionId));
                            }
                            return "OK " + sessionId;


                        }

                    }

                case "SIGNUP":
                    username = args.get(1);
                    password = args.get(2);


                    //Check if user exists
                    if (usernameExists(username)) {
                        System.out.println("** SIGNUP: User " + username + " already exists");
                        return "NOK 3";
                    } else {
                        //Adds user
                        users.add(new User(username, password));
                        System.out.println("** SIGNUP: Successfully added user " + username);
                        return "OK";
                    }

                case "LOGOUT":

            }
        } catch(Exception e) {
            return "ERR";
        }

        return null;
    }


}
