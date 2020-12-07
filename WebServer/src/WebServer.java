import java.io.* ;
import java.net.* ;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.* ;

public final class WebServer
{
        public static void main(String argv[]) throws Exception
        {	
        	// Set the port number.
            int port = 9095;
            
            // Establish the listen socket.
            ServerSocket serverSocket = new ServerSocket(port);

            // Process HTTP service requests in an infinite loop.
            while (true) {
                    // Listen for a TCP connection request.
            	 	Socket clientSocket = serverSocket.accept();
            	 	
            	 	// Construct an object to process the HTTP request message.
            	 	HttpRequest request = new HttpRequest( clientSocket );

            	 	// Create a new thread to process the request.
            	 	Thread thread = new Thread(request);

            	 	// Start the thread.
            	 	thread.start();
            }
                
        }
}

final class HttpRequest implements Runnable
{
	final static String CRLF = "\r\n";
    Socket socket;

    // Constructor
    public HttpRequest(Socket socket) throws Exception 
    {
            this.socket = socket;
    }

    // Implement the run() method of the Runnable interface.
    public void run()
    {
    	try {
            processRequest();
	    } catch (Exception e) {
	            System.out.println(e);
	    }
    }

    private void processRequest() throws Exception
    {
    	BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = br.readLine()).isEmpty()) {
            requestBuilder.append(line + "\r\n");
        }

        String request = requestBuilder.toString();
        System.out.println("\n\n"+request);
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String method = requestLine[0];
        String path = requestLine[1];
        String version = requestLine[2];
        String host = requestsLines[1].split(" ")[1];

        Path filePath = getFilePath(path);
        if (Files.exists(filePath)) {
            // file exist
            String contentType = guessContentType(filePath);
            sendResponse(socket, "200 OK", contentType, Files.readAllBytes(filePath));
            //Thread.sleep(100000);
        } else {
            // 404
            byte[] notFoundContent = "<h1> 404 Not Found </h1>".getBytes(); //"".getBytes(); 
            sendResponse(socket, "404 Not Found", "text/html", notFoundContent);
            //Thread.sleep(100000);
        }

        Thread currentThread = Thread.currentThread();
        System.out.println("Request is responded Thread ID:" + currentThread.getId()+"\n\n");
    }
       
}
