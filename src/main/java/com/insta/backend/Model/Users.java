package com.insta.backend.Model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Users
{
    @Id
    private String id;
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String password;
}
