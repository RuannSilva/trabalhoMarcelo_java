<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.GuiaJogo" %>
<%
    GuiaJogo jogo = (GuiaJogo) request.getAttribute("jogo");
    boolean edicao = (jogo != null);
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title><%= edicao ? "Editar Jogo" : "Adicionar Jogo" %></title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <div class="container">
        <div class="topo">
            <h1><%= edicao ? "Editar Jogo" : "Adicionar Jogo" %></h1>
        </div>

        <form action="<%= request.getContextPath() %>/<%= edicao ? "salvar" : "inserir" %>" method="post">
            <% if (edicao) { %>
                <input type="hidden" name="id" value="<%= jogo.getIdVideo() %>">
            <% } %>

            <div class="campo">
                <label for="nome">Nome do Jogo</label>
                <input type="text" id="nome" name="nome" value="<%= edicao ? jogo.getNomeJogo() : "" %>" required>
            </div>

            <div class="campo">
                <label for="objetivo">Objetivo</label>
                <input type="text" id="objetivo" name="objetivo" value="<%= edicao ? jogo.getObjetivo() : "" %>" required>
            </div>

            <div class="campo">
                <label for="duracao">Duração (min)</label>
                <input type="number" id="duracao" name="duracao" value="<%= edicao ? jogo.getTempoDuracaoMinuto() : "" %>" required>
            </div>

            <div class="rodape-form">
                <button type="submit" class="btn btn-primario">Salvar</button>
                <a href="<%= request.getContextPath() %>/" class="btn btn-secundario">Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>