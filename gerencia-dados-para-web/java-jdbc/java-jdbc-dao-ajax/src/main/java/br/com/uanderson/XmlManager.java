package br.com.uanderson;

import br.com.uanderson.model.Pessoa;
import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class XmlManager {

    public String convertToXml(List<Pessoa> pessoas) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Create root element
            Element rootElement = doc.createElement("pessoas");
            doc.appendChild(rootElement);

            // Add pessoas
            for (Pessoa pessoa : pessoas) {
                Element pessoaElement = doc.createElement("pessoa");
                rootElement.appendChild(pessoaElement);

                // Add id
                Element id = doc.createElement("id");
                id.setTextContent(String.valueOf(pessoa.getId()));
                pessoaElement.appendChild(id);

                // Add nome
                Element nome = doc.createElement("nome");
                nome.setTextContent(pessoa.getNome());
                pessoaElement.appendChild(nome);

                // Add idade
                Element idade = doc.createElement("idade");
                idade.setTextContent(String.valueOf(pessoa.getIdade()));
                pessoaElement.appendChild(idade);
            }

            // Convert to string
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));

            log.info("XML gerado com sucesso para {} pessoas", pessoas.size());
            return writer.toString();

        } catch (Exception e) {
            log.error("Erro ao gerar XML: {}", e.getMessage());
            throw new RuntimeException("Erro ao gerar XML", e);
        }
    }

    public List<Pessoa> convertToPessoas(String xml) {
        List<Pessoa> pessoas = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList pessoasList = doc.getElementsByTagName("pessoa");

            for (int i = 0; i < pessoasList.getLength(); i++) {
                Node pessoaNode = pessoasList.item(i);
                if (pessoaNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element pessoaElement = (Element) pessoaNode;

                    Pessoa pessoa = new Pessoa();
                    pessoa.setId(Integer.parseInt(pessoaElement.getElementsByTagName("id").item(0).getTextContent()));
                    pessoa.setNome(pessoaElement.getElementsByTagName("nome").item(0).getTextContent());
                    pessoa.setIdade(Integer.parseInt(pessoaElement.getElementsByTagName("idade").item(0).getTextContent()));

                    pessoas.add(pessoa);
                }
            }

            log.info("XML convertido com sucesso para {} pessoas", pessoas.size());
            return pessoas;

        } catch (Exception e) {
            log.error("Erro ao converter XML para objetos: {}", e.getMessage());
            throw new RuntimeException("Erro ao converter XML", e);
        }
    }

    public boolean validateXml(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.parse(xml);
            log.info("XML validado com sucesso");
            return true;
        } catch (Exception e) {
            log.error("XML inválido: {}", e.getMessage());
            return false;
        }
    }
}
