package ru.waveaccess.features.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.waveaccess.features.enums.TaskRoles;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table
@ToString(exclude = {"user", "bugs", "feature"})
@EqualsAndHashCode(exclude = {"user", "bugs", "feature"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feature_id")
    @JsonBackReference
    private Feature feature;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "task")
    @JsonManagedReference
    private List<Bug> bugs;

    @Enumerated(value = EnumType.STRING)
    private TaskRoles taskRole;

    private String name;
    private String description;
}
