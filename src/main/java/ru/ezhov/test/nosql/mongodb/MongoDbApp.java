package ru.ezhov.test.nosql.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Consumer;


//https://examples.javacodegeeks.com/software-development/mongodb/java-mongodb-query-document-example/
public class MongoDbApp {

    private static Logger log = Logger.getLogger(MongoDbApp.class);

    // Fetching all documents from the mongo collection.
    private static void getAllDocuments(MongoCollection<Document> col) {
        log.info("Fetching all documents from the collection");

        // Performing a read operation on the collection.
        FindIterable<Document> fi = col.find();
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while (cursor.hasNext()) {
                log.info(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    // Fetch a selective document from the mongo collection.
    private static void getSelectiveDocument(MongoCollection<Document> col) {
        log.info("Fetching a particular document from the collection");

        // Performing a read operation on the collection.
        String col_name = "name", srch_string = "Charlotte Neil";
        FindIterable<Document> fi = col.find(Filters.eq(col_name, srch_string));
        MongoCursor<Document> cursor = fi.iterator();
        try {
            while (cursor.hasNext()) {
                log.info(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }
    }

    private static void showCollections(ListCollectionsIterable<Document> documents) {
        Consumer<Document> consumer = (Document d) -> System.out.println(d.toJson());
        documents.forEach(consumer);
    }

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(new File("E:\\programmer\\git-private\\config\\common\\common.properties")));
            // Mongodb initialization parameters.
            int port_no = Integer.parseInt(properties.getProperty("mongodb.port"));
            String host_name = properties.getProperty("mongodb.host"), db_name = "test", db_coll_name = "paper";

            // Mongodb connection string.
            String client_url = "mongodb://" + host_name + ":" + port_no + "/" + db_name;
            MongoClientURI uri = new MongoClientURI(client_url);

            // Connecting to the mongodb server using the given client uri.
            MongoClient mongo_client = new MongoClient(uri);

            // Fetching the database from the mongodb.
            MongoDatabase db = mongo_client.getDatabase(db_name);
            ListCollectionsIterable<Document> documents = db.listCollections();
            showCollections(documents);

            // Fetching the collection from the mongodb.
            MongoCollection<Document> coll = db.getCollection(db_coll_name);

            // Fetching all the documents from the mongodb.
            getAllDocuments(coll);

            log.info("\n");

            // Fetching a single document from the mongodb based on a search_string.
            getSelectiveDocument(coll);


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}