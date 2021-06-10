package com.lopezsoto.learningwithmykids.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data class representing the different learning goals
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDefinition {

    /**
     * Goal identifier.
     */
    private String name;
}
