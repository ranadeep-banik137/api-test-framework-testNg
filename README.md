# Framework name : api-test-framework-testng

	The API is based on customer onboarding

| Method | Api Endpoint | Call Details |
|--------|--------------|--------------|
| POST | api/customer | Post call to add customer details |
| GET | api/customer/{id} | Get call to get particular customer details |

## Notes To Run Test Cases : 

+ Test Scenarios are identified in different functional aspects of the api call.
	Examples : functional validations, request validations, response validations, http status validations.
+ All above test packages are created under : 

|Package name| Package location|
|------------|-----------------|
| functionalvalidationtests | testFrame\src\test\java\com\project\api\testscenarios\functionalvalidationtests |
| requestvalidationtests | testFrame\src\test\java\com\project\api\testscenarios\requestvalidationtests |
| responsevalidationtests | testFrame\src\test\java\com\project\api\testscenarios\responsevalidationtests |
| httpstatusvalidationtests | testFrame\src\test\java\com\project\api\testscenarios\httpstatusvalidationtests |


+ Test class names has been categorized as per api call

| Class name | Http Method |
|------------|-------------|
| CustomerOnboarding_PostCall.java | POST |
| CustomerOnboarding_GetCall.java | GET |


+ A Java restassured DSL has been created as a parent framework for executing the test flow.
Need to import/add the jar in java libraries while setting up the the framework in IDE.

	Either it has to be added externally or has to set in The pom.xml along with the base directory path
	Example : 
	In pom.xml we have To add a dependency along with the system base directory
	
		<dependency>
	    	<groupId>api-test-framework</groupId>
	    	<artifactId>test-v1</artifactId>
	    	<scope>system</scope>
	    	<version>1.0.0</version>
	    	<systemPath>${basedir}\src\test\resources\api-test-framework-model-version-0.0.03.jar</systemPath>
		</dependency>

|DSL Jar file name| File location|
|-----------------|--------------|
| api-test-framework-model-version-0.0.03.jar | testFrame\src\test\resources |

+ API Endpoint URI (Eg : /api/customers/{id}) has been initiated in test framework as per REST ASSURED Endpoint pattern
where RestAssured.BASEPATH to be defined to ("/api") and RestAssured.BASE_URI is defined to ("/customer/{id}"). 

+ To run the test and hit the api we first have to set the environment variable ("URL") which is the endpoint Url for the api.
Based on the previous comments, We Append this three URLs (URL+BASEPATH + BASEURI) to get The total API endPoint.

	Example: 
	
| URL | BASEPATH | BASEURI |
|-----|----------|---------|
|https://www.servicecall.hp.cn.dgms.com | /api | /customers |

+ Test has also been Categorized as per entity (it is the key of categorization)

		For maven run we have to set -Dentity={anyname through which we can categorize the test call}
	
		For TestNg execution : set an environment variable as entity and set the value.

+ Entity categorization has been done just to categorize the required data such as Request Body file In properties file, schemas, test data files based on the API type/call
	
		Example : we have identified all the testdata inside src/main/resources/{defined entity value}/.....
	Refer to the framework.
	
+ All test scenario details are mentioned above all the scenarios.

## Run Tests Through TestNG :

+ Step 1 ->
	set environment variable "URL" - Pass the url to which api to be called
+ Step 2 ->
	entity Variable is already put in the static block of every test class as property variable Cannot be set while testNg run.
+ Step 3 -> 
	xml files available in "src/test/resources/test-suites/.." location. Need to customize as per the TEST run requirement. Introduced example xmls over there. You can add multiple testNg Files based on exeution form.
+ Step 4 ->
	groups need to be introduced inside xml files as it categorizes which API Call to test for.
	
## Run Tests Through Maven : 

+ Step 1 -> 
	set environment variable "URL" - Pass the url to which api to be called
+ Step 2 -> 
	entity can be set in maven goals while initiating maven build. Eg : clean test -Dentity=getcustomer
+ Step 3 -> 
	particular xml files can be hit while build. You can provide the xml file name need to execute on maven goals (Mandatorily). Eg : clean test -Dtest-execution-xml={name of The xml file}	
	Build plugin has been introduced in pom.xml
		<plugin>
	      <groupId>org.apache.maven.plugins</groupId>
	      <artifactId>maven-surefire-plugin</artifactId>
	      <version>${maven-surefire-plugin-version}</version>
	      	<configuration>
	      		<suiteXmlFiles>
	      			<suiteXmlFile>${test-executor-file-path}/${test-execution-xml}.xml</suiteXmlFile>
	      		</suiteXmlFiles>
	      	</configuration>
	      </plugin>
	

## Schemas Available For POST Call: 
	
	We can assume its request and response body schema as below format: 

+ Request Schema

		 {
		  "$id": "http://example.com/example.json",
		  "type": "object",
		  "definitions": {},
		  "$schema": "http://json-schema.org/draft-07/schema#",
		  "properties": {
		    "customerDetails": {
		      "$id": "/properties/customerDetails",
		      "type": "array",
		      "items": {
		        "$id": "/properties/customerDetails/items",
		        "type": "object",
		        "properties": {
		          "personalDetails": {
		            "$id": "/properties/customerDetails/items/properties/personalDetails",
		            "type": "object",
		            "properties": {
		              "firstName": {
		                "$id": "/properties/customerDetails/items/properties/personalDetails/properties/firstName",
		                "type": "string",
		                "title": "The Firstname Schema ",
		                "default": "",
		                "examples": [
		                  "Pankaj"
		                ]
		              },
		              "lastName": {
		                "$id": "/properties/customerDetails/items/properties/personalDetails/properties/lastName",
		                "type": "string",
		                "title": "The Lastname Schema ",
		                "default": "",
		                "examples": [
		                  "Pandey"
		                ]
		              },
		              "middleName": {
		                "$id": "/properties/customerDetails/items/properties/personalDetails/properties/middleName",
		                "type": "string",
		                "title": "The Middlename Schema ",
		                "default": "",
		                "examples": [
		                  "Kumar"
		                ]
		              },
		              "email": {
		                "$id": "/properties/customerDetails/items/properties/personalDetails/properties/email",
		                "type": "string",
		                "title": "The Email Schema ",
		                "default": "",
		                "examples": [
		                  "pankaj@ABC.com"
		                ]
		              },
		              "dateOfBirth": {
		                "$id": "/properties/customerDetails/items/properties/personalDetails/properties/dateOfBirth",
		                "type": "string",
		                "title": "The Dateofbirth Schema ",
		                "default": "",
		                "examples": [
		                  "13-03-91"
		                ]
		              },
		              "gender": {
		                "$id": "/properties/customerDetails/items/properties/personalDetails/properties/gender",
		                "type": "string",
		                "title": "The Gender Schema ",
		                "default": "",
		                "examples": [
		                  "M"
		                ]
		              },
		              "contact": {
		                "$id": "/properties/customerDetails/items/properties/personalDetails/properties/contact",
		                "type": "string",
		                "title": "The Contact Schema ",
		                "default": "",
		                "examples": [
		                  "020-8029654321"
		                ]
		              }
		            }
		          },
		          "addressDetails": {
		            "$id": "/properties/customerDetails/items/properties/addressDetails",
		            "type": "object",
		            "properties": {
		              "addressLine1": {
		                "$id": "/properties/customerDetails/items/properties/addressDetails/properties/addressLine1",
		                "type": "string",
		                "title": "The Addressline1 Schema ",
		                "default": "",
		                "examples": [
		                  "Cramson Lane"
		                ]
		              },
		              "addressLine2": {
		                "$id": "/properties/customerDetails/items/properties/addressDetails/properties/addressLine2",
		                "type": "string",
		                "title": "The Addressline2 Schema ",
		                "default": "",
		                "examples": [
		                  "Story 01/2"
		                ]
		              },
		              "addressLine3": {
		                "$id": "/properties/customerDetails/items/properties/addressDetails/properties/addressLine3",
		                "type": "string",
		                "title": "The Addressline3 Schema ",
		                "default": "",
		                "examples": [
		                  ""
		                ]
		              },
		              "city": {
		                "$id": "/properties/customerDetails/items/properties/addressDetails/properties/city",
		                "type": "string",
		                "title": "The City Schema ",
		                "default": "",
		                "examples": [
		                  "Shamshing"
		                ]
		              },
		              "pinCode": {
		                "$id": "/properties/customerDetails/items/properties/addressDetails/properties/pinCode",
		                "type": "string",
		                "title": "The Pincode Schema ",
		                "default": "",
		                "examples": [
		                  "877412"
		                ]
		              }
		            }
		          },
		          "professionalDetails": {
		            "$id": "/properties/customerDetails/items/properties/professionalDetails",
		            "type": "object",
		            "properties": {
		              "company": {
		                "$id": "/properties/customerDetails/items/properties/professionalDetails/properties/company",
		                "type": "string",
		                "title": "The Company Schema ",
		                "default": "",
		                "examples": [
		                  "Scholarz"
		                ]
		              },
		              "designation": {
		                "$id": "/properties/customerDetails/items/properties/professionalDetails/properties/designation",
		                "type": "string",
		                "title": "The Designation Schema ",
		                "default": "",
		                "examples": [
		                  "SE"
		                ]
		              },
		              "companyId": {
		                "$id": "/properties/customerDetails/items/properties/professionalDetails/properties/companyId",
		                "type": "string",
		                "title": "The Companyid Schema ",
		                "default": "",
		                "examples": [
		                  "SCH-2958"
		                ]
		              },
		              "companyLocation": {
		                "$id": "/properties/customerDetails/items/properties/professionalDetails/properties/companyLocation",
		                "type": "string",
		                "title": "The Companylocation Schema ",
		                "default": "",
		                "examples": [
		                  "Siyaal"
		                ]
		              }
		            }
		          }
		        }
		      }
		    }
		  }
		}


+ Response Schema: 

		{
		  "$id": "http://example.com/example.json",
		  "type": "object",
		  "definitions": {},
		  "$schema": "http://json-schema.org/draft-07/schema#",
		  "properties": {
		    "customerOnboardingDetails": {
		      "$id": "/properties/customerOnboardingDetails",
		      "type": "array",
		      "items": {
		        "$id": "/properties/customerOnboardingDetails/items",
		        "type": "object",
		        "properties": {
		          "cutomersIdentificationNumber": {
		            "$id": "/properties/customerOnboardingDetails/items/properties/cutomersIdentificationNumber",
		            "type": "string",
		            "title": "The Cutomersidentificationnumber Schema ",
		            "default": "",
		            "examples": [
		              "XHDFBC2145"
		            ]
		          },
		          "customerOnboardingId": {
		            "$id": "/properties/customerOnboardingDetails/items/properties/customerOnboardingId",
		            "type": "string",
		            "title": "The Customeronboardingid Schema ",
		            "default": "",
		            "examples": [
		              "154315482"
		            ]
		          },
		          "customerOnboardingDate": {
		            "$id": "/properties/customerOnboardingDetails/items/properties/customerOnboardingDate",
		            "type": "string",
		            "title": "The Customeronboardingdate Schema ",
		            "default": "",
		            "examples": [
		              "21-09-2514"
		            ]
		          }
		        }
		      }
		    }
		  }
		}

## Schemas Available For GET Call: 

+ Response Schema: 

		{
		  "$id": "http://example.com/example.json",
		  "type": "object",
		  "definitions": {},
		  "$schema": "http://json-schema.org/draft-07/schema#",
		  "properties": {
		    "customerDetails": {
		      "$id": "/properties/customerDetails",
		      "type": "object",
		      "properties": {
		        "customerId": {
		          "$id": "/properties/customerDetails/properties/customerId",
		          "type": "string",
		          "title": "The Customerid Schema ",
		          "default": "",
		          "examples": [
		            "BKJXOUG26541"
		          ]
		        },
		        "personalDetails": {
		          "$id": "/properties/customerDetails/properties/personalDetails",
		          "type": "object",
		          "properties": {
		            "firstName": {
		              "$id": "/properties/customerDetails/properties/personalDetails/properties/firstName",
		              "type": "string",
		              "title": "The Firstname Schema ",
		              "default": "",
		              "examples": [
		                "Pankaj"
		              ]
		            },
		            "lastName": {
		              "$id": "/properties/customerDetails/properties/personalDetails/properties/lastName",
		              "type": "string",
		              "title": "The Lastname Schema ",
		              "default": "",
		              "examples": [
		                "Pandey"
		              ]
		            },
		            "middleName": {
		              "$id": "/properties/customerDetails/properties/personalDetails/properties/middleName",
		              "type": "string",
		              "title": "The Middlename Schema ",
		              "default": "",
		              "examples": [
		                "Kumar"
		              ]
		            },
		            "email": {
		              "$id": "/properties/customerDetails/properties/personalDetails/properties/email",
		              "type": "string",
		              "title": "The Email Schema ",
		              "default": "",
		              "examples": [
		                "pankaj@ABC.com"
		              ]
		            },
		            "dateOfBirth": {
		              "$id": "/properties/customerDetails/properties/personalDetails/properties/dateOfBirth",
		              "type": "string",
		              "title": "The Dateofbirth Schema ",
		              "default": "",
		              "examples": [
		                "13-03-91"
		              ]
		            },
		            "gender": {
		              "$id": "/properties/customerDetails/properties/personalDetails/properties/gender",
		              "type": "string",
		              "title": "The Gender Schema ",
		              "default": "",
		              "examples": [
		                "M"
		              ]
		            },
		            "contact": {
		              "$id": "/properties/customerDetails/properties/personalDetails/properties/contact",
		              "type": "string",
		              "title": "The Contact Schema ",
		              "default": "",
		              "examples": [
		                "020-8029654321"
		              ]
		            }
		          }
		        },
		        "addressDetails": {
		          "$id": "/properties/customerDetails/properties/addressDetails",
		          "type": "object",
		          "properties": {
		            "addressLine1": {
		              "$id": "/properties/customerDetails/properties/addressDetails/properties/addressLine1",
		              "type": "string",
		              "title": "The Addressline1 Schema ",
		              "default": "",
		              "examples": [
		                "Cramson Lane"
		              ]
		            },
		            "addressLine2": {
		              "$id": "/properties/customerDetails/properties/addressDetails/properties/addressLine2",
		              "type": "string",
		              "title": "The Addressline2 Schema ",
		              "default": "",
		              "examples": [
		                "Story 01/2"
		              ]
		            },
		            "addressLine3": {
		              "$id": "/properties/customerDetails/properties/addressDetails/properties/addressLine3",
		              "type": "string",
		              "title": "The Addressline3 Schema ",
		              "default": "",
		              "examples": [
		                ""
		              ]
		            },
		            "city": {
		              "$id": "/properties/customerDetails/properties/addressDetails/properties/city",
		              "type": "string",
		              "title": "The City Schema ",
		              "default": "",
		              "examples": [
		                "Shamshing"
		              ]
		            },
		            "pinCode": {
		              "$id": "/properties/customerDetails/properties/addressDetails/properties/pinCode",
		              "type": "string",
		              "title": "The Pincode Schema ",
		              "default": "",
		              "examples": [
		                "877412"
		              ]
		            }
		          }
		        },
		        "professionalDetails": {
		          "$id": "/properties/customerDetails/properties/professionalDetails",
		          "type": "object",
		          "properties": {
		            "company": {
		              "$id": "/properties/customerDetails/properties/professionalDetails/properties/company",
		              "type": "string",
		              "title": "The Company Schema ",
		              "default": "",
		              "examples": [
		                "Scholarz"
		              ]
		            },
		            "designation": {
		              "$id": "/properties/customerDetails/properties/professionalDetails/properties/designation",
		              "type": "string",
		              "title": "The Designation Schema ",
		              "default": "",
		              "examples": [
		                "SE"
		              ]
		            },
		            "companyId": {
		              "$id": "/properties/customerDetails/properties/professionalDetails/properties/companyId",
		              "type": "string",
		              "title": "The Companyid Schema ",
		              "default": "",
		              "examples": [
		                "SCH-2958"
		              ]
		            },
		            "companyLocation": {
		              "$id": "/properties/customerDetails/properties/professionalDetails/properties/companyLocation",
		              "type": "string",
		              "title": "The Companylocation Schema ",
		              "default": "",
		              "examples": [
		                "Siyaal"
		              ]
		            }
		          }
		        }
		      }
		    }
		  }
		}
