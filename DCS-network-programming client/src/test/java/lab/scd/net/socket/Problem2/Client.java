package lab.scd.net.socket.Problem2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private static Socket socket;
    private static BufferedReader in;
    private static PrintWriter out;

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getByName("localhost");
            socket = new Socket(address, 1900);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            SwingUtilities.invokeLater(Client::createAndShowGUI);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Nu s-a putut realiza conexiunea cu serverul: " + ex.getMessage(),
                    "Eroare Conexiune",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Client Calculator");
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel num1Label = new JLabel("Enter 1st number: ");
        num1Label.setBounds(40, 40, 150, 20);
        frame.add(num1Label);

        JTextField num1TextField = new JTextField();
        num1TextField.setBounds(40, 80, 100, 40);
        frame.add(num1TextField);

        JLabel num2Label = new JLabel("Enter 2nd number: ");
        num2Label.setBounds(240, 40, 150, 20);
        frame.add(num2Label);

        JTextField num2TextField = new JTextField();
        num2TextField.setBounds(240, 80, 100, 40);
        frame.add(num2TextField);

        JLabel opLabel = new JLabel("Enter math op (+,-,*,/): ");
        opLabel.setBounds(440, 40, 150, 20);
        frame.add(opLabel);

        JTextField opTextField = new JTextField();
        opTextField.setBounds(440, 80, 100, 40);
        frame.add(opTextField);

        JLabel resLabel = new JLabel("Result from server: ");
        resLabel.setBounds(240, 180, 150, 20);
        frame.add(resLabel);

        JTextField resTextField = new JTextField();
        resTextField.setBounds(240, 210, 120, 40);
        resTextField.setEditable(false);
        frame.add(resTextField);

        JButton submitButton = new JButton("SUBMIT");
        submitButton.setBounds(225, 300, 150, 50);
        frame.add(submitButton);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (out != null) {
                        out.println("END");
                    }
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String strNum1 = num1TextField.getText().trim();
                String strNum2 = num2TextField.getText().trim();
                String strOp = opTextField.getText().trim();

                if (strNum1.isEmpty() || strNum2.isEmpty() || strOp.isEmpty()) {
                    JOptionPane.showMessageDialog(frame,
                            "Completează toate câmpurile înainte de trimitere!",
                            "Câmpuri lipsă",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    int number1 = Integer.parseInt(strNum1);
                    int number2 = Integer.parseInt(strNum2);
                    char operator = strOp.charAt(0);

                    out.println(number1);
                    out.println(number2);
                    out.println(operator);

                    String response = in.readLine();
                    resTextField.setText(response);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Introdu numere valide în primele două căsuțe!",
                            "Eroare format",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "Eroare de comunicare cu serverul: " + ex.getMessage(),
                            "Eroare Rețea",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}