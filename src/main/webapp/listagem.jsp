<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.GuiaJogo" %>
<%
    List<GuiaJogo> lista = (List<GuiaJogo>) request.getAttribute("lista");
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Lista de Jogos</title>
    <link rel="stylesheet" href="estilos.css">
</head>
<body>
    <div class="container">
        <div class="topo">
            <h1>Guia de Jogos</h1>
            <a href="<%= request.getContextPath() %>/new" class="btn btn-primario">+ Adicionar Jogo</a>
        </div>

        <% if (lista == null || lista.isEmpty()) { %>
            <p class="vazio">Nenhum jogo cadastrado ainda.</p>
        <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>NOME do Jogo</th>
                    <th>OBJETIVO</th>
                    <th>DURAÇÃO</th>
                    <th>AÇÕES</th>
                </tr>
            </thead>
            <tbody>
                <% for (GuiaJogo g : lista) { %>
                <tr>
                    <td><%= g.getIdVideo() %></td>
                    <td><%= g.getNomeJogo() %></td>
                    <td><%= g.getObjetivo() %></td>
                    <td><%= g.getTempoDuracaoMinuto() %> min</td>
                    <td class="acoes">
                        <a href="<%= request.getContextPath() %>/editar?id=<%= g.getIdVideo() %>" class="btn btn-editar">Editar</a>
                        <a href="<%= request.getContextPath() %>/remover?id=<%= g.getIdVideo() %>" class="btn btn-remover">Remover</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <% } %>
    </div>
</body>
</html>