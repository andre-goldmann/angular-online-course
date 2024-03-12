package org.goldmann.backend.interfaces.users;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class UserDto {

    String id;
    String fullName;
    Integer age;
    String email;

}
