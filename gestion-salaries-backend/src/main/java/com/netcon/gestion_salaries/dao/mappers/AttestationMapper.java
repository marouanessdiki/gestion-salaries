package com.netcon.gestion_salaries.dao.mappers;

import com.netcon.gestion_salaries.entity.Attestation;
import com.netcon.gestion_salaries.records.AttestationDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {EmployeMapper.class}, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AttestationMapper {

    Attestation from(AttestationDto dto);

    AttestationDto from(Attestation entity);

    List<AttestationDto> fromList(List<Attestation> entity);
}
