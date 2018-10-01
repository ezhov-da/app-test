package ru.ezhov.test.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

//https://stackoverflow.com/questions/23326562/apache-pdfbox-convert-pdf-to-images/23327024#23327024
public class PdfImage {
    public static void main(String[] args) throws Exception {
        PDDocument document =
                PDDocument.load(new File("D:\\programmer\\books\\in-library\\docker-Docker cookbook.pdf"));
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        if (document.getNumberOfPages() > 0) {
            BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 100, ImageType.RGB);

            // suffix in filename will be used as the file format
            ImageIO.write(bim, "png", new File("pdf-image.png"));
        }
        document.close();
    }
}
