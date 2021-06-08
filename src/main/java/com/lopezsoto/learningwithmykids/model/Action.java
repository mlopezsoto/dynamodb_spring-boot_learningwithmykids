package com.lopezsoto.learningwithmykids.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data class representing actions performed by family members in order to get closer to their learning goals.
 */

@Data
@AllArgsConstructor
public class Action {

    private String id;

    private GoalDefinition goalDefinition;

    private ActivityDefinition activityDefinition;

    private String description;

    private LocalDate date;

    private int timeInvestedInMinutes;


}
