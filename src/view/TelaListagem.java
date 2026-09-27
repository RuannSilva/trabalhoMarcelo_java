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

    public TelaListagem() {
        setTitle("Lista de Jogos");
        setSize(650, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel(new Object[]{"ID", "Nome do Jogo", "Objetivo", "Duração (min)", "Ações"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tabela = new JTable(modelo);
        tabela.setRowHeight(28);
        tabela.getColumn("Ações").setCellRenderer(new BotaoAcoesRenderer());
        tabela.getColumn("Ações").setCellEditor(new BotaoAcoesEditor());
        tabela.getColumn("Ações").setMaxWidth(60);

        JPanel gradeAcoes = new JPanel(new GridLayout(1, 3, 10, 0));

        gradeAcoes.add(botaoAdicionar);
        botaoAdicionar.addActionListener(e -> adicionarJogo());

        gradeAcoes.add(botaoEditar);
        botaoEditar.addActionListener(e -> new TelaListagem());

        gradeAcoes.add(botaoRemover);
        botaoRemover.addActionListener(e -> new TelaListagem());

        add(gradeAcoes, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

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
                    "⋮"
            });
        }
    }

    private void editarJogo(int linha) {
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
                if (campoNome.getText().equals("") || campoObjetivo.equals("")) {
                    JOptionPane.showMessageDialog(null,"NÃO FOI POSSÍVEL SALVAR! INFORME O NOME DO JOGO..");
                } else {
                    dao.altera(editado);
                    popularTabela();
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Duração deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerJogo(int linha) {
        int id = (int) modelo.getValueAt(linha, 0);
        int confirmar = JOptionPane.showConfirmDialog(this, "Deseja realmente remover este item?", "ATENÇÃO", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            dao.remove(id);
            popularTabela();
            //COLOCAR FORMA DE FECHAR A TELA DE LISTAGEM QUANDO SALVAR A EDICAO 
        }
    }

    private static class BotaoAcoesRenderer extends JButton implements TableCellRenderer {
        BotaoAcoesRenderer() {
            setText("⋮");
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    private class BotaoAcoesEditor extends AbstractCellEditor implements TableCellEditor {
        private final JButton botao = new JButton("⋮");
        private int linhaAtual;

        BotaoAcoesEditor() {
            botao.setFocusPainted(false);
            botao.addActionListener(e -> {
                JPopupMenu menu = new JPopupMenu();
                JMenuItem itemEditar = new JMenuItem("Editar");
                JMenuItem itemRemover = new JMenuItem("Remover");

                itemEditar.addActionListener(ev -> {
                    fireEditingStopped();
                    editarJogo(linhaAtual);
                });
                itemRemover.addActionListener(ev -> {
                    fireEditingStopped();
                    removerJogo(linhaAtual);
                });

                menu.add(itemEditar);
                menu.add(itemRemover);
                menu.show(botao, 0, botao.getHeight());
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            linhaAtual = row;
            return botao;
        }

        @Override
        public Object getCellEditorValue() {
            return "⋮";
        }
    }

}
