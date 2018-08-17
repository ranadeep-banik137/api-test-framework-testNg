package com.project.api.testscenarios.httpstatusvalidationtests;

import io.restassured.http.ContentType;

import java.security.InvalidAlgorithmParameterException;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.Constants;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.FlatFile;
import com.project.api.constants.PathParamConstants;
import com.project.app.api_test_v1.models.Operators;
import com.project.app.api_test_v1.restFiles.GetApiCall;
import com.project.app.api_test_v1.utilities.DataUtil;
import com.project.app.api_test_v1.utilities.RandomUtils;
import com.project.app.api_test_v1.utilities.RestCalls;

@Test(alwaysRun = true, groups=ApiCallConfig.GET_CUSTOMER_API)
public class CustomerOnboarding_GetCall {

	
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
	private void setUp() {
		this.baseUri = DataUtil.fetchDataFromTestDataFile(Constants.BASE_URI);
		this.basePath = DataUtil.fetchDataFromTestDataFile(Constants.BASE_PATH);
		
		this.restCall = new GetApiCall(this.basePath, this.baseUri);
		this.restCall.setAllRestAssuredParameters();
		try {
			this.restCall.modifyPathParam(PathParamConstants.ID, DataUtil.fetchDataFromTestDataFile(PathParamConstants.ID));
		} catch (InvalidAlgorithmParameterException exceptionObject) {
			exceptionObject.printStackTrace();
		}
	}
	
	/**
	 * Tested get call On a assumed customer API (url Provided externally)
	 * Expected Output : SUCCESS
	 */
	@Test
	public void successGetCall() {
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_OK);
		this.restCall.matchResponseWithSchema(FlatFile.GET_CUSTOMER_RESPONSE_SCHEMA);
	}
	
	/**
	 * Tested delete call On an assumed customer API - POST call (url Provided externally)
	 * Expected Output : 405
	 */
	@Test
	public void postCall_methodNotAllowed() {
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Tested get call On an assumed customer API - DELETE call (url Provided externally)
	 * Expected Output : 405
	 */
	@Test
	public void deleteCall_methodNotAllowed() {
		this.restCall.deleteCall();
		this.restCall.assertResponseCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Tested put call On an assumed customer API - PUT call (url Provided externally)
	 * Expected Output : 405
	 */
	@Test
	public void putCall_methodNotAllowed() {
		this.restCall.putCall();
		this.restCall.assertResponseCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Functional scenario tested By hitting GET Call Customer details with invalid id
	 * Expected Output : NOT FOUND
	 */
	@Test
	public void testGetCallForInvalidCustomerId() {
		try {
			this.restCall.modifyPathParam(PathParamConstants.ID, RandomUtils.getRandomAlphaNumericWithinCount(9));
		} catch (InvalidAlgorithmParameterException exceptionObject) {
			exceptionObject.printStackTrace();
		}
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_FOUND);
	}
	
	/**
	 * Functional scenario tested By hitting GET Call Customer details with invalid urls
	 * Expected Output : NOT FOUND
	 */
	@Test
	public void testGetCallWithInvalidUrl() {
		this.baseUri = Operators.FORWARD_SLASH_OPERATOR.concat(RandomUtils.getRandomAlphaNumericWithinCount(8));
		this.restCall = new GetApiCall(this.basePath, this.baseUri);
		restCall.setAllRestAssuredParameters();
		this.restCall.clearPathParams();
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_FOUND);
	}
	
	/**
	 * Functional scenario tested By hitting GET Call Customer details with invalid Saml Header
	 * Expected Output : FORBIDDEN
	 */
	@Test
	public void testGetCallWithInvalidSamlHeader() {
		try {
			this.restCall.modifyHeader(Constants.SAML_HEADER, RandomUtils.getRandomAlphaNumericWithinCount(20));
		} catch (InvalidAlgorithmParameterException exceptionObject) {
			exceptionObject.printStackTrace();
		}
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_FORBIDDEN);
	}
	
	/**
	 * Functional scenario tested By hitting GET Call Customer details with invalid path Param type
	 * Eg : PathParam Supports string but here It is Passed as number
	 * Expected Output : BAD REQUEST
	 */
	@Test
	public void testGetCallWithInvalidPathParamType() {
		try {
			this.restCall.modifyPathParam(PathParamConstants.ID, RandomUtils.getRandomNumericWithinCount(7));
		} catch (Exception exceptionObject) {
			exceptionObject.printStackTrace();
		}
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_FORBIDDEN);
	}
	
	/**
	 * Tested by Calling get call with request content type as ANY
	 * Expected : 406
	 */
	@Test
	public void getCallWithContentTypeAsAny() {
		this.restCall.setContentType(ContentType.ANY);
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	/**
	 * Tested by Calling get call with request content type as HTML
	 * Expected : 406
	 */
	@Test
	public void getCallWithContentTypeAsHtml() {
		this.restCall.setContentType(ContentType.HTML);
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	/**
	 * Tested by Calling get call with request content type as BINARY
	 * Expected : 406
	 */
	@Test
	public void getCallWithContentTypeAsBinary() {
		this.restCall.setContentType(ContentType.BINARY);
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	/**
	 * Tested by Calling get call with request content type as TEXT
	 * Expected : 406
	 */
	@Test
	public void getCallWithContentTypeAsText() {
		this.restCall.setContentType(ContentType.TEXT);
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}

	/**
	 * Tested by Calling get call with request content type as XML
	 * Expected : 406
	 */
	@Test
	public void getCallWithContentTypeAsXml() {
		this.restCall.setContentType(ContentType.XML);
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
}
