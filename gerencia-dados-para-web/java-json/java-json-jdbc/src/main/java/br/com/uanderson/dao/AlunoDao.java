package br.com.uanderson.dao;

import br.com.uanderson.dao.exception.DatabaseConnectionException;
import br.com.uanderson.model.Aluno;

import java.util.List;

public interface AlunoDao {
    void inserir(Aluno aluno) throws DatabaseConnectionException; //pode gerar o id de forma aleatória ou outra forma qualquer que fique fácil

    Aluno pegaPorId(int id) throws DatabaseConnectionException;

    List<Aluno> pegaTodos() throws DatabaseConnectionException;

    void deletar(Aluno aluno) throws DatabaseConnectionException;
//    void editar(Aluno aluno) throws ErroDao; //opcional
}
