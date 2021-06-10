package com.lopezsoto.learningwithmykids.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data class representing a Family
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Family {

    private String id;

    private String country;

    private String city;

    private List<FamilyMember> members;

}
