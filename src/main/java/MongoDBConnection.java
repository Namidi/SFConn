
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MongoDBConnection {

    private final String CONNECTION_STRING = "mongodb://localhost:27017";
    private final String DATABASE_NAME = "SF";
    private MongoCollection<Document> collectionCrateWeighing;
    private MongoCollection<Document> collectionProducts;

    public MongoDBConnection () {
            com.mongodb.client.MongoClient mongoClient = com.mongodb.client.MongoClients.create(CONNECTION_STRING);
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            this.collectionCrateWeighing = database.getCollection("crateWeighing");
            this.collectionProducts = database.getCollection("products");
    }

    public void writeCrateWeighing(String writeString) {

        String slaughterNumber = writeString.substring(0, 4);
        String stockMutationCode = writeString.substring(4, 6);
        String employeeCode = writeString.substring(6, 9);
        String productCode = writeString.substring(9, 13);
        String weightKG = writeString.substring(13, 16);
        String weightGR = writeString.substring(16, 18);
        String date = writeString.substring(18, 24);
        String time = writeString.substring(24, 30);
        String weighingScaleID = writeString.substring(30, 31);

        Document document = new Document("slaughterNumber", slaughterNumber)
                .append("stockMutationCode", stockMutationCode)
                .append("employeeCode", employeeCode)
                .append("productCode", productCode)
                .append("weightKG", weightKG)
                .append("weightGR", weightGR)
                .append("date", date)
                .append("time", time)
                .append("weighingScaleId", weighingScaleID);
        collectionCrateWeighing.insertOne(document);
        System.out.println("Inserted document into collectionCrateWeighing. " + stockMutationCode);

        if (stockMutationCode.equals("01")) {
            addStock(productCode, weightKG, weightGR, date, time);
        }

    }

    public void addStock (String productCode, String weightKG, String weightGR, String date, String time) {

        Bson filter = eq("productCode", productCode);

        Document document = new Document("$set", new Document("date", date)
                .append("time", time))
                .append("$inc", new Document("weightKG", Integer.parseInt(weightKG))
                        .append("weightGR", Integer.parseInt(weightGR)));
        
        collectionProducts.updateOne(filter, document);;
        System.out.println("Inserted document into collectionProducts.");

    }






    /*last line*/
}
