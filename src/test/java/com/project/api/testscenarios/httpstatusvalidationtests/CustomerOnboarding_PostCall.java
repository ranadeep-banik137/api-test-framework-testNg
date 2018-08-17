package com.project.api.testscenarios.httpstatusvalidationtests;

import io.restassured.http.ContentType;

import java.security.InvalidAlgorithmParameterException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.project.api.constants.ApiCallConfig;
import com.project.api.constants.Constants;
import com.project.api.constants.EnvironmentVariables;
import com.project.api.constants.FlatFile;
import com.project.api.constants.PayloadFields;
import com.project.app.api_test_v1.models.Operators;
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
		this.baseUri = DataUtil.fetchDataFromTestDataFile(Constants.BASE_URI);
		this.basePath = DataUtil.fetchDataFromTestDataFile(Constants.BASE_PATH);
		this.restCall = new PostApiCall(basePath, baseUri);
		restCall.loadPayloadString(FlatFile.POST_CUSTOMER_REQUEST_PAYLOAD);
		restCall.setAllRestAssuredParameters();
	}
	
	/**
	 * Tested delete call On an assumed customer API - POST call (url Provided externally)
	 * Expected Output : 405
	 */
	@Test
	public void deleteCall_methodNotAllowed() {
		this.restCall.deleteCall();
		this.restCall.assertResponseCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Tested get call On an assumed customer API - POST call (url Provided externally)
	 * Expected Output : 405
	 */
	@Test
	public void getCall_methodNotAllowed() {
		this.restCall.getCall();
		this.restCall.assertResponseCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Tested put call On an assumed customer API - POST call (url Provided externally)
	 * Expected Output : 405
	 */
	@Test
	public void putCall_methodNotAllowed() {
		this.restCall.putCall();
		this.restCall.assertResponseCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Tested by Calling post call without request body
	 * Expected : 415
	 */
	@Test
	public void postCallWithEmptyBody() {
		this.restCall.clearRequest();
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
	}
	
	/**
	 * Tested by Calling post call with invalid url
	 * Expected : 404
	 */
	@Test
	public void postCallWithInvalidUrl() {
		String baseUri = Operators.FORWARD_SLASH_OPERATOR.concat(RandomStringUtils.randomAlphabetic(8));
		RestCalls restObject = new PostApiCall(this.basePath, baseUri);
		restObject.setAllRestAssuredParameters();
		restObject.postCall();
		restObject.assertResponseCode(HttpStatus.SC_NOT_FOUND);
	}
	
	/**
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
	 * Tested by Calling post call with invalid URL provided by user (Invalid input in URL)
	 * Eg : Field that supports String populated as Number
	 * Expected : 501
	 */
	/* Skipped this test case as url provided as environment variable Is dependent upon users */
	@Test(enabled = false)
	public void postCallWithInvalidUserInputUrl() {
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_IMPLEMENTED);
	}
	
	/**
	 * Tested by Calling post call with invalid header saml (Authorization Token - Not for all organizations)
	 * Eg : Field that supports String populated as Number
	 * Expected : 403
	 */
	public void postCallWithInvalidAuthHeader() {
		try {
			this.restCall.modifyHeader(Constants.SAML_HEADER, RandomUtils.getRandomAlphaNumericWithinCount(9));
		} catch (InvalidAlgorithmParameterException exceptionObject) {
			// TODO Auto-generated catch block
			exceptionObject.printStackTrace();
		}
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_FORBIDDEN);
	}
	
	/**
	 * Tested by Calling post call with request content type as HTML
	 * Expected : 406
	 */
	@Test
	public void postCallWithContentTypeAsHtml() {
		this.restCall.setContentType(ContentType.HTML);
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	/**
	 * Tested by Calling post call with request content type as ANY
	 * Expected : 406
	 */
	@Test
	public void postCallWithContentTypeAsAny() {
		this.restCall.setContentType(ContentType.ANY);
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	/**
	 * Tested by Calling post call with request content type as BINARY
	 * Expected : 406
	 */
	@Test
	public void postCallWithContentTypeAsBinary() {
		this.restCall.setContentType(ContentType.BINARY);
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	/**
	 * Tested by Calling post call with request content type as TEXT
	 * Expected : 406
	 */
	@Test
	public void postCallWithContentTypeAsText() {
		this.restCall.setContentType(ContentType.TEXT);
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
	
	/**
	 * Tested by Calling post call with request content type as XML
	 * Expected : 406
	 */
	@Test
	public void postCallWithContentTypeAsXml() {
		this.restCall.setContentType(ContentType.XML);
		this.restCall.postCall();
		this.restCall.assertResponseCode(HttpStatus.SC_NOT_ACCEPTABLE);
	}
}
