package com.example.VacationRequest.usermgmt.mapper;

import com.example.VacationRequest.usermgmt.dto.request.UserCreateRequest;
import com.example.VacationRequest.usermgmt.dto.request.UserUpdateRequest;
import com.example.VacationRequest.usermgmt.dto.response.UserResponse;
import com.example.VacationRequest.usermgmt.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    // Entity -> Response
    UserResponse toResponse(User user);

    // Create Request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "role", ignore = true) // هنجيبه في Service
    User toEntity(UserCreateRequest request);

    // Update Request -> Existing Entity
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UserUpdateRequest request, @MappingTarget User user);
}
