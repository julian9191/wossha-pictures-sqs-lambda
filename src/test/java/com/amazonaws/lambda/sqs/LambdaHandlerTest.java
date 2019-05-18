package com.amazonaws.lambda.sqs;

import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.junit.contrib.java.lang.system.EnvironmentVariables;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wossha.pictures.sqs.LambdaHandler;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PowerMockIgnore({ "javax.net.ssl.*", "javax.management.*", "javax.security.*", "javax.crypto.*" })
public class LambdaHandlerTest {

	@ClassRule
    public static final EnvironmentVariables environmentVariables = new EnvironmentVariables();
	
	private static final String DATABASE_NAME = "wossha";
	private static final String DATABASE_URL = "jdbc:oracle:thin:@wosshapass.clvdmhzyqopx.us-east-1.rds.amazonaws.com:1521/wossha";
	private static final String DATABASE_USER = "WSSPICTURESCXN1";
	private static final String DATABASE_PASSWORD= "WSSPICTURESCXN1";
	
	@Mock
	private SQSEvent sqsEvent;
	
	@Before
	public void setUp() throws IOException {

		environmentVariables.set("DATABASE_NAME", DATABASE_NAME);
        environmentVariables.set("DATABASE_URL", DATABASE_URL);
        environmentVariables.set("DATABASE_USER", DATABASE_USER);
        environmentVariables.set("DATABASE_PASSWORD", DATABASE_PASSWORD);
		
		// Set up AWS resources
		List<SQSMessage> records = new ArrayList<>();
		SQSMessage sqs = new SQSMessage();
		String sampleJson = new ObjectMapper().readTree(new File("src/test/resources/sampleInputData.json")).toString();
		sqs.setBody(sampleJson);
		records.add(sqs);
		when(sqsEvent.getRecords()).thenReturn(records);
	}
	
	@Test
	public void test() throws JsonProcessingException, IOException {

		LambdaHandler handler = new LambdaHandler();
		Context context = createContext();
		
		handler.handleRequest(sqsEvent, context);
	}

	private Context createContext() {
		TestContext ctx = new TestContext();
		// TODO: customize your context here if needed.
		ctx.setFunctionName("wossha-api-authorizer-lambda");
		return ctx;
	}
}
