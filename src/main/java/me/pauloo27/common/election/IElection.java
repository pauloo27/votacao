package me.pauloo27.common.election;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IElection extends Remote {
    public List<Candidate> listCandidates() throws RemoteException;

    public ElectionState getElectionState() throws RemoteException;

    public void vote(int number) throws RemoteException;
}
