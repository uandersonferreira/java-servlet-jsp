package br.com.uanderson.repository.impl;

import br.com.uanderson.model.Reporter;
import br.com.uanderson.repository.ReporterDao;
import br.com.uanderson.repository.connection.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
@Repository
public class ReporterDaoImpl implements ReporterDao {
    private final Logger log = Logger.getLogger(String.valueOf(ReporterDaoImpl.class));

    private final ConnectionFactory connectionFactory;

    public ReporterDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    @Override
    public void save(Reporter reporter) {
        String sql = "INSERT INTO reporter (nome, login, senha) VALUES (?, ?, ?)";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, reporter.getNome());
            stmt.setString(2, reporter.getLogin());
            stmt.setString(3, reporter.getSenha());
            stmt.executeUpdate();
            log.info(String.format("Inserindo reporter '%s' no banco de dados", reporter.getNome()));
        } catch (SQLException e) {
            log.info(String.format("Erro ao inserir reporter '%s' no banco de dados: '%s'", reporter.getNome(), e.getMessage()));
        }
    }


    @Override
    public void update(Reporter reporter) {
        String sql = "UPDATE reporter SET nome = ?, login = ?, senha = ? WHERE id = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, reporter.getNome());
            stmt.setString(2, reporter.getLogin());
            stmt.setString(3, reporter.getSenha());
            stmt.setLong(4, reporter.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info(String.format("Reporter '%s' atualizada com sucesso", reporter.getNome()));
            } else {
                log.info(String.format("Nenhuma reporter encontrada com ID '%s' para atualizar", reporter.getId()));
            }
        } catch (SQLException e) {
            log.info(String.format("Erro ao atualizar reporter '%s': '%s'", reporter.getNome(), e.getMessage()));
        }
    }

    @Override
    public void delete(Reporter reporterForDelete) {
        this.deleteById(reporterForDelete.getId());
    }

    @Override
    public void deleteById(Long id) {
        // Atualize o campo 'ativo' para false em vez de excluir o registro onde id é igual ao solicitado e esteja ativo
        String sql = "UPDATE reporter SET ativo = false WHERE id = ? AND ativo = true";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                log.info(String.format("Reporter com ID '%s' inativado com sucesso", id));
            } else {
                log.info(String.format("Nenhuma reporter encontrada com ID '%s' para inativar", id));
            }
        } catch (SQLException e) {
            log.info(String.format("Erro ao inativar o reporter com ID '%s': '%s'", id, e.getMessage()));
            e.printStackTrace();
        }
    }


    @Override
    public List<Reporter> listAll() {
        String sql = "SELECT * FROM reporter WHERE ativo = true";
        List<Reporter> reporters = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            log.info("Listando todas as reporters do banco de dados . . .");
            while (rs.next()) {
                Reporter reporter = new Reporter();
                reporter.setId(rs.getLong("id"));
                reporter.setNome(rs.getString("nome"));
                reporter.setLogin(rs.getString("login"));
                reporter.setSenha(rs.getString("senha"));
                reporters.add(reporter);
            }
            log.info(String.format("Reportes listadas com sucesso! Total: '%s'", reporters.size()));
        } catch (SQLException e) {
            log.info(String.format("Erro ao listar reporters do banco de dados: '%s'", e.getMessage()));
        }

        return reporters;
    }

    @Override
    public Reporter findById(Long id) {
        String sql = "SELECT * FROM reporter WHERE id = ? AND ativo = true";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            log.info(String.format("Buscando reporter com ID '%s'", id));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Reporter reporter = new Reporter();
                    reporter.setId(rs.getLong("id"));
                    reporter.setNome(rs.getString("nome"));
                    reporter.setLogin(rs.getString("login"));
                    reporter.setSenha(rs.getString("senha"));
                    reporter.setAtivo(rs.getBoolean("ativo"));
                    log.info(String.format("Reporter encontrada: '%s'", reporter.getNome()));
                    return reporter;
                }
            }
            log.info(String.format("Nenhuma reporter encontrada com ID '%s'", id));

        } catch (SQLException e) {
            log.info(String.format("Erro ao buscar reporter com ID '%s': '%s'", id, e.getMessage()));
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reporter> findByNome(String nome) {
        String sql = "SELECT * FROM reporter WHERE nome ILIKE ? AND ativo = true";
        List<Reporter> reporters = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            log.info(String.format("Buscando reporters com nome contendo '%s'", nome));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Reporter reporter = new Reporter();
                    reporter.setId(rs.getLong("id"));
                    reporter.setNome(rs.getString("nome"));
                    reporter.setLogin(rs.getString("login"));
                    reporter.setSenha(rs.getString("senha"));
                    reporter.setAtivo(rs.getBoolean("ativo"));
                    reporters.add(reporter);
                }
            }

            if (!reporters.isEmpty()) {
                log.info(String.format("Encontradas '%s' reporters com nome contendo '%s'", reporters.size(), nome));
            } else {
                log.info(String.format("Nenhuma reporter encontrada com nome contendo '%s'", nome));
            }

        } catch (SQLException e) {
            log.info(String.format("Erro ao buscar reporters com nome '%s': '%s'", nome, e.getMessage()));
        }

        return reporters;
    }

    @Override
    public Reporter findByLogin(String login) {
        String sql = "SELECT * FROM reporter WHERE login = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, login);
            log.info(String.format("Buscando reporter com login '%s'", login));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Reporter reporter = new Reporter();
                    reporter.setId(rs.getLong("id"));
                    reporter.setNome(rs.getString("nome"));
                    reporter.setLogin(rs.getString("login"));
                    reporter.setSenha(rs.getString("senha"));
                    reporter.setAtivo(rs.getBoolean("ativo"));
                    log.info(String.format("Reporter encontrado: '%s'", reporter.getNome()));
                    return reporter;
                }
            }
            log.info(String.format("Nenhum reporter encontrado com login '%s'", login));

        } catch (SQLException e) {
            log.info(String.format("Erro ao buscar reporter com login '%s': %s", login, e.getMessage()));
        }
        return null;
    }
}
