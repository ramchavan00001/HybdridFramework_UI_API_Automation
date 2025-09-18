# HybdridFramework_UI_API_Automation 
# Author: Ram Chavan - Senior QA Engineer
complete, ready-to-drop-into-a-Maven project implementing the Hybrid UI + API Test Automation Framework

I used Sauce Demo (https://www.saucedemo.com) as the UI AUT and ReqRes (https://reqres.in) as the API AUT (both are public/open for testing). 

The framework includes:
src/main/java/com/yourcompany

base (TestBase, UI & API bootstrapping, DriverFactory)

pages (POM pages with constructors)

api/clients (RestAssured clients)

api/models (POJOs)

utils (ConfigReader, JsonUtils, APIUtils)

resources (Config reader class)

src/test/java/com/yourcompany

ui tests (LoginUITests)

api tests (UserAPITests)

integration tests (end-to-end hybrid test)

src/test/resources

config files, testdata (users.json), schemas folder

reporting (ExtentReports hooks in TestBase), log4j usage

pom.xml, testng.xml
