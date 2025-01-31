package aula.daomatutino.Dao;

import aula.daomatutino.Modelo.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDaoClasse implements ProdutoDaoInterface {
    Connection con;

    public ProdutoDaoClasse() throws ErroDao {
        con = FabricaConexao.pegaConexao();
    }

    @Override
    public void inserir(Produto p) throws ErroDao {
        PreparedStatement pstm = null;
        try {
            pstm = con.prepareStatement("insert into produto(nome,descricao,preco) values(?,?,?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getDescricao());
            pstm.setFloat(3, p.getPreco());
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();

            if (rs.next())
                p.setId(rs.getInt(1));

            pstm.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }

    @Override
    public Produto buscar(int id) throws ErroDao {
        PreparedStatement pstm;
        Produto p = new Produto();
        try {
            pstm = con.prepareStatement("select * from produto where id = ?");
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getFloat("preco"));
                return p;
            }

            pstm.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
        return p;
    }

    @Override
    public List<Produto> buscar() throws ErroDao {
        PreparedStatement pstm = null;
        List<Produto> produtos = new ArrayList<>();
        try {
            pstm = con.prepareStatement("select * from produto");
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getFloat("preco"));
                produtos.add(p);
            }

            pstm.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
        return produtos;
    }

    @Override
    public void deletar(int id) throws ErroDao {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection conexao = FabricaConexao.pegaConexao();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pessoa com ID {} removida com sucesso " + id);
            } else {
                System.out.println("Nenhuma pessoa encontrada com ID {" + id + "}para remover");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover pessoa com ID { " + id + "}");
            e.printStackTrace();
        }

    }

    @Override
    public void deletar(Produto p) throws ErroDao {
        deletar(p.getId());
    }

    @Override
    public void editar(Produto p) throws ErroDao {

    }

    @Override
    public void sair() throws ErroDao {
        try {
            con.close();
        } catch (SQLException e) {
            throw new ErroDao(e);
        }
    }
}
