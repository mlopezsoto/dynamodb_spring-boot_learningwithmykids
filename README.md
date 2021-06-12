# Learning With My Kids

This is a simple set of REST endpoints providing basic functionality to store and retrieve information about activities that help members of a family achieve a "learning" goal.

The design of the database follows some of the patters expressed here: 
- https://www.dynamodbguide.com/
- https://www.alexdebrie.com/posts/dynamodb-one-to-many/

Here is an example of the DynamoDB table: https://docs.google.com/spreadsheets/d/1wmnaqWdzVnjSVOqfkwL6l0B_hLI6aoTlyL5YDi5yb6I/edit?usp=sharing

Sample result of DynamoDB describe-table command:

```
 aws dynamodb describe-table --table-name LearningWithMyKids
{
    "Table": {
        "AttributeDefinitions": [
            {
                "AttributeName": "partitionKey",
                "AttributeType": "S"
            },
            {
                "AttributeName": "sortKey",
                "AttributeType": "S"
            }
        ],
        "TableName": "LearningWithMyKids",
        "KeySchema": [
            {
                "AttributeName": "partitionKey",
                "KeyType": "HASH"
            },
            {
                "AttributeName": "sortKey",
                "KeyType": "RANGE"
            }
        ],
        "TableStatus": "ACTIVE",
        "CreationDateTime": "2021-06-04T18:11:39.822000+10:00",
        "ProvisionedThroughput": {
            "LastDecreaseDateTime": "2021-06-04T18:12:44.019000+10:00",
            "NumberOfDecreasesToday": 0,
            "ReadCapacityUnits": 1,
            "WriteCapacityUnits": 1
        },
        "TableSizeBytes": 1963,
        "ItemCount": 23,
        "TableArn": "arn:aws:dynamodb:ap-southeast-2:321459183723:table/LearningWithMyKids",
        "TableId": "b81ea62c-4224-4832-abd2-e4f5eb61debb"
    }
}
```

The technology stack includes:

- Java 8+
- Spring Boot
- Spring Data
- DynamoDB

# Running the application

You need to have a AWS DynamoDB table (and an AWS account of course) named **LearningWithMyKids** with a partition key named **partitionKey** and a sort key named **sortKey**. You also need to provide your AWS credentials in the file **application.properties**. After that, you you can run the app as a regular Spring Boot Application. 

There is a Swagger UI that can be used to explore the API. i.e. localhost: http://localhost:8080/swagger-ui/


**_MORE DOCUMENTATION COMMING_**
