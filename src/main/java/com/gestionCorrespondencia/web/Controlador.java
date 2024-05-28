package com.gestionCorrespondencia.web;

import com.gestionCorrespondencia.dao.RegistroDao;
import com.gestionCorrespondencia.domain.Registro;
import com.gestionCorrespondencia.service.RegistroService;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class Controlador {

    @Autowired
    private RegistroService registroService;
    private RegistroDao registroDao;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {

        var registros = registroService.listarRegistro();

        //Mostrar los utlimos tres registros
       int totalRegistros = registros.size();
       // List<Registro> ultimosTresRegistros = registros.subList(Math.max(totalRegistros - 3, 0), totalRegistros);

        log.info("Ejecutando el controlador");
        log.info("Usuario que hizo login" + user);
        model.addAttribute("registros", registros);
       
       //model.addAttribute("registros", ultimosTresRegistros);

        var registroTotal = 0;
        for (var p : registros) {
            registroTotal++;
        }
        LocalDate fechaActual = LocalDate.now();
        int year = fechaActual.getYear();
        String numeroRegistro = String.format("R-%02d/%d", registroTotal, year);

        model.addAttribute("registroTotal", registroTotal);
        model.addAttribute("totalRegistros", registros.size());
        model.addAttribute("numeroRegistro", numeroRegistro);
       
        return "index";

    }


    @GetMapping("/todosLosRegistros")
    public String verTodosLosRegistros(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Registro> registros = registroService.listarRegistro();

        if (keyword != null && !keyword.isEmpty()) {
            registros = registros.stream()
                    .filter(registro -> registro.getNumregistro().contains(keyword)
                    || registro.getRemitente().contains(keyword)
                    || registro.getReferencia().contains(keyword))
                    .collect(Collectors.toList());
        }

        model.addAttribute("registros", registros);
        model.addAttribute("keyword", keyword);
        return "todosLosRegistros"; // Nombre de la plantilla en src/main/resources/templates
    }

    @GetMapping("/crearDocumento")
    public String crearDocumento() {
        return "layout/crearDocumento";
    }

    @GetMapping("/agregar") //Get Obtener informacion
    public String agregar(Registro registro) {
        return "modificar";
    }

    @PostMapping("/guardar") //Post Enviar informacion al servidor
    public String guardar(@Valid Registro registro, Errors errors) {

        if (errors.hasErrors()) {
            return "modificar";
        }
        registroService.guardar(registro);
        return "redirect:/";
    }

    @GetMapping("/editar/{idregistro}")
    public String editar(Registro registro, Model model) {
        registro = registroService.encontrarRegistro(registro);
        model.addAttribute("registro", registro);
        return "modificar";
    }

    @GetMapping("/eliminar/{idregistro}")
    public String eliminar(Registro registro) {
        registroService.eliminar(registro);
        return "redirect:/";
    }
}
