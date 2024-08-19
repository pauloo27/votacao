package me.pauloo27.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import me.pauloo27.client.view.scenes.WinVote;
import me.pauloo27.common.election.IElection;

public class App {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        IElection election = (IElection) Naming.lookup("rmi://localhost:1900/election");

        var vote = new WinVote(election);
        vote.setVisible(true);
    }
}
