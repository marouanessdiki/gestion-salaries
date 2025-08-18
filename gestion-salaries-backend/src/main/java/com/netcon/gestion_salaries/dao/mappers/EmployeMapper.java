package com.netcon.gestion_salaries.dao.mappers;

import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.records.EmployeDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)

public interface EmployeMapper {
    Employe from(EmployeDto dto);

    EmployeDto from(Employe dto);

    List<EmployeDto> fromList(List<Employe> all);
}
