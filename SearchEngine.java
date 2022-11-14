import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler implements URLHandler {
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        System.out.printf("Path: %s Query: %s\n", url.getPath(), url.getQuery());
        if (url.getPath().equals("/")) {
            if (strings.size() == 0) {
                return String.format("There's nothing to search for!\nTry adding strings to the data bank so you can query them.\n");
            } else {
                return String.format("Your strings: %s", String.join("\n", strings));
            }
        } else if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strings.add(parameters[1]);
                return String.format("%s added!", parameters[1]);
            } else {
                return "You forgot the s\nThe query should have the format ?s=STRING";
            }
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String search = parameters[1];
                ArrayList<String> results = new ArrayList<String>();
                for (String str: strings) {
                    if (str.contains(search)) {
                        results.add(str);
                    }
                }
                return String.format("Results:\n%s", String.join("\n", results));
            } else {
                return "You forgot the s\nThe query should have the format ?s=STRING";
            }
        }
        return "404 Not Found!";
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}