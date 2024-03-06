package com.example.vet.bussines.abstracts;

import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.customerRequest.CustomerSaveRequest;
import com.example.vet.dto.request.customerRequest.CustomerUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.customerResponse.CustomerResponse;
import com.example.vet.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService {
    ResultData<CustomerResponse> save(CustomerSaveRequest customerSaveRequest);

    Customer get(long id);

    ResultData<CursorResponse<CustomerResponse>> cursor(int page, int pageSize);


    ResultData<CustomerResponse> update(CustomerUpdateRequest customerUpdateRequest);

    Result delete(long id);
    ResultData<List<AnimalResponse>> getAnimals(long id);
    ResultData<List<CustomerResponse>> getByName(String name);
}
