package com.lopezsoto.learningwithmykids.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * Data class representing the activities that can be performed in order to achieve the learning goals.
 */

@Data
@AllArgsConstructor
public class ActivityDefinition {

    /**
     * Activity identifier
     */
    private String activityDefinition;
}
