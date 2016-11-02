/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Initializer;

import Server.IServer;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author realnetwoking
 */
public class ClientInitializer {
    
    private static int userID;
    private static String username="";
    
    public static void explainGame(){
        System.out.println("========    Speculate   =======");        
    }
 
    public static void preparePlayer(){
        Scanner sc = new Scanner(System.in);
        
        System.out.println("          ~Cadastro~         \n");
        System.out.print("Digite um nome de jogador: ");
        username = sc.nextLine();
    }
    
    private static int JOGADA = 0;
    
    public static int showMenu(IServer cm) throws RemoteException{
        
        String cmd = "";
        int linha = 0;
        int coluna = 0;
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Speculate : "+username+"$ ");
        String line = sc.nextLine();
        
        StringTokenizer st = new StringTokenizer(line," ");
        cmd = st.nextToken();
        int errorCode=-1;
        switch(cmd){
            case "sair":
                if(cm.finishSession(userID)==0){
                    System.out.println("Partida encerrada com sucesso!");
                }
                return 1;
            case "jogada":
                linha = Integer.parseInt(st.nextToken());
                coluna = Integer.parseInt(st.nextToken());
                errorCode = cm.sendPlay(userID);
                return handleErrorCode(JOGADA, errorCode);
            default:
                break;
        }
        return 0;
    }
    
    private static int handleErrorCode(int cmd, int errorCode){
        int status=1; 
        switch(cmd){
            case 0: // JOGADA
                if(errorCode==-3){
                    status =0;
                    System.out.println("Jogada já efetuada!");
                }
                if(errorCode==-2) {
                    status = 0;
                    System.out.println("Jogada inválida! Tente novamente...");
                }
                if(errorCode==-1){
                    status = 1;
                    System.out.println("Identificador inválido!");
                }
                if(errorCode==1) status = 0;
                if(errorCode==2){
                    status = 1;
                    System.out.println("Parabéns "+username+"! Você ganhou!");
                }
                break;
            case 1: 
                if(errorCode==-2){
                    status = 0;
                    System.out.println("Posição inválida!");
                    System.out.println("Ah.. não esqueça de escolher casas entre (1,1) e (5,5).");
                }
                if(errorCode==-1){
                    status = 1;
                    System.out.println("Identificador inválido!");
                }
                if(errorCode==1) status = 0;
                break;
            case 2: 
                if(errorCode==-2){
                    status = 0;
                    System.out.println("Posição inválida!");
                    System.out.println("Ah.. não esqueça de escolher casas entre (1,1) e (5,5).");
                }
                if(errorCode==-1){
                    status = 1;
                    System.out.println("Identificador inválido!");
                }
                if(errorCode==1) status = 0;
                break;
            default:
                break;
        }
        return status;
    }

    public static void main(String[] args){
        
        String hostname = "127.0.0.1";
        explainGame();
        preparePlayer();
        
        try {
            IServer serverInterface = (IServer) Naming.lookup("//"+hostname+"/Speculate");
            
            System.setProperty("sun.rmi.transport.connectionTimeout","120000");
            System.out.println("sun.rmi.transport.connectionTimeout==" 
            +System.getProperty("sun.rmi.transport.connectionTimeout"));
       
            userID = serverInterface.registerPlayer(username);
            if(userID==-1){
                System.out.println("Jogador já cadastrado!");
                return;
            }else if(userID==-2){
                System.out.println("Número maximo de jogadores atingido!");
                return;
            }
            System.out.println("    ~Que os jogos comecem!~     ");
            System.out.println("\n"+serverInterface.getBoard(userID)+"\n");
            int sairDoLoop = 0;
            while(sairDoLoop == 0){
                sairDoLoop = showMenu(serverInterface);
                System.out.println("\n"+serverInterface.getBoard(userID)+"\n");
            }
        } catch ( MalformedURLException | RemoteException | NotBoundException ex) {
//            ex.printStackTrace();
            System.err.println("Ops! - Ocorreu algum problema ao comunicar com o servidor...");
            
        }
}
}
