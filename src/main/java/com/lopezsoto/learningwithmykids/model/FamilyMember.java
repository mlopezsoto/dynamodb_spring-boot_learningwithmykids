package com.lopezsoto.learningwithmykids.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data class representing a member of a family.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMember {

    private String id;

    private int age;

    private String firstName;

    private String lastName;

    private List<Action> actions;
}
