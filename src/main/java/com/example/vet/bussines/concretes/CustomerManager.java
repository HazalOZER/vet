package com.example.vet.bussines.concretes;

import com.example.vet.bussines.abstracts.ICustomerService;
import com.example.vet.core.config.modelMapper.IModelMapperService;
import com.example.vet.core.exceptions.NotFoundException;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.core.utilies.Msg;
import com.example.vet.core.utilies.ResultHelper;
import com.example.vet.dao.CustomerRepo;
import com.example.vet.dto.request.customerRequest.CustomerSaveRequest;
import com.example.vet.dto.request.customerRequest.CustomerUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.customerResponse.CustomerResponse;
import com.example.vet.entity.Animal;
import com.example.vet.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerManager implements ICustomerService {
    private final CustomerRepo customerRepo;
    private final IModelMapperService modelMapper;


    public CustomerManager(CustomerRepo customerRepo, IModelMapperService mapperMapper) {
        this.customerRepo = customerRepo;
        this.modelMapper = mapperMapper;
    }

    @Override
    public ResultData<CustomerResponse> save(CustomerSaveRequest customerSaveRequest) {

        Customer customer = this.modelMapper.forRequest().map(customerSaveRequest, Customer.class);
        this.customerRepo.save(customer);
        CustomerResponse customerResponse = this.modelMapper.forResponse().map(customer, CustomerResponse.class);

        return ResultHelper.created(customerResponse);
    }

    @Override
    public Customer get(long id) {
        return this.customerRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public ResultData<CursorResponse<CustomerResponse>> cursor(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Customer> customerPage = this.customerRepo.findAll(pageable);
        Page<CustomerResponse> customerResponsePage = customerPage
                .map(customer -> this.modelMapper.forResponse().map(customer, CustomerResponse.class));

        return ResultHelper.cursor(customerResponsePage);
    }

    @Override
    public ResultData<CustomerResponse> update(CustomerUpdateRequest customerUpdateRequest) {

        this.get(customerUpdateRequest.getId());
        Customer customer = this.modelMapper.forRequest().map(customerUpdateRequest, Customer.class);
        this.customerRepo.save(customer);
        CustomerResponse customerResponse = this.modelMapper.forResponse().map(customer, CustomerResponse.class);
        return ResultHelper.success(customerResponse);
    }

    @Override
    public Result delete(long id) {
        Customer customer = this.get(id);
        this.customerRepo.delete(customer);
        return ResultHelper.ok();
    }

    @Override
    public ResultData<List<AnimalResponse>> getAnimals(long id) {
       Customer customer = this.get(id);

        List < Animal> animals = customer.getAnimals();
        List<AnimalResponse> animalResponseList= new ArrayList<>();
        for (Animal animal:animals) {
            animalResponseList.add(this.modelMapper.forResponse().map(animal,AnimalResponse.class));
        }
        return ResultHelper.success(animalResponseList);
    }

    @Override
    public ResultData<List<CustomerResponse>> getByName(String name) {

        List<Customer> customers =this.customerRepo.findByName(name);

        if(customers.isEmpty()){
            new NotFoundException(Msg.NOT_FOUND);
        }

        List<CustomerResponse> customerResponseList = new ArrayList<>();

        for (Customer customer : customers){
            customerResponseList.add(this.modelMapper.forResponse().map(customer,CustomerResponse.class));
        }


        return ResultHelper.success(customerResponseList);
    }
}
