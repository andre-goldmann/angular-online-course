package org.goldmann.backend.domain;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.goldmann.backend.domain.domain.UserEntity;
import org.goldmann.backend.domain.domain.UserEntityRepository;
import org.goldmann.backend.domain.domain.UserMapper;
import org.goldmann.backend.interfaces.users.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserEntityRepository userEntityRepository;

    private final UserMapper mapper;

    public Optional<UserDto> findByUuid(UUID uuid) {
        return this.userEntityRepository.findById(uuid).map(this.mapper::mapUserToDto);
    }

    public UserDto save(UserDto dto) {
        final UserEntity entity = this.mapper.mapUserToEntity(dto);
        return this.mapper.mapUserToDto(this.userEntityRepository.save(entity));
    }
}
