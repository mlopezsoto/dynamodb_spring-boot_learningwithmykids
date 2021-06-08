package com.lopezsoto.learningwithmykids.dataaccess.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Composite key for table LearningWithMyKids
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearningWithMyKidsDynamoDBId implements Serializable {
    private static final long serialVersionUID = 1L;

    @DynamoDBHashKey
    private String partitionKey;

    @DynamoDBRangeKey
    private String sortKey;


}
