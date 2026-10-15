package lab.scd.net.socket.Problem2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception{
        Scanner userInput = new Scanner(System.in);
        Socket socket = null;

        int num1, num2;
        char operation;
        int result;
        System.out.println("Enter first number:");
        num1 = userInput.nextInt();
        System.out.println("Enter 2nd number:");
        num2 = userInput.nextInt();
        System.out.println("Enter math operation:");
        operation = userInput.next().charAt(0);

        try {
            // creating object address that identifies the server address
            InetAddress address = InetAddress.getByName("localhost");
            // the alternative could be used: InetAddress.getByName ("127.0.0.1")
            socket = new Socket(address, 1900);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Output is automatically flushed
            // by PrintWriter:
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                    true);

            out.println("first number: " + num1);
            out.println("second number: " + num2);
            out.println("operation to be done: " + operation);
            out.print("END");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            socket.close();
        }
    }

}
