package com.netcon.gestion_salaries.controller.mappers;

import com.netcon.gestion_salaries.controller.data.AttestationCmd;
import com.netcon.gestion_salaries.controller.data.EmployeCmd;
import com.netcon.gestion_salaries.records.AttestationDto;
import com.netcon.gestion_salaries.records.EmployeDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EmployeMapper {
    EmployeDto from(EmployeCmd attestationCmd);
}
