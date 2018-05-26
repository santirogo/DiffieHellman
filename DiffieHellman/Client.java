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
public class Client {
    final String HOST = "192.168.0.6";

    final int PUERTO=5000;

    Socket socket;

    ObjectOutputStream oos;
    ObjectInputStream ois;

    //Cliente

    public static void main(String[] args) {
        Client cliente = new Client();
        cliente.initClient();
    }
    
    
    public void initClient(){ /*ejecuta este metodo para correr el cliente */

        try{

            socket = new Socket( HOST , PUERTO ); /*conectar a un servidor en localhost con puerto 5000*/

            //creamos el flujo de datos por el que se enviara un mensaje

            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            //enviamos el mensaje

            BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter prime number:");
            BigInteger p=new BigInteger(br.readLine());
            System.out.println("Enter primitive root of "+p+":");
            BigInteger g=new BigInteger(br.readLine());
            System.out.println("Enter value for x less than "+p+":");
            BigInteger x=new BigInteger(br.readLine()); // Kpra
            BigInteger R1=g.modPow(x,p); //Kpa
            String R = R1.toString();
            System.out.println("Mensaje enviado: "+R);
            
            oos.writeObject(R);
            String message = (String) ois.readObject();
            System.out.println("Mensaje recibido: "+message);
            
            BigInteger R2 = new BigInteger(message);
            
            BigInteger k2=R2.modPow(x,p);
            System.out.println("K: "+k2);
//            salida.writeUTF(R);
            

//            salida.writeUTF("hola que tal!!");

            //cerramos la conexión
            ois.close();
            oos.close();
            socket.close();

        }catch(Exception e ){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
