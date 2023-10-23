package org.so;

import org.apache.poi.ooxml.util.DocumentHelper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.XmlObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static final File destFile = new File("template-OUTPUT.docx");

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Begin");
        Main main = new Main();
        main.myFunc(List.of(new MyObject("A", "2", 2)));
    }

    private void replaceTextFor(XWPFDocument doc, Map<String, String> map) {
        doc.getParagraphs().forEach(p -> p.getRuns().forEach(run -> {
            String text = run.text();
            map.forEach((findText, replaceText) -> {
                if (text.contains(findText)) {
                    run.setText(text.replace(findText, replaceText), 0);
                }
            });
        }));
    }

    public void myFunc(List<MyObject> codeList) throws IOException, URISyntaxException {
        String resourcePath = "template-ORG.docx";
        Path templatePath = Paths.get(Objects.requireNonNull(DocumentHelper.class.getClassLoader().getResource(resourcePath)).toURI());

        XWPFDocument doc = new XWPFDocument(Files.newInputStream(templatePath));
        for (MyObject current : codeList) {
            String currentTitlePlaceholder = "#position";
            String currentFieldCodePlaceholder = "#firstName";
            Map<String, String> map = new HashMap<>();
            map.put(currentTitlePlaceholder, current.getTitle());
            map.put(currentFieldCodePlaceholder, current.getCode());
            replaceTextFor(doc, map);

            FileOutputStream out = new FileOutputStream(destFile);
            doc.write(out);
            out.close();
            doc.close();
            System.out.println("Saved!");

        }
    }
}