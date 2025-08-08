package com.netcon.gestion_salaries.dao.mappers;

import com.netcon.gestion_salaries.entity.Employe;
import com.netcon.gestion_salaries.records.EmployeDto;

public interface EmployeMapper {
    Employe from(EmployeDto dto);
    EmployeDto from(Employe dto);
}
