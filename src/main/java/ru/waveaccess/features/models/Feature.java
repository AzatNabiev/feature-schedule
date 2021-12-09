package ru.waveaccess.features.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.waveaccess.features.enums.FeatureRoles;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@EqualsAndHashCode
@Table
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "feature")
    @JsonManagedReference
    private List<Task> tasks;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Enumerated(value = EnumType.STRING)
    private FeatureRoles featureRoles;


    private String name;

    private String description;
}
