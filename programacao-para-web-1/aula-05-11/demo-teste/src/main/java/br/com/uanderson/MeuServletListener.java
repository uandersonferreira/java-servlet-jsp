package br.com.uanderson;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

public class MeuServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //Simulaçõa, ficticia da recuperação de uma string de conexão com o banco de dados, usando listeners
        // e passando essa string para o contexto da aplicação usando o listener.
        String stringdeconexao = sce.getServletContext().getInitParameter("stringdeconexao");
        Object obj = new String(stringdeconexao);
        sce.getServletContext().setAttribute("conexao", obj);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }


}//class
