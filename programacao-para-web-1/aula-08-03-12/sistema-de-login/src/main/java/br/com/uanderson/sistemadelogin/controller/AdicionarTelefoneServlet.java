package br.com.uanderson.sistemadelogin.controller;

import br.com.uanderson.sistemadelogin.model.Telefone;
import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdicionarTelefoneServlet", value = "/adicionar-telefone")
public class AdicionarTelefoneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Verifica se o usuário está logado
        HttpSession session = request.getSession(false);
        Usuario usuarioLogado = (Usuario) (session != null ? session.getAttribute("usuario") : null);

        if (usuarioLogado == null) {
            response.sendRedirect("index.jsp?mensagem=usuario-nao-autenticado");
            return;
        }

        String tid = request.getParameter("id");
        String tipoTelefone = request.getParameter("tipo");
        String numeroTelefone = request.getParameter("numero");

        if (tid != null && !tid.isBlank() && tipoTelefone != null && !tipoTelefone.isBlank() &&
                numeroTelefone != null && !numeroTelefone.isBlank()) {

            ServletContext application = getServletContext();
            List<Usuario> usuarios = (List<Usuario>) application.getAttribute("usersContext");

            int id = Integer.parseInt(tid);

            for (Usuario u : usuarios) {
                if (u.getId() == id) {
                    List<Telefone> telefones = u.getTelefones();
                    if (telefones == null) {
                        telefones = new ArrayList<>();
                    }
                    Telefone telefone = new Telefone(pegaProximoId(application), tipoTelefone, numeroTelefone);
                    telefones.add(telefone);
                    u.setTelefones(telefones);

                    System.out.println("USUÁRIO: " + u);
                    response.sendRedirect("relatorio.jsp?mensagem=success-update-user");
                    return;
                }
            }

            response.sendRedirect("relatorio.jsp?mensagem=erro-update-user");
        } else {
            // Preserva os dados no request
            request.setAttribute("id", tid);
            request.setAttribute("tipo", tipoTelefone);
            request.setAttribute("numero", numeroTelefone);

            // Redireciona de volta para o JSP de edição usando RequestDispatcher
            RequestDispatcher dispatcher = request.getRequestDispatcher("adicionartelefone.jsp?mensagem=alerta-campos");
            dispatcher.forward(request, response);
        }
    }//method

    private Long pegaProximoId(ServletContext aplication) {
        Long serial = (Long) aplication.getAttribute("serial");
        serial++;
        aplication.setAttribute("serial", serial);
        return serial;
    }

}//class