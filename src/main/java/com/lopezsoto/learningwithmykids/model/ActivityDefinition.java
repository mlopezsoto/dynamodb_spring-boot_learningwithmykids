package com.lopezsoto.learningwithmykids.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Data class representing the activities that can be performed in order to achieve the learning goals.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDefinition {

    /**
     * Activity identifier
     */
    private String name;
}
