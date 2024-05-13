import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import models.Client;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class BaseTest {

    private static final String URI = "https://stellarburgers.nomoreparties.site/api";
    private static final String REGISTER_API = "/auth/register";
    private static final String LOGIN_API = "/auth/login";
    private static final String USER_API = "/auth/user";

    protected static Faker faker = new Faker();
    protected String email;
    protected String password;
    protected String name;


    @BeforeClass
    @DisplayName("Инициализация адреса шлюза")
    @Description("Инициализация адреса шлюза")
    public static void setup() {
        RestAssured.baseURI = URI;
    }

    @Before
    @DisplayName("Инициализация тестовых данных")
    @Description("Инициализация нового пользователя для теста")
    public void initTest() {
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().username();
    }

    @After
    @DisplayName("Очистка тестовых данных")
    @Description("Удаление пользователя после каждого теста")
    public void after() {

        if(authUser(email, password).then().extract().path("accessToken") != null) {
            deleteUser();
        }
    }

    @Step("Создание пользователя")
    protected Response createUser(String email, String password, String name) {
        Client client = new Client(email, password, name);

        return given()
                .contentType(ContentType.JSON)
                .body(client)
                .post(REGISTER_API);
    }

    @Step("Авторизация пользователя")
    protected Response authUser(String email, String password) {
        Client client = new Client(email, password);

        return given()
                .contentType(ContentType.JSON)
                .body(client)
                .post(LOGIN_API);
    }

    @Step("Получение информации о пользователе")
    protected Response infoUser(String token) {
        return given()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .get(USER_API);
    }

    @Step("Удаление пользователя")
    protected Response deleteUser() {
        return given()
                .delete(USER_API);
    }

}
