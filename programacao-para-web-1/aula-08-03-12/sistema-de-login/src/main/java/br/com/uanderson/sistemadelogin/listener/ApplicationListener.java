package br.com.uanderson.sistemadelogin.listener;

import br.com.uanderson.sistemadelogin.model.Telefone;
import br.com.uanderson.sistemadelogin.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.ArrayList;
import java.util.List;

@WebListener
public class ApplicationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        List<Telefone> telefones = new ArrayList<>();
        telefones.add(new Telefone(4L, "Residencial", "999999999"));
        telefones.add(new Telefone(5L, "Trabalho", "8848484848"));

        List<Usuario> users = new ArrayList<>();
        users.add(new Usuario(1L, "José", "jose", "123", false, null));
        users.add(new Usuario(2L, "Maria", "maria", "abcd", false, null));
        users.add(new Usuario(3L, "Admin", "admin", "admin", true, telefones));

        servletContext.setAttribute("usersContext", users);

        servletContext.setAttribute("serial", 5L);

    }
}
