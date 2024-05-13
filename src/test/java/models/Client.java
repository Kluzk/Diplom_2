package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {
    private String email;
    private String password;
    private String name;

    public Client(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
