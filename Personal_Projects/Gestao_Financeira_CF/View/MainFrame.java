package View;

import Controller.TransacaoService;
import Controller.ArmazenamentoDados;
import Model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private TransacaoService service;
    private JPanel dashboardPanel;
    private JPanel transacoesPanel;
    private JComboBox<String> filtroCombo;
    
    // Cores do tema
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BG_COLOR = new Color(236, 240, 241);
    private final Color CARD_COLOR = Color.WHITE;

    public MainFrame(TransacaoService service) {
        this.service = service;
        
        setTitle("Sistema de Controle Financeiro");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        
        // Salvar dados ao fechar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ArmazenamentoDados.salvarDados(service);
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG_COLOR);

        // Painel superior - Dashboard
        add(createDashboardPanel(), BorderLayout.NORTH);

        // Painel central - Transações
        add(createCenterPanel(), BorderLayout.CENTER);

        // Painel direito - Ações
        add(createActionsPanel(), BorderLayout.EAST);
    }

    private JPanel createDashboardPanel() {
        dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(1, 4, 15, 15));
        dashboardPanel.setBackground(BG_COLOR);
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        atualizarDashboard();
        
        return dashboardPanel;
    }

    private void atualizarDashboard() {
        dashboardPanel.removeAll();
        
        // Card Saldo Total
        double saldoTotal = 0;
        for (Conta c : service.listarContas()) {
            saldoTotal += c.getSaldoAtual();
        }
        dashboardPanel.add(createCard("Saldo Total", String.format("R$ %.2f", saldoTotal), PRIMARY_COLOR));
        
        // Card Receitas
        double receitas = service.calcularTotalReceitas();
        dashboardPanel.add(createCard("Receitas", String.format("R$ %.2f", receitas), SUCCESS_COLOR));
        
        // Card Despesas (usa filtro se disponível)
        double despesas = filtroCombo != null ? calcularDespesasPorFiltro() : service.calcularTotalDespesas();
        dashboardPanel.add(createCard("Despesas", String.format("R$ %.2f", despesas), DANGER_COLOR));
        
        // Card Balanço
        double balanco = receitas - despesas;
        Color balancoColor = balanco >= 0 ? SUCCESS_COLOR : DANGER_COLOR;
        dashboardPanel.add(createCard("Balanço", String.format("R$ %.2f", balanco), balancoColor));
        
        dashboardPanel.revalidate();
        dashboardPanel.repaint();
    }

    private JPanel createCard(String titulo, String valor, Color cor) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(cor, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        tituloLabel.setForeground(Color.GRAY);

        JLabel valorLabel = new JLabel(valor);
        valorLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valorLabel.setForeground(cor);

        card.add(tituloLabel, BorderLayout.NORTH);
        card.add(valorLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

        // Painel de transações - DEVE SER CRIADO ANTES DO COMBO BOX DISPARAR EVENTOS
        transacoesPanel = new JPanel();
        transacoesPanel.setLayout(new BoxLayout(transacoesPanel, BoxLayout.Y_AXIS));
        transacoesPanel.setBackground(CARD_COLOR);
        
        JScrollPane scrollPane = new JScrollPane(transacoesPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Painel de filtro
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(BG_COLOR);
        
        JLabel filtroLabel = new JLabel("Filtrar por:");
        filtroLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        filtroCombo = new JComboBox<>(new String[]{"Hoje", "Esta Semana", "Este Mês", "Este Ano", "Tudo"});
        filtroCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        filtroCombo.setBackground(Color.WHITE);
        filtroCombo.setForeground(Color.DARK_GRAY);
        filtroCombo.addActionListener(e -> atualizarTransacoes());
        filtroCombo.setSelectedItem("Tudo");
        
        filtroPanel.add(filtroLabel);
        filtroPanel.add(filtroCombo);

        centerPanel.add(filtroPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        atualizarTransacoes();

        return centerPanel;
    }

    private void atualizarTransacoes() {
        transacoesPanel.removeAll();
        
        List<Transacao> transacoes = filtrarTransacoes();
        
        if (transacoes.isEmpty()) {
            JLabel emptyLabel = new JLabel("Nenhuma transação encontrada");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            transacoesPanel.add(emptyLabel);
        } else {
            for (Transacao t : transacoes) {
                transacoesPanel.add(createTransacaoCard(t));
                transacoesPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }
        
        transacoesPanel.revalidate();
        transacoesPanel.repaint();
        
        // Atualiza dashboard apenas se filtroCombo já existe
        if (filtroCombo != null) {
            atualizarDashboard();
        }
    }

    private List<Transacao> filtrarTransacoes() {
        if (filtroCombo == null) {
            return service.listarTransacoes();
        }
        
        String filtro = (String) filtroCombo.getSelectedItem();
        LocalDate hoje = LocalDate.now();
        LocalDate dataInicio;

        switch (filtro) {
            case "Hoje":
                dataInicio = hoje;
                break;
            case "Esta Semana":
                dataInicio = hoje.minusDays(hoje.getDayOfWeek().getValue() - 1);
                break;
            case "Este Mês":
                dataInicio = hoje.withDayOfMonth(1);
                break;
            case "Este Ano":
                dataInicio = hoje.withDayOfYear(1);
                break;
            default:
                return service.listarTransacoes();
        }

        return service.listarTransacoes().stream()
            .filter(t -> !t.getData().isBefore(dataInicio))
            .collect(Collectors.toList());
    }

    private double calcularDespesasPorFiltro() {
        return filtrarTransacoes().stream()
            .filter(t -> t.getCategoria().getTipoCategoria() == Categoria.TipoCategoria.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();
    }

    private JPanel createTransacaoCard(Transacao t) {
        JPanel card = new JPanel(new BorderLayout(10, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        // Info principal
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel descLabel = new JLabel(t.getDescricao());
        descLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel catLabel = new JLabel(t.getCategoria().getNomeCategoria() + " • " + t.getData());
        catLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        catLabel.setForeground(Color.GRAY);
        
        infoPanel.add(descLabel);
        infoPanel.add(catLabel);

        // Valor
        JLabel valorLabel = new JLabel(String.format("R$ %.2f", t.getValor()));
        valorLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        Color valorCor;
        if (t.getCategoria().getTipoCategoria() == Categoria.TipoCategoria.RECEITA) {
            valorCor = SUCCESS_COLOR;
            valorLabel.setText("+ " + valorLabel.getText());
        } else if (t.getCategoria().getTipoCategoria() == Categoria.TipoCategoria.DESPESA) {
            valorCor = DANGER_COLOR;
            valorLabel.setText("- " + valorLabel.getText());
        } else {
            valorCor = PRIMARY_COLOR;
        }
        valorLabel.setForeground(valorCor);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(valorLabel, BorderLayout.EAST);

        return card;
    }

    private JPanel createActionsPanel() {
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new BoxLayout(actionsPanel, BoxLayout.Y_AXIS));
        actionsPanel.setBackground(BG_COLOR);
        actionsPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        actionsPanel.setPreferredSize(new Dimension(250, 0));

        JLabel tituloLabel = new JLabel("Ações");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(tituloLabel);
        actionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botões
        actionsPanel.add(createActionButton("+ Nova Transação", PRIMARY_COLOR, e -> abrirDialogTransacao()));
        actionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        actionsPanel.add(createActionButton("+ Nova Conta", SUCCESS_COLOR, e -> abrirDialogConta()));
        actionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        actionsPanel.add(createActionButton("+ Nova Categoria", new Color(52, 152, 219), e -> abrirDialogCategoria()));
        actionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel de contas
        JLabel contasLabel = new JLabel("Minhas Contas");
        contasLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        actionsPanel.add(contasLabel);
        actionsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel contasPanel = new JPanel();
        contasPanel.setLayout(new BoxLayout(contasPanel, BoxLayout.Y_AXIS));
        contasPanel.setBackground(BG_COLOR);
        
        for (Conta c : service.listarContas()) {
            contasPanel.add(createContaCard(c));
            contasPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        JScrollPane contasScroll = new JScrollPane(contasPanel);
        contasScroll.setBorder(null);
        contasScroll.setMaximumSize(new Dimension(250, 300));
        actionsPanel.add(contasScroll);

        actionsPanel.add(Box.createVerticalGlue());

        return actionsPanel;
    }

    private JButton createActionButton(String text, Color color, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        
        // Efeito hover
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private JPanel createContaCard(Conta c) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel nomeLabel = new JLabel(c.getNomeConta());
        nomeLabel.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel tipoLabel = new JLabel(c.getTipoConta().toString());
        tipoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        tipoLabel.setForeground(Color.GRAY);

        JLabel saldoLabel = new JLabel(String.format("R$ %.2f", c.getSaldoAtual()));
        saldoLabel.setFont(new Font("Arial", Font.BOLD, 13));
        saldoLabel.setForeground(c.getSaldoAtual() >= 0 ? SUCCESS_COLOR : DANGER_COLOR);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(CARD_COLOR);
        infoPanel.add(nomeLabel);
        infoPanel.add(tipoLabel);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(saldoLabel, BorderLayout.EAST);

        return card;
    }

    private void abrirDialogTransacao() {
        new TransacaoDialog(this, service, () -> {
            atualizarTransacoes();
            refreshActionsPanel();
        }).setVisible(true);
    }

    private void abrirDialogConta() {
        new ContaDialog(this, service, () -> refreshActionsPanel()).setVisible(true);
    }

    private void abrirDialogCategoria() {
        new CategoriaDialog(this, service).setVisible(true);
    }

    private void refreshActionsPanel() {
        Container contentPane = getContentPane();
        contentPane.remove(contentPane.getComponentCount() - 1);
        contentPane.add(createActionsPanel(), BorderLayout.EAST);
        contentPane.revalidate();
        contentPane.repaint();
        atualizarDashboard();
    }
}
