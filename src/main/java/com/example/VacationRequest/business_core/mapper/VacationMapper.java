package com.example.VacationRequest.business_core.mapper;

import com.example.VacationRequest.business_core.dto.CreateRequest;
import com.example.VacationRequest.business_core.dto.Response;
import com.example.VacationRequest.business_core.entity.VacationRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VacationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "vacationType", ignore = true)
    @Mapping(target = "halfDay", source = "halfDay")
    VacationRequest toEntity(CreateRequest dto);

    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "employeeEmail", source = "employee.email")
    @Mapping(target = "managerId", source = "manager.id")
    @Mapping(target = "managerEmail", source = "manager.email")
    @Mapping(target = "vacationTypeName", source = "vacationType.name")
    @Mapping(target = "vacationTypeId", source = "vacationType.id")
    @Mapping(target = "managerReason", source = "managerComment")
    Response toResponse(VacationRequest entity);


}