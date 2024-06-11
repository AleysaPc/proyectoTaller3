package com.gestionCorrespondencia.web;

import com.gestionCorrespondencia.dao.RegistroDao;
import com.gestionCorrespondencia.domain.Registro;
import com.gestionCorrespondencia.service.RegistroService;
import com.gestionCorrespondencia.domain.Enviados;
import com.gestionCorrespondencia.service.EnviadoService;
import com.gestionCorrespondencia.service.EnviadosRepository;
import com.gestionCorrespondencia.web.documents.DocumentoWord;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.gestionCorrespondencia.dao.TasksDao;
import com.gestionCorrespondencia.domain.Tasks;
import com.gestionCorrespondencia.service.TasksService;
import java.util.Locale;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@Slf4j
public class Controlador {

    @Autowired
    private RegistroService registroService;
    @Autowired
    private EnviadoService enviadoService;
    private RegistroDao registroDao;
    private final List<Enviados> documentos = new ArrayList<>();

    @Autowired
    private EnviadosRepository enviadosRepository;

    @Autowired
    private TasksService tasksService;

    @GetMapping("/")
    public String inicio(Model model, @AuthenticationPrincipal User user) {

        var registros = registroService.listarRegistro();
        var envios = enviadoService.listarEnvios();

        //contador
        int totalregistros = registros.size();
        int totalenvios = envios.size();

        var registroTotal = 0;
        for (var p : registros) {
            registroTotal++;
        }

        var envioTotal = 0;
        for (var c : envios) {
            envioTotal++;
        }

        var numeroRegistro = "R-" + registroTotal + "/2024";
        var numeroCite = "CITE: ISAF/ADM/" + envioTotal + "/2024";

        log.info("Ejecutando el controlador");
        log.info("Usuario que hizo login" + user);
        model.addAttribute("registros", registros);
        model.addAttribute("registroTotal", registroTotal);
        model.addAttribute("numeroRegistro", numeroRegistro);
        model.addAttribute("numeroCite", numeroCite);

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

    @PostMapping("/subirDocumento")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            @RequestParam("fecha") String fecha,
            @RequestParam("numcite") String numcite,
            @RequestParam("remitente") String remitente,
            @RequestParam("destinatario") String destinatario,
            @RequestParam("institucion") String institucion,
            @RequestParam("referencia") String referencia,
            @RequestParam("estado") String estado,
            RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por favor seleccione un archivo para subir");
            return "redirect:/enviados";
        }

        try {
            // Guarda el archivo en la base de datos
            Enviados documento = new Enviados();
            documento.setFecha(LocalDate.parse(fecha)); // Asegúrate de convertir la fecha correctamente
            documento.setNumcite(numcite);
            documento.setRemitente(remitente);
            documento.setDestinatario(destinatario);
            documento.setInstitucion(institucion);
            documento.setReferencia(referencia);
            documento.setEstado(estado);
            documento.setNota(estado);
            documento.setContenido(file.getBytes()); // Asegúrate de almacenar el contenido del archivo

            enviadosRepository.save(documento);

            redirectAttributes.addFlashAttribute("message", "Archivo subido exitosamente: " + file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Error al subir el archivo: " + file.getOriginalFilename());
        }

        return "redirect:/enviados";
    }

    @GetMapping("/verDocumento/{iddocumento}")
    public ResponseEntity<Resource> verDocumento(@PathVariable Long iddocumento) {
        Enviados documento = enviadosRepository.findById(iddocumento).orElse(null);
        if (documento == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 si el documento no se encuentra
        }

        ByteArrayResource resource = new ByteArrayResource(documento.getContenido());

        String fileName = documento.getFecha() + "_Cite" + documento.getNumcite() + ".pdf";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");
        headers.setContentType(MediaType.APPLICATION_PDF);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @GetMapping("/enviados")
    public String documentosEnviados(Model model) {
        List<Enviados> documentos = enviadosRepository.findAll();
        model.addAttribute("documentos", documentos);
        return "layout/enviados";
    }

    @GetMapping("/registrosEnviados")
    public String registrosEnviados(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Enviados> enviados = enviadoService.listarEnvios();
        if (keyword != null && !keyword.isEmpty()) {
            enviados = enviados.stream()
                    .filter(enviado -> enviado.getEstado().contains(keyword)
                    || enviado.getNumcite().contains(keyword)
                    || enviado.getRemitente().contains(keyword)
                    || enviado.getDestinatario().contains(keyword)
                    || enviado.getInstitucion().contains(keyword)
                    || enviado.getReferencia().contains(keyword)
                    || (enviado.getNota() != null && enviado.getNota().contains(keyword))
                    )
                    .collect(Collectors.toList());
        }
        model.addAttribute("enviados", enviados);
        model.addAttribute("keyword", keyword);
        return "layout/enviados";
    }

    @GetMapping("/eliminarDocumento/{iddocumento}")
    public String eliminarDocumento(@PathVariable Long iddocumento) {
        // Eliminar el documento de la base de datos
        enviadosRepository.deleteById(iddocumento);
        return "redirect:/enviados";
    }

    @GetMapping("/contactos")
    public String listaContactos() {
        return "layout/contactos";
    }

    @GetMapping("/personal")
    public String personalInstitucional() {
        return "layout/personal";
    }

    @GetMapping("crearDocumento")
    public String mostrarDocumentos() {
        return "layout/crearDocumento";
    }

    @GetMapping("/descargarDocumento")
    public ResponseEntity<Resource> descargarDocumento() throws InvalidFormatException {
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

    //Tareas
    @GetMapping("/agregarTarea")
    public String agregarTarea(Model model) {
        // Obtener la lista de tareas pendientes
        List<Tasks> listaTareas = tasksService.listarTareasPendientes();
        // Pasar la lista de tareas al modelo
        model.addAttribute("tasks", listaTareas);
        return "Tasks"; // Devolver la vista
    }

    @PostMapping("/guardarTarea")
    public String guardarTarea(Tasks tasks) {
        tasksService.guardar(tasks);
        return "redirect:/";
    }

    @GetMapping("/editarTarea/{idtasks}")
    public String editarTarea(@PathVariable("idtasks") Long idtasks, Model model) {
        Tasks tasks = tasksService.encontrarTarea(idtasks);
        model.addAttribute("tasks", tasks);
        return "layout/editarTarea"; // Devolver el nombre de la vista de edición
    }

    @GetMapping("/eliminarTarea/{idtasks}")
    public String eliminarTarea(@PathVariable("idtasks") Long idtasks) {
        tasksService.eliminar(idtasks);
        return "redirect:/"; // Opcional: redirige a la página principal después de eliminar la tarea
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return result.getFieldError().getDefaultMessage();
    }

    @GetMapping("almacenArchivos")
    public String almacenArcivos(){
        return "layout/almacenArchivos";
    }
    

}
