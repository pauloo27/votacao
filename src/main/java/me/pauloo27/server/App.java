package me.pauloo27.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import me.pauloo27.server.election.Election;
import me.pauloo27.server.view.scenes.WinServer;

public class App {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        var election = new Election();
        var register = new WinServer(election);
        register.setVisible(true);
        startRMI(election);
    }

    public static void startRMI(Election election) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(1900);
        Naming.rebind("rmi://localhost:1900/election", election);
    }
}
