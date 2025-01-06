package br.com.uanderson.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class NotaReader {
    public static Nota readNota(String xmlPath) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Nota.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Nota) unmarshaller.unmarshal(new File(xmlPath));
    }
}

