package ru.ezhov.test.webdav;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import java.util.List;

public class Application {
    public static void main(String[] args) throws Exception {
        String user = args[0];
        String pass = args[1];
        String rootDirectory = args[2];

        Sardine sardine = SardineFactory.begin(user, pass);
        List<DavResource> resources = sardine.list(rootDirectory + "/test/");
        for (DavResource res : resources) {
            System.out.println(res);
        }

        sardine.delete(rootDirectory + "/test/");
    }
}
