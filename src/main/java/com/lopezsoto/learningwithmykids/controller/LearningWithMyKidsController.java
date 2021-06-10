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
 * JSON REST API. Exposes via web services the backend functionality of the Learning With My Kids
 */
@RestController
public class LearningWithMyKidsController {

    @Autowired
    LearningWithMyKidsDao learningWithMyKidsDao;

    /**
     * Gets a list of Activity Definitions
     * @return
     */
    @GetMapping("/activityDefinitions")
    public List<ActivityDefinition> getActivityDefinitions() {
        return learningWithMyKidsDao.getActivityDefinitions();
    }

    @GetMapping("/goalDefinitions")
    public List<GoalDefinition> getGoalDefinitions() {
        return learningWithMyKidsDao.getGoalDefinitions();
    }

    @GetMapping("/family/{familyId}")
    public Family getFamilyById(@PathVariable String familyId) {
        Family family = learningWithMyKidsDao.getFamilyById(familyId);

        if(family == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No Family found for ID '" + familyId + "'");
        }

        return family;
    }

    @GetMapping("/familyMembers/{familyId}")
    public List<FamilyMember> getFamilyMembersByFamilyId(@PathVariable String familyId) {
        return learningWithMyKidsDao.getFamilyMembersByFamilyId(familyId);
    }

    @GetMapping("/familyMember/family/{familyId}/member/{memberId}")
    public FamilyMember getFamilyMemberByFamilyIdAndMemberId(@PathVariable String familyId,
                                                             @PathVariable String memberId) {
        return learningWithMyKidsDao.getFamilyMemberByFamilyIdAndMemberId(familyId, memberId);
    }

    @GetMapping("/memberActions/family/{familyId}/member/{memberId}")
    public List<Action> getMemberActions(String familyId, String memberId) {
        return learningWithMyKidsDao.getMemberActions(familyId, memberId);
    }

    @PutMapping(value = "/family", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveFamily(@RequestBody Family family) {
        learningWithMyKidsDao.saveFamily(family);
    }

    @PutMapping(value = "/familyMember/family/{familyId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveFamilyMember(@PathVariable String familyId,
                                 @RequestBody FamilyMember familyMember) {
        learningWithMyKidsDao.saveFamilyMember(familyId, familyMember);
    }

    @PutMapping(value = "/action/family/{familyId}/member/{memberId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveAction(@PathVariable String familyId,
                           @PathVariable String memberId,
                           @RequestBody Action action) {
        learningWithMyKidsDao.saveAction(familyId, memberId, action);
    }

    @PutMapping(value = "/activityDefinition", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveActivityDefinition(@RequestBody ActivityDefinition activityDefinition) {
        learningWithMyKidsDao.saveActivityDefinition(activityDefinition);
    }

    @PutMapping(value = "/goalDefinition", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveGoalDefinition(@RequestBody GoalDefinition goalDefinition) {
        learningWithMyKidsDao.saveGoalDefinition(goalDefinition);
    }
}
