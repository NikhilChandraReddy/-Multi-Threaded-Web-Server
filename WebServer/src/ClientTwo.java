import java.net.*;
import java.io.*;

public class ClientTwo {
 
    public static void main(String[] args) {  
    //get arguments from the terminal/cmd
    //example java ClientOne localhost:<filename>
    
        if (args.length < 1) return;
 
        URL url;
 
        try {
            url = new URL(args[0]);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return;
        }
 
        String hostname = url.getHost();
        int port = url.getPort();
 
        try (Socket socket = new Socket(hostname, port)) {
 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
            writer.println("HEAD " + url.getPath() + " HTTP/1.0");
            writer.println("Host: " + hostname);
            writer.println("User-Agent: ClientOne");
            writer.println("Accept: text/html");
            writer.println("Accept-Language: en-US");
            writer.println("Connection: close");
            writer.println();
 
            InputStream input = socket.getInputStream();
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            String line;
 
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
    }
}
