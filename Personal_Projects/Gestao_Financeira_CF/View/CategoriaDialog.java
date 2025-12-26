package View;

import Controller.TransacaoService;
import Model.Categoria;
import javax.swing.*;
import java.awt.*;

public class CategoriaDialog extends JDialog {
    private TransacaoService service;

    public CategoriaDialog(JFrame parent, TransacaoService service) {
        super(parent, "Nova Categoria", true);
        this.service = service;

        setSize(400, 250);
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
        formPanel.add(new JLabel("Nome da Categoria:"), gbc);
        JTextField nomeField = new JTextField(15);
        nomeField.setFont(new Font("Arial", Font.PLAIN, 14));
        nomeField.setBackground(Color.WHITE);
        nomeField.setForeground(Color.DARK_GRAY);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        // Tipo
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tipo:"), gbc);
        JComboBox<Categoria.TipoCategoria> tipoCombo = new JComboBox<>(Categoria.TipoCategoria.values());
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
        salvarBtn.setBackground(new Color(52, 152, 219));
        salvarBtn.setForeground(Color.WHITE);
        salvarBtn.setOpaque(true);
        salvarBtn.setContentAreaFilled(true);
        salvarBtn.setFocusPainted(false);
        salvarBtn.addActionListener(e -> {
            try {
                String nome = nomeField.getText();
                Categoria.TipoCategoria tipo = (Categoria.TipoCategoria) tipoCombo.getSelectedItem();

                if (nome.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nome não pode ser vazio!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Categoria categoria = new Categoria(service.getProxIdCategoria(), nome, tipo);
                service.adicionarCategoria(categoria);

                JOptionPane.showMessageDialog(this, "Categoria adicionada com sucesso!");
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
