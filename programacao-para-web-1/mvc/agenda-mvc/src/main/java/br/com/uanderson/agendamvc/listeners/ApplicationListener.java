package br.com.uanderson.agendamvc.listeners;

import br.com.uanderson.agendamvc.model.Agenda;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Aplicação iniciada!");

        //Criar uma agenda e coloca no contexto de toda a aplicação
        Agenda agendaContext = new Agenda();
        servletContextEvent.getServletContext().setAttribute("agendaContext", agendaContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Aplicação finalizada!");
    }

}//class
