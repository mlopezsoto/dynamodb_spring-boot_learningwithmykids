package com.lopezsoto.learningwithmykids.dataaccess;

import com.lopezsoto.learningwithmykids.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Defines the data access methods
 */
@Service
public interface LearningWithMyKidsDao {

    /**
     * Gets all activity definitions.
     * @return List with all the @{@link ActivityDefinition} from the data layer.
     */
    List<ActivityDefinition> getActivityDefinitions();

    /**
     * Gets all goal definitions.
     * @return List with all the  {@link com.lopezsoto.learningwithmykids.model.GoalDefinition} from the data layer.
     */
    List<GoalDefinition> getGoalDefinitions();

    /**
     * Gets a {@link Family} by its ID
     * @param familyId Family unique ID
     * @return a {@link Family} instance or null if not found.
     */
    Family getFamilyById(String familyId);

    /**
     * Gets all family members of a given family.
     */
    List<FamilyMember> getFamilyMembersByFamilyId(String familyId);

    FamilyMember getFamilyMemberByFamilyIdAndMemberId(String familyId, String memberId);

    /**
     * Gets all actions for a family member.
     * @param familyId
     * @param memberId
     * @return
     */
    List<Action> getMemberActions(String familyId, String memberId);

    void saveFamily(Family family);

    void saveFamilyMember(String familyId, FamilyMember familyMember);

    void saveAction(String familyId, String memberId, Action action);

    void saveActivityDefinition(ActivityDefinition activityDefinition);

    void saveGoalDefinition(GoalDefinition goalDefinition);
}
