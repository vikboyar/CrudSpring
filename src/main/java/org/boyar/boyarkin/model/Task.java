package org.boyar.boyarkin.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
public class Task {
    public static final String SPRING_SEQUENCE = "SPRING_SEQUENCE";

    @Id
    @Column(name = "id", nullable = false)
    @SequenceGenerator(allocationSize = 1, name = SPRING_SEQUENCE, sequenceName = SPRING_SEQUENCE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SPRING_SEQUENCE)
    private Long id;

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "creator_id", precision = 19, nullable = false)
    private Long creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", /*table = "user_user",*/ referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;
}
