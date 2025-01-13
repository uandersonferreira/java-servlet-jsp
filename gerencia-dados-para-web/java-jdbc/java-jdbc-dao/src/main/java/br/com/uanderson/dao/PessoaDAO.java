package br.com.uanderson.dao;

import br.com.uanderson.model.Pessoa;

import java.util.List;

public interface PessoaDAO {
    //implementar os métodos CRUD para a entidade Pessoa
    void save(Pessoa pessoa);
    void update(Pessoa pessoa);
    void delete(Pessoa pessoa);
    void deleteById(int id);
    void deleteAll();
    List<Pessoa> listAll();
    Pessoa findById(int id);
    List<Pessoa> findByNome(String nome);

}
