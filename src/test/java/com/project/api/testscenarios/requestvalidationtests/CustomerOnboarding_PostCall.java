package com.project.api.testscenarios.requestvalidationtests;

import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.Constants;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.FlatFile;
import com.project.api.constants.PayloadFields;
import com.project.app.api_test_v1.restFiles.PostApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RandomUtils;
import com.project.app.api_test_v1.utilities.RestCalls;

@Test(alwaysRun = true, groups=ApiCallConfig.POST_CUSTOMER_API)
public class CustomerOnboarding_PostCall {

	private static Logger LOGGER = Logger.getLogger(CustomerOnboarding_PostCall.class.getName());
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
	private void setUp(Method method) {
		this.baseUri = DataUtil.fetchDataFromTestDataFile(Constants.BASE_URI);
		this.basePath = DataUtil.fetchDataFromTestDataFile(Constants.BASE_PATH);
		this.restCall = new PostApiCall(this.basePath, this.baseUri);
		restCall.loadPayloadString(FlatFile.POST_CUSTOMER_REQUEST_PAYLOAD);
		restCall.setAllRestAssuredParameters();
		LOGGER.info("Method initiating ".concat(method.getName()));
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
	
	@AfterMethod
	private void conclude(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		LOGGER.info(result.getStatus() == TestResult.SUCCESS ? "Execution successful. Method ".concat(testName).concat(" PASSED") : (result.getStatus() == TestResult.SKIP ? "Method ".concat(testName).concat(" SKIPPED") : "Method ".concat(testName).concat(" FAILED")));
	}
}
