package org.boyar.boyarkin.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TaskDto implements Serializable {
    private Long id;
    private String title;
    private String description;
    private Long creatorId;
    private Date created = new Date();
    private Date updated;
}
