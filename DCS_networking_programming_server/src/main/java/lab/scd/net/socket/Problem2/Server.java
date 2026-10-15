package lab.scd.net.socket.Problem2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket ss=null;
        Socket s=null;
        int num1,num2;
        char operator;
        int result = 0;

        try{
            String line="";
            ss = new ServerSocket(1900); //create a serversocket object
            System.out.println("Server is waiting for connections...");
            s = ss.accept(); //start waiting for connections at port 1900
            //When a client connects, `ss.accept()` returns.
            //a Socket object that identifies the connection

            //Create the input and output streams.
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));

            PrintWriter out = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(
                            s.getOutputStream())),true);

            //extract the IP address and port from which the client connected
            InetSocketAddress remoteadr = (InetSocketAddress)s.getRemoteSocketAddress();
            String remotehost = remoteadr.getHostName();
            int remoteport = remoteadr.getPort();

            System.out.println("Client nou conectat: "+remotehost+":"+remoteport);

            while(!line.equals("END")){
                line = in.readLine(); //read data from the client
                num1 = Integer.parseInt(line.trim());
                line = in.readLine();
                num2 = Integer.parseInt(line.trim());
                line = in.readLine();
                System.out.println("furst num: "+num1);
                System.out.println("2nd num: "+num2);
                operator = line.trim().charAt(0);
                out.flush();
                //System.out.println("Server a receptionat:"+line);
                //out.println("ECHO "+line); //send data to the client
                if(operator != '0'){
                    result = performComputation(num1,num2,operator);
                }
                //System.out.println("Result is "+result);
                out.println(result);
            }

            System.out.println("Aplicatie server gata.");

        }catch(Exception e){e.printStackTrace();}
        finally{
            ss.close();
            if(s!=null) s.close();
        }
    }

    public static int performComputation(int number1, int number2, char operation){
        switch (operation){
            case '+':
                return number1 + number2;
            case '-':
                return number1 - number2;
            case '*':
                return number1 * number2;
            case '/':
                return number1 / number2;
            default:
                System.out.println("The operation received is invalid");
        }
        return 0;
    }
}
