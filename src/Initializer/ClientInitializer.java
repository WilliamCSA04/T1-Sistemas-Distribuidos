/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Initializer;

import Server.IServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author realnetwoking
 */
public class ClientInitializer {
    
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            IServer stub = (IServer) registry.lookup("Server");
            System.out.println("Insira seu nome para se cadastrar: ");
            String name = input.nextLine();
            boolean response = stub.registerPlayer(name);
            System.out.println("Board: " + stub.getBoard(0));
            System.out.println("response: " + response);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
    
}
