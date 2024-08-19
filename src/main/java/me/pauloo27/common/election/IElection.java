package me.pauloo27.common.election;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IElection extends Remote {
    public List<Candidate> listCandidates() throws RemoteException;

    // public void syncNodeVotes(Map<Candidate, Integer> candidateVotes) throws
    // RemoteException;

    public ElectionState getElectionState() throws RemoteException;
}
