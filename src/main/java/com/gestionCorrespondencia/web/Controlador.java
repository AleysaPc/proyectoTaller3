package com.gestionCorrespondencia.web;

import com.gestionCorrespondencia.dao.RegistroDao;
import com.gestionCorrespondencia.domain.Registro;
import com.gestionCorrespondencia.service.RegistroService;
import com.gestionCorrespondencia.web.documents.DocumentoWord;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        model.addAttribute("totalRegistros", registros.size());

        return "index";
    }

    @GetMapping("/todosLosRegistros")
    public String todosLosRegistros(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
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
        return "todosLosRegistros";
    }

    @GetMapping("crearDocumento")
    public String mostrarDocumentos() {
        return "layout/crearDocumento";
    }

    @GetMapping("/descargarDocumento")
    public ResponseEntity<Resource> descargarDocumento() {
        // Generar el documento Word
        DocumentoWord documento = new DocumentoWord();
        documento.generarDocumento();

        // Obtener la ruta del archivo generado
        String filePath = "src/main/resources/static/documento.docx";

        // Crear un recurso a partir del archivo
        Resource file = new FileSystemResource(filePath);

        // Construir la respuesta HTTP para enviar el archivo al navegador
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .body(file);
    }

    @GetMapping("/agregar") //Get Obtener informacion
    public String agregar(Registro registro
    ) {
        return "modificar";
    }

    @PostMapping("/guardar") //Post Enviar informacion al servidor
    public String guardar(@Valid Registro registro, Errors errors
    ) {

        if (errors.hasErrors()) {
            return "modificar";
        }
        registroService.guardar(registro);
        return "redirect:/";
    }

    @GetMapping("/editar/{idregistro}")
    public String editar(Registro registro, Model model
    ) {
        registro = registroService.encontrarRegistro(registro);
        model.addAttribute("registro", registro);
        return "modificar";
    }

    @GetMapping("/eliminar/{idregistro}")
    public String eliminar(Registro registro
    ) {
        registroService.eliminar(registro);
        return "redirect:/";
    }
}
