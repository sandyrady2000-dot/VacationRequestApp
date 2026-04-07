package com.example.VacationRequest.usermgmt.mapper;

import com.example.VacationRequest.usermgmt.dto.response.RoleResponse;
import com.example.VacationRequest.usermgmt.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse toResponse(Role role);

}
