package com.bussinestracking.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
public class MongoDBConnection {
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDBConnection(String url, String nombredb) {
        mongoClient = MongoClients.create(url);
        database = mongoClient.getDatabase(nombredb);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        mongoClient.close();
    }

}
