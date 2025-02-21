package br.com.uanderson.repository;

import br.com.uanderson.model.Noticia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticiaDao {
    Noticia save(Noticia noticia);

    boolean update(Noticia noticia);

    boolean delete(Noticia noticia);

    boolean deleteById(Long id);

    List<Noticia> listAll();

    Noticia findById(Long id);

    List<Noticia> findByTitulo(String titulo);

    List<Noticia> findByReporterId(Long reporterId);
}