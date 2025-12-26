package View;

import Controller.TransacaoService;
import Controller.ArmazenamentoDados;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class TransacaoDialog extends JDialog {
    private TransacaoService service;
    private Runnable onSuccess;
    private JComboBox<String> categoriaCombo;
    private JComboBox<String> origemCombo;
    private JComboBox<String> destinoCombo;
    private JLabel tipoValorLabel;

    public TransacaoDialog(JFrame parent, TransacaoService service, Runnable onSuccess) {
        super(parent, "Nova Transação", true);
        this.service = service;
        this.onSuccess = onSuccess;

        setSize(500, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initComponents();
    }

    private void initComponents() {
        if (service.listarCategorias().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Antes crie pelo menos uma categoria.");
            dispose();
            return;
        }

        if (service.listarContas().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Antes crie pelo menos uma conta.");
            dispose();
            return;
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Valor
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Valor (R$):"), gbc);
        JTextField valorField = new JTextField(15);
        valorField.setFont(new Font("Arial", Font.PLAIN, 14));
        valorField.setBackground(Color.WHITE);
        valorField.setForeground(Color.DARK_GRAY);
        gbc.gridx = 1;
        formPanel.add(valorField, gbc);

        // Categoria
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Categoria:"), gbc);
        categoriaCombo = new JComboBox<>();
        categoriaCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        categoriaCombo.setBackground(Color.WHITE);
        categoriaCombo.setForeground(Color.DARK_GRAY);
        for (Categoria c : service.listarCategorias()) {
            categoriaCombo.addItem(c.getNomeCategoria() + " (" + c.getTipoCategoria() + ")");
        }
        categoriaCombo.setRenderer(styleRenderer());
        gbc.gridx = 1;
        formPanel.add(categoriaCombo, gbc);

        // Tipo (informativo)
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tipo da transação:"), gbc);
        tipoValorLabel = new JLabel("-");
        tipoValorLabel.setFont(new Font("Arial", Font.BOLD, 13));
        tipoValorLabel.setForeground(new Color(52, 73, 94));
        gbc.gridx = 1;
        formPanel.add(tipoValorLabel, gbc);

        // Descrição
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Descrição:"), gbc);
        JTextField descricaoField = new JTextField(15);
        descricaoField.setFont(new Font("Arial", Font.PLAIN, 14));
        descricaoField.setBackground(Color.WHITE);
        descricaoField.setForeground(Color.DARK_GRAY);
        gbc.gridx = 1;
        formPanel.add(descricaoField, gbc);

        // Conta Origem
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel origemLabel = new JLabel("Conta Origem:");
        formPanel.add(origemLabel, gbc);
        origemCombo = new JComboBox<>();
        origemCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        origemCombo.setBackground(Color.WHITE);
        origemCombo.setForeground(Color.DARK_GRAY);
        origemCombo.addItem("-- Nenhuma --");
        for (Conta c : service.listarContas()) {
            origemCombo.addItem(c.getNomeConta());
        }
        origemCombo.setRenderer(styleRenderer());
        gbc.gridx = 1;
        formPanel.add(origemCombo, gbc);

        // Conta Destino
        gbc.gridx = 0; gbc.gridy = 5;
        JLabel destinoLabel = new JLabel("Conta Destino:");
        formPanel.add(destinoLabel, gbc);
        destinoCombo = new JComboBox<>();
        destinoCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        destinoCombo.setBackground(Color.WHITE);
        destinoCombo.setForeground(Color.DARK_GRAY);
        destinoCombo.addItem("-- Nenhuma --");
        for (Conta c : service.listarContas()) {
            destinoCombo.addItem(c.getNomeConta());
        }
        destinoCombo.setRenderer(styleRenderer());
        gbc.gridx = 1;
        formPanel.add(destinoCombo, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Refresca habilitação dos campos conforme tipo
        categoriaCombo.addActionListener(e -> atualizarCamposPorTipo());
        atualizarCamposPorTipo();

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        JButton cancelarBtn = new JButton("Cancelar");
        cancelarBtn.setFont(new Font("Arial", Font.PLAIN, 14));
        cancelarBtn.addActionListener(e -> dispose());

        JButton salvarBtn = new JButton("Salvar");
        salvarBtn.setFont(new Font("Arial", Font.BOLD, 14));
        salvarBtn.setBackground(new Color(41, 128, 185));
        salvarBtn.setForeground(Color.WHITE);
        salvarBtn.setOpaque(true);
        salvarBtn.setContentAreaFilled(true);
        salvarBtn.setFocusPainted(false);
        salvarBtn.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(valorField.getText());
                int catIndex = categoriaCombo.getSelectedIndex();
                if (catIndex < 0) {
                    JOptionPane.showMessageDialog(this, "Escolha uma categoria.");
                    return;
                }
                Categoria categoria = service.listarCategorias().get(catIndex);
                String descricao = descricaoField.getText();

                Conta origem = null;
                Conta destino = null;

                if (categoria.getTipoCategoria() == Categoria.TipoCategoria.RECEITA) {
                    if (destinoCombo.getSelectedIndex() <= 0 && destinoCombo.getItemCount() > 1) {
                        destinoCombo.setSelectedIndex(1); // escolhe primeira conta automaticamente
                    }
                    if (destinoCombo.getSelectedIndex() <= 0) {
                        JOptionPane.showMessageDialog(this, "Receita precisa de conta destino.");
                        return;
                    }
                    destino = service.listarContas().get(destinoCombo.getSelectedIndex() - 1);
                } else if (categoria.getTipoCategoria() == Categoria.TipoCategoria.DESPESA) {
                    if (origemCombo.getSelectedIndex() <= 0 && origemCombo.getItemCount() > 1) {
                        origemCombo.setSelectedIndex(1); // escolhe primeira conta automaticamente
                    }
                    if (origemCombo.getSelectedIndex() <= 0) {
                        JOptionPane.showMessageDialog(this, "Despesa precisa de conta origem.");
                        return;
                    }
                    origem = service.listarContas().get(origemCombo.getSelectedIndex() - 1);
                } else { // TRANSFERENCIA
                    if (origemCombo.getSelectedIndex() <= 0 && origemCombo.getItemCount() > 1) {
                        origemCombo.setSelectedIndex(1);
                    }
                    if (destinoCombo.getSelectedIndex() <= 0 && destinoCombo.getItemCount() > 1) {
                        destinoCombo.setSelectedIndex(1);
                    }
                    if (origemCombo.getSelectedIndex() <= 0 || destinoCombo.getSelectedIndex() <= 0) {
                        JOptionPane.showMessageDialog(this, "Transferência precisa de origem e destino.");
                        return;
                    }
                    origem = service.listarContas().get(origemCombo.getSelectedIndex() - 1);
                    destino = service.listarContas().get(destinoCombo.getSelectedIndex() - 1);
                }

                Transacao t = new Transacao(
                    service.getProxIdTransacao(),
                    valor,
                    categoria,
                    origem,
                    destino,
                    LocalDate.now(),
                    descricao
                );

                if (service.adicionarTransacao(t)) {
                    JOptionPane.showMessageDialog(this, "Transação adicionada com sucesso!");
                    // Salva imediatamente para persistir histórico (evita depender do fechamento)
                    ArmazenamentoDados.salvarDados(service);
                    onSuccess.run();
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelarBtn);
        buttonPanel.add(salvarBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void atualizarCamposPorTipo() {
        int idx = categoriaCombo.getSelectedIndex();
        if (idx < 0) {
            tipoValorLabel.setText("-");
            return;
        }
        Categoria.TipoCategoria tipo = service.listarCategorias().get(idx).getTipoCategoria();
        tipoValorLabel.setText(tipo.toString());

        if (tipo == Categoria.TipoCategoria.RECEITA) {
            origemCombo.setEnabled(false);
            destinoCombo.setEnabled(true);
            origemCombo.setSelectedIndex(0);
            if (destinoCombo.getSelectedIndex() <= 0 && destinoCombo.getItemCount() > 1) {
                destinoCombo.setSelectedIndex(1); // pré-seleciona primeira conta
            }
        } else if (tipo == Categoria.TipoCategoria.DESPESA) {
            origemCombo.setEnabled(true);
            destinoCombo.setEnabled(false);
            destinoCombo.setSelectedIndex(0);
            if (origemCombo.getSelectedIndex() <= 0 && origemCombo.getItemCount() > 1) {
                origemCombo.setSelectedIndex(1); // pré-seleciona primeira conta
            }
        } else {
            origemCombo.setEnabled(true);
            destinoCombo.setEnabled(true);
            if (origemCombo.getSelectedIndex() <= 0 && origemCombo.getItemCount() > 1) {
                origemCombo.setSelectedIndex(1);
            }
            if (destinoCombo.getSelectedIndex() <= 0 && destinoCombo.getItemCount() > 1) {
                destinoCombo.setSelectedIndex(1);
            }
        }
    }

    private DefaultListCellRenderer styleRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? new Color(220, 230, 241) : Color.WHITE);
                setForeground(Color.DARK_GRAY);
                return c;
            }
        };
    }
}
