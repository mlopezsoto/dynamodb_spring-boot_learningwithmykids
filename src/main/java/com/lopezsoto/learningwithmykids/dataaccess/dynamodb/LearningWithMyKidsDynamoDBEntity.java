package com.lopezsoto.learningwithmykids.dataaccess.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 * Entity class mapping the LearningWithMyKids DynamoDB table.
 */

@DynamoDBTable(tableName = "LearningWithMyKids")
@Data
@NoArgsConstructor
public class LearningWithMyKidsDynamoDBEntity {

    /**
     * DynamoDB composite key.
     */
    @Id
    @DynamoDBIgnore
    private LearningWithMyKidsDynamoDBId learningWithMyKidsDynamoDBId;

    @DynamoDBHashKey(attributeName = "partitionKey")
    public String getPartitionKey() {
        return learningWithMyKidsDynamoDBId != null ? learningWithMyKidsDynamoDBId.getPartitionKey() : null;
    }

    public void setPartitionKey(String partitionKey) {
        if (learningWithMyKidsDynamoDBId == null) {
            learningWithMyKidsDynamoDBId = new LearningWithMyKidsDynamoDBId();
        }
        learningWithMyKidsDynamoDBId.setPartitionKey(partitionKey);
    }

    @DynamoDBRangeKey(attributeName = "sortKey")
    public String getSortKey() {
        return learningWithMyKidsDynamoDBId != null ? learningWithMyKidsDynamoDBId.getSortKey() : null;
    }

    public void setSortKey(String sortKey) {
        if (learningWithMyKidsDynamoDBId == null) {
            learningWithMyKidsDynamoDBId = new LearningWithMyKidsDynamoDBId();
        }
        learningWithMyKidsDynamoDBId.setSortKey(sortKey);
    }

    private String goal;

    private String activity;

    private String country;

    private String city;

    private Integer age;

    private String firstName;

    private String lastName;

    private String description;

    private String date;

    private Integer timeInvestedInMinutes;
}
