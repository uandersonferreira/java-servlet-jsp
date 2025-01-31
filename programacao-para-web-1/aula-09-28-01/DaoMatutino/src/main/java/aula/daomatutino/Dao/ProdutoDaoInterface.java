package aula.daomatutino.Dao;

import aula.daomatutino.Modelo.Produto;

import java.sql.SQLException;
import java.util.List;

public interface ProdutoDaoInterface {
    public void inserir(Produto p) throws ErroDao;
    public Produto buscar(int id) throws ErroDao;
    public List<Produto> buscar() throws ErroDao;
    public void deletar(int id) throws ErroDao;
    public void deletar(Produto p) throws ErroDao;
    public void editar(Produto p) throws ErroDao;
    public void sair() throws ErroDao;
}
