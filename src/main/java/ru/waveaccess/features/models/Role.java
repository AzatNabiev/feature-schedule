package ru.waveaccess.features.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import ru.waveaccess.features.enums.UserRoles;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@EqualsAndHashCode(exclude = {"user"})
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"user"})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    private UserRoles userRoles;

    @OneToMany(mappedBy = "role")
    @JsonManagedReference
    private List<User> user;
}
