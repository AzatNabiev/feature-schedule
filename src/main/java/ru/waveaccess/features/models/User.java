package ru.waveaccess.features.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(exclude = {"role", "featureList", "taskList"})
@EqualsAndHashCode(exclude = {"role", "featureList", "taskList"})
@Table(name = "account")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Feature> featureList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Task> taskList;

    @ManyToOne
    @JsonBackReference
    private Role role;

}
