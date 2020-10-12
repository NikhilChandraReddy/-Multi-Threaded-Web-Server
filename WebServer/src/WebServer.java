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
    	
    }
       
}