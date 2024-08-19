package me.pauloo27.client.view.scenes;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import me.pauloo27.common.election.Candidate;
import me.pauloo27.common.election.ElectionState;
import me.pauloo27.common.election.IElection;
import me.pauloo27.common.utils.AppException;
import me.pauloo27.common.utils.TableButton;
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

        String[] columns = { "Nome do Candidato", "Número do Candidato", "Votar" };
        this.table = new JTable(new DefaultTableModel(new Object[][] {}, columns));
        this.table.setDefaultEditor(Object.class, null);

        var scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.addAt(scroll, 10, 50, 600, 300);

        var voteAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleVote(e);
            }
        };

        new TableButton(table, voteAction, 2);
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
            model.addRow(new Object[] { candidate.getName(), candidate.getNumber(), "Votar" });
        });
    }

    public void handleVote(ActionEvent e) {
        var candidateName = (String) this.table.getValueAt(this.table.getSelectedRow(), 0);
        var candidateNumber = (int) this.table.getValueAt(this.table.getSelectedRow(), 1);

        var confirmed = JOptionPane.showConfirmDialog(this,
                "Confirma o voto no candidato " + candidateName + " (" + candidateNumber + ")?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);

        if (confirmed != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            this.election.vote(candidateNumber);
        } catch (AppException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    ex.getTitle(),
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao computar voto", "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return;
        }

        JOptionPane.showMessageDialog(this, "Voto computado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
}
