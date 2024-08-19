package me.pauloo27.server.election;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.List;

import me.pauloo27.common.election.Candidate;
import me.pauloo27.common.election.ElectionState;
import me.pauloo27.common.election.IElection;
import me.pauloo27.common.utils.AppException;

public class Election extends UnicastRemoteObject implements IElection {
    private LinkedHashMap<Candidate, Integer> candidates;
    private ElectionState state;

    public Election() throws RemoteException {
        this.candidates = new LinkedHashMap<>();
        this.state = ElectionState.PRE_ELECTION;
    }

    public void addCandidate(Candidate candidate) throws AppException {
        if (this.state != ElectionState.PRE_ELECTION) {
            throw new AppException("Erro", "Só é possível cadastrar candidatos antes da eleição");
        }

        synchronized (this.candidates) {
            this.candidates.entrySet().stream()
                    .filter(c -> c.getKey().getNumber() == candidate.getNumber()).findFirst()
                    .ifPresentOrElse(c -> {
                        throw new AppException("Error", "Já existe um candidato com esse número");
                    }, () -> {
                        this.candidates.put(candidate, 0);
                    });
        }
    }

    public void startElection() throws AppException {
        if (this.state != ElectionState.PRE_ELECTION) {
            throw new AppException("Erro", "Eleição já foi iniciada");
        }

        this.state = ElectionState.ACTIVE;
    }

    public void endElection() throws AppException {
        if (this.state != ElectionState.ACTIVE) {
            throw new AppException("Erro", "Eleição já foi encerrada");
        }

        this.state = ElectionState.COMPLETED;
    }

    public LinkedHashMap<Candidate, Integer> getCandidates() {
        synchronized (this.candidates) {
            return new LinkedHashMap<>(this.candidates);
        }
    }

    @Override
    public ElectionState getElectionState() {
        return this.state;
    }

    @Override
    public List<Candidate> listCandidates() throws RemoteException {
        synchronized (this.candidates) {
            return this.candidates.keySet().stream().toList();
        }
    }

    @Override
    public void vote(int number) throws RemoteException, AppException {
        if (this.state != ElectionState.ACTIVE) {
            throw new AppException("Erro", "Só é possível votar durante a eleição");
        }

        synchronized (this.candidates) {
            this.candidates.entrySet().stream()
                    .filter(c -> c.getKey().getNumber() == number).findFirst()
                    .ifPresentOrElse(c -> {
                        this.candidates.put(c.getKey(), c.getValue() + 1);
                    }, () -> {
                        throw new AppException("Erro", "Candidato não encontrado");
                    });
        }
    }
}
