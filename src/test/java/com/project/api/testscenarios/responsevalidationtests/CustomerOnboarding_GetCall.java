package com.project.api.testscenarios.responsevalidationtests;

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
import com.project.api.constants.PathParamConstants;
import com.project.api.utilities.CommonUtils;
import com.project.app.api_test_v1.restFiles.GetApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RestCalls;

@Test(alwaysRun = true, groups=ApiCallConfig.GET_CUSTOMER_API)
public class CustomerOnboarding_GetCall {

	private static Logger LOGGER = Logger.getLogger(CustomerOnboarding_GetCall.class.getName());
	RestCalls restCall;
	String baseUri;
	String basePath;
	
	/* Setting Up the config entity through system property variables
	 * So that all the data Type I need to hit The API Is fetched from 
	 * src/main/resources/{mentioned system property}/......
	 */
	static {
		if (System.getProperty(EnvironmentVariables.ENTITY) == null) {
			System.setProperty(EnvironmentVariables.ENTITY, ApiCallConfig.GET_CUSTOMER_API);
		}
	}

	/**
	 * basic configuration set up done for Initializing the call
	 */
	@BeforeMethod
	private void setUp(Method method) {
		this.baseUri = DataUtil.fetchDataFromTestDataFile(Constants.BASE_URI);
		this.basePath = DataUtil.fetchDataFromTestDataFile(Constants.BASE_PATH);
		
		this.restCall = new GetApiCall(this.basePath, this.baseUri);
		this.restCall.setAllRestAssuredParameters();
		try {
			this.restCall.modifyPathParam(PathParamConstants.ID, DataUtil.fetchDataFromTestDataFile(PathParamConstants.ID));
		} catch (InvalidAlgorithmParameterException exceptionObject) {
			exceptionObject.printStackTrace();
		}
		LOGGER.info("Method initiating ".concat(method.getName()));
	}
	
	/**
	 * Test get call On a assumed customer API (url Provided externally)
	 * Expected Output : SUCCESS
	 * Validates each response fields
	 */
	@Test
	public void successGetCall() {
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.GET_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validateGetSuccessResponse(this.restCall);
	}
	
	@AfterMethod
	private void conclude(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		LOGGER.info(result.getStatus() == TestResult.SUCCESS ? "Execution successful. Method ".concat(testName).concat(" PASSED") : (result.getStatus() == TestResult.SKIP ? "Method ".concat(testName).concat(" SKIPPED") : "Method ".concat(testName).concat(" FAILED")));
	}
}
