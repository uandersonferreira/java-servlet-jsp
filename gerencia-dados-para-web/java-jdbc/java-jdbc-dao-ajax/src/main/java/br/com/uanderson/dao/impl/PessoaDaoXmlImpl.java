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
    private final String XML_FILE = "src/main/resources/pessoas.xml";
    private Document document;

    public PessoaDaoXmlImpl() {
        try {
            File file = new File(XML_FILE);
            if (!file.exists()) {
                createXmlFile();
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(file);
        } catch (Exception e) {
            log.error("Erro ao inicializar XML: {}", e.getMessage());
        }
    }

    private void createXmlFile() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.newDocument();
        Element rootElement = document.createElement("pessoas");
        document.appendChild(rootElement);
        saveXmlFile();
    }

    private void saveXmlFile() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(XML_FILE));
        transformer.transform(source, result);
    }

    @Override
    public void save(Pessoa pessoa) {
        try {
            Element pessoaElement = document.createElement("pessoa");

            Element nomeElement = document.createElement("nome");
            nomeElement.setTextContent(pessoa.getNome());
            pessoaElement.appendChild(nomeElement);

            Element idadeElement = document.createElement("idade");
            idadeElement.setTextContent(String.valueOf(pessoa.getIdade()));
            pessoaElement.appendChild(idadeElement);

            document.getDocumentElement().appendChild(pessoaElement);
            saveXmlFile();

            log.info("Pessoa '{}' salva no XML", pessoa.getNome());
        } catch (Exception e) {
            log.error("Erro ao salvar pessoa no XML: {}", e.getMessage());
        }
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