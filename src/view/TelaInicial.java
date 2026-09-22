package view;

import model.GuiaJogo;
import dao.GuiaJogoDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaInicial extends JFrame {

    private final GuiaJogoDAO dao = new GuiaJogoDAO();

    private JButton botaoMenu = new JButton("☰");
    private JPopupMenu menuOpcoes = new JPopupMenu();
    private JMenuItem itemPerfil = new JMenuItem("Perfil");
    private JMenuItem itemListar = new JMenuItem("Listar");
    private JMenuItem itemSair = new JMenuItem("Sair");

    private JLabel labelMaiorNome = new JLabel("-");
    private JLabel labelMaiorTempo = new JLabel("-");
    private JLabel labelMenorNome = new JLabel("-");
    private JLabel labelMenorTempo = new JLabel("-");
    private JLabel labelTotalTempo = new JLabel("-");

    private JButton botaoAdicionar = new JButton("+ Adicionar Jogo");

    public TelaInicial() {
        setTitle("Guia de Jogos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new BorderLayout());
        painelTopo.setBackground(new Color(45, 45, 45));
        painelTopo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel labelTitulo = new JLabel("Guia de Jogos");
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelTopo.add(labelTitulo, BorderLayout.WEST);

        botaoMenu.setBackground(new Color(45, 45, 45));
        botaoMenu.setForeground(Color.WHITE);
        botaoMenu.setFocusPainted(false);
        botaoMenu.setBorderPainted(false);
        botaoMenu.setFont(new Font("Arial", Font.PLAIN, 22));
        painelTopo.add(botaoMenu, BorderLayout.EAST);

        add(painelTopo, BorderLayout.NORTH);

        menuOpcoes.add(itemPerfil);
        menuOpcoes.add(itemListar);
        menuOpcoes.add(itemSair);

        botaoMenu.addActionListener(e -> {
            if (menuOpcoes.isVisible()) {
                menuOpcoes.setVisible(false);
            } else {
                menuOpcoes.show(botaoMenu, 0, botaoMenu.getHeight());
            }
        });

        itemPerfil.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Funcionalidade de perfil em desenvolvimento.", "Perfil", JOptionPane.INFORMATION_MESSAGE));

        itemListar.addActionListener(e -> new TelaListagem());

        itemSair.addActionListener(e -> {
            int confirmar = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente sair?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                dispose();
                new TelaLogin();
            }
        });

        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.setBackground(new Color(240, 240, 240));
        painelCentro.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        painelCentro.add(criarLinha("Maior jogo:", labelMaiorNome, labelMaiorTempo));
        painelCentro.add(Box.createVerticalStrut(15));
        painelCentro.add(criarLinha("Menor jogo:", labelMenorNome, labelMenorTempo));
        painelCentro.add(Box.createVerticalStrut(15));
        painelCentro.add(criarLinhaTotal("Total de horas:", labelTotalTempo));
        painelCentro.add(Box.createVerticalStrut(20));

        JLabel labelExplicacao = new JLabel("Isso leva em conta todos os jogos adicionados na sua lista.");
        labelExplicacao.setFont(new Font("Arial", Font.PLAIN, 11));
        labelExplicacao.setForeground(Color.GRAY);
        labelExplicacao.setAlignmentX(Component.LEFT_ALIGNMENT);
        painelCentro.add(labelExplicacao);

        painelCentro.add(Box.createVerticalStrut(20));
        botaoAdicionar.setAlignmentX(Component.LEFT_ALIGNMENT);
        botaoAdicionar.addActionListener(e -> adicionarJogo());
        painelCentro.add(botaoAdicionar);

        add(painelCentro, BorderLayout.CENTER);

        carregarEstatisticas();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel criarLinha(String descricao, JLabel labelNome, JLabel labelTempo) {
        JPanel linha = new JPanel(new GridLayout(1, 3, 10, 0));
        linha.setBackground(new Color(240, 240, 240));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelDescricao = new JLabel(descricao);
        labelDescricao.setFont(new Font("Arial", Font.BOLD, 13));

        linha.add(labelDescricao);
        linha.add(labelNome);
        linha.add(labelTempo);

        return linha;
    }

    private JPanel criarLinhaTotal(String descricao, JLabel labelTempo) {
        JPanel linha = new JPanel(new GridLayout(1, 2, 10, 0));
        linha.setBackground(new Color(240, 240, 240));
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelDescricao = new JLabel(descricao);
        labelDescricao.setFont(new Font("Arial", Font.BOLD, 13));

        linha.add(labelDescricao);
        linha.add(labelTempo);

        return linha;
    }

    private void carregarEstatisticas() {
        List<GuiaJogo> lista = dao.getLista();

        if (lista.isEmpty()) {
            labelMaiorNome.setText("Nenhum jogo cadastrado");
            labelMaiorTempo.setText("-");
            labelMenorNome.setText("Nenhum jogo cadastrado");
            labelMenorTempo.setText("-");
            labelTotalTempo.setText("0h 0min");
            return;
        }

        GuiaJogo maior = lista.get(0);
        GuiaJogo menor = lista.get(0);
        int somaMinutos = 0;

        for (GuiaJogo g : lista) {
            if (g.getTempoDuracaoMinuto() > maior.getTempoDuracaoMinuto()) {
                maior = g;
            }
            if (g.getTempoDuracaoMinuto() < menor.getTempoDuracaoMinuto()) {
                menor = g;
            }
            somaMinutos += g.getTempoDuracaoMinuto();
        }

        labelMaiorNome.setText(maior.getNomeJogo());
        labelMaiorTempo.setText(maior.getTempoDuracaoMinuto() + " min");
        labelMenorNome.setText(menor.getNomeJogo());
        labelMenorTempo.setText(menor.getTempoDuracaoMinuto() + " min");
        labelTotalTempo.setText((somaMinutos / 60) + "h " + (somaMinutos % 60) + "min");
    }

    private void adicionarJogo() {
        JTextField campoNome = new JTextField();
        JTextField campoObjetivo = new JTextField();
        JTextField campoDuracao = new JTextField();

        Object[] campos = {
                "Nome do Jogo:", campoNome,
                "Objetivo:", campoObjetivo,
                "Duração (min):", campoDuracao
        };

        int opcao = JOptionPane.showConfirmDialog(this, campos, "Adicionar Guia de Jogo", JOptionPane.OK_CANCEL_OPTION);
        if (opcao == JOptionPane.OK_OPTION) {
            try {
                int duracao = Integer.parseInt(campoDuracao.getText());
                GuiaJogo novo = new GuiaJogo(0, duracao, campoObjetivo.getText(), campoNome.getText());
                dao.adiciona(novo);
                carregarEstatisticas();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
