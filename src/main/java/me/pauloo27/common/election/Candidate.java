package me.pauloo27.common.election;

public class Candidate {
    private String name;
    private int number;

    public Candidate(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }
}
