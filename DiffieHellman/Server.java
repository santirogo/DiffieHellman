/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffie;
import java.net.*;
import java.io.*;
import java.math.BigInteger;

/**
 *
 * @author andres
 */
public class Server {
    final int PUERTO=5000;

    ServerSocket serverSocket;

    Socket socket;

    String mensajeRecibido;

    //SERVIDOR

    public static void main(String[] args) {
        Server server = new Server();
        server.initServer();
    }
    
    public void initServer(){


        try {

            serverSocket = new ServerSocket(PUERTO );/* crea socket servidor que escuchara en puerto 5000*/

            socket=new Socket();

            System.out.println("Esperando una conexión:");

            socket = serverSocket.accept();
            //Inicia el socket, ahora esta esperando una conexión por parte del cliente

            System.out.println("Un cliente se ha conectado.");

            //Canales de entrada y salida de datos
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Confirmando conexion al cliente....");

            //Recepcion de mensaje

            mensajeRecibido = (String) ois.readObject();
            
            //mensajeRecibido = entrada.readLine();

            
            
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter prime number:");
            BigInteger p=new BigInteger(br.readLine());
            System.out.println("Enter primitive root of "+p+":");
            BigInteger g=new BigInteger(br.readLine());
            System.out.println("Enter value for x less than "+p+":");
            BigInteger y=new BigInteger(br.readLine()); // Kpra
            BigInteger R2=g.modPow(y,p); //Kpa
            String R = R2.toString();
            System.out.println("Mesaje enviado: "+R);
            
            oos.writeObject(R);
            System.out.println("Mesaje recibido: "+mensajeRecibido);
            
            BigInteger R1 = new BigInteger(mensajeRecibido);
            
            BigInteger k1=R1.modPow(y,p);
            System.out.println("K: "+k1);

            System.out.println("Cerrando conexión...");

            ois.close();
            oos.close();
            serverSocket.close();//Aqui se cierra la conexión con el cliente

        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
