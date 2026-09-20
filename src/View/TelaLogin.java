package View;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private String usuario = "admim";
    private String senha = "1234";
    private JLabel labelUsuario = new JLabel("USUARIO: ");
    private JTextField campoUsuario = new JTextField(15);
    private JLabel labelSenha = new JLabel("SENHA: ");
    private JPasswordField campoSenha = new JPasswordField(15);
    private JButton botaoEntrar = new JButton("Entrar");

    public TelaLogin() {

        setTitle("LOGIN USUARIO");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        add(labelUsuario, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        add(campoUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(labelSenha, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        add(campoSenha, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        add(botaoEntrar, gbc);

        botaoEntrar.addActionListener(e -> {
            String usuarioDigitado = campoUsuario.getText();
            String senhaDigitada = new String(campoSenha.getPassword());

            if (usuarioDigitado.equals(this.usuario) && senhaDigitada.equals(this.senha)) {
                dispose();
                new TelaInicial();
            } else {
                JOptionPane.showMessageDialog(null, "DADOS INVÁLIDOS! TENTE NOVAMENTE", "ALERTA", JOptionPane.ERROR_MESSAGE);
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

}