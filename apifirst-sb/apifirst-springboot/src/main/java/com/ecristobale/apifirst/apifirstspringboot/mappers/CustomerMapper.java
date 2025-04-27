package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.model.CustomerDto;
import com.ecristobale.apifirst.model.CustomerPatchDto;
import org.mapstruct.*;

@Mapper
@DecoratedWith(CustomerMapperDecorator.class)
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    Customer customerDtoToCustomer(CustomerDto customerDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    Customer updateCustomer(CustomerDto customerDto, @MappingTarget Customer customer);

    CustomerPatchDto customerToCustomerPatchDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateUpdated", ignore = true)
    @Mapping(target = "shipToAddress.id", ignore = true)
    @Mapping(target = "billToAddress.id", ignore = true)
    @Mapping(target = "paymentMethods", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void patchCustomer(CustomerPatchDto customerPatchDto, @MappingTarget Customer target);
}
