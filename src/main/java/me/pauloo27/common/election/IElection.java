package me.pauloo27.common.election;

import java.util.Map;

public interface IElection {
    Map<Candidate, Integer> listCandidates();

    void syncNodeVotes(Map<Candidate, Integer> candidateVotes);

    ElectionState getElectionState();
}
