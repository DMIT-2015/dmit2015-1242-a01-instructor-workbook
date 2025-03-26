package dmit2015.model;

import lombok.Data;

@Data
public class Student {

    private Long id;

    private String firstName;

    private String lastName;

    private String courseSection;

    private Integer version;

}