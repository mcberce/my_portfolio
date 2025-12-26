package View;

import Controller.TransacaoService;
import Model.Conta;
import javax.swing.*;
import java.awt.*;

public class ContaDialog extends JDialog {
    private TransacaoService service;
    private Runnable onSuccess;

    public ContaDialog(JFrame parent, TransacaoService service, Runnable onSuccess) {
        super(parent, "Nova Conta", true);
        this.service = service;
        this.onSuccess = onSuccess;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nome da Conta:"), gbc);
        JTextField nomeField = new JTextField(15);
        nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        // Saldo Inicial
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Saldo Inicial (R$):"), gbc);
        JTextField saldoField = new JTextField(15);
        saldoField.setFont(new Font("Arial", Font.PLAIN, 14));
        saldoField.setBackground(Color.WHITE);
        saldoField.setForeground(Color.DARK_GRAY);
        saldoField.setText("0.00");
        gbc.gridx = 1;
        formPanel.add(saldoField, gbc);

        // Tipo
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tipo:"), gbc);
        JComboBox<Conta.TipoConta> tipoCombo = new JComboBox<>(Conta.TipoConta.values());
        tipoCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        tipoCombo.setBackground(Color.WHITE);
        tipoCombo.setForeground(Color.DARK_GRAY);
        tipoCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(220, 230, 241) : Color.WHITE);
                setForeground(Color.DARK_GRAY);
                return c;
            }
        });
        gbc.gridx = 1;
        formPanel.add(tipoCombo, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelarBtn.addActionListener(e -> dispose());

        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        salvarBtn.setBackground(new Color(39, 174, 96));
        salvarBtn.setForeground(Color.WHITE);
        salvarBtn.setOpaque(true);
        salvarBtn.setContentAreaFilled(true);
        salvarBtn.setFocusPainted(false);
        salvarBtn.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                double saldo = Double.parseDouble(saldoField.getText());
                Conta.TipoConta tipo = (Conta.TipoConta) tipoCombo.getSelectedItem();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nome não pode ser vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Conta conta = new Conta(service.getProxIdConta(), nome, saldo, tipo);
                service.adicionarConta(conta);

                JOptionPane.showMessageDialog(this, "Conta adicionada com sucesso!");
                onSuccess.run();
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelarBtn);
        buttonPanel.add(salvarBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
