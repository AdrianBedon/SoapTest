package com.soapservice.soaptest;

import com.travels.travel_soap.GetCustomerRequest;
import com.travels.travel_soap.GetCustomerResponse;
import com.travels.travel_soap.Customer;
import static com.soapservice.soaptest.SoaptestApplication.NAMESPACE_URI;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import com.soapservice.soaptest.consumingJPA.CustomerJPA;

@Endpoint
public class TravelEndpoint {

  private CustomerJPA customerJpa;

  @Autowired
  private RestTemplate restTemplate;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerRequest")
  @ResponsePayload
  public GetCustomerResponse getCustomer(@RequestPayload GetCustomerRequest request) {
    GetCustomerResponse response = new GetCustomerResponse();

    customerJpa = restTemplate.getForObject(
          "http://localhost:8080/people/" + request.getName().getValue(), CustomerJPA.class);

    Customer customer = new Customer();
    customer.setName(customerJpa.getFirstName());
    customer.setLastname(customerJpa.getLastName());

    response.setCustomer(customer);
    return response;
  }
}