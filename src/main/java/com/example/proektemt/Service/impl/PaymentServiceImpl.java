package com.example.proektemt.Service.impl;

import com.example.proektemt.Model.dto.ChargeRequest;
import com.example.proektemt.Service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Service
public class PaymentServiceImpl implements PaymentService {

        @Value("sk_test_51Gu1dnEZZvMcWSViTmBJpYyBy36pzYO4hh91mipagi8i7fvnIj20xXmwZ4SnSPQUUjGSRAw5i8kEuyHIM7EtnOZy00u5Gr32Xx")
        public String secretKey;

        @PostConstruct
        public void init(){Stripe.apiKey= this.secretKey; }

        @Override
        public Charge pay(ChargeRequest charheRequest) throws StripeException {
                Map<String, Object> chargeMap= new HashMap<>();
                chargeMap.put("amount", charheRequest.getAmount());
                chargeMap.put("currency",charheRequest.getCurrency());
                chargeMap.put("source", charheRequest.getStripeToken());
                chargeMap.put("description", charheRequest.getDescription());
               // Charge charge= Charge.create(chargeMap);
                return Charge.create(chargeMap);
        }
}
