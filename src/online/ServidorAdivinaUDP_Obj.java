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
    PartidaOnline partidaOnlineRecibida;

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

    public void runServer() throws IOException {
        byte [] receivingData = new byte[2048];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        //el servidor atén el port mentre hi hagi jugadors
        while(partidaOnline.getEstadoPartida() != PartidaOnline.EstadoPartida.ACABADA){
            DatagramPacket packet = new DatagramPacket(receivingData, receivingData.length);
            socket.receive(packet);
            sendingData = processData(packet.getData(), packet.getLength());
            clientIP = packet.getAddress();
            clientPort = packet.getPort();
            packet = new DatagramPacket(sendingData, sendingData.length, clientIP, clientPort);
            socket.send(packet);

            DatagramPacket multipacket = new DatagramPacket(sendingData, sendingData.length, multicastIp,multiport);
            multisocket.send(multipacket);
        }
        //Enviamos el ultimo resultado a todos.
        DatagramPacket packet = new DatagramPacket(receivingData, receivingData.length);
        socket.receive(packet);
        sendingData = processData(packet.getData(), packet.getLength());
        clientIP = packet.getAddress();
        clientPort = packet.getPort();
        packet = new DatagramPacket(sendingData, sendingData.length, clientIP, clientPort);
        socket.send(packet);

        socket.close();
    }

    //Processar la Jugada: Nom i numero
    private byte[] processData(byte[] data, int length) {

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            partidaOnlineRecibida = (PartidaOnline) ois.readObject();

            //Si el host se conecta por primera vez, actualizamos la partida del server y seteamos su primera conexión a false.
            if(partidaOnlineRecibida.isFirstConection() && partidaOnlineRecibida.getPlayersInGame().get(0).isHost()) {
                partidaOnlineRecibida.setFirstConection(false);
                partidaOnline=partidaOnlineRecibida;
            }

            if(partidaOnlineRecibida.isFirstConection()) {
                partidaOnlineRecibida.setFirstConection(false);
            }

            //Comprobamos que la partida recibida no sea el host y su partida tenga un solo jugador (aún no se ha unido a la partida)
            //Entonces añadimos el jugador, y su packman a la partida actual.
            if(partidaOnlineRecibida.getPlayersInGame().size()==1 && !partidaOnlineRecibida.getPlayersInGame().get(0).isHost()) {
                System.out.println(partidaOnlineRecibida.getPlayersInGame().get(0).getName());
                partidaOnline.addPlayer(partidaOnlineRecibida.getPlayersInGame().get(0));
                partidaOnline.addPacman2(partidaOnlineRecibida.getPackmans().get(0));
                partidaOnline.setEstadoPartida(PartidaOnline.EstadoPartida.EMPEZADA);
            }
            //Si no, actualizaremos la partidaOnline añadiendo los campos de ese player.
            else if(partidaOnlineRecibida.getPlayersInGame().size()>1) {
                if(partidaOnlineRecibida.getIdEmisor()==0) {
                    partidaOnline.setPackman(partidaOnlineRecibida.getPackmans().get(0),0);
                    partidaOnline.setJugador(partidaOnlineRecibida.getPlayersInGame().get(0),0);
                    partidaOnline.setFantasmas(partidaOnlineRecibida.getFantasmas());
                }else {
                    partidaOnline.setPackman(partidaOnlineRecibida.getPackmans().get(1),1);
                    partidaOnline.setJugador(partidaOnlineRecibida.getPlayersInGame().get(1),1);
                }

                if(partidaOnlineRecibida.getBolitasJugadas()>partidaOnline.getBolitasJugadas()) {
                    partidaOnline.setBolitasJugadas(partidaOnlineRecibida.getBolitasJugadas());
                    partidaOnline.setBolita(partidaOnlineRecibida.getBolita());
                }

                //comprobamos si algun jugador ya ha perdido.
                if(partidaOnlineRecibida.getPlayersInGame().get(0).isEliminado() && partidaOnlineRecibida.getPlayersInGame().get(1).isEliminado()) {
                    partidaOnline.setEstadoPartida(PartidaOnline.EstadoPartida.ACABADA);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        //La resposta és el tauler amb les dades de tots els jugadors
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            oos.writeObject(partidaOnline);
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