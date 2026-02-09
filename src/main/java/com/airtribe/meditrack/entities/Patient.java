package com.airtribe.meditrack.entities;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Patient extends Person{

        @Length(min = 1, max = 50,message = "min length is 50")
        private String name;

        @Range(min = 1, max = 50)
        private Integer age;

}


