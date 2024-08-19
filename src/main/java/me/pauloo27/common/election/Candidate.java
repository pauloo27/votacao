package me.pauloo27.common.election;

import java.io.Serializable;

public class Candidate implements Serializable {
        private static final long serialVersionUID = 1L;

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
