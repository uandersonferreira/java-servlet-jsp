package br.com.uanderson.dao.impl;

import br.com.uanderson.dao.PessoaDaoAjax;
import br.com.uanderson.model.Pessoa;
import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class PessoaDaoXmlImpl implements PessoaDaoAjax {
    private String xmlFilePath;
    private Document document;

    public PessoaDaoXmlImpl() {
        try {
            // Obtém o caminho real do diretório WEB-INF
            String webInfPath = getClass().getClassLoader().getResource("").getPath();
            xmlFilePath = webInfPath + "pessoas.xml";

            File file = new File(xmlFilePath);
            if (!file.exists()) {
                createXmlFile();
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);

            log.info("Arquivo XML inicializado em: {}", xmlFilePath);
        } catch (Exception e) {
            log.error("Erro ao inicializar XML: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private void createXmlFile() throws ParserConfigurationException, TransformerException {
        try {
            // Criar diretório se não existir
            File directory = new File(xmlFilePath).getParentFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Criar documento XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            Element rootElement = document.createElement("pessoas");
            document.appendChild(rootElement);

            // Salvar arquivo
            saveXmlFile();

            log.info("Novo arquivo XML criado em: {}", xmlFilePath);
        } catch (Exception e) {
            log.error("Erro ao criar arquivo XML: {}", e.getMessage());
            throw e;
        }
    }

    private void saveXmlFile() throws TransformerException {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

            log.info("Arquivo XML salvo com sucesso");
        } catch (Exception e) {
            log.error("Erro ao salvar arquivo XML: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void save(Pessoa pessoa) {

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
        List<Pessoa> pessoas = new ArrayList<>();
        NodeList pessoaNodes = document.getElementsByTagName("pessoa");

        for (int i = 0; i < pessoaNodes.getLength(); i++) {
            Node pessoaNode = pessoaNodes.item(i);
            if (pessoaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element pessoaElement = (Element) pessoaNode;
                Pessoa pessoa = new Pessoa();
                pessoa.setNome(pessoaElement.getElementsByTagName("nome").item(0).getTextContent());
                pessoa.setIdade(Integer.parseInt(pessoaElement.getElementsByTagName("idade").item(0).getTextContent()));
                pessoas.add(pessoa);
            }
        }

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

    // Implementar os outros métodos do CRUD...
}