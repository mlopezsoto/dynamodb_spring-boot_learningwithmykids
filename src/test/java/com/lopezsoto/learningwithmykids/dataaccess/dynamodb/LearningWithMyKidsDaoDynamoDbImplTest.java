package com.lopezsoto.learningwithmykids.dataaccess.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.lopezsoto.learningwithmykids.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Set of integration tests.
 */
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
class LearningWithMyKidsDaoDynamoDbImplTest {

    @Autowired
    private LearningWithMyKidsDaoDynamoDbImpl learningWithMyKidsDaoDynamoDb;

    @Autowired
    private LearningWithMyKidsDynamoDBRepository learningWithMyKidsDynamoDBRepository;


    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    /* Integration test data */
    private static String TEST_MEMBER_ID_1 = "PATRICIA";
    private static String TEST_MEMBER_ID_2 = "RAMO";
    private static String TEST_MEMBER_ID_3 = "JAMES";
    private static String TEST_MEMBER_ID_4 = "SANDRA";

    private static String TEST_FAMILY_ID_1 = "MONTOYA";
    private static String TEST_FAMILY_ID_2 = "OH";

    private static Action TEST_ACTION_1;
    private static Action TEST_ACTION_2;
    private static Action TEST_ACTION_3;
    private static Action TEST_ACTION_4;
    private static Action TEST_ACTION_5;


    private static FamilyMember TEST_FAMILY_MEMBER_1;
    private static FamilyMember TEST_FAMILY_MEMBER_2;
    private static FamilyMember TEST_FAMILY_MEMBER_3;
    private static FamilyMember TEST_FAMILY_MEMBER_4;

    private static Family TEST_FAMILY_1;
    private static Family TEST_FAMILY_2;

    @BeforeAll
    static void setUpTestData() {
        TEST_ACTION_1 = new Action("1",
                new GoalDefinition("BASKETBALL"),
                new ActivityDefinition("FAMILY-PRACTICE"),
                "Park play with family",
                LocalDate.parse("2021-07-06"),
                45);

        TEST_ACTION_2 = new Action("2",
                new GoalDefinition("BASKETBALL"),
                new ActivityDefinition("FAMILY-PRACTICE"),
                "Park play with family",
                LocalDate.parse("2021-07-07"),
                60);

        TEST_ACTION_3 = new Action("3",
                new GoalDefinition("BASKETBALL"),
                new ActivityDefinition("GAME"),
                "Schools tournament",
                LocalDate.parse("2021-07-09"),
                90);

        TEST_ACTION_4 = new Action("4",
                new GoalDefinition("COOKING"),
                new ActivityDefinition("STUDY"),
                "Cooking School",
                LocalDate.parse("2021-07-10"),
                90);

        TEST_ACTION_5 = new Action("5",
                new GoalDefinition("COOKING"),
                new ActivityDefinition("FAMILY-DINNER"),
                "Preparing Family BBQ",
                LocalDate.parse("2021-07-11"),
                120);

        List<Action> TEST_ACTION_List1 = List.of(TEST_ACTION_1, TEST_ACTION_2, TEST_ACTION_3);
        List<Action> TEST_ACTION_List2 = List.of(TEST_ACTION_4, TEST_ACTION_5);

        TEST_FAMILY_MEMBER_1 = new FamilyMember(TEST_MEMBER_ID_1,
                17,
                "PATRICIA MARIA",
                "MONTOYA",
                TEST_ACTION_List1);

        TEST_FAMILY_MEMBER_2 = new FamilyMember(TEST_MEMBER_ID_2,
                14,
                "RAMONA",
                "MONTOYA",
                TEST_ACTION_List2);

        TEST_FAMILY_MEMBER_3 = new FamilyMember(TEST_MEMBER_ID_3,
                14,
                "JAMES CAMERON",
                "OH",
                Collections.emptyList());

        TEST_FAMILY_MEMBER_4 = new FamilyMember(TEST_MEMBER_ID_4,
                14,
                "SANDRA",
                "OH",
                Collections.emptyList());

        List<FamilyMember> TEST_FAMILY_MEMBER_List1 = List.of(TEST_FAMILY_MEMBER_1, TEST_FAMILY_MEMBER_2);
        List<FamilyMember> TEST_FAMILY_MEMBER_List2 = List.of(TEST_FAMILY_MEMBER_3, TEST_FAMILY_MEMBER_4);

        TEST_FAMILY_1 = new Family(TEST_FAMILY_ID_1,
                "COLOMBIA",
                "CARTAGO",
                TEST_FAMILY_MEMBER_List1);

        TEST_FAMILY_2 = new Family(TEST_FAMILY_ID_2,
                "AUSTRALIA",
                "MUSWELLBROOK",
                TEST_FAMILY_MEMBER_List2);

    }

    @BeforeEach
    void setUp() {
        learningWithMyKidsDynamoDBRepository.deleteAll();
    }

    @Test
    void saveAndGetActivityDefinitions() {
        ActivityDefinition activityDefinition1 = new ActivityDefinition("MYActDef1");
        learningWithMyKidsDaoDynamoDb.saveActivityDefinition(activityDefinition1);
        List<ActivityDefinition> activityDefinitions = learningWithMyKidsDaoDynamoDb.getActivityDefinitions();
        Assertions.assertEquals(1, activityDefinitions.size());
        Assertions.assertEquals(activityDefinition1, activityDefinitions.get(0));

        ActivityDefinition activityDefinition2 = new ActivityDefinition("MYActDef2");
        learningWithMyKidsDaoDynamoDb.saveActivityDefinition(activityDefinition2);
        activityDefinitions = learningWithMyKidsDaoDynamoDb.getActivityDefinitions();
        Assertions.assertEquals(2, activityDefinitions.size());

        Assertions.assertTrue(activityDefinitions.stream()
                .anyMatch(activityDefinition -> "MYActDef2".equals(activityDefinition.getName())));

    }

    @Test
    void saveAndGetGoalDefinitions() {
        GoalDefinition goalDefinition = new GoalDefinition("MYGoalDef1");
        learningWithMyKidsDaoDynamoDb.saveGoalDefinition(goalDefinition);

        List<GoalDefinition> goalDefinitions = learningWithMyKidsDaoDynamoDb.getGoalDefinitions();
        Assertions.assertEquals(1, goalDefinitions.size());
        Assertions.assertEquals(goalDefinition, goalDefinitions.get(0));
    }

    @Test
    void getFamilyById() {
        learningWithMyKidsDaoDynamoDb.saveFamily(TEST_FAMILY_1);
        Family family = learningWithMyKidsDaoDynamoDb.getFamilyById(TEST_FAMILY_ID_1);

        Assertions.assertEquals(TEST_FAMILY_1, family);
    }

    @Test
    void getFamilyByNonExistentId() {
        Family family = learningWithMyKidsDaoDynamoDb.getFamilyById(TEST_FAMILY_ID_1);
        Assertions.assertNull(family);
    }

    @Test
    void getFamilyMembersByFamilyId() {
        learningWithMyKidsDaoDynamoDb.saveFamilyMember(TEST_FAMILY_ID_2, TEST_FAMILY_MEMBER_3);
        learningWithMyKidsDaoDynamoDb.saveFamilyMember(TEST_FAMILY_ID_2, TEST_FAMILY_MEMBER_4);

        List<FamilyMember> familyMembers = learningWithMyKidsDaoDynamoDb.getFamilyMembersByFamilyId(TEST_FAMILY_ID_2);
        Assertions.assertEquals(2, familyMembers.size());

        Assertions.assertTrue(familyMembers.stream()
                .anyMatch(familyMember -> TEST_FAMILY_MEMBER_3.equals(familyMember)));
        Assertions.assertTrue(familyMembers.stream()
                .anyMatch(familyMember -> TEST_FAMILY_MEMBER_4.equals(familyMember)));

    }

    @Test
    void getFamilyMembersByNonExistentId() {
        List<FamilyMember> familyMembers = learningWithMyKidsDaoDynamoDb.getFamilyMembersByFamilyId(TEST_FAMILY_ID_2);
        Assertions.assertEquals(0, familyMembers.size());
    }

    @Test
    void getMemberActions() {
        learningWithMyKidsDaoDynamoDb.saveFamilyMember(TEST_FAMILY_ID_1, TEST_FAMILY_MEMBER_2);
        List<Action> actions = learningWithMyKidsDaoDynamoDb.getMemberActions(TEST_FAMILY_ID_1, TEST_FAMILY_MEMBER_2.getId());
        Assertions.assertEquals(2, actions.size());

        Assertions.assertTrue(actions.stream()
                .anyMatch(action -> TEST_ACTION_4.equals(action)));
        Assertions.assertTrue(actions.stream()
                .anyMatch(action -> TEST_ACTION_5.equals(action)));

        learningWithMyKidsDaoDynamoDb.saveFamilyMember(TEST_FAMILY_ID_2, TEST_FAMILY_MEMBER_4);
        actions = learningWithMyKidsDaoDynamoDb.getMemberActions(TEST_FAMILY_ID_2, TEST_FAMILY_MEMBER_4.getId());
        Assertions.assertEquals(0, actions.size());
    }

    @Test
    void saveFamilyNullParam() {
        Assertions.assertThrows(NullPointerException.class, () ->  learningWithMyKidsDaoDynamoDb.saveFamily(null));
    }

    @Test
    void saveFamilyMember() {

        learningWithMyKidsDaoDynamoDb.saveFamilyMember(TEST_FAMILY_ID_1, TEST_FAMILY_MEMBER_1);

        FamilyMember dynamoDbFamilyMember = learningWithMyKidsDaoDynamoDb.getFamilyMemberByFamilyIdAndMemberId(
                TEST_FAMILY_ID_1, TEST_MEMBER_ID_1);

        Assertions.assertEquals(TEST_FAMILY_MEMBER_1, dynamoDbFamilyMember);
    }

    @Test
    void saveFamily() {
        learningWithMyKidsDaoDynamoDb.saveFamily(TEST_FAMILY_2);
        Family family = learningWithMyKidsDaoDynamoDb.getFamilyById(TEST_FAMILY_ID_2);

        Assertions.assertEquals(TEST_FAMILY_2, family);
    }

    @Test
    void saveAction() {
        learningWithMyKidsDaoDynamoDb.saveAction(TEST_FAMILY_ID_1, TEST_MEMBER_ID_1, TEST_ACTION_1);
        List<Action> actions = learningWithMyKidsDaoDynamoDb.getMemberActions(TEST_FAMILY_ID_1, TEST_MEMBER_ID_1);

        Assertions.assertEquals(1, actions.size());
        Assertions.assertEquals(TEST_ACTION_1, actions.get(0));

    }

    private void deleteTestTable() {
        try {
            DeleteTableResult deleteTableResult = amazonDynamoDB.deleteTable("TestLearningWithMyKids");
            log.info("Table deleted");
        } catch (ResourceNotFoundException ex) {
            log.debug("While trying to delete 'TestLearningWithMyKids' found it does not exist.");
        }
    }

    private void createTestTable() throws InterruptedException {
        List<KeySchemaElement> keySchemaElements = Arrays.asList(
                new KeySchemaElement("partitionKey", KeyType.HASH), // Partition                // key
                new KeySchemaElement("sortKey", KeyType.RANGE));

        List<AttributeDefinition> attributeDefinitions = Arrays.asList(
                new AttributeDefinition("partitionKey", ScalarAttributeType.S),
                new AttributeDefinition("sortKey", ScalarAttributeType.S));

        try {
            CreateTableResult createTableResult = amazonDynamoDB.createTable(attributeDefinitions,
                    "TestLearningWithMyKids",
                    keySchemaElements,
                    new ProvisionedThroughput(5L, 5L));
            TableUtils.waitUntilActive(amazonDynamoDB, "TestLearningWithMyKids");
            log.info("Table created");
        } catch (ResourceInUseException ex) {
            log.warn("While trying to create 'TestLearningWithMyKids' found it already exists.");
        }

    }

}