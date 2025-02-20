package br.com.uanderson.controller;

import br.com.uanderson.model.Reporter;
import br.com.uanderson.repository.ReporterDao;
import br.com.uanderson.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/reporters")
public class ReporterController {
    private final Logger log = Logger.getLogger(String.valueOf(ReporterController.class));
    private final ReporterDao reporterDao;
    private final AuthenticationService authenticationService;

    public ReporterController(ReporterDao reporterDao, AuthenticationService authenticationService) {
        this.reporterDao = reporterDao;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/registro")
    public String exibirFormularioRegistro() {
        return "reporters/registroView"; // página jsp para registro de repórter
    }

    @PostMapping("/registro")
    public String cadastrarReporter(@ModelAttribute Reporter reporter, RedirectAttributes redirectAttributes) {

        if (reporter.getNome() == null || reporter.getNome().trim().isEmpty() ||
                reporter.getLogin() == null || reporter.getLogin().trim().isEmpty() ||
                reporter.getSenha() == null || reporter.getSenha().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Preencha todos os campos obrigatórios.");
            return "redirect:/reporters/registro";
        }

        // Verificar se já existe algum repórter com o mesmo login
        Reporter existente = reporterDao.findByLogin(reporter.getLogin());
        if (existente != null) {
            log.info(String.format("Tentativa de cadastro com login '%s' já existente", reporter.getLogin()));
            redirectAttributes.addFlashAttribute("error", "Login já existente. Escolha outro.");
            return "redirect:/reporters/registro"; //endpoint
        }

        reporterDao.save(reporter);
        log.info(String.format("Repórter '%s' cadastrado com sucesso", reporter.getNome()));
        redirectAttributes.addFlashAttribute("success", "Cadastro realizado com sucesso!");
        return "redirect:/reporters/login"; //endpoint de login
    }

    @GetMapping("/login")
    public String exibirFormularioLogin() {
        return "reporters/loginView"; // PAGINA DE LOGIN JSP
    }

    @PostMapping("/login")
    public String login(@RequestParam("login") String login,
                        @RequestParam("senha") String senha,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Preencha login e senha.");
            return "redirect:/reporters/login";
        }

        // Verifica se já existe uma sessão ativa
        if (authenticationService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Já existe uma sessão ativa. Faça logout primeiro.");
            return "redirect:/reporters/login";
        }

        Reporter reporter = authenticationService.authenticate(login, senha);
        if (reporter != null) {
            session.setMaxInactiveInterval(1800); // 30 minutos duração da sessão
            session.setAttribute("reporterLogado", reporter);
            redirectAttributes.addFlashAttribute("success", "Login realizado com sucesso!");
            log.info(String.format("Repórter '%s' logado com sucesso", reporter.getNome()));
            return "redirect:/reporters/admin/listar";
        } else {
            redirectAttributes.addFlashAttribute("error", "Login ou senha incorretos");
            return "redirect:/reporters/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        if (authenticationService.isLoggedIn(session)) {
            authenticationService.logout(session);
            redirectAttributes.addFlashAttribute("success", "Logout realizado com sucesso!");
        }
        return "redirect:/reporters/login";
    }
    @GetMapping("/admin/perfil/{id}")
    public ModelAndView verPerfil(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Reporter reporterSalvo = reporterDao.findById(id);

        if (reporterSalvo == null) {
            redirectAttributes.addFlashAttribute("error", "Repórter não encontrado");
            return new ModelAndView("redirect:/reporters/admin/listar");
        }

        if (!estaLogado(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para acessar este recurso");
            return new ModelAndView("redirect:/reporters/login");
        }
        ModelAndView mv = new ModelAndView("reporters/perfilView");
        mv.addObject("reporter", reporterSalvo);
        return mv;
    }

    @PostMapping("/admin/perfil")
    public String atualizarPerfilReporter(@ModelAttribute Reporter reporter,
                                          HttpSession session,
                                          RedirectAttributes redirectAttributes) {

        // Verificar se o repórter está logado e se os dados foram preenchidos corretamente
        if (!estaLogado(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para atualizar seu perfil");
            return "redirect:/reporters/login";
        }

        // Validar os dados do repórter se não são nulos ou vazios
        if (reporter.getNome() == null || reporter.getNome().isEmpty() || reporter.getSenha() == null || reporter.getSenha().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos os campos são obrigatórios");
            return String.format("redirect:/reporters/admin/perfil/%s", reporter.getId());
        }

        Reporter currentReporter = (Reporter) session.getAttribute("reporterLogado");
        if (!currentReporter.getId().equals(reporter.getId())) {
            redirectAttributes.addFlashAttribute("error", "Você não tem permissão para Editar este perfil");
            return "redirect:/reporters/admin/listar";
        }

        currentReporter.setNome(reporter.getNome());
        currentReporter.setSenha(reporter.getSenha());

        reporterDao.update(currentReporter);
        session.setAttribute("reporterLogado", currentReporter);
        log.info(String.format("Perfil do repórter '%s' atualizado com sucesso", currentReporter.getNome()));
        redirectAttributes.addFlashAttribute("success", "Perfil atualizado com sucesso!");
        return "redirect:/reporters/admin/listar";
    }

    @GetMapping("/admin/listar")
    public ModelAndView listarReporters(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!estaLogado(session)) {
            redirectAttributes.addFlashAttribute("error", "Acesso restrito. Faça o Login para acessar esta página");
            return new ModelAndView("redirect:/reporters/login");
        }
        List<Reporter> reporters = reporterDao.listAll();
        ModelAndView mv = new ModelAndView("reporters/listarView");
        mv.addObject("reporters", reporters);
        return mv;
    }

    @GetMapping("/admin/excluir/{id}")
    public String excluirReporter(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!estaLogado(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para acessar este recurso");
            return "redirect:/reporters/login";
        }
        Reporter reporterSalvo = reporterDao.findById(id);
        if (reporterSalvo == null) {
            redirectAttributes.addFlashAttribute("error", "Repórter não encontrado");
            return "redirect:/reporters/admin/listar";
        }
        reporterDao.deleteById(reporterSalvo.getId());
        log.info(String.format("Repórter '%s' excluído com sucesso", reporterSalvo.getNome()));
        redirectAttributes.addFlashAttribute("success", "Repórter excluído com sucesso!");

        if (authenticationService.isLoggedIn(session)) {
            authenticationService.logout(session);
        }
        return "redirect:/reporters/login";
    }

    private boolean estaLogado(HttpSession session) {
        return session.getAttribute("reporterLogado") != null;
    }
}

/*
WEB-INF/views/reporters/registroView.jsp - Formulário de registro - OK
WEB-INF/views/reporters/loginView.jsp - Formulário de login - OK
WEB-INF/views/reporters/perfilView.jsp - Página de visualização/edição de perfil
WEB-INF/views/reporters/listarView.jsp - Lista de repórteres (apenas para admin)
 */