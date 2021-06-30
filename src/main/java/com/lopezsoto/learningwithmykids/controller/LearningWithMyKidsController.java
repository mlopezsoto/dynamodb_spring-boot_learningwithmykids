package com.lopezsoto.learningwithmykids.controller;

import com.lopezsoto.learningwithmykids.dataaccess.LearningWithMyKidsDao;
import com.lopezsoto.learningwithmykids.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * JSON REST API. Exposes via web services the backend functionality of the Learning With My Kids Application.
 */
@RestController
public class LearningWithMyKidsController {

    @Autowired
    LearningWithMyKidsDao learningWithMyKidsDao;

    /**
     * Gets a list of Activity Definitions
     * @return List of list of Activity Definitions. Returns an empty list if there are no activity definitions.
     */
    @GetMapping("/activityDefinitions")
    public List<ActivityDefinition> getActivityDefinitions() {
        return learningWithMyKidsDao.getActivityDefinitions();
    }

    /**
     * Gets a list of Goal Definitions
     * @return List of list of Goal Definitions. Returns an empty list if there are no goal definitions.
     */
    @GetMapping("/goalDefinitions")
    public List<GoalDefinition> getGoalDefinitions() {
        return learningWithMyKidsDao.getGoalDefinitions();
    }

    /**
     * Returns an specific Family with all its dependencies: List of family members, and list of actions for
     * each member.
     * @param familyId Unique ID of the family.
     * @return A Family object including related objects.
     */
    @GetMapping("/family/{familyId}")
    public Family getFamilyById(@PathVariable String familyId) {
        Family family = learningWithMyKidsDao.getFamilyById(familyId);

        if(family == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Family found for ID '" + familyId + "'");
        }

        return family;
    }

    /**
     * Returns the list of family members for a given family.
     * @param familyId Unique ID of the family.
     * @return list of family members including related objects, such as list of activities.
     */
    @GetMapping("/familyMembers/{familyId}")
    public List<FamilyMember> getFamilyMembersByFamilyId(@PathVariable String familyId) {
        return learningWithMyKidsDao.getFamilyMembersByFamilyId(familyId);
    }

    /**
     * Returns a specific family member.
     * @param familyId Unique ID of the family.
     * @param memberId Unique ID of the family member in a family.
     * @return Family member info including related objects, such as list of activities.
     */
    @GetMapping("/familyMember/family/{familyId}/member/{memberId}")
    public FamilyMember getFamilyMemberByFamilyIdAndMemberId(@PathVariable String familyId,
                                                             @PathVariable String memberId) {
        return learningWithMyKidsDao.getFamilyMemberByFamilyIdAndMemberId(familyId, memberId);
    }

    /**
     * Returns a list of actions for a specific family member.
     * @param familyId Unique ID of the family.
     * @param memberId Unique ID of the family member in a family.
     * @return list of actions for a specific family member including related objects, such as list of activities.
     */
    @GetMapping("/memberActions/family/{familyId}/member/{memberId}")
    public List<Action> getMemberActions(String familyId, String memberId) {
        return learningWithMyKidsDao.getMemberActions(familyId, memberId);
    }

    /**
     * Creates or updates a family.
     * @param family Family data
     */
    @PutMapping(value = "/family", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveFamily(@RequestBody Family family) {
        learningWithMyKidsDao.saveFamily(family);
    }

    /**
     * Creates or updates a family member.
     * @param familyId Family ID
     * @param familyMember  family member data
     */
    @PutMapping(value = "/familyMember/family/{familyId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveFamilyMember(@PathVariable String familyId,
                                 @RequestBody FamilyMember familyMember) {
        learningWithMyKidsDao.saveFamilyMember(familyId, familyMember);
    }

    /**
     * Creates or updates a family member action.
     * @param familyId Family ID
     * @param memberId Member ID
     * @param action Action data
     */
    @PutMapping(value = "/action/family/{familyId}/member/{memberId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveAction(@PathVariable String familyId,
                           @PathVariable String memberId,
                           @RequestBody Action action) {
        learningWithMyKidsDao.saveAction(familyId, memberId, action);
    }

    /**
     * Creates or updates an activity definition.
     * @param activityDefinition Activity data
     */
    @PutMapping(value = "/activityDefinition", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveActivityDefinition(@RequestBody ActivityDefinition activityDefinition) {
        learningWithMyKidsDao.saveActivityDefinition(activityDefinition);
    }

    /**
     * Creates or updates an goal definition.
     * @param goalDefinition Goal Data
     */
    @PutMapping(value = "/goalDefinition", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveGoalDefinition(@RequestBody GoalDefinition goalDefinition) {
        learningWithMyKidsDao.saveGoalDefinition(goalDefinition);
    }
}
