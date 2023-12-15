package org.boyar.boyarkin.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user_user")
@Data
@NoArgsConstructor
public class User {
    public static final String SPRING_SEQUENCE = "SPRING_SEQUENCE";

    @Id
    @Column(name = "id")
    @SequenceGenerator(allocationSize = 1, name = SPRING_SEQUENCE, sequenceName = SPRING_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SPRING_SEQUENCE)
    private Long id;

    @Column(name = "fio")
    private String fio;
}
