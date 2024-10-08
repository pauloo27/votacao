package me.pauloo27.server.view.scenes;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import me.pauloo27.common.election.Candidate;
import me.pauloo27.server.election.Election;
import me.pauloo27.common.utils.AppException;
import me.pauloo27.common.view.WinBase;

public class WinServer extends WinBase {
    private Election election;

    private JTable table;
    private JButton btnNewCandidate;
    private JButton btnStart;
    private JButton btnEnd;
    private JLabel lblStatus;

    public WinServer(Election election) {
        super("Servidor - Início", 800, 500);
        this.election = election;
        this.startRefreshTimer();
    }

    public void setupComponents() {
        super.setupComponents();

        String[] columns = { "Nome do Candidato", "Número do Candidato", "Votos" };
        this.table = new JTable(new DefaultTableModel(new Object[][] {}, columns));
        this.table.setEnabled(false);

        this.lblStatus = new JLabel("Cadastre os candidatos e inicie a eleição.");
        this.addAt(lblStatus, 10, 50, 600, 20);

        var scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.addAt(scroll, 10, 70, 600, 300);

        this.btnNewCandidate = new JButton("Cadastrar Canditado");
        this.btnStart = new JButton("Iniciar Eleição");
        this.btnEnd = new JButton("Encerrar Eleição");

        this.addAt(btnNewCandidate, 10, 380);
        this.addAt(btnStart, 180, 380);
        this.addAt(btnEnd, 310, 380);

        btnNewCandidate.addActionListener(event -> {
            try {
                this.handleAddCandidate();
            } catch (AppException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        ex.getTitle(),
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnStart.addActionListener(event -> {
            this.handleStartElection();
        });

        btnEnd.setEnabled(false);
        btnEnd.addActionListener(event -> {
            this.handleEndElection();
        });
    }

    private void handleAddCandidate() {
        var name = JOptionPane.showInputDialog("Digite o nome do candidato");
        int number;

        try {
            number = Integer.parseInt(JOptionPane.showInputDialog("Digite o número do candidato"));
            if (number <= 0 || number > 999) {
                throw new AppException("Erro", "O número do candidato deve ser um número entre 1 e 999");
            }
        } catch (NumberFormatException ex) {
            throw new AppException("Erro", "O número do candidato deve ser um número inteiro");
        }

        var candidate = new Candidate(name, number);
        this.election.addCandidate(candidate);
        this.refreshTable();
    }

    private void handleStartElection() {
        var confirmation = JOptionPane.showConfirmDialog(this,
                "Não será possível adicionar mais candidatos após iniciar a eleição. Deseja continuar?");
        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        this.election.startElection();
        this.btnNewCandidate.setEnabled(false);
        this.btnStart.setEnabled(false);
        this.btnEnd.setEnabled(true);
        this.lblStatus.setText("Eleição em andamento. Os votos são atualizados a cada 5 segundos.");
        this.refreshTable();
    }

    private void handleEndElection() {
        this.election.endElection();
        this.btnEnd.setEnabled(false);
        this.lblStatus.setText("Eleição encerrada. Resultado final:");
        this.refreshTable();
    }

    private void refreshTable() {
        var model = (DefaultTableModel) this.table.getModel();
        model.setRowCount(0);
        this.election.getCandidates().forEach((candidate, votes) -> {
            model.addRow(new Object[] { candidate.getName(), candidate.getNumber(), votes });
        });
    }

    private void startRefreshTimer() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    SwingUtilities.invokeLater(() -> this.refreshTable());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
