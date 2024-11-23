package br.com.uanderson.agendamvc.model;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private List<Contato> contatos = new ArrayList<>();
    private int proximoCodigo = 1;


    public void adicionarContato(Contato contato) {
        contato.setCodigo(proximoCodigo);
        proximoCodigo++;
        contatos.add(contato);
    }

    public List<Contato> getContatos() {
        return contatos;
    }

    public Contato buscarContato(int codigo) throws Exception {
        for (Contato contato : contatos) {
            if (contato.getCodigo() == codigo) {
                return contato;
            }
        }
        throw new Exception("Contato não existe na agenda!\n");
    }

    public void removerContato(int codigo) throws Exception {
            buscarContato(codigo);
            contatos.remove(buscarContato(codigo));
            System.out.println("Contato removido com sucesso!\n");
    }

    public void editarContato(Contato contato) {
        try {
            Contato contatoParaUpdate = buscarContato(contato.getCodigo());
            contatoParaUpdate.setNome(contato.getNome());
            contatoParaUpdate.setTelefone(contato.getTelefone());
            System.out.println("Contato atualizado com sucesso!\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        String texto = "";
        for (Contato contato : contatos) {
            texto += contato.toString();
        }
        return texto;
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

            Contato contatoParaEditar = agenda.buscarContato(3);

            System.out.println("contatoParaEditar: " + contatoParaEditar);

            contatoParaEditar.setNome("Pedro da Silva");

            contatoParaEditar.setTelefone("9999-9999");

            agenda.editarContato(contatoParaEditar);

            System.out.println("List all (after-update): \n" + agenda);


        } catch (Exception e) {
            System.out.println("[ERROR]: " + e + "\n");
        }//try-catch

    }//method


}//class
