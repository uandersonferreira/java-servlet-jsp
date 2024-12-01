package br.com.uanderson.agendamvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Agenda {
    private List<Contato> contatos = new ArrayList<>();
    private int proximoCodigo = 1;


    // Adiciona um novo contato
    public void adicionarContato(Contato contato) {
        if (contato == null || contato.getNome() == null || contato.getNome().isEmpty() ||
                contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("Nome e telefone são obrigatórios!");
        }

        contato.setCodigo(proximoCodigo);
        proximoCodigo++;
        contatos.add(contato);
    }

    // Retorna todos os contatos
    public List<Contato> getContatos() {
        return new ArrayList<>(contatos); // Retorna uma cópia para preservar a lista original
    }

    // Busca um contato pelo código
    public Optional<Contato> buscarContato(int codigo) {
        return contatos.stream()
                .filter(contato -> contato.getCodigo() == codigo)
                .findFirst();
    }

    // Remove um contato pelo código
    public void removerContato(int codigo) {
        Optional<Contato> contatoParaRemover = buscarContato(codigo);
        if (contatoParaRemover.isPresent()) {
            contatos.remove(contatoParaRemover.get());
            System.out.println("Contato removido com sucesso!");
        } else {
            throw new IllegalArgumentException("Contato não encontrado!");
        }
    }

    // Edita um contato
    public void editarContato(Contato contato) {
        if (contato == null || contato.getNome() == null || contato.getNome().isEmpty() ||
                contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("Nome e telefone são obrigatórios!");
        }

        Optional<Contato> contatoParaUpdate = buscarContato(contato.getCodigo());
        if (contatoParaUpdate.isPresent()) {
            Contato existente = contatoParaUpdate.get();
            existente.setNome(contato.getNome());
            existente.setTelefone(contato.getTelefone());
            System.out.println("Contato atualizado com sucesso!");
        } else {
            throw new IllegalArgumentException("Contato não encontrado!");
        }
    }

    // Retorna a lista de contatos como uma string
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder();
        for (Contato contato : contatos) {
            texto.append(contato.toString()).append("\n");
        }
        return texto.toString();
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();
        Contato c1 = new Contato(1, "João", "1234-5678");
        Contato c2 = new Contato(2, "Maria", "9876-5432");
        Contato c3 = new Contato(3, "Pedro", "9999-9999");

        try {
            agenda.adicionarContato(c1);

            agenda.adicionarContato(c2);

            agenda.adicionarContato(c3);

            System.out.println("List All: \n" + agenda);

            agenda.removerContato(2);

            System.out.println("List All (remove): \n" + agenda);

            System.out.println("List one (find-by-codigo): " + agenda.buscarContato(1));

            System.out.println("List All (before-update): \n" + agenda);

            Optional<Contato> contatoParaEditar = agenda.buscarContato(3);

            System.out.println("contatoParaEditar: " + contatoParaEditar);

            contatoParaEditar.get().setNome("Pedro da Silva");

            contatoParaEditar.get().setTelefone("9999-9999");

            agenda.editarContato(contatoParaEditar.orElse(null));

            System.out.println("List all (after-update): \n" + agenda);


        } catch (Exception e) {
            System.out.println("[ERROR]: " + e + "\n");
        }//try-catch

    }//method


}//class
