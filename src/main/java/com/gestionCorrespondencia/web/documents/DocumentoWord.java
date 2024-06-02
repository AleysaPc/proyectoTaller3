package com.gestionCorrespondencia.web.documents;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocumentoWord {

    public void generarDocumento() throws InvalidFormatException {
        try {
            // Crear un nuevo documento Word
            XWPFDocument document = new XWPFDocument();

            // Agregar contenido al documento
            agregarContenido(document);

            // Guardar el documento en un archivo
            String filePath = "src/main/resources/static/documento.docx";
            FileOutputStream out = new FileOutputStream(filePath);
            document.write(out);
            out.close();

            System.out.println("Documento Word creado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al crear el documento Word: " + e.getMessage());
        }
    }

    private void agregarContenido(XWPFDocument document) throws InvalidFormatException {

        // Membretado
        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
        XWPFParagraph headerParagraph = header.createParagraph();
        XWPFRun headerRun = headerParagraph.createRun();
        headerRun.setBold(true);

        String imagePathEncabezado = "/img/encabezado.JPG";

        try (InputStream inputStreamEncabezado = DocumentoWord.class.getResourceAsStream(imagePathEncabezado)) {
            if (inputStreamEncabezado != null) {
                XWPFRun runEncabezado = headerParagraph.createRun();

                // Añadir la imagen al documento con el ancho de la página
                runEncabezado.addPicture(inputStreamEncabezado, Document.PICTURE_TYPE_JPEG, imagePathEncabezado, Units.toEMU(470), Units.toEMU(50)); // Ajusta la altura según sea necesario
            } else {
                System.out.println("Error: No se pudo encontrar la imagen en la ruta especificada.");
            }
        } catch (IOException e) {
            System.out.println("Error al agregar la imagen: " + e.getMessage());
        }

        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fechaActual.format(formatter);

        // Agregar la ubicación y fecha
        XWPFParagraph ubicacionFechaParagraph = document.createParagraph();
        ubicacionFechaParagraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun ubicacionFechaRun = ubicacionFechaParagraph.createRun();
        ubicacionFechaRun.setFontFamily("Verdana");
        ubicacionFechaRun.setBold(true);
        ubicacionFechaRun.addBreak();
        ubicacionFechaRun.setText("La Paz, " + fechaFormateada);
        ubicacionFechaRun.addBreak();
        ubicacionFechaRun.setText("CITE: ISAF/R-S/001/24");
        ubicacionFechaRun.addBreak();
        ubicacionFechaRun.addBreak();

        /*
        // Agregar el CITE
        XWPFParagraph citeParagraph = document.createParagraph();
        XWPFRun citeRun = citeParagraph.createRun();
        citeRun.setBold(true);
        citeRun.setFontFamily("Verdana");
        citeRun.setText("CITE: ISAF/R-S/001/24");
        citeRun.addBreak();
        citeRun.addBreak();
         */
        //Destinatario
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run1 = paragraph.createRun();
        run1.setFontFamily("Verdana");
        run1.setText("Señor");
        run1.addBreak();

        // Agregar el texto "Ing. Manuel Cardenas Suarez" en Verdana sin negritas
        XWPFRun run2 = paragraph.createRun();
        run2.setFontFamily("Verdana");
        run2.setText("Ing. Manuel Cardenas Suarez");
        run2.addBreak();

        // Agregar el texto "GERENTE GENERAL" en negritas y Verdana
        XWPFRun run3 = paragraph.createRun();
        run3.setFontFamily("Verdana"); // Establecer la fuente
        run3.setBold(true); // Establecer negritas
        run3.setText("GERENTE GENERAL");
        run3.addBreak();

        // Agregar el texto "ENDE SERVICIOS Y CONSTRUCCIONES S.A." en negritas y Verdana
        XWPFRun run4 = paragraph.createRun();
        run4.setFontFamily("Verdana"); // Establecer la fuente
        run4.setBold(true); // Establecer negritas
        run4.setText("ENDE SERVICIOS Y CONSTRUCCIONES S.A.");
        run4.addBreak();

        // Agregar el texto "Presente.-" en Verdana sin negritas
        XWPFRun run5 = paragraph.createRun();
        run5.setFontFamily("Verdana");
        run5.setText("Presente.-");
        run5.addBreak();

        // Agregar la referencia
        XWPFParagraph refParagraph = document.createParagraph();
        XWPFRun refRun = refParagraph.createRun();
        refParagraph.setAlignment(ParagraphAlignment.RIGHT);
        refRun.setBold(true);
        refRun.setFontFamily("Verdana");
        refRun.setText("Ref.: SOLICITUD DE PERMISO");
        refRun.addBreak();

        // Agregar el saludo inicial
        XWPFParagraph saludoParagraph = document.createParagraph();
        XWPFRun saludoRun = saludoParagraph.createRun();
        saludoRun.setFontFamily("Verdana");
        saludoRun.setText("De nuestra mayor consideración:");
        saludoRun.addBreak();

        // Agregar el cuerpo de la carta
        XWPFParagraph cuerpoParagraph = document.createParagraph();
        XWPFRun cuerpoRun = cuerpoParagraph.createRun();
        cuerpoRun.setFontFamily("Verdana");
        cuerpoRun.setText("A través de la presente nos dirigimos a su distinguida autoridad, a razón de solicitar permiso para el Sr. Osvaldo………………");
        cuerpoRun.addBreak();
        cuerpoRun.addBreak();
        cuerpoRun.addBreak();
        cuerpoRun.addBreak();
        cuerpoRun.addBreak();

        // Agregar el cierre
        XWPFParagraph cierreParagraph = document.createParagraph();
        XWPFRun cierreRun = cierreParagraph.createRun();
        cierreRun.setFontFamily("Verdana");
        cierreRun.setText("Sin otro particular nos despedimos con las consideraciones más distinguidas.");
        cierreRun.addBreak();

        // Agregar la despedida
        XWPFParagraph despedidaParagraph = document.createParagraph();
        XWPFRun despedidaRun = despedidaParagraph.createRun();
        despedidaRun.setBold(true);
        despedidaRun.setFontFamily("Verdana");
        despedidaRun.setText("Atentamente,");
        despedidaRun.addBreak();
        despedidaRun.addBreak();
        despedidaRun.addBreak();
        despedidaRun.addBreak();

        // Agregar la firma
        XWPFParagraph firmaParagraph = document.createParagraph();
        XWPFRun firmaRun = firmaParagraph.createRun();
        firmaParagraph.setAlignment(ParagraphAlignment.CENTER);
        firmaRun.setBold(true);
        firmaRun.setFontFamily("Verdana");
        firmaRun.setText(" P' DIRECTORIO");

        XWPFFooter footer = document.createFooter(HeaderFooterType.DEFAULT);
        XWPFParagraph footerParagraph = footer.createParagraph();

        // Insertar la imagen en el pie de página
        String imagePathPie = "/img/footer.JPG"; // Ruta de la imagen del pie de página
        try (InputStream inputStreamPie = DocumentoWord.class.getResourceAsStream(imagePathPie)) {
            if (inputStreamPie != null) {
                XWPFRun runPie = footerParagraph.createRun();

                // Añadir la imagen al pie de página
                runPie.addPicture(inputStreamPie, Document.PICTURE_TYPE_JPEG, imagePathPie, Units.toEMU(470), Units.toEMU(50)); // Ajusta el ancho y la altura según sea necesario
            } else {
                System.out.println("Error: No se pudo encontrar la imagen del pie de página en la ruta especificada.");
            }
        } catch (IOException e) {
            System.out.println("Error al agregar la imagen del pie de página: " + e.getMessage());
        }
    }
}
