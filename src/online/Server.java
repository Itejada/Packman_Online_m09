package online;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class Server {

    Gson gson=new Gson();
    Map<InetAddress,Player> players;
    int numero;
    String mensaje;

    DatagramSocket socket;

    public void init(int port) throws SocketException {
        players = new HashMap<>();
        socket = new DatagramSocket(port);
        this.numero=(int) (Math.random()*15);
    }

    public void runServer() throws IOException {
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        //el servidor atén el port indefinidament
        while(true){
            //creació del paquet per rebre les dades
            DatagramPacket packet = new DatagramPacket(receivingData, 1024);
            //espera de les dades
            socket.receive(packet);
            //processament de les dades rebudes i obtenció de la resposta
            sendingData = processData(packet.getData(), packet.getLength());
            //obtenció de l'adreça del client
            clientIP = packet.getAddress();
            //obtenció del port del client
            clientPort = packet.getPort();
            //creació del paquet per enviar la resposta
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort);
            //enviament de la resposta
            socket.send(packet);
        }
    }

    private byte[] processData(byte[] data, int length) {
//procés diferent per cada aplicació

//        players= gson.fromJson(ByteBuffer.wrap(data).toString(),players.);

        return data ;
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.init(5555);
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}