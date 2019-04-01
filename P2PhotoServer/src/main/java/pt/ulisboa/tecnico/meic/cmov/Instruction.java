package pt.ulisboa.tecnico.meic.cmov;

import java.util.ArrayList;
import java.util.List;

public abstract class Instruction {

    /** Debug verbose messages **/
    public static final String VERBOSE_NOK1 = "User %s does not exist!";
    public static final String VERBOSE_NOK2 = "User %s failed login!";
    public static final String VERBOSE_NOK3 = "Username %s already exists!";
    public static final String VERBOSE_NOK4 = "Invalid sessionID!";
    public static final String VERBOSE_NOK5 = "Invalid albumID!";
    public static final String VERBOSE_NOK6 = "User %s does not exists or it is the owner when trying to add to album.";
    public static final String VERBOSE_NOK7 = "URL %s is invalid!";

    //Server responses must follow API!
    public static final String ERR     = "ERR"  ;
    public static final String NOK_1   = "NOK 1";
    public static final String NOK_2   = "NOK 2";
    public static final String NOK_3   = "NOK 3";
    public static final String NOK_4   = "NOK 4";
    public static final String NOK_5   = "NOK 5";
    public static final String NOK_6   = "NOK 6";
    public static final String NOK_7   = "NOK 7";
    public static final String OK_PLUS = "OK "  ;
    public static final String OK      = "OK"   ;


    /** The instruction name **/
    private String name;

    /** List of arguments **/
    protected List<String> args;

    protected Server server;

    Instruction() {
        this.name = "";
        this.args = new ArrayList<>();
        this.server = null;
    }

    Instruction(String name) {
        this.name = name;
        this.args = new ArrayList<>();
        this.server = null;
    }


    Instruction(String name, List<String> args) {
        this.name = name;
        this.args = args;
        this.server = null;
    }

    Instruction(String name, List<String> args, Server server) {
        this.name = name;
        this.args = args;
        this.server = server;
    }

    public String getName() {
        return name;
    }

    public List<String> getArgs() {
        return args;
    }

    /** Logic of instruction **/
    public abstract String execute();

    /**
     * Displays a debug message
     * @param message to be displayed
     */
    public void displayDebug(String message) {
        System.out.println("** " + name + ": " + message);
    }

    public void displayDebug(String message, String... args) {
        System.out.printf("** " + name + ": " + message, args);
    }

}
