import Services.ConfigFileReaderService;
import Services.CoordinationService.CoordinationService;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
import org.apache.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import Services.ChatService.ChatClientService;

//
//public class Server
//{
//    //initialize socket and input stream
//    private Socket          socket   = null;
//    private ServerSocket    server   = null;
//    private DataInputStream in       =  null;
//
//    // constructor with port
//    public Server(int port)
//    {
//        // starts server and waits for a connection
//        try
//        {
//            server = new ServerSocket(port);
//            System.out.println("Server started");
//
//            System.out.println("Waiting for a client ...");
//
//            socket = server.accept();
////            System.out.println(server.);
//            System.out.println("Client accepted");
//
//            // takes input from the client socket
//            in = new DataInputStream
//                    (socket.getInputStream());
//            String line = "";
//
//            // reads message from client until "Over" is sent
//            String msg = in.readUTF();
//            System.out.println(msg);
//            System.out.println("Closing connection");
//
//            // close connection
//            socket.close();
//            in.close();
//        }
//        catch(IOException i)
//        {
//            System.out.println(i);
//        }
//    }
//
//    public static void main(String args[])
//    {
//        Server server1 = new Server(4444);
//        Server server2 = new Server(5000);
//    }
//}

//=====================================================================================
//public class Server {
//    public static void main(String[] args) throws IOException {
//
////        System.out.println(new YMLReader().readClientsYML().getClients());
////        new YMLWriter().writeClientsYML(new YMLReader().readClientsYML());
//        ServerSocket ss = new ServerSocket(4444);
//        Socket s = ss.accept();
//        System.out.println("Client Connected");
//        InputStreamReader in = new InputStreamReader(s.getInputStream());
//        BufferedReader bf = new BufferedReader(in);
//
////        String msg = bf.readLine();
////        System.out.println(msg);
//        StringBuilder sb = new StringBuilder();
//
//
////        String line;
////        while ((line = bf.readLine()) != null) {
////            sb.append(line);
////        }
//        sb.append(bf.readLine());
//        System.out.println(sb);
//        JSONObject json = new JSONObject(sb.toString());
//        String identity = (String) json.get("identity");
//        System.out.println(identity);
////        JSONObject json = new JSONObject(sb.toString());
////        System.out.println(json);
//
//    }
//
//}

public class Server {
    public static void main(String[] args) throws IOException {

        String serverID;
        String serversConf;
        CmdLineValues values = new CmdLineValues();
        CmdLineParser parser = new CmdLineParser(values);

        try {
            parser.parseArgument(args);
            serverID = values.getServerId();
            serversConf = values.getServerConfig();
            System.setProperty("serverID", serverID);

            Logger logger = Logger.getLogger(Server.class);
            logger.info("Server configuration.");

            new ConfigFileReaderService().readConfigFile(serverID, serversConf);
            ChatClientService chatClient = ChatClientService.getInstance();
            Thread chatClientThread = new Thread(chatClient);
            CoordinationService coordinator = CoordinationService.getInstance();
            Thread coordinatorThread = new Thread(coordinator);
            chatClientThread.start();
            coordinatorThread.start();
//            Thread coordinatorThread = new Thread(() -> {
//                try {
//                    CoordinationServer.getInstance().run();
//                } catch (Exception e) {
//                    logger.error(e.getMessage());
//                }
//            });
//
//            coordinatorThread.start();
//            CoordinationServer.getInstance().SelectCoordinator();
//            ChatClientServer.getInstance().run();

        } catch (CmdLineException e) {
            e.printStackTrace();
        }

    }
}