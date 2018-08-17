package com.project.api.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import junit.framework.Assert;

import com.project.api.constants.PathParamConstants;
import com.project.api.constants.PayloadFields;
import com.project.app.api_test_v1.utilities.RestCalls;

import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

/**
 * CommonUtils class consist Of all common validation methods
 * @author Ranadeep Banik
 * @email ranadeep.banik137@yahoo.com
 *
 */

public class CommonUtils {
	
	private static Logger LOGGER = Logger.getLogger(CommonUtils.class.getName());
	private static String requestBody;
	private static String responseBody;
	private static JsonPath response;
	
	public static void validatePostSuccessResponse(ValidatableResponse response) {
		CommonUtils.responseBody = response.extract().response().asString();
		CommonUtils.validatePostSuccessResponse(CommonUtils.responseBody);
	}
	
	public static void validateGetSuccessResponse(ValidatableResponse response) {
		CommonUtils.responseBody = response.extract().response().asString();
		CommonUtils.validateGetSuccessResponse(CommonUtils.responseBody);
	}
	
	public static void validatePostSuccessResponse(String response) {
		CommonUtils.response = JsonPath.from(response);
		List<HashMap<String, Object>> responseFields = CommonUtils.response.getList(PayloadFields.CUSTOMER_ONBOARDING_DETAILS_RESPONSE_PATH);
		if (responseFields.size() > 0) {
			for (HashMap<String, Object> responseField : responseFields) {
				Assert.assertNotNull(responseField.get(PayloadFields.CUSTOMER_IDENTIFICATION_NUMBER));
				Assert.assertNotNull(responseField.get(PayloadFields.CUSTOMER_ONBOARDING_ID));
				Assert.assertNotNull(responseField.get(PayloadFields.CUSTOMER_ONBOARDING_DATE));
				LOGGER.info("All response fields validated");
			}
		}
	}
	
	public static void validateGetSuccessResponse(String response) {
		CommonUtils.response = JsonPath.from(response);
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.FIRST_NAME))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.LAST_NAME))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.MIDDLE_NAME))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.EMAIL))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.DATE_OF_BIRTH))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.GENDER))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PERSONAL_DETAILS_PATH.concat(PayloadFields.CONTACT))));
		
		LOGGER.info("Personal Details validated");
		
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.ADDRESS_DETAILS_PATH.concat(PayloadFields.ADDRESS_LINE_1))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.ADDRESS_DETAILS_PATH.concat(PayloadFields.ADDRESS_LINE_2))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.ADDRESS_DETAILS_PATH.concat(PayloadFields.ADDRESS_LINE_3))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.ADDRESS_DETAILS_PATH.concat(PayloadFields.CITY))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.ADDRESS_DETAILS_PATH.concat(PayloadFields.PIN_CODE))));
		
		LOGGER.info("Address Details validated");
		
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PROFESSIONAL_DETAILS_PATH.concat(PayloadFields.COMPANY))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PROFESSIONAL_DETAILS_PATH.concat(PayloadFields.DESIGNATION))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PROFESSIONAL_DETAILS_PATH.concat(PayloadFields.COMPANY_ID))));
		Assert.assertNotNull(CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.PROFESSIONAL_DETAILS_PATH.concat(PayloadFields.COMPANY_LOCATION))));
		
		LOGGER.info("Professional Details validated");
		LOGGER.info("All response details validated successfully");
	}
	
	public static void validatePostSuccessResponse(RestCalls restObject) {
		CommonUtils.responseBody = restObject.getResponseJson();
		CommonUtils.validatePostSuccessResponse(CommonUtils.responseBody);
		
	}
	
	public static void validateGetSuccessResponse(RestCalls restObject) {
		CommonUtils.requestBody = String.valueOf(restObject.getPathParamsMap().get(PathParamConstants.ID));
		CommonUtils.responseBody = restObject.getResponseJson();
		CommonUtils.response = JsonPath.from(CommonUtils.responseBody);
		Assert.assertEquals(CommonUtils.requestBody, CommonUtils.response.getString(PayloadFields.GET_CUSTOMER_DETAILS_PATH.concat(PayloadFields.CUSTOMER_ID)));
		LOGGER.info("Customer Id Validated. Customer details of customer ".concat(CommonUtils.requestBody).concat(" validated"));
		CommonUtils.validateGetSuccessResponse(CommonUtils.responseBody);
		
	}

}
