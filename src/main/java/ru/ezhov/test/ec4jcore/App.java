package ru.ezhov.test.ec4jcore;

import org.ec4j.core.PropertyTypeRegistry;
import org.ec4j.core.Resource;
import org.ec4j.core.model.EditorConfig;
import org.ec4j.core.model.Property;
import org.ec4j.core.model.PropertyType;
import org.ec4j.core.model.Version;
import org.ec4j.core.parser.EditorConfigModelHandler;
import org.ec4j.core.parser.EditorConfigParser;
import org.ec4j.core.parser.ErrorHandler;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        try {
            java.nio.file.Path editorConfigFile = Paths.get("D:/repository/work/smstore-site-ccc/node-app/node_modules/define-properties/.editorconfig");
//            java.nio.file.Path editorConfigFile = Paths.get("D:/repository/work/smstore-site-ccc");
            EditorConfigParser parser = EditorConfigParser.builder().build();
            EditorConfigModelHandler handler = new EditorConfigModelHandler(PropertyTypeRegistry.default_(), Version.CURRENT);
            parser.parse(Resource.Resources.ofPath(editorConfigFile, StandardCharsets.UTF_8), handler, ErrorHandler.THROWING);
            EditorConfig editorConfig = handler.getEditorConfig();
            System.out.println(editorConfig.toString());

            Property property = editorConfig.getSections().get(0).getProperties().get("end_of_line");
            PropertyType.EndOfLineValue endOfLineValue = property.getValueAs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
