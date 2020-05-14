package model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;

@EqualsAndHashCode(callSuper = true)
@Data
public class Master extends BasePerson {
    @Column(name = "rating")
    private double rating;
    @Column(name = "description")
    private String description;
}
