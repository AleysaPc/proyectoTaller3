package com.gestionCorrespondencia.web.documents;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class DocumentoWord {

    public void generarDocumento() {
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

    private void agregarContenido(XWPFDocument document) {
        // Agregar un párrafo al documento
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("Este es un documento Word generado desde Java.");

        // Agregar una imagen al documento (requiere la ruta de la imagen)
        // Para agregar una imagen, necesitas tener un archivo de imagen en tu sistema
        // y proporcionar la ruta del archivo de imagen.
        // Por ejemplo:
        // String imagePath = "path/to/image.jpg";
        // try {
        //     FileInputStream inputStream = new FileInputStream(imagePath);
        //     run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_JPEG, imagePath, Units.toEMU(200), Units.toEMU(200));
        //     inputStream.close();
        // } catch (IOException e) {
        //     System.out.println("Error al agregar la imagen: " + e.getMessage());
        // }
        // Agregar una tabla al documento
        // XWPFTable table = document.createTable();
        // Agrega filas y celdas a la tabla según sea necesario
        // Puedes agregar más contenido como tablas, listas, etc., según tus necesidades
    }

}
