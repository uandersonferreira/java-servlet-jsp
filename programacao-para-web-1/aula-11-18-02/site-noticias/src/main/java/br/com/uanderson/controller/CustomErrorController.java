package br.com.uanderson.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode != null) {
            if (statusCode >= 100 && statusCode < 200) {
                return "errors/100";
            } else if (statusCode >= 200 && statusCode < 300) {
                return "errors/200";
            } else if (statusCode >= 300 && statusCode < 400) {
                return "errors/300";
            } else if (statusCode >= 400 && statusCode < 500) {
                return "errors/400";
            } else if (statusCode >= 500) {
                return "errors/500";
            }
        }
        return "errors/500"; // Página padrão para erros desconhecidos
    }
}
