package com.project.api.testscenarios.requestvalidationtests;

import java.security.InvalidAlgorithmParameterException;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.Constants;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.PayloadFields;
import com.project.app.api_test_v1.restFiles.PostApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RandomUtils;
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
	 * 
	 * Tested by Calling post call with invalid field value type
	 * Eg : Field that supports number populated as String
	 * Expected : 400
	 */
	@Test
	public void postCallWithInvalidFieldValueAsString() {
		this.restCall.updatePayloadField(PayloadFields.CUSTOMER_DETAILS_INITIAL_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.CONTACT)), RandomUtils.getRandomNumericInStringWithinCount(9));
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	/**
	 * Tested by Calling post call with invalid field value type
	 * Eg : Field that supports String populated as Number
	 * Expected : 400
	 */
	@Test
	public void postCallWithInvalidFieldValueAsNumber() {
		this.restCall.updatePayloadField(PayloadFields.CUSTOMER_DETAILS_INITIAL_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.FIRST_NAME)), RandomUtils.getRandomNumericWithinRange(99999));
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	/**
	 * Tested by Calling post call with invalid header saml (Authorization Token - Not for all organizations)
	 * Eg : Field that supports String populated as Number
	 * Expected : 400
	 */
	public void postCallWithInvalidAuthHeader() {
		try {
			this.restCall.modifyHeader(Constants.SAML_HEADER, RandomUtils.getRandomNumericWithinCount(10));
		} catch (InvalidAlgorithmParameterException exceptionObject) {
			// TODO Auto-generated catch block
			exceptionObject.printStackTrace();
		}
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_FORBIDDEN);
	}
}
