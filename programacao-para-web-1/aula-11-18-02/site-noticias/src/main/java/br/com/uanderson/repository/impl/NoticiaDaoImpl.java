package br.com.uanderson.repository.impl;

import br.com.uanderson.model.Noticia;
import br.com.uanderson.model.Reporter;
import br.com.uanderson.repository.NoticiaDao;
import br.com.uanderson.repository.ReporterDao;
import br.com.uanderson.repository.connection.ConnectionFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class NoticiaDaoImpl implements NoticiaDao {
    private final Logger log = Logger.getLogger(String.valueOf(NoticiaDaoImpl.class));
    private final ConnectionFactory connectionFactory;
    private final ReporterDao reporterDao;

    public NoticiaDaoImpl(ConnectionFactory connectionFactory, ReporterDao reporterDao) {
        this.connectionFactory = connectionFactory;
        this.reporterDao = reporterDao;
    }

    @Override
    public Noticia save(Noticia noticia) {
        String sql = "INSERT INTO noticia (titulo, lide, corpoNoticia, dataPublicacao, reporter_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, noticia.getTitulo());
            stmt.setString(2, noticia.getLide());
            stmt.setString(3, noticia.getCorpoNoticia());
            stmt.setTimestamp(4, Timestamp.valueOf(noticia.getDataPublicacao()));
            stmt.setLong(5, noticia.getReporter().getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        noticia.setId(generatedKeys.getLong(1));
                        log.info(String.format("Notícia '%s' inserida com sucesso. ID: %d",
                                noticia.getTitulo(), noticia.getId()));
                        return noticia;
                    }
                }
            }

            log.info(String.format("Inserindo notícia '%s' no banco de dados", noticia.getTitulo()));
        } catch (SQLException e) {
            log.info(String.format("Erro ao inserir notícia '%s' no banco de dados: %s",
                    noticia.getTitulo(), e.getMessage()));
        }
        return null;
    }

    @Override
    public boolean update(Noticia noticia) {
        String sql = "UPDATE noticia SET titulo = ?, lide = ?, corpoNoticia = ?, dataPublicacao = ?, reporter_id = ? WHERE id = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, noticia.getTitulo());
            stmt.setString(2, noticia.getLide());
            stmt.setString(3, noticia.getCorpoNoticia());
            stmt.setTimestamp(4, Timestamp.valueOf(noticia.getDataPublicacao()));
            stmt.setLong(5, noticia.getReporter().getId());
            stmt.setLong(6, noticia.getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                log.info(String.format("Notícia '%s' atualizada com sucesso", noticia.getTitulo()));
            } else {
                log.info(String.format("Nenhuma notícia encontrada com ID %d para atualizar", noticia.getId()));
            }
            return true;
        } catch (SQLException e) {
            log.info(String.format("Erro ao atualizar notícia '%s': %s", noticia.getTitulo(), e.getMessage()));
        }
        return false;
    }

    @Override
    public boolean delete(Noticia noticiaForDelete) {
        return this.deleteById(noticiaForDelete.getId());
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM noticia WHERE id = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                log.info(String.format("Notícia com ID %d removida com sucesso", id));
            } else {
                log.info(String.format("Nenhuma notícia encontrada com ID %d para remover", id));
            }
            return true;
        } catch (SQLException e) {
            log.info(String.format("Erro ao remover notícia com ID %d: %s", id, e.getMessage()));
        }
        return false;
    }

    @Override
    public List<Noticia> listAll() {
        String sql = "SELECT * FROM noticia";
        List<Noticia> noticias = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            log.info("Listando todas as notícias do banco de dados . . .");
            while (rs.next()) {
                Noticia noticia = new Noticia();
                noticia.setId(rs.getLong("id"));
                noticia.setTitulo(rs.getString("titulo"));
                noticia.setLide(rs.getString("lide"));
                noticia.setCorpoNoticia(rs.getString("corpoNoticia"));
                noticia.setDataPublicacao(rs.getTimestamp("dataPublicacao").toLocalDateTime());

                // Buscar o reporter pelo ID
                Long reporterId = rs.getLong("reporter_id");
                Reporter reporter = reporterDao.findById(reporterId);
                noticia.setReporter(reporter);

                noticias.add(noticia);
            }
            log.info(String.format("Notícias listadas com sucesso! Total: %d", noticias.size()));
        } catch (SQLException e) {
            log.info(String.format("Erro ao listar notícias do banco de dados: %s", e.getMessage()));
        }

        return noticias;
    }

    @Override
    public Noticia findById(Long id) {
        String sql = "SELECT * FROM noticia WHERE id = ?";

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, id);
            log.info(String.format("Buscando notícia com ID %d", id));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Noticia noticia = new Noticia();
                    noticia.setId(rs.getLong("id"));
                    noticia.setTitulo(rs.getString("titulo"));
                    noticia.setLide(rs.getString("lide"));
                    noticia.setCorpoNoticia(rs.getString("corpoNoticia"));
                    noticia.setDataPublicacao(rs.getTimestamp("dataPublicacao").toLocalDateTime());

                    // Buscar o reporter pelo ID
                    Long reporterId = rs.getLong("reporter_id");
                    Reporter reporter = reporterDao.findById(reporterId);
                    noticia.setReporter(reporter);

                    log.info(String.format("Notícia encontrada: '%s'", noticia.getTitulo()));
                    return noticia;
                }
            }
            log.info(String.format("Nenhuma notícia encontrada com ID %d", id));

        } catch (SQLException e) {
            log.info(String.format("Erro ao buscar notícia com ID %d: %s", id, e.getMessage()));
        }
        return null;
    }

    @Override
    public List<Noticia> findByTitulo(String titulo) {
        String sql = "SELECT * FROM noticia WHERE titulo ILIKE ?";
        List<Noticia> noticias = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, "%" + titulo + "%");
            log.info(String.format("Buscando notícias com título contendo '%s'", titulo));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Noticia noticia = new Noticia();
                    noticia.setId(rs.getLong("id"));
                    noticia.setTitulo(rs.getString("titulo"));
                    noticia.setLide(rs.getString("lide"));
                    noticia.setCorpoNoticia(rs.getString("corpoNoticia"));
                    noticia.setDataPublicacao(rs.getTimestamp("dataPublicacao").toLocalDateTime());

                    // Buscar o reporter pelo ID
                    Long reporterId = rs.getLong("reporter_id");
                    Reporter reporter = reporterDao.findById(reporterId);
                    noticia.setReporter(reporter);

                    noticias.add(noticia);
                }
            }

            if (!noticias.isEmpty()) {
                log.info(String.format("Encontradas %d notícias com título contendo '%s'", noticias.size(), titulo));
            } else {
                log.info(String.format("Nenhuma notícia encontrada com título contendo '%s'", titulo));
            }

        } catch (SQLException e) {
            log.info(String.format("Erro ao buscar notícias com título '%s': %s", titulo, e.getMessage()));
        }

        return noticias;
    }

    @Override
    public List<Noticia> findByReporterId(Long reporterId) {
        String sql = "SELECT * FROM noticia WHERE reporter_id = ?";
        List<Noticia> noticias = new ArrayList<>();

        try (Connection conexao = connectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setLong(1, reporterId);
            log.info(String.format("Buscando notícias do repórter com ID %d", reporterId));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Noticia noticia = new Noticia();
                    noticia.setId(rs.getLong("id"));
                    noticia.setTitulo(rs.getString("titulo"));
                    noticia.setLide(rs.getString("lide"));
                    noticia.setCorpoNoticia(rs.getString("corpoNoticia"));
                    noticia.setDataPublicacao(rs.getTimestamp("dataPublicacao").toLocalDateTime());

                    // Buscar o reporter pelo ID
                    Reporter reporter = reporterDao.findById(reporterId);
                    noticia.setReporter(reporter);

                    noticias.add(noticia);
                }
            }

            if (!noticias.isEmpty()) {
                log.info(String.format("Encontradas %d notícias do repórter com ID %d", noticias.size(), reporterId));
            } else {
                log.info(String.format("Nenhuma notícia encontrada para o repórter com ID %d", reporterId));
            }

        } catch (SQLException e) {
            log.info(String.format("Erro ao buscar notícias do repórter com ID %d: %s", reporterId, e.getMessage()));
        }

        return noticias;
    }
}