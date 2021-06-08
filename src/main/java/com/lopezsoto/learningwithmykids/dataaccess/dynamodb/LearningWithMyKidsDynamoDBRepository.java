package com.lopezsoto.learningwithmykids.dataaccess.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * LearningWithMyKids DynamoDB {@link org.springframework.data.repository.Repository}
 */
public interface LearningWithMyKidsDynamoDBRepository extends CrudRepository<LearningWithMyKidsDynamoDBEntity, LearningWithMyKidsDynamoDBId>,
        LearningWithMyKidsDynamoDBRepositoryCustom {

    /**
     * Queries DynamoDB table for all records with the same partitionKey.
     * @param partitionKey Table PK
     * @return List of records with the partitionKey param
     */
    List<LearningWithMyKidsDynamoDBEntity> findByPartitionKey(String partitionKey);

    /**
     * Queries DynamoDB table for all records with the same partitionKey and sortKey beginning with a given
     * prefix.
     * @param partitionKey Table PK
     * @param sortKeyPrefix sortKey Prefix
     * @return List of records that match the criteria.
     */
    List<LearningWithMyKidsDynamoDBEntity> findByPartitionKeyAndSortKeyStartingWith(String partitionKey, String sortKeyPrefix);

    /**
     * Queries DynamoDB table for all records beginning with the same sortKeyPrefix Prefix
     * prefix.
     * @param sortKeyPrefix sortKey Prefix
     * @return List of records that match the criteria.
     */
    List<LearningWithMyKidsDynamoDBEntity> findBySortKeyStartingWith(String sortKeyPrefix);


    /**
     * Queries DynamoDB table for all a record matching the composite primary key
     * @param partitionKey Primary Key
     * @param sortKey Sort Key
     * @return
     */
    Optional<LearningWithMyKidsDynamoDBEntity> findByPartitionKeyAndSortKey(String partitionKey, String sortKey);

    /**
     * To be used on integration testing.
     */
    @EnableScan
    Iterable<LearningWithMyKidsDynamoDBEntity> findAll();

    /**
     * To be used on integration testing.
     */
    @EnableScan
    void deleteAll();
}
