package View;
import Model.GuiaJogo;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaInicial extends JFrame {

    private JButton botaoPerfil = new JButton("👤");
    private JPopupMenu menuPerfil = new JPopupMenu();
    private JMenuItem itemListagem = new JMenuItem("☰ Listagem");
    private JMenuItem itemLogout = new JMenuItem("⏻ Logout");
    private JLabel jogoMaiorHora = new JLabel();
    private JLabel JogoMenosHoras = new JLabel();
    private JLabel horasTotais = new JLabel();
    private ArrayList<GuiaJogo> lista = new ArrayList<>();

    public TelaInicial() {

        setTitle("Guia de Jogos");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTopo.setBackground(new Color(45, 45, 45));

        botaoPerfil.setBackground(new Color(45, 45, 45));
        botaoPerfil.setForeground(Color.WHITE);
        botaoPerfil.setFocusPainted(false);
        botaoPerfil.setBorderPainted(false);
        botaoPerfil.setFont(new Font("Arial", Font.PLAIN, 20));

        painelTopo.add(botaoPerfil);
        add(painelTopo, BorderLayout.NORTH);

        menuPerfil.add(itemListagem);
        menuPerfil.addSeparator();
        menuPerfil.add(itemLogout);

        JPanel painelCentro = new JPanel();
        painelCentro.setBackground(new Color(240, 240, 240));
        add(painelCentro, BorderLayout.CENTER);

        botaoPerfil.addActionListener(e -> {
            menuPerfil.show(botaoPerfil, 0, botaoPerfil.getHeight());
        });

        itemListagem.addActionListener(e -> {
            new TelaListagem();
        });

        itemLogout.addActionListener(e -> {
            int confirmar = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente sair?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirmar == JOptionPane.YES_OPTION) {
                dispose();
                new TelaLogin();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void calcularHoras() {

    }
}