package com.mmj.data.core.payments;

import com.mmj.data.core.dto.entity.PaymentTypeDTO;
import com.mmj.data.core.exception.SystemException;
import com.mmj.data.core.extclient.lookup.PaymentTypeClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mmj.data.core.util.SystemProperty.G4_LOOKUP_URL;

/**
 *
 */
public class PaymentService {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);
    private static final List<String> CREDIT_METHODS_OF_PAYMENTS = new ArrayList<>();
    private static final List<String> DEBIT_METHODS_OF_PAYMENTS = new ArrayList<>();

    static {
        CREDIT_METHODS_OF_PAYMENTS.add("MC");
        CREDIT_METHODS_OF_PAYMENTS.add("VI");
        CREDIT_METHODS_OF_PAYMENTS.add("DS");
        DEBIT_METHODS_OF_PAYMENTS.add("MD");
        DEBIT_METHODS_OF_PAYMENTS.add("DD");
        DEBIT_METHODS_OF_PAYMENTS.add("VD");
    }

    public PaymentService() {
    }

    /**
     * Returns a list of payment types
     *
     * @return List<PaymentTypeDTO>
     */
    public List<PaymentTypeDTO> getPaymenttypes() {
        ClientResponse<List<com.mmj.data.core.extclient.dto.lookup.PaymentTypeDTO>> response = null;
        int statusCode = 0;
        try {
            String g4LookupUrl = G4_LOOKUP_URL.getValue();
            if (StringUtils.isBlank(g4LookupUrl)) {
                throw new SystemException("System property " + G4_LOOKUP_URL + " not set");
            }
            PaymentTypeClient paymentTypeClient = ProxyFactory.create(PaymentTypeClient.class, g4LookupUrl);
            response = paymentTypeClient.getAllPaymentTypes();
            statusCode = response.getResponseStatus().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                String msg = G4_LOOKUP_URL.getValue() + " returned status code " + statusCode;
                LOG.error(msg);
                throw new SystemException(msg);
            }
            List<com.mmj.data.core.extclient.dto.lookup.PaymentTypeDTO> paymentTypeDTOList = response.getEntity();
            if (LOG.isDebugEnabled()) {
                for (com.mmj.data.core.extclient.dto.lookup.PaymentTypeDTO paymentTypeDTO : paymentTypeDTOList) {
                    LOG.debug("Retrieved PaymentTypeDTO : " + paymentTypeDTO);
                }
            }
            List<PaymentTypeDTO> newPaymentTypeDtoList = new ArrayList<>();
            for (com.mmj.data.core.extclient.dto.lookup.PaymentTypeDTO paymentTypeDTO : paymentTypeDTOList) {
                String idx = paymentTypeDTO.getIdx();
                if (CREDIT_METHODS_OF_PAYMENTS.contains(idx) || DEBIT_METHODS_OF_PAYMENTS.contains(idx) || "PA".equals(idx) || "NA".equals(idx)) {
                    PaymentTypeDTO pt = new PaymentTypeDTO();
                    pt.setCode(idx);
                    if (paymentTypeDTO.getIdx().equals("DS")) {
                        pt.setName("Discover Credit");
                    } else if (paymentTypeDTO.getIdx().equals("DD")) {
                        pt.setName("Discover Debit");
                    } else if (paymentTypeDTO.getIdx().equals("MC")) {
                        pt.setName("MasterCard Credit");
                    } else if (paymentTypeDTO.getIdx().equals("MD")) {
                        pt.setName("MasterCard Debit");
                    } else if (paymentTypeDTO.getIdx().equals("VI")) {
                        pt.setName("Visa Credit");
                    } else if (paymentTypeDTO.getIdx().equals("VD")) {
                        pt.setName("Visa Debit");
                    } else {
                        pt.setName(paymentTypeDTO.getDescription());
                    }
                    pt.setId(paymentTypeDTO.getRecordId());
                    if (CREDIT_METHODS_OF_PAYMENTS.contains(idx)) {
                        pt.setCategory("CREDIT");
                    } else if (DEBIT_METHODS_OF_PAYMENTS.contains(idx)) {
                        pt.setCategory("DEBIT");
                    }
                    newPaymentTypeDtoList.add(pt);
                }
            }
            return newPaymentTypeDtoList;
        } catch (Exception e) {
            LOG.error("Could not get payment types", e);
            throw new SystemException("Could not get payment types", e);
        } finally {
            if (response != null) {
                response.releaseConnection();
            }
        }
    }

}
