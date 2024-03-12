package org.goldmann.backend.domain;

import org.goldmann.backend.domain.domain.UserEntityRepository;
import org.goldmann.backend.domain.domain.UserMapper;
import org.goldmann.backend.domain.domain.UserMapperImpl;
import org.goldmann.backend.interfaces.users.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.matomo.java.tracking.TrackerConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserServiceTest {

    UserService cut;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @MockBean
    private TrackerConfiguration trackerConfiguration;

    @MockBean
    private org.matomo.java.tracking.MatomoTracker matomoTracker;


    @BeforeEach
    void setUp() {
        this.cut = new UserService(this.userEntityRepository, new UserMapperImpl());
    }

    @Test
    @Sql("createUser.sql")
    void findByUuidUserNotFound() {
        Optional<UserDto> result = this.cut.findByUuid(UUID.randomUUID());
        assertFalse(result.isPresent());
    }

    @Test
    @Sql("createUser.sql")
    void findByUuidUserFound() {
        Optional<UserDto> result = this.cut.findByUuid(UUID.fromString("d9d053ed-9a97-4f32-8626-03e20aea133f"));
        assertTrue(result.isPresent());
    }
}