package DynamoDB;

import org.json.JSONObject;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.piotrfilipowicz.aws.Connection;

public class DynamoDB_Data {
	ObjectMapper mapper = new ObjectMapper();
	AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
DynamoDB dynamoDB = new DynamoDB(client);
Table table = dynamoDB.getTable("Employees");
private String accessKey = "AKIAXMRCYLQMERF3RGVP"; 
//@Value("${cloud.aws.credentials.secret-Key1}")
private String secretKey = "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";
//@Value("${cloud.aws.credentials.region}")
private String region = "us-west-1";
//@Value("${cloud.aws.credentials.gateway_end_point}")
private String gateway_end_point = "https://1e0wk8bifa.execute-api.us-west-1.amazonaws.com/Test";
public void StringToJson(String str) {
	try {
		JSONObject json= new JSONObject(str);
		
		JSONObject ga = json.getJSONObject("wgsOrder");
		JSONObject ga1 = json.getJSONObject("wgsEmployee");
		JSONObject ga2 = json.getJSONObject("wgsPayee");
		//EmployeeObject employee = deserializer.deserialize(jsonStr);
		int das= ga1.getInt("ssn");
		int len =ga1.length()/4;
		//int deductionAmount = 100;
		for(int i=0;i<len;i++) {
		String irCaseId = ga.getString("stCd");
		String date  = json.getString("lienIssueDate");
		Item item = new Item()
			    .withPrimaryKey("ssn",das )
			    .withString("TransmissionId", json.getString("TransmissionId"))
			    .withString("stCd", irCaseId)
			    .withString("irCaseId", json.getString("irCaseId"))
			    .withString("FirstName", ga1.getString("FirstName"))
			    .withString("MiddleInitial", ga1.getString("MiddleInitial"))
			    .withString("LastName", ga1.getString("LastName"))
			    .withNumber("ZIPCd", ga2.getInt("ZIPCd"))
			    .withString("payeeName1", ga2.getString("payeeName1"))
			    .withString("AddressLine1Txt", ga2.getString("AddressLine1Txt"))
			    .withString("CityNm", ga2.getString("CityNm"))
			    .withString("StateAbbreviationCd", ga2.getString("StateAbbreviationCd"))
			    .withString("recordTypCd", json.getString("recordTypCd"))
			    .withNumber("goalAmount", json.getInt("goalAmount"))
			    .withNumber("deductionAmount", json.getInt("deductionAmount"))
			    .withString("lienIssueDate",date);
		table.putItem(item);
		Connection c = new Connection();
        c.ConnectionToAPI(accessKey , secretKey, region, gateway_end_point, json.getString("TransmissionId") );
		System.out.println(irCaseId);
		}
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
