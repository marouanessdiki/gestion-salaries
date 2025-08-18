package com.netcon.gestion_salaries.controller.mappers;

import com.netcon.gestion_salaries.controller.data.AttestationCmd;
import com.netcon.gestion_salaries.records.AttestationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AttestationCmdMapper {

    @Mapping(source = "employeId", target = "employeId")
    AttestationDto from(AttestationCmd attestationCmd);
}
