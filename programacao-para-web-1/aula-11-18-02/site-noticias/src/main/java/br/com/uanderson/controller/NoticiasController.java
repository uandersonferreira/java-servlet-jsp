package br.com.uanderson.controller;

import br.com.uanderson.model.Noticia;
import br.com.uanderson.model.Reporter;
import br.com.uanderson.repository.NoticiaDao;
import br.com.uanderson.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/noticias")
public class NoticiasController {
    private final Logger log = Logger.getLogger(String.valueOf(NoticiasController.class));
    private final NoticiaDao noticiaDao;
    private final AuthenticationService authenticationService;

    public NoticiasController(NoticiaDao noticiaDao, AuthenticationService authenticationService) {
        this.noticiaDao = noticiaDao;
        this.authenticationService = authenticationService;
    }


    // Exibe os detalhes de uma notícia específica
    @GetMapping("/{id}")
    public ModelAndView verNoticia(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("index");
        Noticia noticia = noticiaDao.findById(id);
        if (noticia == null) {
            mv.setViewName("redirect:/");
            return mv;
        }
        mv.addObject("noticia", noticia);
        mv.addObject("noticias", noticiaDao.listAll());
        return mv;
    }

    // Exibe o formulário de criação de notícia
    @GetMapping("/admin/nova")
    public ModelAndView formularioNovaNoticia(HttpSession session) {
        if (!authenticationService.isLoggedIn(session)) {
            return new ModelAndView("redirect:/reporters/login");
        }
        return new ModelAndView("noticias/cadastrarView");
    }

    // Processa a criação de uma nova notícia
    @PostMapping("/admin/nova")
    public ModelAndView criarNoticia(
            @RequestParam String titulo,
            @RequestParam String lide,
            @RequestParam String corpo,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!authenticationService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para criar uma notícia");
            return new ModelAndView("redirect:/reporters/login");
        }

        // Valida os dados da notícia
        if (titulo == null || titulo.isEmpty() || lide == null || lide.isEmpty() || corpo == null || corpo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos os campos são obrigatórios");
            return new ModelAndView("redirect:/noticias/nova");
        }

        //validar tamanho do titulo que deve ser menor que 255
        if (titulo.length() > 255) {
            redirectAttributes.addFlashAttribute("error", "O título deve ter no máximo 255 caracteres");
            return new ModelAndView("redirect:/noticias/admin/nova");
        }

        // Cria uma nova notícia com o autor atual
        Noticia noticiaForSave = new Noticia(null, titulo, lide, corpo, LocalDateTime.now(), authenticationService.getLoggedReporter(session));
        Noticia noticiaSalva = noticiaDao.save(noticiaForSave);

        if (noticiaSalva.getId() != null) {
            redirectAttributes.addFlashAttribute("success", "Notícia criada com sucesso!");
            return new ModelAndView("redirect:/noticias/" + noticiaSalva.getId());
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao criar notícia");
            return new ModelAndView("redirect:/noticias/nova");
        }
    }

    // Exibe o formulário de edição de notícia
    @GetMapping("/admin/editar/{id}")
    public ModelAndView formularioEditarNoticia(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!authenticationService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para editar uma notícia");
            return new ModelAndView("redirect:/reporters/login");
        }

        Noticia noticia = noticiaDao.findById(id);
        if (noticia == null) {
            redirectAttributes.addFlashAttribute("error", "Notícia não encontrada");
            return new ModelAndView("redirect:/noticias");
        }

        Reporter currentReporter = authenticationService.getLoggedReporter(session);
        if (!noticia.getReporter().getId().equals(currentReporter.getId())) {
            redirectAttributes.addFlashAttribute("error", "Você não tem permissão para editar esta notícia");
            return new ModelAndView("redirect:/noticias");
        }

        ModelAndView mv = new ModelAndView("noticias/cadastrarView");
        mv.addObject("noticia", noticia);
        return mv;
    }

    // Processa a atualização de uma notícia
    @PostMapping("/admin/editar/{id}")
    public ModelAndView atualizarNoticia(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String lide,
            @RequestParam String corpo,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!authenticationService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para editar uma notícia");
            return new ModelAndView("redirect:/reporters/login");
        }

        Noticia noticia = noticiaDao.findById(id);
        if (noticia == null) {
            redirectAttributes.addFlashAttribute("error", "Notícia não encontrada");
            return new ModelAndView("redirect:/noticias");
        }
        // Valida os dados da notícia
        if (titulo == null || titulo.isEmpty() || lide == null || lide.isEmpty() || corpo == null || corpo.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Todos os campos são obrigatórios");
            return new ModelAndView("redirect:/noticias/" + id + "/editar");
        }

        // Atualiza os dados da notícia
        noticia.setTitulo(titulo);
        noticia.setLide(lide);
        noticia.setCorpoNoticia(corpo);
        noticia.setDataPublicacao(LocalDateTime.now());

        // Salva a notícia no banco de dados
        boolean success = noticiaDao.update(noticia);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Notícia atualizada com sucesso!");
            return new ModelAndView("redirect:/noticias/" + id);
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar notícia");
            return new ModelAndView("redirect:/noticias/" + id + "/editar");
        }
    }

    // Processa a exclusão de uma notícia
    @GetMapping("/admin/deletar/{id}")
    public ModelAndView deletarNoticia(
            @PathVariable Long id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        if (!authenticationService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para deletar uma notícia");
            return new ModelAndView("redirect:/reporters/login");
        }

        boolean success = noticiaDao.deleteById(id);

        if (success) {
            redirectAttributes.addFlashAttribute("success", "Notícia deletada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar notícia");
        }

        return new ModelAndView("redirect:/");
    }

    // Lista notícias de um repórter específico
    @GetMapping("/admin/reporter/{reporterId}")
    public ModelAndView listarNoticiasPorReporter(@PathVariable Long reporterId,
                                                  HttpSession session,
                                                  RedirectAttributes redirectAttributes) {
        if (!authenticationService.isLoggedIn(session)) {
            redirectAttributes.addFlashAttribute("error", "Você precisa estar logado para deletar uma notícia");
            return new ModelAndView("redirect:/reporters/login");
        }

        ModelAndView mv = new ModelAndView("noticias/noticiasView");

        Reporter currentReporter = (Reporter) session.getAttribute("reporterLogado");
        if (!currentReporter.getId().equals(reporterId)) {
            redirectAttributes.addFlashAttribute("error", "Você não tem permissão para Acessar esses noticias");
            return new ModelAndView("redirect:/reporters/login");
        }

        List<Noticia> noticias = noticiaDao.findByReporterId(reporterId);
        mv.addObject("noticias", noticias);
        return mv;
    }
}