package br.com.uanderson.dao.impl;

import br.com.uanderson.dao.AlunoDao;
import br.com.uanderson.dao.connection.ConnectionFactory;
import br.com.uanderson.dao.exception.DatabaseConnectionException;
import br.com.uanderson.model.Aluno;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class AlunoDaoBancoImpl implements AlunoDao {
    private ConnectionFactory connectionFactory;
    private final Logger logger = Logger.getLogger(AlunoDaoBancoImpl.class.getName());

    public AlunoDaoBancoImpl() {
        this.connectionFactory = ConnectionFactory.getInstance();
    }

    @Override
    public void inserir(Aluno aluno) throws DatabaseConnectionException {
        String sql = "INSERT INTO aluno (nome, nota1, nota2) VALUES (?, ?,?)";

        try (PreparedStatement stmt = connectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setDouble(2, aluno.getNotas()[0]);
            stmt.setDouble(3, aluno.getNotas()[1]);
            stmt.executeUpdate();

            // Recupera o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    aluno.setId(generatedKeys.getInt(1));
                }
            }
            logger.info("Aluno inserido com sucesso: " + aluno);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Aluno pegaPorId(int id) throws DatabaseConnectionException {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try (PreparedStatement stmt = connectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            double[] notas = new double[2];
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Aluno aluno = new Aluno();
                    aluno.setId(rs.getInt("id"));
                    aluno.setNome(rs.getString("nome"));
                    notas[0] = rs.getDouble("nota1");
                    notas[1] = rs.getDouble("nota2");
                    aluno.setNotas(notas);

                    logger.info("Aluno encontrado com sucesso pelo ID: " + id + " - " + aluno);
                    return aluno;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Aluno> pegaTodos() throws DatabaseConnectionException {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT * FROM aluno";
        try (PreparedStatement stmt = connectionFactory.getConnection().prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            double[] notas = new double[2];
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                notas[0] = rs.getDouble("nota1");
                notas[1] = rs.getDouble("nota2");
                aluno.setNotas(notas);
                alunos.add(aluno);
            }
            logger.info("Listando todos os alunos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }

    @Override
    public void deletar(Aluno aluno) throws DatabaseConnectionException {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (PreparedStatement stmt = connectionFactory.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, aluno.getId());
            stmt.executeUpdate();
            logger.info("Deletado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AlunoDao alunoDao = new AlunoDaoBancoImpl();

        try {
            //alunoDao.inserir(new Aluno("Pedro", new double[]{7.8, 9}));
            Aluno aluno = alunoDao.pegaPorId(1);
            System.out.println(aluno);

            List<Aluno> alunos = alunoDao.pegaTodos();
            for (Aluno aluno1 : alunos) {
                System.out.println(aluno1);
            }

            alunoDao.deletar(aluno);


        } catch (DatabaseConnectionException e) {
            throw new RuntimeException(e);
        }
    }//main test


}//class