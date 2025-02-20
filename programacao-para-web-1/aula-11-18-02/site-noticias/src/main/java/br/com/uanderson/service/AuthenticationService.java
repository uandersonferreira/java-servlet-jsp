package br.com.uanderson.service;


import br.com.uanderson.model.Reporter;
import br.com.uanderson.repository.ReporterDao;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AuthenticationService {
    private final Logger log = Logger.getLogger(String.valueOf(AuthenticationService.class));
    private final ReporterDao reporterDao;

    public AuthenticationService(ReporterDao reporterDao) {
        this.reporterDao = reporterDao;
    }

    public Reporter authenticate(String login, String senha) {
        Reporter reporter = reporterDao.findByLogin(login);
        if (reporter != null && reporter.getSenha().equals(senha) && reporter.isAtivo()) {
            log.info(String.format("Repórter '%s' autenticado com sucesso", reporter.getNome()));
            return reporter;
        }
        log.info("Falha na autenticação: usuário ou senha inválidos");
        return null;
    }

    public void logout(HttpSession session) {
        Reporter reporter = (Reporter) session.getAttribute("reporterLogado");
        if (reporter != null) {
            log.info(String.format("Repórter '%s' deslogado com sucesso", reporter.getNome()));
            session.removeAttribute("reporterLogado");
            session.invalidate();
        }
    }

    public Reporter getLoggedReporter(HttpSession session) {
        return (Reporter) session.getAttribute("reporterLogado");
    }

    public boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("reporterLogado") != null;
    }
}