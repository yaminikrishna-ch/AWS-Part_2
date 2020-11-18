package com.piotrfilipowicz.aws;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import com.amazonaws.http.HttpMethodName;
import com.google.gson.Gson;
import com.piotrfilipowicz.aws.ApiGatewayResponse;
import com.piotrfilipowicz.aws.JsonApiGatewayCaller;

public class Connection {
	public void ConnectionToAPI( String AWS_IAM_ACCESS_KEY, String AWS_IAM_SECRET_ACCESS_KEY, String AWS_REGION, String AWS_API_GATEWAY_ENPOINT, String TransmissionId) {
		try {
			json j = new json(TransmissionId);
			 Gson gson = new Gson();
		     String json1 = gson.toJson(j);
            JsonApiGatewayCaller caller = new JsonApiGatewayCaller(
                    AWS_IAM_ACCESS_KEY,
                    AWS_IAM_SECRET_ACCESS_KEY,
                    null,
                    AWS_REGION,
                    new URI(AWS_API_GATEWAY_ENPOINT)
            );
            	
            ApiGatewayResponse response = caller.execute(HttpMethodName.POST, "/dynamodb-retrieval", new ByteArrayInputStream(json1.getBytes()));
            System.out.println("In API Lambda");
            System.out.println(response.getBody());

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
	}

}
