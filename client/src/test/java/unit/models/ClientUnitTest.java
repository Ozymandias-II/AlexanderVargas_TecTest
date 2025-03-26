package unit.models;

import com.bank_system.client.model.Client;
import com.bank_system.client.model.Person;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ClientUnitTest {

    private static Validator validator;

    @BeforeAll
    static void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenClientIsValid_thenNoConstraintViolations() {
        Person testPerson = new Person();
        testPerson.setId(1L);

        Client client = new Client();
        client.setPerson(testPerson);
        client.setPassword("Secure123!");
        client.setStatus(true);
        client.setId(testPerson.getId());

        var violations = validator.validate(client);
        assertThat(violations).isEmpty();
    }

    @Test
    void whenPasswordIsNull_thenOneConstraintViolation() {
        Person testPerson = new Person();
        testPerson.setId(1L);

        Client client = new Client();
        client.setPerson(testPerson);
        client.setPassword(null);
        client.setStatus(true);
        client.setId(1L);

        var violations = validator.validateProperty(client, "password");

        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .containsAnyOf(
                        "Password must not be null"
                );
    }

    @Test
    void testClientPersonRelationship() {
        Person testPerson = new Person();
        testPerson.setId(1L);

        Client client = new Client();
        client.setPerson(testPerson);
        client.setId(testPerson.getId());

        assertThat(client.getId()).isEqualTo(1L);
        assertThat(client.getPerson()).isEqualTo(testPerson);
    }

    @Test
    void whenPersonIsNull_thenConstraintViolationOccurs() {
        Client client = new Client();
        client.setPerson(null);
        client.setPassword("Pass123");
        client.setStatus(true);

        var violations = validator.validateProperty(client, "person");
        assertThat(violations)
                .hasSize(1)
                .extracting("message")
                .containsAnyOf(
                        "Person must not be null"
                );
    }

    @Test
    void toString_containsClientDetails() {
        Person testPerson = new Person();
        testPerson.setId(1L);

        Client client = new Client();
        client.setPerson(testPerson);
        client.setPassword("secret");

        assertThat(client.toString())
                .contains("1")
                .contains("secret");
    }
}
