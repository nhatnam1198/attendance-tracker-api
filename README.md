## Introduction
This service is being used in Attendance Tracker app domain. Used to manage entities, persist data, communicate with recognition service and others.

## Prerequisites
- Apache-maven-3.8.1
- Spring boot 2.4.5
- Java 8

The recommended me JDK to build this project is JDK 1.8

JDK 1.8 Installation for Mac user:
```sh
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
brew install openjdk@8
```

## Build and run locally
### Set Environment Variables
For Windows user:
```
setx DATASOURCE_URL "YOUR DATASOURCE URL"
setx DATASOURCE_USERNAME "YOUR DATABASE USER NAME"
setx DATASOURCE_PASSWORD "YOUR DATABASE PASSWORD"
setx ISSUER_URI "Microsoft Indentify platform Issuer Endpoint"
```

For Mac User:
```
export DATASOURCE_URL="YOUR DATASOURCE URL"
export DATASOURCE_USERNAME="YOUR DATABASE USER NAME"
export DATASOURCE_PASSWORD="YOUR DATABASE PASSWORD"
export ISSUER_URI="Microsoft Indentify platform Issuer Endpoint"
```
For more information on how to use Microsoft Identity Platform, please go through this article below: 
`https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-overview`

Open terminal and navigate to the directory where you clone this project by using cd command.
```sh
../../attendance-tracker-api
```

Run the following command in the terminal window directory:
```sh
./mvnw spring-boot:run
```
After running this command, the service should be running on `localhost:8080`