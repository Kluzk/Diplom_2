import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTests extends BaseUserTest {

    @Test
    @DisplayName("Проверка изменения почты пользователя")
    @Description("Изменение почты пользователя")
    public void checkChangeEmailUser() {
        token = authUser(email, password).then().extract().path("accessToken").toString().replace("Bearer ", "");

        String new_email = faker.internet().emailAddress();

        changeUserData(token, new_email, null)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(new_email));
    }

    @Test
    @DisplayName("Проверка изменения имени пользователя")
    @Description("Изменение имени пользователя")
    public void checkChangePassword() {
        token = authUser(email, password).then().extract().path("accessToken").toString().replace("Bearer ", "");

        String new_name = faker.name().username();

        changeUserData(token, email, new_name)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("user.name", equalTo(new_name));
    }

    @Test
    @DisplayName("Проверка изменения данных пользователя без авторизации")
    @Description("Попытка изменения почты и имени пользователя без авторизации")
    public void checkChangePasswordWithoutAuth() {
        String new_email = faker.internet().emailAddress();
        String new_name = faker.name().username();

        changeUserData("", new_email, new_name)
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }
}
