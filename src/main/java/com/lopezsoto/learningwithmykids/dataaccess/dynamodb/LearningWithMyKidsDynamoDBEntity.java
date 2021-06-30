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

    /**
     * DynamoDB partition key.
     */
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

    /**
     * DynamoDB sort key.
     */
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

    /**
     * Learning goal.
     */
    private String goal;

    /**
     * goal activity.
     */
    private String activity;

    /**
     * Family's country info
     */
    private String country;

    /**
     * Family's city info
     */
    private String city;

    /**
     * Family member age
     */
    private Integer age;

    /**
     * Family member first name
     */
    private String firstName;

    /**
     * Family member last name
     */
    private String lastName;

    /**
     * Activity description
     */
    private String description;

    /**
     * Activity date
     */
    private String date;

    /**
     * Time investing in the learning activity
     */
    private Integer timeInvestedInMinutes;
}
