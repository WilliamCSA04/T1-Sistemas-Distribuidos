/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Initializer;

import Server.Server;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author realnetwoking
 */
public class ServerInitializer {

    public static void main(String args[]) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (RemoteException e) {
            System.out.println("RMI registry already running.");
        }
        try {
            Naming.rebind("Server", new Server());
            System.out.println("PidServer is ready.");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("PidServer failed:");
            e.printStackTrace();
        }
    }
}
