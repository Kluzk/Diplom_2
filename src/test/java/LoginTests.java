import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.emptyOrNullString;

public class LoginTests extends BaseTest {

    @Test
    @DisplayName("Логирование существующего пользователя")
    @Description("Вход существующего пользователя")
    public void checkLoginExistUser() {
        createUser(email, password, name)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));

        authUser(email, password)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .and()
                .body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("Логирование существующего пользователя")
    @Description("Вход существующего пользователя")
    public void checkLoginWithNotValidLoginAndPass() {
        authUser(email, password)
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .and()
                .body("accessToken", emptyOrNullString());
    }
}
