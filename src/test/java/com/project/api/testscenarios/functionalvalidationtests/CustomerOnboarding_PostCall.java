package com.project.api.testscenarios.functionalvalidationtests;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.restassured.path.json.JsonPath;

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
	 * Tested delete call On a assumed customer API (url Provided externally)
	 * Expected Output : SUCCESS
	 */
	@Test
	public void successPostCall() {
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.POST_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validatePostSuccessResponse(this.restCall.getResponseJson());
	}
	
	/**
	 * Functional scenario tested By requesting Only One Customer details in request JSON
	 * Expected Output : SUCCESS
	 */
	@Test
	public void testSuccessPostCallForOnlyOneCustomer() {
		JsonPath requestBody = JsonPath.from(this.restCall.getRequestJson());
		List<Object> customerDetailElements = requestBody.getList(PayloadFields.CUSTOMER_DETAILS_PATH);
		for (@SuppressWarnings("unused") Object object : customerDetailElements) {
			this.restCall.deletePayloadField(PayloadFields.CUSTOMER_DETAILS_PATH.concat("[".concat(String.valueOf(1)).concat("]")));
		}
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.POST_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validatePostSuccessResponse(this.restCall.getResponseJson());
	}
	
	/**
	 * Functional scenario tested By requesting Two Customer details in request JSON having same entry and Details
	 * Expected Output : SUCCESS
	 */
	@Test
	public void testSuccessPostCallForDuplicateCustomerDetails() {
		JsonPath requestBody = JsonPath.from(this.restCall.getRequestJson());
		List<Object> customerDetailElements = requestBody.getList(PayloadFields.CUSTOMER_DETAILS_PATH);
		Object requestObject = customerDetailElements.get(0);
		for (@SuppressWarnings("unused") Object object : customerDetailElements) {
			this.restCall.deletePayloadField(PayloadFields.CUSTOMER_DETAILS_PATH.concat("[".concat(String.valueOf(1)).concat("]")));
		}
		this.restCall.addFieldInJsonArray(PayloadFields.CUSTOMER_DETAILS_PATH, requestObject);
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.POST_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validatePostSuccessResponse(this.restCall.getResponseJson());
	}
	
	/**
	 * Functional scenario tested By requesting Customer details in request JSON having random details entry
	 * Expected Output : SUCCESS
	 */
	@Test
	public void testSuccessPostCallForRandomDetailsEntry() {
		Map<String, Object> changedDetailsMapper = new HashMap<>();
		this.restCall.updatePayloadFields(changedDetailsMapper);
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.POST_CUSTOMER_RESPONSE_SCHEMA);
		CommonUtils.validatePostSuccessResponse(this.restCall.getResponseJson());
	}
	
	@AfterMethod
	private void conclude(ITestResult result) {
		String testName = result.getMethod().getMethodName();
		LOGGER.info(result.getStatus() == TestResult.SUCCESS ? "Execution successful. Method ".concat(testName).concat(" PASSED") : (result.getStatus() == TestResult.SKIP ? "Method ".concat(testName).concat(" SKIPPED") : "Method ".concat(testName).concat(" FAILED")));
	}
}
