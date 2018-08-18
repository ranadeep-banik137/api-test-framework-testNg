package com.project.api.testscenarios.responsevalidationtests;

import java.lang.reflect.Method;
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
import com.project.api.utilities.CommonUtils;
import com.project.app.api_test_v1.restFiles.PostApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
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
	
	@AfterMethod
	private void conclude(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		LOGGER.info(result.getStatus() == TestResult.SUCCESS ? "Execution successful. Method ".concat(testName).concat(" PASSED") : (result.getStatus() == TestResult.SKIP ? "Method ".concat(testName).concat(" SKIPPED") : "Method ".concat(testName).concat(" FAILED")));
	}
}
