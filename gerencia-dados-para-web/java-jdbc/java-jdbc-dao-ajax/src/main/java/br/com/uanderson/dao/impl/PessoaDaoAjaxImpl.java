package br.com.uanderson.dao.impl;

import br.com.uanderson.XmlManager;
import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.dao.connection.ConnectionFactory;
import br.com.uanderson.model.Pessoa;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PessoaDaoAjaxImpl implements PessoaDaoAjax {
    private final ConnectionFactory connectionFactory;
    private final XmlManager xmlManager = new XmlManager();

    public PessoaDaoAjaxImpl() {
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    @Override
    public void save(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, idade) VALUES (?, ?)";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.executeUpdate();
            log.info("Inserindo pessoa '{}' no banco de dados", pessoa.getNome());
        } catch (SQLException e) {
            log.error("Erro ao inserir pessoa '{}' no banco de dados: {}", pessoa.getNome(), e.getMessage());
        }
    }

    @Override
    public void update(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nome = ?, idade = ? WHERE id = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setInt(3, pessoa.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info("Pessoa '{}' atualizada com sucesso", pessoa.getNome());
            } else {
                log.warn("Nenhuma pessoa encontrada com ID {} para atualizar", pessoa.getId());
            }
        } catch (SQLException e) {
            log.error("Erro ao atualizar pessoa '{}': {}", pessoa.getNome(), e.getMessage());
        }
    }

    @Override
    public void delete(Pessoa pessoaForDelete) {
        this.deleteById(pessoaForDelete.getId());
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Pessoa com ID {} removida com sucesso", id);
            } else {
                log.warn("Nenhuma pessoa encontrada com ID {} para remover", id);
            }
        } catch (SQLException e) {
            log.error("Erro ao remover pessoa com ID {}: {}", id, e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM pessoa";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                log.info("Todas as pessoas foram removidas do banco de dados. Total de registros removidos: {}", rowsAffected);
            } else {
                log.info("Nenhum registro encontrado para remover");
            }
        } catch (SQLException e) {
            log.error("Erro ao remover todas as pessoas do banco de dados: {}", e.getMessage());
        }
    }

    @Override
    public List<Pessoa> listAll() {
        String sql = "SELECT * FROM pessoa";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            log.info("Listando todas as pessoas do banco de dados . . .");
            while (rs.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(rs.getInt("id"));
                pessoa.setNome(rs.getString("nome"));
                pessoa.setIdade(rs.getInt("idade"));
                pessoas.add(pessoa);
            }
            log.info("Pessoas listadas com sucesso! Total: {}", pessoas.size());
        } catch (SQLException e) {
            log.error("Erro ao listar pessoas do banco de dados: {}", e.getMessage());
        }

        return pessoas;
    }

    @Override
    public Pessoa findById(int id) {
        String sql = "SELECT * FROM pessoa WHERE id = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            log.debug("Buscando pessoa com ID {}", id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(rs.getInt("id"));
                    pessoa.setNome(rs.getString("nome"));
                    pessoa.setIdade(rs.getInt("idade"));
                    log.info("Pessoa encontrada: '{}'", pessoa.getNome());
                    return pessoa;
                }
            }
            log.warn("Nenhuma pessoa encontrada com ID {}", id);

        } catch (SQLException e) {
            log.error("Erro ao buscar pessoa com ID {}: {}", id, e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pessoa> findByNome(String nome) {
        String sql = "SELECT * FROM pessoa WHERE nome ILIKE ?";
        List<Pessoa> pessoas = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            log.debug("Buscando pessoas com nome contendo '{}'", nome);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(rs.getInt("id"));
                    pessoa.setNome(rs.getString("nome"));
                    pessoa.setIdade(rs.getInt("idade"));
                    pessoas.add(pessoa);
                }
            }

            if (!pessoas.isEmpty()) {
                log.info("Encontradas {} pessoas com nome contendo '{}'", pessoas.size(), nome);
            } else {
                log.warn("Nenhuma pessoa encontrada com nome contendo '{}'", nome);
            }

        } catch (SQLException e) {
            log.error("Erro ao buscar pessoas com nome '{}': {}", nome, e.getMessage());
        }

        return pessoas;
    }

    public String convertToXml() {
        List<Pessoa> pessoas = listAll();
        return xmlManager.convertToXml(pessoas);
    }

}//class