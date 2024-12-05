package com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.implementation;

import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.AccountResponseDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.PayActionResponseDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.dto.TransferDTO;
import com.example.toktoralieva_orozbekova_duishenaliev.pizza.services.SmmpService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class SmmpServiceImpl implements SmmpService{

    @Value("${my.smmp.url}")
    String myUrl;

    @Value("${my.smmp.plainCreds}")
    String plainCreds;




    public PayActionResponseDTO doAction(String token, String customer, BigDecimal amount) {

        PayActionResponseDTO payActionResponse =
                new PayActionResponseDTO().payment(false).description("unbekanntes Problem. Transfer nicht erfolgreich");

        return smmpAccountCommunication(token, customer,amount, payActionResponse);
    }

    private PayActionResponseDTO smmpAccountCommunication(String token, String customer,BigDecimal amount, PayActionResponseDTO payActionResponse) {


        String uriReturn;
        ResponseEntity<?> response = null;
        RestTemplate restTemplate = new RestTemplate();

        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<String>(headers);


        switch (token){
            case "transfer":
                headers.setContentType(MediaType.APPLICATION_JSON);
                TransferDTO transferDTO = new TransferDTO(amount);
                HttpEntity<TransferDTO> requestPost = new HttpEntity<>(transferDTO, headers);
                uriReturn = myUrl + customer + "/payment";
                response = restTemplate.exchange(uriReturn, HttpMethod.POST, requestPost, AccountResponseDTO.class);
                break;
            case "balance":
                uriReturn = myUrl + customer + "/account";
                response = restTemplate.exchange(uriReturn, HttpMethod.GET, request, AccountResponseDTO.class);
                break;
            case "openAcc":
                uriReturn = myUrl + customer + "/opened";
                response = restTemplate.exchange(uriReturn, HttpMethod.PUT, request, AccountResponseDTO.class);
                break;
        }


        AccountResponseDTO accountResponse = (AccountResponseDTO) response.getBody();
        //description
        payActionResponse.description(accountResponse.getCode());

        if (response.getStatusCode().equals(HttpStatus.OK))
        {
            payActionResponse.payment(true);
        }
        else {
            payActionResponse.payment(false);
        }
        return payActionResponse;
    }

}

