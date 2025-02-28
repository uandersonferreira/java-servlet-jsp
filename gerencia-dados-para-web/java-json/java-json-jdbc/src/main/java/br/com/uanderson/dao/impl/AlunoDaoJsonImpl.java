package br.com.uanderson.dao.impl;

import br.com.uanderson.dao.AlunoDao;
import br.com.uanderson.dao.exception.DatabaseConnectionException;
import br.com.uanderson.model.Aluno;
import jakarta.json.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AlunoDaoJsonImpl implements AlunoDao {
    private static final String JSON_FILE = "src/main/webapp/WEB-INF/alunos.json";
    Logger logger = Logger.getLogger(AlunoDaoJsonImpl.class.getName());


    public String alunoToJson(Aluno aluno) {
        JsonArrayBuilder notasBuilder = Json.createArrayBuilder();
        for (double nota : aluno.getNotas()) {
            notasBuilder.add(nota);
        }

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("id", aluno.getId())
                .add("nome", aluno.getNome())
                .add("notas", notasBuilder)
                .build();

        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.writeObject(jsonObject);
        }

        return stringWriter.toString();
    }

    public Aluno jsonToAluno(String jsonString) {
        try (JsonReader jsonReader = Json.createReader(new StringReader(jsonString))) {
            JsonObject jsonObject = jsonReader.readObject();

            Aluno aluno = new Aluno();
            aluno.setId(jsonObject.getInt("id"));
            aluno.setNome(jsonObject.getString("nome"));

            JsonArray notasArray = jsonObject.getJsonArray("notas");
            double[] notas = new double[notasArray.size()];
            for (int i = 0; i < notasArray.size(); i++) {
                notas[i] = notasArray.getJsonNumber(i).doubleValue();
            }
            aluno.setNotas(notas);

            return aluno;
        }
    }

    @Override
    public void inserir(Aluno aluno) throws DatabaseConnectionException {
        List<Aluno> alunos = pegaTodos();
        alunos.add(aluno);
        salvarTodos(alunos);
        logger.info("Aluno inserido com sucesso: " + aluno);
    }

    @Override
    public Aluno pegaPorId(int id) throws DatabaseConnectionException {
        return pegaTodos().stream()
                .filter(aluno -> aluno.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Aluno> pegaTodos() throws DatabaseConnectionException {
        List<Aluno> alunos = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(JSON_FILE)));
            if (!content.trim().isEmpty()) {
                try (JsonReader jsonReader = Json.createReader(new StringReader(content))) {
                    JsonArray jsonArray = jsonReader.readArray();
                    for (JsonValue value : jsonArray) {
                        JsonObject jsonObject = (JsonObject) value;
                        alunos.add(jsonToAluno(jsonObject.toString()));
                    }
                }
            }
        } catch (IOException e) {
            throw new DatabaseConnectionException("Erro ao ler arquivo JSON", e);
        }
        return alunos;
    }

    @Override
    public void deletar(Aluno aluno) throws DatabaseConnectionException {
        List<Aluno> alunos = pegaTodos();
        alunos.removeIf(a -> a.getId() == aluno.getId());
        salvarTodos(alunos);
        logger.info("Aluno deletado com sucesso: " + aluno);
    }

    private void salvarTodos(List<Aluno> alunos) throws DatabaseConnectionException {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Aluno aluno : alunos) {
            arrayBuilder.add(Json.createReader(new StringReader(alunoToJson(aluno))).readObject());
        }

        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write(arrayBuilder.build().toString());
        } catch (IOException e) {
            throw new DatabaseConnectionException("Erro ao salvar arquivo JSON", e);
        }
    }

    public static void main(String[] args) {
        AlunoDaoJsonImpl alunoDaoJson = new AlunoDaoJsonImpl();

        try {
            alunoDaoJson.inserir(new Aluno(1, "uanderson", new double[]{8,7}));
            alunoDaoJson.inserir(new Aluno(2, "Pedro", new double[]{8,7}));
            alunoDaoJson.inserir(new Aluno(3, "maria", new double[]{8,7}));
            System.out.println("------------------------");

            Aluno aluno = alunoDaoJson.pegaPorId(1);
            System.out.println("ALUNO POR ID: " + aluno);
            System.out.println("------------------------");

            List<Aluno> alunos = alunoDaoJson.pegaTodos();
            for (Aluno aluno1 : alunos) {
                System.out.println(aluno1);
            }
            System.out.println("------------------------");

            alunoDaoJson.deletar(aluno);
            System.out.println("------------------------");

            List<Aluno> alunos2 = alunoDaoJson.pegaTodos();
            for (Aluno aluno1 : alunos2) {
                System.out.println(aluno1);
            }


        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }

    }


}
