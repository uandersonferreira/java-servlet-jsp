package br.com.uanderson.controller;

import br.com.uanderson.model.Noticia;
import br.com.uanderson.repository.NoticiaDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final NoticiaDao noticiaDao;

    public HomeController(NoticiaDao noticiaDao) {
        this.noticiaDao = noticiaDao;
    }

    @GetMapping
    public String home(Model model) {
        List<Noticia> noticias = noticiaDao.listAll();
        model.addAttribute("noticias", noticias);
        return "index";
    }

}
