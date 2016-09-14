package com.mmj.data.web.webservice.helper;

import com.mmj.data.core.dto.entity.PaymentTypeDTO;
import com.mmj.data.core.payments.PaymentService;

import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;

@Alternative
public class PaymentServiceSpecialized extends PaymentService {
    @Override
    public List<PaymentTypeDTO> getPaymenttypes() {
        List<PaymentTypeDTO> result = new ArrayList<>();
        return result;
    }
}
