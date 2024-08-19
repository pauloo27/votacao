package me.pauloo27.client.view.scenes;

import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import me.pauloo27.common.election.Candidate;
import me.pauloo27.common.election.ElectionState;
import me.pauloo27.common.election.IElection;
import me.pauloo27.common.view.WinBase;

public class WinVote extends WinBase {
    private IElection election;
    private JTable table;

    public WinVote(IElection election) {
        super("Votação", 800, 500);
        this.election = election;
        this.showElection();
    }

    @Override
    public void setupComponents() {
        super.setupComponents();

        String[] columns = { "Nome do Candidato", "Número do Candidato" };
        this.table = new JTable(new DefaultTableModel(new Object[][] {}, columns));
        this.table.setEnabled(false);

        var scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.addAt(scroll, 10, 50, 600, 300);

    }

    public void showElection() {
        ElectionState state = null;
        List<Candidate> candidates = null;

        try {
            state = this.election.getElectionState();
            candidates = this.election.listCandidates();
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar estado da eleição", "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            System.exit(1);
        }

        DefaultTableModel model = (DefaultTableModel) this.table.getModel();
        model.setRowCount(0);
        candidates.forEach((candidate) -> {
            model.addRow(new Object[] { candidate.getName(), candidate.getNumber() });
        });
    }

}
