package com.project.api.testscenarios.responsevalidationtests;

import java.security.InvalidAlgorithmParameterException;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
	
}
