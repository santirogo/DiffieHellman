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
    Socket socket2;

    String mensajeRecibido1;
    String mensajeRecibido2;

    //SERVIDOR

    public static void main(String[] args) {
        Server server = new Server();
        server.initServer();
    }
    
    public void initServer(){


        try {

            serverSocket = new ServerSocket(PUERTO );/* crea socket servidor que escuchara en puerto 5000*/

            socket=new Socket();
            socket2=new Socket();

            System.out.println("Esperando una conexión:");
            
            System.out.println("ashashshas");
            socket = serverSocket.accept();
            socket2 = serverSocket.accept();
            //Inicia el socket, ahora esta esperando una conexión por parte del cliente

            System.out.println("Un cliente se ha conectado.");

            //Canales de entrada y salida de datos
            
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
            ObjectInputStream ois2 = new ObjectInputStream(socket2.getInputStream());
            ObjectOutputStream oos2 = new ObjectOutputStream(socket2.getOutputStream());

            System.out.println("Confirmando conexion al cliente....");

            //Recepcion de mensaje

            //mensajeRecibido1 = (String) ois.readObject();
            //mensajeRecibido2 = (String) ois2.readObject();
            
            
            //mensajeRecibido = entrada.readLine();

            
            
            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
//            System.out.println("Enter prime number:");
//            BigInteger p=new BigInteger(br.readLine()); //n
            BigInteger p = new BigInteger("17");
//            System.out.println("Enter primitive root of "+p+":");
//            BigInteger g=new BigInteger(br.readLine()); //a
            BigInteger g = new BigInteger("2");
//            System.out.println("Enter value for x less than "+p+":");
//            BigInteger y1=new BigInteger(br.readLine()); // Kprb'
            BigInteger kprb = new BigInteger("11"); //Kprb'
            System.out.println("Clave privada para reemplazar a Bob: "+kprb);
            BigInteger R2=g.modPow(kprb,p); //Kpb'
            System.out.println("Clave pública para reemplazar a Bob: "+R2);
//            System.out.println("Enter value for x less than "+p+":");
//            BigInteger y2=new BigInteger(br.readLine()); // Kpra'
            BigInteger kpra=new BigInteger("7"); //Kpra'
            System.out.println("Clave privada para reemplazar a Alice: "+kpra);
            BigInteger R3=g.modPow(kpra,p); //Kpa'
            System.out.println("Clave pública para reemplazar a Alice: "+R3);
            String mensaje1 = R2.toString();
            String mensaje2 = R3.toString();
            
            mensajeRecibido1 = (String) ois.readObject(); //Interceptar Mensaje de Alice
            
            System.out.println("Mensaje recibido de Alice : "+mensajeRecibido1);
            oos2.writeObject(mensaje2); //Se envia Kpa falsa a Bob
            System.out.println("Kpa falsa enviada a Bob: "+mensaje2);
            
            mensajeRecibido2 = (String) ois2.readObject();
            
            System.out.println("Mesaje recibido de Bob: "+mensajeRecibido2);
            oos.writeObject(mensaje1); //Se envia Kpb falsa a Alice
            System.out.println("Kpb falsa enviada a Alice: "+mensaje1);
            
            BigInteger m1 = new BigInteger(mensajeRecibido1);
            BigInteger m2 = new BigInteger(mensajeRecibido2);
            
            BigInteger k1 = m1.modPow(kprb,p);
            System.out.println("K con Alice calculada por Eve: "+k1);
            
            BigInteger k2 = m2.modPow(kpra,p);
            System.out.println("K con Bob calculada por Eve: "+k2);

            System.out.println("Cerrando conexión...");

            ois.close();
            oos.close();
            serverSocket.close();//Aqui se cierra la conexión con el cliente

        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
        }
    }
}