package View;

import Model.GuiaJogo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaListagem extends JFrame {

    private JTable tabela = new JTable();
    private JScrollPane scroll = new JScrollPane();
    private DefaultTableModel modelo;
    private ArrayList<GuiaJogo> listaJogos;

    public TelaListagem() {
        setTitle("Lista de Jogos");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);


        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome do Jogo");
        modelo.addColumn("Objetivo");
        modelo.addColumn("Duração (min)");

        tabela = new JTable(modelo);

        scroll = new JScrollPane(tabela);

        add(scroll);

        popularTabela();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void popularTabela() {

        for (GuiaJogo g : listaJogos) {
            modelo.addRow(new Object[]{
                    g.getIdVideo(),
                    g.getNomeJogo(),
                    g.getObjetivo(),
                    g.getTempoDuracaoMinuto()
            });
        }
    }

    public JTable getTabela() {
        return tabela;
    }

    public JScrollPane getScroll() {
        return scroll;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public ArrayList<GuiaJogo> getListaJogos() {
        return listaJogos;
    }
}
