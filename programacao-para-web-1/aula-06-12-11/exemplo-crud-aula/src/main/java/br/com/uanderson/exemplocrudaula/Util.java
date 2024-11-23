package br.com.uanderson.exemplocrudaula;

import jakarta.servlet.ServletContext;

public class Util {
    public static int proximoSerial(ServletContext aplicacao) {
        int serial=(int) aplicacao.getAttribute("serial");
        serial++;
        aplicacao.setAttribute("serial",serial);
        return serial;
    }
}
