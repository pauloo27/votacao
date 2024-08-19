package me.pauloo27.common.election;

import java.util.LinkedHashMap;

import me.pauloo27.common.utils.AppException;

public class Election {
    private LinkedHashMap<Candidate, Integer> candidates;
    private ElectionState state;

    public Election() {
        this.candidates = new LinkedHashMap<>();
        this.state = ElectionState.PRE_ELECTION;
    }

    public void addCandidate(Candidate candidate) throws AppException {
        if (this.state != ElectionState.PRE_ELECTION) {
            throw new AppException("Erro", "Só é possível cadastrar candidatos antes da eleição");
        }

        this.candidates.entrySet().stream()
                .filter(c -> c.getKey().getNumber() == candidate.getNumber()).findFirst()
                .ifPresentOrElse(c -> {
                    throw new AppException("Error", "Já existe um candidato com esse número");
                }, () -> {
                    this.candidates.put(candidate, 0);
                });
    }

    public void startElection() throws AppException {
        if (this.state != ElectionState.PRE_ELECTION) {
            throw new AppException("Erro", "Eleição já foi iniciada");
        }

        this.state = ElectionState.ACTIVE;
    }

    public LinkedHashMap<Candidate, Integer> getCandidates() {
        return new LinkedHashMap<>(this.candidates);
    }

    public enum ElectionState {
        PRE_ELECTION,
        ACTIVE,
        COUTING_VOTES,
        COMPLETED,
    }
}
