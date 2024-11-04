package com.bussinestracking.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static MongoDatabase database;
    private static MongoClient mongoClient;

    public static void conexion() {
        String uri = "mongodb+srv://Tan2004:Jimena2014@cluster0.g6slc.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase("BussinesTracking");
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void close() {
        mongoClient.close();
    }
}
