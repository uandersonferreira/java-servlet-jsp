package br.com.uanderson;

import br.com.uanderson.dao.PessoaDAO;
import br.com.uanderson.dao.impl.PessoaDAOImpl;
import br.com.uanderson.model.Pessoa;

public class Main {
    public static void main(String[] args) {
        PessoaDAO pessoaDAO = new PessoaDAOImpl();

        System.out.println("\n=== Teste de Inserção ===");
        // Criando e salvando algumas pessoas
        Pessoa pessoa1 = new Pessoa("João Silva", 25);
        Pessoa pessoa2 = new Pessoa("Maria Santos", 30);
        Pessoa pessoa3 = new Pessoa("Pedro Silva", 35);

        pessoaDAO.save(pessoa1);
        pessoaDAO.save(pessoa2);
        pessoaDAO.save(pessoa3);

        System.out.println("\n=== Teste de Listagem ===");
        System.out.println("Pessoas cadastradas:");
        pessoaDAO.listAll().forEach(System.out::println);

        System.out.println("\n=== Teste de Busca por ID ===");
        // Assumindo que existe um registro com ID 1
        Pessoa pessoaEncontrada = pessoaDAO.findById(1);
        if (pessoaEncontrada != null) {
            System.out.println("Pessoa encontrada por ID: " + pessoaEncontrada);

            System.out.println("\n=== Teste de Atualização ===");
            pessoaEncontrada.setIdade(26);
            pessoaDAO.update(pessoaEncontrada);

            System.out.println("Pessoa após atualização: " + pessoaDAO.findById(1));
        }

        System.out.println("\n=== Teste de Busca por Nome ===");
        System.out.println("Pessoas com 'Silva' no nome:");
        pessoaDAO.findByNome("Silva").forEach(System.out::println);

        System.out.println("\n=== Teste de Deleção ===");
        // Deletando a última pessoa cadastrada pelo id
        pessoaDAO.deleteById(1);

        Pessoa pessoaSalvaParaDeletar = pessoaDAO.findById(4);
        if (pessoaSalvaParaDeletar != null) {
            System.out.println("Pessoa salva para deletar: " + pessoaSalvaParaDeletar);
            pessoaDAO.delete(pessoaSalvaParaDeletar);
        }

        System.out.println("\n=== Lista Final ===");
        System.out.println("Pessoas após deleção:");
        pessoaDAO.listAll().forEach(System.out::println);

        // Teste de casos especiais
        System.out.println("\n=== Testes de Casos Especiais ===");

        // Tentando buscar ID inexistente
        System.out.println("Buscando ID inexistente:");
        Pessoa pessoaInexistente = pessoaDAO.findById(9999);
        System.out.println(pessoaInexistente == null ? "Pessoa não encontrada" : pessoaInexistente);

        // Buscando nome que não existe
        System.out.println("\nBuscando nome inexistente:");
        var pessoasNaoEncontradas = pessoaDAO.findByNome("XPTO");
        System.out.println(pessoasNaoEncontradas.isEmpty() ?
                "Nenhuma pessoa encontrada com este nome" :
                pessoasNaoEncontradas);

        System.out.println("\n=== Teste de Remoção de Todos os Registros ===");
        //pessoaDAO.deleteAll();
        System.out.println("Lista após deleteAll:");
        pessoaDAO.listAll().forEach(System.out::println);

    }//main

}//class