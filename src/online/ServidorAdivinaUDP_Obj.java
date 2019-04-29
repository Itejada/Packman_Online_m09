package online;

import javafx.stage.Stage;
import sprites.Bolita;
import sprites.Fantasma;
import sprites.Packman;

import java.io.*;
import java.net.*;


public class ServidorAdivinaUDP_Obj {
    /** Servidor que es pensa un número i ClientAdivinaUDP_Obj.java l'ha d'encertar.
     ** Comunicació UDP amb transmissió d'objectes.
     ** Reb un Jugada i envia un Tauler de puntuacions
     ** Accepta varis clients i es tanca la comunicació quan tots els clients ja hagin encertat el número.
     **/

    DatagramSocket socket;
    int port, multiport=5557;
    MulticastSocket multisocket;
    InetAddress multicastIp;
    PartidaOnline partidaOnline;

    public ServidorAdivinaUDP_Obj(int port, PartidaOnline partidaOnline)  {
        try {
            socket = new DatagramSocket(port);
            multisocket = new MulticastSocket(multiport);
            multicastIp = InetAddress.getByName("224.0.0.10");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.partidaOnline=partidaOnline;
        this.port = port;
    }

    public void setPartidaOnline(PartidaOnline partidaOnline) {
        this.partidaOnline=partidaOnline;
    }

    public void runServer() throws IOException {
        byte [] receivingData = new byte[2048];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        //el servidor atén el port mentre hi hagi jugadors
        while(partidaOnline.getEstadoPartida() != PartidaOnline.EstadoPartida.ACABADA){
            System.out.println("esperando datos.");
            DatagramPacket packet = new DatagramPacket(receivingData, receivingData.length);
            socket.receive(packet);
            sendingData = processData(packet.getData(), packet.getLength());
            clientIP = packet.getAddress();
            clientPort = packet.getPort();
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort);
            socket.send(packet);
            //A cada jugada també enviem les dades del tauler per multicast
            //perquè el clients que ja hagin acabat puguin seguir el jic
            DatagramPacket multipacket = new DatagramPacket(sendingData, sendingData.length,
                    multicastIp,multiport);
            multisocket.send(multipacket);
        }
        socket.close();
    }

    //Processar la Jugada: Nom i numero
    private byte[] processData(byte[] data, int length) {
        System.out.println("hola");
        PartidaOnline partidaOnlineRecibida = null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            partidaOnlineRecibida = (PartidaOnline) ois.readObject();
            System.out.println(partidaOnlineRecibida.getFantasmas().get(0).getPosX());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //comprobamos vidas del packman
        if(partidaOnlineRecibida.getPackmans().get(0).getLives()<0 && partidaOnlineRecibida.getPackmans().get(0).getLives()<0) {
            partidaOnlineRecibida.setEstadoPartida(PartidaOnline.EstadoPartida.ACABADA);
        }

        setPartidaOnline(partidaOnlineRecibida);
        System.out.println(partidaOnline.getEstadoPartida());
        //La resposta és el tauler amb les dades de tots els jugadors
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(partidaOnlineRecibida);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] resposta = os.toByteArray();
        return resposta;
    }

    public static void main(String[] args) throws SocketException, IOException {
        PartidaOnline partidaOnline = new PartidaOnline();
        ServidorAdivinaUDP_Obj sAdivina = new ServidorAdivinaUDP_Obj(5556, partidaOnline);
        try {
            sAdivina.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fi Servidor");
    }
}