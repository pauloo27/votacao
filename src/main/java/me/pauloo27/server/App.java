package me.pauloo27.server;

import me.pauloo27.server.election.Election;
import me.pauloo27.server.view.scenes.WinStart;

public class App {
    public static void main(String[] args) {
        var election = new Election();
        var register = new WinStart(election);
        register.setVisible(true);
    }
}
