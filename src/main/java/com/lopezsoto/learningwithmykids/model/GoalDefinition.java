package com.lopezsoto.learningwithmykids.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data class representing the different learning goals
 */

@Data
@AllArgsConstructor
public class GoalDefinition {

    /**
     * Goal identifier.
     */
    private String goalDefinition;
}
