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

        
        log.info("Ejecutando el controlador");
        log.info("Usuario que hizo login" + user);
        model.addAttribute("registros", registros);

        var registroTotal = 0;
        for (var p : registros) {
            registroTotal += p.getTotalrecibidas();
        }

        model.addAttribute("registroTotal", registroTotal);
        model.addAttribute("totalRegistros", registros.size());
        
        return "index";

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
