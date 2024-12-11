package br.com.uanderson.sistemadelogin.listener;

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

        List<Usuario> users = new ArrayList<>();
        users.add(new Usuario(1L, "José", "jose", "123"));
        users.add(new Usuario(2L, "Maria", "maria", "abcd"));

        servletContext.setAttribute("usersContext", users);

        servletContext.setAttribute("serial", 2L);

    }
}
