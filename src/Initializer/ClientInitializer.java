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
            boolean invalidRegister = userID == -1 || userID == -2 || userID == -3;
            while (invalidRegister) {
                switch (userID) {
                    case -1:
                        System.out.println("Esse usuario já existe");
                        break;
                    case -2:
                        System.out.println("Houve uma falha no registro");
                        break;
                    case -3:
                        System.out.println("Não há mais espaço para novos jogadores");
                        break;
                }
                System.out.println("Tente novamente. Insira seu nome para se cadastrar: ");
                name = input.nextLine();
                userID = stub.registerPlayer(name);
                invalidRegister = userID == -1 || userID == -2 || userID == -3;
            }
            System.out.println("Registrado!");
            System.out.println("======BEM-VINDO======");
            System.out.println("usuario: " + name);
            System.out.println("codigo: " + userID);

            System.out.println("Esperando outro jogador...");
            int gameID = stub.requestToEnterInGame(userID);
            boolean serverIsFull = gameID == -1;
            while (serverIsFull) {
                System.out.println("Tentando novamente...");
                gameID = stub.requestToEnterInGame(userID);
                serverIsFull = gameID == -1;
            }
            int startResult;
            do {
                startResult = stub.tryStart(userID, gameID);
            } while (startResult == -1);
            System.out.println("Jogo iniciado!");
            System.out.println("Codigo do jogo: " + gameID);
            System.out.println("Espere sua vez...");
            while (true) {

                while (!stub.itsMyTurn(userID)){
                    if(stub.checkForForceGameOver(gameID)){
                        break;
                    }
                }
                int result = -3;
                boolean exit = false;
                boolean invalidRoll = result == -3;
                while (result == -3) {
                    System.out.println("Quantas vezes deseja jogar o dado?");
                    String rollTimes = input.nextLine();
                    int rollDice;
                    try {
                        rollDice = Integer.parseInt(rollTimes);
                    }catch(NumberFormatException ex){
                        System.out.println("Caracteres invalidos, digite apenas numeros");
                        continue;
                    }
                    
                    result = stub.sendPlay(gameID, userID, rollDice);
                    
                    if(rollDice == 0){
                        stub.finishSession(userID);
                        exit=true;
                        System.out.println("Adeus");
                        break;
                    }
                    
                    if (stub.checkForForceGameOver(gameID)) {
                        System.out.println("Seu adversário saiu do jogo");
                        exit = true;
                        break;
                    }
                    
                    
                    
                    if (result == 1) {
                        System.out.println("PARABENS!!! VOCÊ VENCEU O JOGO :D");
                        exit = true;
                        break;
                    }
                    if(result == -3){
                        System.out.println("Jogada invalida, numero de jogadas excedido ou invalido");
                    }

                }
                if(exit){
                    break;
                }
                System.out.println(stub.playerStatus(userID));
                System.out.println("Board: " + stub.getBoard(gameID));
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
