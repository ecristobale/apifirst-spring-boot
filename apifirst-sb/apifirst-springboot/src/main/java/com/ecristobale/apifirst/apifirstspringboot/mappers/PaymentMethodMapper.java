package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.PaymentMethod;
import com.ecristobale.apifirst.model.CustomerPaymentMethodPatchDto;
import com.ecristobale.apifirst.model.PaymentMethodDto;
import org.mapstruct.*;

@Mapper
public interface PaymentMethodMapper {

    PaymentMethodDto paymentMethodToPaymentMethodDto(PaymentMethod paymentMethod);

    //PaymentMethod paymentMethodDtoToPaymentMethod(PaymentMethodDto paymentMethodDto);

    @Mapping(target = "dateUpdated", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updatePaymentMethod(CustomerPaymentMethodPatchDto customerPaymentMethodPatchDto,
                             @MappingTarget PaymentMethod paymentMethod);
}
