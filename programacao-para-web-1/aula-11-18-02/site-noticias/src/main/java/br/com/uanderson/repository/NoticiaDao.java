package br.com.uanderson.repository;

import br.com.uanderson.model.Noticia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaDao {
    boolean save(Noticia noticia);

    boolean update(Noticia noticia);

    boolean delete(Noticia noticia);

    boolean deleteById(Long id);

    void deleteAll();

    List<Noticia> listAll();
    List<Noticia> buscarNoticiasPorTitulo(String titulo);

    Noticia findById(Long id);

    List<Noticia> findByTitulo(String titulo);

    List<Noticia> findByReporterId(Long reporterId);
}