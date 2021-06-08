package com.lopezsoto.learningwithmykids.dataaccess.dynamodb;

import org.socialsignin.spring.data.dynamodb.core.DynamoDBTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LearningWithMyKidsDynamoDBRepositoryCustomImpl implements LearningWithMyKidsDynamoDBRepositoryCustom {

    @Autowired
    private DynamoDBTemplate dynamoDBTemplate;


}
