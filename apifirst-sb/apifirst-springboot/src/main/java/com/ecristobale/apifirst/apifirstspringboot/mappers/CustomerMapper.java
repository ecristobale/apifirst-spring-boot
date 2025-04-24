package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.model.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    Customer customerDtoToCustomer(CustomerDto customerDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    Customer updateCustomer(CustomerDto customerDto, @MappingTarget Customer customer);
}
