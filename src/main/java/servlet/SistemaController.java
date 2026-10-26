package servlet;

import dao.GuiaJogoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.GuiaJogo;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class SistemaController extends HttpServlet {

    private GuiaJogoDAO dao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String requisicao = request.getServletPath();

        switch (requisicao){
            case ("/new"):
                addLista(request, response);
                break;
            case ("/inserir"):
                inserir(request, response);
                break;
            case ("/editar"):
                editarJogo(request, response);
                break;
            case ("/remover"):
                removerJogo(request, response);
                break;
            case ("/salvar"):
                salvarNaLista(request, response);
                break;
            case "/estilos.css":
                getServletContext().getNamedDispatcher("default").forward(request, response);
                break;
            default:
                listarJogos(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }

    @Override
    public void init(){
        dao = new GuiaJogoDAO();
    }

    private void listarJogos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        List<GuiaJogo> lista = dao.getLista();
        request.setAttribute("lista", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/listagem.jsp");
        dispatcher.forward(request, response);

    }

    private void salvarNaLista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String objetivo = request.getParameter("objetivo");
        int duracao = Integer.parseInt(request.getParameter("duracao"));

        GuiaJogo editado = new GuiaJogo(id, duracao, objetivo, nome);
        dao.altera(editado);

        response.sendRedirect(request.getContextPath() + "/");

    }

    private void removerJogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        dao.remove(id);
        response.sendRedirect(request.getContextPath() + "/");

    }

    private void editarJogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        GuiaJogo jogo = null;
        for (GuiaJogo g : dao.getLista()) {
            if (g.getIdVideo() == id) {
                jogo = g;
                break;
            }
        }

        request.setAttribute("jogo", jogo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/formulario.jsp");
        dispatcher.forward(request, response);

    }

    private void inserir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String objetivo = request.getParameter("objetivo");
        int duracao = Integer.parseInt(request.getParameter("duracao"));

        GuiaJogo novo = new GuiaJogo(0, duracao, objetivo, nome);
        dao.adiciona(novo);

        response.sendRedirect(request.getContextPath() + "/");

    }
    private void addLista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        RequestDispatcher dispatcher = request.getRequestDispatcher("/formulario.jsp");
        dispatcher.forward(request, response);

    }
}
