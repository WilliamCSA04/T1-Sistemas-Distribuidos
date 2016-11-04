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
            System.out.println("Registrado!");

            String option = "";
            System.out.println("======BEM-VINDO======");
            System.out.println("usuario: " + name);
            System.out.println("codigo: " + userID);

            do {

                System.out.println("Esperando outro jogador...");
                int gameID = stub.requestToEnterInGame(userID);
                int startResult;
                do {
                    startResult = stub.tryStart(userID, gameID);
                } while (startResult == -1);
                while (true) {
                    System.out.println("Jogo iniciado!");
                    System.out.println("Codigo do jogo: " + gameID);
                    System.out.println("Espere sua vez...");
                    while (!stub.itsMyTurn(userID));
                    System.out.println("Quantas vezes deseja jogar o dado?");
                    String rollTimes = input.nextLine();
                    if(stub.checkForForceGameOver(gameID)){
                        System.out.println("Seu adversário saiu do jogo");
                        break;
                    }
                    int result = stub.sendPlay(gameID, userID, Integer.parseInt(rollTimes));
                    if (result == 1) {
                        System.out.println("PARABENS!!! VOCÊ VENCEU O JOGO :D");
                        break;
                    } else{
                        
                    }
                    System.out.println(stub.playerStatus(userID));
                    System.out.println("Board: " + stub.getBoard(gameID));
                }
            } while (!option.equals("0"));

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
