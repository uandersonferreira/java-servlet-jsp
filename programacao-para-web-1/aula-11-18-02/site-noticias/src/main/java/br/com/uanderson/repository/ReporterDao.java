package br.com.uanderson.repository;


import br.com.uanderson.model.Reporter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporterDao {

    //Methods CRUD
    void save(Reporter reporter);
    void update(Reporter reporter);
    void delete(Reporter reporter);
    void deleteById(Long id);
    List<Reporter> listAll();
    Reporter findById(Long id);
    List<Reporter> findByNome(String nome);
    Reporter findByLogin (String login);
}
