package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.PaymentMethod;
import com.ecristobale.apifirst.model.PaymentMethodDto;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentMethodMapper {

    PaymentMethodDto paymentMethodToPaymentMethodDto(PaymentMethod paymentMethod);

    PaymentMethod paymentMethodDtoToPaymentMethod(PaymentMethodDto paymentMethodDto);
}
