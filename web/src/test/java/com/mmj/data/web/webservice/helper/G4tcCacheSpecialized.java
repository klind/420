package com.mmj.data.web.webservice.helper;

import com.mmj.data.core.dto.entity.PaymentTypeDTO;
import com.mmj.data.ejb.cache.G4tcCache;

import javax.enterprise.inject.Alternative;

/**
 *
 */
@Alternative
public class G4tcCacheSpecialized extends G4tcCache {

    @Override
    public PaymentTypeDTO getPaymentTypeByCode(String code) {
        PaymentTypeDTO paymentTypeDTO = new PaymentTypeDTO();
        paymentTypeDTO.setId((long) 7);
        paymentTypeDTO.setCategory("FOO");
        paymentTypeDTO.setCode("VCC");
        paymentTypeDTO.setDescription("FOO");
        paymentTypeDTO.setName("Visa Credit Card");
        return paymentTypeDTO;
    }
}
