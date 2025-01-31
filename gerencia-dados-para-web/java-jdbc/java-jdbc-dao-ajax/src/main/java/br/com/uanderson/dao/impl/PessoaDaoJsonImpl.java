package br.com.uanderson.dao.impl;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.model.Pessoa;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PessoaDaoJsonImpl implements PessoaDaoAjax {
    private final String JSON_FILE = "pessoas.json";
    private final ObjectMapper objectMapper;

    public PessoaDaoJsonImpl() {
        this.objectMapper = new ObjectMapper();
        File file = new File(JSON_FILE);
        if (!file.exists()) {
            try {
                objectMapper.writeValue(file, new ArrayList<Pessoa>());
            } catch (IOException e) {
                log.error("Erro ao criar arquivo JSON: {}", e.getMessage());
            }
        }
    }

    private List<Pessoa> readJsonFile() {
        try {
            return objectMapper.readValue(
                    new File(JSON_FILE),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Pessoa.class)
            );
        } catch (IOException e) {
            log.error("Erro ao ler arquivo JSON: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    private void writeJsonFile(List<Pessoa> pessoas) {
        try {
            objectMapper.writeValue(new File(JSON_FILE), pessoas);
        } catch (IOException e) {
            log.error("Erro ao escrever arquivo JSON: {}", e.getMessage());
        }
    }

    @Override
    public void save(Pessoa pessoa) {
        List<Pessoa> pessoas = readJsonFile();
        pessoa.setId(pessoas.size() + 1); // Simples geração de ID
        pessoas.add(pessoa);
        writeJsonFile(pessoas);
        log.info("Pessoa '{}' salva no JSON", pessoa.getNome());
    }

    @Override
    public void update(Pessoa pessoa) {

    }

    @Override
    public void delete(Pessoa pessoa) {

    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Pessoa> listAll() {
        List<Pessoa> pessoas = readJsonFile();
        log.info("Listando {} pessoas do JSON", pessoas.size());
        return pessoas;
    }

    @Override
    public Pessoa findById(int id) {
        return null;
    }

    @Override
    public List<Pessoa> findByNome(String nome) {
        return List.of();
    }

}