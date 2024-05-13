import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class ChangeUserDataTests extends BaseTest {

    @Before
    @DisplayName("Инициализация нового пользователя для теста")
    @Description("Инициализация логина, пароля и почтового адреса, создание пользователя")
    public void initTest() {
        super.initTest();
        createUser(email, password, name);
    }

    @Test
    @DisplayName("Проверка изменения данных пользователя")
    @Description("Изменение почты, пароля и имени пользователя")
    public void checkChangePassword() {
        token = authUser(email, password).then().extract().path("accessToken").toString().replace("Bearer ", "");

        email = faker.internet().emailAddress();
        name = faker.name().username();

        changeUserData(token, email, password, name)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("user.email", equalTo(email))
                .body("user.name", equalTo(name));
    }

    @Test
    @DisplayName("Проверка изменения данных пользователя без авторизации")
    @Description("Изменение почты, пароля и имени пользователя")
    public void checkChangePasswordWithoutAuth() {
        email = faker.internet().emailAddress();
        name = faker.name().username();

        changeUserData("", email, password, name)
                .then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }
}
