package com.lopezsoto.learningwithmykids.dataaccess.dynamodb;

import com.lopezsoto.learningwithmykids.dataaccess.LearningWithMyKidsDao;
import com.lopezsoto.learningwithmykids.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * LearningWithMyKidsDao implementation using a DynamoDB database.
 */
@Service

public class LearningWithMyKidsDaoDynamoDbImpl implements LearningWithMyKidsDao {

    private static String GOAL_DEFINITION_PK = "GOALDEF";
    private static String ACTIVITY_DEFINITION_PK = "ACTIVITYDEF";
    private static String FAMILY_ID_PREFIX = "FAM#";
    private static String FAMILY_MEMBER_ID_PREFIX = "MEMBER#";
    private static String ACTION_ID_PREFIX = "ACTION#";
    private static String FAMILY_METADATA_SK = "FAM-METADATA";

    @Autowired
    private LearningWithMyKidsDynamoDBRepository learningWithMyKidsDynamoDBRepository;

    @Override
    public List<ActivityDefinition> getActivityDefinitions() {
        return learningWithMyKidsDynamoDBRepository.findByPartitionKey(ACTIVITY_DEFINITION_PK)
                .stream()
                .map(learningWithMyKidsDynamoDBEntity -> new ActivityDefinition(learningWithMyKidsDynamoDBEntity.getSortKey()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GoalDefinition> getGoalDefinitions() {
        return learningWithMyKidsDynamoDBRepository.findByPartitionKey(GOAL_DEFINITION_PK)
                .stream()
                .map(learningWithMyKidsDynamoDBEntity -> new GoalDefinition(learningWithMyKidsDynamoDBEntity.getSortKey()))
                .collect(Collectors.toList());
    }


    @Override
    public Family getFamilyById(String familyId) {

        Optional<LearningWithMyKidsDynamoDBEntity> learningWithMyKidsDynamoDBEntityOptional = learningWithMyKidsDynamoDBRepository.
               findByPartitionKeyAndSortKey(FAMILY_ID_PREFIX + familyId, FAMILY_METADATA_SK);

        if(learningWithMyKidsDynamoDBEntityOptional.isEmpty()){
            return null;
        } else {
            LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity = learningWithMyKidsDynamoDBEntityOptional.get();
            return new Family(familyId,
                    learningWithMyKidsDynamoDBEntity.getCountry(),
                    learningWithMyKidsDynamoDBEntity.getCity(),
                    getFamilyMembersByFamilyId(familyId));
        }
    }

    @Override
    public List<FamilyMember> getFamilyMembersByFamilyId(String familyId) {
        return learningWithMyKidsDynamoDBRepository.findByPartitionKeyAndSortKeyStartingWith(
                FAMILY_ID_PREFIX + familyId, FAMILY_MEMBER_ID_PREFIX)
                .stream()
                .map(this::buildFamilyMember)
                .collect(Collectors.toList());
    }

    @Override
    public FamilyMember getFamilyMemberByFamilyIdAndMemberId(String familyId, String memberId) {
        Optional<LearningWithMyKidsDynamoDBEntity> learningWithMyKidsDynamoDBEntity = learningWithMyKidsDynamoDBRepository.findByPartitionKeyAndSortKey(
                FAMILY_ID_PREFIX + familyId, FAMILY_MEMBER_ID_PREFIX + memberId);

        if(learningWithMyKidsDynamoDBEntity.isPresent()) {
            return buildFamilyMember(learningWithMyKidsDynamoDBEntity.get());
        } else {
            return null;
        }
    }

    @Override
    public List<Action> getMemberActions(String familyId, String memberId) {
        String partitionKey = FAMILY_ID_PREFIX + familyId + "#" + FAMILY_MEMBER_ID_PREFIX + memberId;
        return getMemberActionsByPartitionKey(partitionKey);
    }

    @Override
    public void saveFamily(@NonNull Family family) {
        LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity = new LearningWithMyKidsDynamoDBEntity();
        learningWithMyKidsDynamoDBEntity.setPartitionKey(FAMILY_ID_PREFIX + family.getId());
        learningWithMyKidsDynamoDBEntity.setSortKey(FAMILY_METADATA_SK);
        learningWithMyKidsDynamoDBEntity.setCountry(family.getCountry());
        learningWithMyKidsDynamoDBEntity.setCity(family.getCity());
        learningWithMyKidsDynamoDBRepository.save(learningWithMyKidsDynamoDBEntity);

        family.getMembers().stream().forEach(familyMember -> saveFamilyMember(family.getId(), familyMember));
    }

    @Override
    public void saveFamilyMember(String familyId, FamilyMember familyMember) {
        LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity = new LearningWithMyKidsDynamoDBEntity();
        learningWithMyKidsDynamoDBEntity.setPartitionKey(FAMILY_ID_PREFIX + familyId);
        learningWithMyKidsDynamoDBEntity.setSortKey(FAMILY_MEMBER_ID_PREFIX + familyMember.getId());
        learningWithMyKidsDynamoDBEntity.setAge(familyMember.getAge());
        learningWithMyKidsDynamoDBEntity.setFirstName(familyMember.getFirstName());
        learningWithMyKidsDynamoDBEntity.setLastName(familyMember.getLastName());
        learningWithMyKidsDynamoDBRepository.save(learningWithMyKidsDynamoDBEntity);

        familyMember.getActions().stream().forEach(action -> saveAction(familyId, familyMember.getId(), action));
    }

    @Override
    public void saveAction(String familyId, String memberId, Action action) {
        LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity = new LearningWithMyKidsDynamoDBEntity();
        learningWithMyKidsDynamoDBEntity.setPartitionKey(FAMILY_ID_PREFIX + familyId + "#" + FAMILY_MEMBER_ID_PREFIX + memberId);
        learningWithMyKidsDynamoDBEntity.setSortKey(ACTION_ID_PREFIX + action.getId());
        learningWithMyKidsDynamoDBEntity.setGoal(action.getGoalDefinition().getGoalDefinition());
        learningWithMyKidsDynamoDBEntity.setActivity(action.getActivityDefinition().getActivityDefinition());
        learningWithMyKidsDynamoDBEntity.setDescription(action.getDescription());
        learningWithMyKidsDynamoDBEntity.setDate(action.getDate().toString());
        learningWithMyKidsDynamoDBEntity.setTimeInvestedInMinutes(action.getTimeInvestedInMinutes());
        learningWithMyKidsDynamoDBRepository.save(learningWithMyKidsDynamoDBEntity);

        saveActivityDefinition(action.getActivityDefinition());
        saveGoalDefinition(action.getGoalDefinition());
    }

    @Override
    public void saveActivityDefinition(ActivityDefinition activityDefinition) {
        LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity = new LearningWithMyKidsDynamoDBEntity();
        learningWithMyKidsDynamoDBEntity.setPartitionKey(ACTIVITY_DEFINITION_PK);
        learningWithMyKidsDynamoDBEntity.setSortKey(activityDefinition.getActivityDefinition());
        learningWithMyKidsDynamoDBRepository.save(learningWithMyKidsDynamoDBEntity);
    }

    @Override
    public void saveGoalDefinition(GoalDefinition goalDefinition) {
        LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity = new LearningWithMyKidsDynamoDBEntity();
        learningWithMyKidsDynamoDBEntity.setPartitionKey(GOAL_DEFINITION_PK);
        learningWithMyKidsDynamoDBEntity.setSortKey(goalDefinition.getGoalDefinition());
        learningWithMyKidsDynamoDBRepository.save(learningWithMyKidsDynamoDBEntity);
    }

    private List<Action> getMemberActionsByPartitionKey(String partitionKey) {
        return learningWithMyKidsDynamoDBRepository.findByPartitionKey(partitionKey)
                .stream()
                .map(learningWithMyKidsDynamoDBEntity -> buildAction(learningWithMyKidsDynamoDBEntity))
                .collect(Collectors.toList());
    }


    private Action buildAction(LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity) {
        String actionId = learningWithMyKidsDynamoDBEntity.getSortKey().substring(ACTION_ID_PREFIX.length());
        return new Action(actionId,
                new GoalDefinition(learningWithMyKidsDynamoDBEntity.getGoal()),
                new ActivityDefinition(learningWithMyKidsDynamoDBEntity.getActivity()),
                learningWithMyKidsDynamoDBEntity.getDescription(),
                LocalDate.parse(learningWithMyKidsDynamoDBEntity.getDate()),
                learningWithMyKidsDynamoDBEntity.getTimeInvestedInMinutes());
    }

    private FamilyMember buildFamilyMember(LearningWithMyKidsDynamoDBEntity learningWithMyKidsDynamoDBEntity){
        String familyMemberId = learningWithMyKidsDynamoDBEntity.getSortKey().substring(ACTION_ID_PREFIX.length());
        return new FamilyMember(
                familyMemberId,
                learningWithMyKidsDynamoDBEntity.getAge(),
                learningWithMyKidsDynamoDBEntity.getFirstName(),
                learningWithMyKidsDynamoDBEntity.getLastName(),
                getMemberActionsByPartitionKey(learningWithMyKidsDynamoDBEntity.getPartitionKey() + "#" +
                        learningWithMyKidsDynamoDBEntity.getSortKey()));
    }

}
