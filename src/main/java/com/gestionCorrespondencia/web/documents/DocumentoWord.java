package com.gestionCorrespondencia.web.documents;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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

        //Memebretado
        XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
        XWPFParagraph headerParagraph = header.createParagraph();
        XWPFRun headerRun = headerParagraph.createRun();
        headerRun.setBold(true);

        String imagePathLogo = "/img/logoIsaf.jpg";
        String imagePathEncabezado = "/img/encabezado.JPG";
        
        
        try (InputStream inputStreamEncabezado = DocumentoWord.class.getResourceAsStream(imagePathEncabezado)) {
            if (inputStreamEncabezado != null) {
                XWPFRun runEncabezado = headerParagraph.createRun();
                runEncabezado.addPicture(inputStreamEncabezado, Document.PICTURE_TYPE_JPEG, imagePathEncabezado, Units.toEMU(400), Units.toEMU(50));
            } else {
                System.out.println("Error: No se pudo encontrar la imagen en la ruta especificada.");
            }
        } catch (IOException e) {
            System.out.println("Error al agregar la imagen: " + e.getMessage());
        }
        
        
        // Agregar primera imagen (logo)
        try (InputStream inputStreamLogo = DocumentoWord.class.getResourceAsStream(imagePathLogo)) {
            if (inputStreamLogo != null) {
                XWPFRun runLogo = headerParagraph.createRun();
                runLogo.addPicture(inputStreamLogo, Document.PICTURE_TYPE_JPEG, imagePathLogo, Units.toEMU(50), Units.toEMU(50));
            } else {
                System.out.println("Error: No se pudo encontrar la imagen en la ruta especificada.");
            }
        } catch (IOException e) {
            System.out.println("Error al agregar la imagen: " + e.getMessage());
        }

        // Agregar segunda imagen (encabezado)
       
        

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
