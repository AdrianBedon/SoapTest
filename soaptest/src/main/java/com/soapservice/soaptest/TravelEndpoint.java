package com.soapservice.soaptest;

import com.travels.travel_soap.GetCustomerRequest;
import com.travels.travel_soap.GetCustomerResponse;
import com.travels.travel_soap.Customer;
import static com.soapservice.soaptest.SoaptestApplication.NAMESPACE_URI;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.CommandLineRunner;
import com.soapservice.soaptest.consumingJPA.CustomerJPA;

@Endpoint
public class TravelEndpoint {

  private static final String HELLO_TPL = "%s";
  private CustomerJPA customerJpa;

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Bean
  public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
    return args -> {
      System.out.print("Llamando a JPA"); 
      customerJpa = restTemplate.getForObject(
          "http://localhost:8080/people/1", CustomerJPA.class);
        System.out.print(customerJpa.toString());
    };
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
  @ResponsePayload
  public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
    GetCustomerResponse response = new GetCustomerResponse();

    Customer customer = new Customer();
    customer.setName(
            String.format(
                    HELLO_TPL, request.getName() == null || request.getName().getValue() == null
                    ? "World" : request.getName().getValue()
            )
    );
    customer.setLastname("Bedón");

    response.setCustomer(customer);
    return response;
  }
}