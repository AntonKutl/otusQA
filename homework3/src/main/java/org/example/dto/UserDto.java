
package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
  private String email;

  private String firstName;

  private Long id;

  private String lastName;

  private String password;

  private String phone;

  private Long userStatus;

  private String username;
}
