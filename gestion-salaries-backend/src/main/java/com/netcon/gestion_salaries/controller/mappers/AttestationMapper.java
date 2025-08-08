package com.netcon.gestion_salaries.controller.mappers;

import com.netcon.gestion_salaries.controller.data.AttestationCmd;
import com.netcon.gestion_salaries.records.AttestationDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", uses = { EmployeMapper.class }, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AttestationMapper {
    
    AttestationDto from(AttestationCmd attestationCmd);
}
