package pt.ulisboa.tecnico.meic.cmov;

import java.util.List;

public class FindUser extends Instruction {

    FindUser(List<String> args, Server server) {
        super("USR-FND", args, server);
    }

    @Override
    public String execute() {

        try {

            if (args.size() != 3)
                return ERR;

            String sessionId = args.get(1);
            String pattern = args.get(2);
            List<String> matches;

            if (server.getUserNameBySessionID(sessionId) == null) {
                displayDebug(VERBOSE_NOK4);
                return NOK_4;
            } else {

                if (pattern.contains("*")) {
                    matches = server.findUserNameByPattern("\\b(\\w*" + pattern.replace("*", "") + "\\w*)\\b");
                } else matches = server.findUserNameByPattern("\\b(\\w*" + pattern + "\\w*)\\b");

                return OK_PLUS + server.representList(matches);
            }
        } catch(NullPointerException | IndexOutOfBoundsException e) {
            return ERR;
        }
    }
}
