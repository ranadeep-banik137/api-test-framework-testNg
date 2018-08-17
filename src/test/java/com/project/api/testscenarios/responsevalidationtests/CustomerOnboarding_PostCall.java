package com.project.api.testscenarios.responsevalidationtests;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.FlatFile;
import com.project.api.utilities.CommonUtils;
import com.project.app.api_test_v1.restFiles.PostApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RestCalls;

@Test(alwaysRun = true, groups=ApiCallConfig.POST_CUSTOMER_API)
public class CustomerOnboarding_PostCall {

	RestCalls restCall;
	private String basePath;
	private String baseUri;
	
	/* Setting Up the config entity through system property variables
	 * So that all the data Type I need to hit The API Is fetched from 
	 * src/main/resources/{mentioned system property}/......
	 */
	static {
		if (System.getProperty(EnvironmentVariables.ENTITY) == null) {
			System.setProperty(EnvironmentVariables.ENTITY, ApiCallConfig.POST_CUSTOMER_API);
		}
	}

	/**
	 * basic configuration set up done for Initializing the call
	 */
	@BeforeMethod
	private void setUp() {
		this.baseUri = DataUtil.fetchDataFromTestDataFile("baseUri");
		this.basePath = DataUtil.fetchDataFromTestDataFile("basePath");
		this.restCall = new PostApiCall(basePath, baseUri);
		restCall.loadPayloadString("customers-request-payload.properties");
		restCall.setAllRestAssuredParameters();
	}
	
	/**
	 * Test post call On a assumed customer API (url Provided externally)
	 * Expected Output : SUCCESS
	 * Validate Response fields
	 */
	@Test
	public void successPostCallResponseValidation() {
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.POST_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validatePostSuccessResponse(this.restCall);
	}
}
