package View;

import Model.GuiaJogo;
import dao.GuiaJogoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaListagem extends JFrame {

    private final GuiaJogoDAO dao = new GuiaJogoDAO();

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton botaoAdicionar = new JButton("Adicionar");
    private JButton botaoEditar = new JButton("Editar");
    private JButton botaoRemover = new JButton("Remover");

    public TelaListagem() {
        setTitle("Lista de Jogos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome do Jogo", "Objetivo", "Duração (min)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoRemover);
        add(painelBotoes, BorderLayout.SOUTH);

        botaoAdicionar.addActionListener(e -> adicionarJogo());
        botaoEditar.addActionListener(e -> editarJogo());
        botaoRemover.addActionListener(e -> removerJogo());

        popularTabela();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void popularTabela() {
        modelo.setRowCount(0);
        List<GuiaJogo> lista = dao.getLista();
        for (GuiaJogo g : lista) {
            modelo.addRow(new Object[]{
                    g.getIdVideo(),
                    g.getNomeJogo(),
                    g.getObjetivo(),
                    g.getTempoDuracaoMinuto()
            });
        }
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
                popularTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarJogo() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        JTextField campoNome = new JTextField((String) modelo.getValueAt(linha, 1));
        JTextField campoObjetivo = new JTextField((String) modelo.getValueAt(linha, 2));
        JTextField campoDuracao = new JTextField(String.valueOf(modelo.getValueAt(linha, 3)));

        Object[] campos = {
                "Nome do Jogo:", campoNome,
                "Objetivo:", campoObjetivo,
                "Duração (min):", campoDuracao
        };

        int opcao = JOptionPane.showConfirmDialog(this, campos, "Editar Guia de Jogo", JOptionPane.OK_CANCEL_OPTION);
        if (opcao == JOptionPane.OK_OPTION) {
            try {
                int duracao = Integer.parseInt(campoDuracao.getText());
                GuiaJogo editado = new GuiaJogo(id, duracao, campoObjetivo.getText(), campoNome.getText());
                dao.altera(editado);
                popularTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerJogo() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente remover este item?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            dao.remove(id);
            popularTabela();
        }
    }
}
