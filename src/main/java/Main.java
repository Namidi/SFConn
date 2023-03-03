public class Main {

    public static void main(String[] args) {

       // INDConnection indConnection = new INDConnection(args [0]);
      //  indConnection.indConnection();

        MongoDBConnection mdbConnection = new MongoDBConnection();
        mdbConnection.writeCrateWeighing("1874013910091035150705222210951");
    }

}
