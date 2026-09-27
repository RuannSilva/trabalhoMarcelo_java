package view;

import model.GuiaJogo;
import dao.GuiaJogoDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class TelaListagem extends JFrame {

    private GuiaJogoDAO dao = new GuiaJogoDAO();
    private JTable tabela;
    private DefaultTableModel modelo;
    private JButton botaoAdicionar = new JButton("+ Adicionar Jogo");
    private JButton botaoEditar = new JButton("\uD83D\uDCDD Editar Jogo");
    private JButton botaoRemover = new JButton("\u2716 Remover Jogo");
    private final Runnable aoAtualizar;

    public TelaListagem(Runnable aoAtualizar) {
        setTitle("Lista de Jogos");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.aoAtualizar = aoAtualizar;

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome do Jogo", "Objetivo", "Duração (min)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modelo);
        tabela.setShowGrid(true);
        tabela.setGridColor(Color.LIGHT_GRAY);  


        JPanel gradeAcoes = new JPanel(new GridLayout(1, 3, 10, 0));

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        gradeAcoes.add(botaoAdicionar);
        botaoAdicionar.addActionListener(e -> adicionarJogo());

        gradeAcoes.add(botaoEditar);
        botaoEditar.addActionListener(e -> editarJogo());

        gradeAcoes.add(botaoRemover);
        botaoRemover.addActionListener(e -> removerJogo());

        add(gradeAcoes, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        popularTabela();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

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
                if (campoNome.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,"NÃO FOI POSSÍVEL ADICIONAR A LISTA! INFORME O NOME DO JOGO..");
                } else {
                    dao.adiciona(novo);
                    popularTabela();
                    if (aoAtualizar != null) {
                        aoAtualizar.run();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void popularTabela() {
        modelo.setRowCount(0);
        List<GuiaJogo> lista = dao.getLista();
        for (GuiaJogo g : lista) {
            modelo.addRow(new Object[]{
                    g.getIdVideo(),
                    g.getNomeJogo(),
                    g.getObjetivo(),
                    g.getTempoDuracaoMinuto(),
            });
        }
    }


    private void editarJogo() {
        
        int linha = tabela.getSelectedRow();
        if(linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um jogo na lista para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
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

            if (campoNome.getText().trim().isEmpty() || campoObjetivo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            GuiaJogo editado = new GuiaJogo(id, duracao, campoObjetivo.getText(), campoNome.getText());
            dao.altera(editado);
            popularTabela();
            if (aoAtualizar != null) {
                aoAtualizar.run();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
        
    private void removerJogo() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
        JOptionPane.showMessageDialog(this, "Selecione um jogo na lista para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
        }

        int id = (int) modelo.getValueAt(linha, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente remover este item?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            dao.remove(id);
            popularTabela();
            if (aoAtualizar != null) {
            aoAtualizar.run();
            }
        }
    }

}
