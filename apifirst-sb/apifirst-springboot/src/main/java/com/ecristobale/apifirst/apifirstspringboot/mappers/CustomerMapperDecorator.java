package com.ecristobale.apifirst.apifirstspringboot.mappers;

import com.ecristobale.apifirst.apifirstspringboot.domain.Customer;
import com.ecristobale.apifirst.model.CustomerDto;
import com.ecristobale.apifirst.model.CustomerPatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class CustomerMapperDecorator implements CustomerMapper {

    @Autowired
    @Qualifier("delegate")
    private CustomerMapper delegate;

    @Autowired
    private PaymentMethodMapper paymentMethodMapper;

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        return delegate.customerToCustomerDto(customer);
    }

    @Override
    public Customer customerDtoToCustomer(CustomerDto customerDto) {
        return delegate.customerDtoToCustomer(customerDto);
    }

    @Override
    public Customer updateCustomer(CustomerDto customerDto, Customer customer) {
        return delegate.updateCustomer(customerDto, customer);
    }

    @Override
    public CustomerPatchDto customerToCustomerPatchDto(Customer customer) {
        return delegate.customerToCustomerPatchDto(customer);
    }

    @Override
    public void patchCustomer(CustomerPatchDto customerPatchDto, Customer target) {
        delegate.patchCustomer(customerPatchDto, target);

        if (customerPatchDto.getPaymentMethods() != null) {
            customerPatchDto.getPaymentMethods().forEach(paymentMethodPatchDto -> {
                target.getPaymentMethods().forEach(paymentMethod -> {
                    if (paymentMethod.getId().equals(paymentMethodPatchDto.getId())) {
                        paymentMethodMapper.updatePaymentMethod(paymentMethodPatchDto, paymentMethod);
                    }
                });
            });
        }
    }
}
