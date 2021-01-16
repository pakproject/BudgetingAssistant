INSTALLATION
To build standalone version with embedded Spring Boot, execute
mvn clean install

EXECUTION
Application jar is available in target folder after build. To execute, call
java -jar BudgetingAssistant-1.0-SNAPSHOT.jar
Application runs as a web server available under
http://localhost:8080


AVAILABLE API
METHOD      NAME        OPERTION                            PARAMETERS

POST        recharge    adds given amount to account        JSON string containing name and amount to transfer, e.g.
                                                            {name : "Wallet", amount : 100}
POST        transfer    transfers amount between accounts   JSON string containing source, target and amount to transfer, e.g.
                                                            {source : "Wallet", target : "Savings", amount : 100}
POST        checkAll    prints on server side all acounts   NONE
