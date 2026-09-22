package dao;

import model.GuiaJogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GuiaJogoDAO {

    public void adiciona(GuiaJogo guiaJogo) {
        String sql = "INSERT INTO guiaJogo (nomeJogo, objetivo, tempoDuracao) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, guiaJogo.getNomeJogo());
            stmt.setString(2, guiaJogo.getObjetivo());
            stmt.setInt(3, guiaJogo.getTempoDuracaoMinuto());
            stmt.execute();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    guiaJogo.setIdVideo(chaves.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir guia de jogo", e);
        }
    }

    public List<GuiaJogo> getLista() {
        String sql = "SELECT id, nomeJogo, objetivo, tempoDuracao FROM guiaJogo";
        List<GuiaJogo> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GuiaJogo guiaJogo = new GuiaJogo(
                        rs.getInt("id"),
                        rs.getInt("tempoDuracao"),
                        rs.getString("objetivo"),
                        rs.getString("nomeJogo")
                );
                lista.add(guiaJogo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar lista de guias de jogo", e);
        }

        return lista;
    }

    public void altera(GuiaJogo guiaJogo) {
        String sql = "UPDATE guiaJogo SET nomeJogo = ?, objetivo = ?, tempoDuracao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, guiaJogo.getNomeJogo());
            stmt.setString(2, guiaJogo.getObjetivo());
            stmt.setInt(3, guiaJogo.getTempoDuracaoMinuto());
            stmt.setInt(4, guiaJogo.getIdVideo());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar guia de jogo", e);
        }
    }

    public void remove(int id) {
        String sql = "DELETE FROM guiaJogo WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover guia de jogo", e);
        }
    }
}
