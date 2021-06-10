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
     * Empty list if there are no {@link ActivityDefinition}
     */
    List<ActivityDefinition> getActivityDefinitions();

    /**
     * Gets all goal definitions.
     * @return List with all the  {@link com.lopezsoto.learningwithmykids.model.GoalDefinition} from the data layer.
     * Empty list if there are no {@link GoalDefinition}
     */
    List<GoalDefinition> getGoalDefinitions();

    /**
     * Gets a {@link Family} by its ID. Return null if not found.
     * @param familyId Family unique ID
     * @return a {@link Family} instance or null if not found.
     */
    Family getFamilyById(String familyId);

    /**
     * Returns all family members for a given family.
     * @param familyId Unique family ID
     * @return List of {@link FamilyMember}. Empty list of none found.
     */
    List<FamilyMember> getFamilyMembersByFamilyId(String familyId);


    /**
     * Returns a single {@link FamilyMember} for the given parameters.
     * @param familyId Unique Family ID
     * @param memberId Unique member ID for this family. There can be members with the same ID in other families.
     * @return null if not found.
     */
    FamilyMember getFamilyMemberByFamilyIdAndMemberId(String familyId, String memberId);

    /**
     * Gets all actions for a family member.
     * @param familyId Unique Family ID
     * @param memberId Unique member ID for this family. There can be members with the same ID in other families.
     * @return List with {@link Action}s. Empty list of none found.
     */
    List<Action> getMemberActions(String familyId, String memberId);

    /**
     * Persists a {@link Family} including related objects.
     * @param family {@link Family} to be persisted.
     */
    void saveFamily(Family family);

    /**
     * Persists a {@link FamilyMember} including related objects.
     * @param familyId ID of the family this memebers belongs to.
     * @param familyMember {@link FamilyMember} to be persisted.
     */
    void saveFamilyMember(String familyId, FamilyMember familyMember);

    /**
     * Persis a member's {@link Action}
     * @param familyId Unique Family ID
     * @param memberId Unique member ID for this family. There can be members with the same ID in other families.
     * @param action
     */
    void saveAction(String familyId, String memberId, Action action);

    /**
     * Persists an {@link ActivityDefinition}
     * @param activityDefinition {@link ActivityDefinition} to be persisted.
     */
    void saveActivityDefinition(ActivityDefinition activityDefinition);

    /**
     * Persists an {@link GoalDefinition}
     * @param goalDefinition {@link GoalDefinition} to be persisted.
     */
    void saveGoalDefinition(GoalDefinition goalDefinition);
}
