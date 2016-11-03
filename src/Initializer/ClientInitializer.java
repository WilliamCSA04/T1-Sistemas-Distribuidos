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
            int userID = stub.registerPlayer(name);
            
            int gameID = stub.tryStart(userID);
            System.out.println("Esperando outro jogador...");
            while (gameID == -1) {
                gameID = stub.tryStart(userID);
                if(gameID<0){
                    System.out.println("userID invalido - " +gameID +": Erro no registro");
            }
            }
            System.out.println("response: " + userID);
            while (true) {
                System.out.println("Jogo iniciado!");
                System.out.println("Codigo do jogo: " + gameID);
                System.out.println("Espere sua vez...");
                while (!stub.itsMyTurn(userID, gameID));
                System.out.println("Quantas vezes deseja jogar o dado?");
                String rollTimes = input.nextLine();
                int result = stub.sendPlay(gameID, userID, Integer.parseInt(rollTimes));
                if(result == 1){
                    System.out.println("PARABENS!!! VOCÊ VENCEU O JOGO :D");
                    break;
                }
                System.out.println(stub.playerStatus(userID));
                System.out.println("Board: " + stub.getBoard(0));
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
