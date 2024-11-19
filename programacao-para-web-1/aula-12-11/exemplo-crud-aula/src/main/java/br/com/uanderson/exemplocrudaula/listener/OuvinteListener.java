package br.com.uanderson.exemplocrudaula.listener;

import br.com.uanderson.exemplocrudaula.model.Produto;
import br.com.uanderson.exemplocrudaula.model.Usuario;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.util.ArrayList;
import java.util.List;

@WebListener
public class OuvinteListener implements ServletContextListener {

    /**
     * Este método é chamado automaticamente quando o contexto da aplicação
     * é inicializado. O evento ServletContextEvent passado como argumento
     * contém o ServletContext, que representa o escopo global da aplicação
     * e pode ser usado para acessar recursos compartilhados entre todos os
     * servlets e listeners.
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext aplicacao = servletContextEvent.getServletContext();

        List<Produto> produtos = new ArrayList<>();
        aplicacao.setAttribute("produtos",produtos);

        produtos.add(new Produto(1,"Computador",4000));
        produtos.add(new Produto(2,"Livro de Java",400));
        produtos.add(new Produto(3,"Mouse",50));

        List<Usuario> usuarios = new ArrayList<>();
        aplicacao.setAttribute("usuarios",usuarios);
        usuarios.add(new Usuario(4,"José","jose","123"));
        usuarios.add(new Usuario(5,"Maria","maria","abcd"));
        usuarios.add(new Usuario(6,"Uanderson","uanderson","123"));

        aplicacao.setAttribute("serial",5);
    }
}
/*
 Por que armazenar no contexto da aplicação e não em outro lugar?

    Escopo global: Os dados ficam disponíveis enquanto a aplicação estiver em execução.

    Compartilhamento fácil: Todos os componentes têm acesso aos dados sem a necessidade de passar
    objetos diretamente.

    Dados iniciais: Permite carregar informações prontas no momento da inicialização,
    otimizando o uso posterior.

 */