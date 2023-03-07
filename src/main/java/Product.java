import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class Product {

    MongoDatabase database;
    private MongoCollection<Document> collectionProducts;

    public Product(MongoDatabase database) {
        this.database = database;
        this.collectionProducts = database.getCollection("products");
    }

    public void manageProduct(String stockMutationCode, String productCode, String weightKG, String weightGR, String date, String time) {

        /* code 01/03 add product to stock - code 02/04 remove product from stock*/
        int mutatedWeightKG = (stockMutationCode.equals("01") || stockMutationCode.equals("03")) ? Integer.parseInt(weightKG) : Integer.parseInt(weightKG) * -1;
        int mutatedWeightGR = (stockMutationCode.equals("01") || stockMutationCode.equals("03")) ? Integer.parseInt(weightGR) : Integer.parseInt(weightGR) * -1;


        Bson filter = eq("productCode", productCode);
        Document document = new Document("$set", new Document("date", date)
                .append("time", time))
                .append("$inc", new Document("weightKG", mutatedWeightKG)
                        .append("weightGR", mutatedWeightGR));

        collectionProducts.updateOne(filter, document);

        System.out.println((collectionProducts.find(filter).first() != null)? "Product " + filter + " updated" : "Product " + filter + " not found");

    }

    /*last line*/
}




