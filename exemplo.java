import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LambdaFunctionHandler implements RequestHandler<Object, String> {

    private static final String BUCKET_NAME = "s3-bucket-name";
    private static final String FILE_NAME = "file-name";
    private static final String SEARCH_STRING = "search-string";
    private static final String TABLE_NAME = "dynamodb-table-name";

    private final AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();
    private final AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
    private final DynamoDB dynamoDBMapper = new DynamoDB(dynamoDB);

    @Override
    public String handleRequest(Object input, Context context) {
        try {
            S3Object s3Object = s3.getObject(new GetObjectRequest(BUCKET_NAME, FILE_NAME));
            BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object.getObjectContent()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(SEARCH_STRING)) {
                    updateDynamoDB(line);
                }
            }

            reader.close();
            return "Success";
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void updateDynamoDB(String line) {
        Table table = dynamoDBMapper.getTable(TABLE_NAME);
        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("id", 1)
                .withUpdateExpression("set line = :l")
                .withValueMap(new ValueMap().withString(":l", line))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            System.out.println("Update succeeded:\n" + outcome.getItem().toJSONPretty());
        } catch (Exception e) {
            System.err.println("Unable to update item: " + 1);
            System.err.println(e.getMessage());
        }
    }
}
