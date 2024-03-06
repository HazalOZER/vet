package com.example.vet.api;

import com.example.vet.bussines.abstracts.ICustomerService;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.customerRequest.CustomerSaveRequest;
import com.example.vet.dto.request.customerRequest.CustomerUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.customerResponse.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;

    }

    //Create -Değerlendirme Formu 10
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<CustomerResponse> save(@Valid @RequestBody CustomerSaveRequest customerSaveRequest) {

        return this.customerService.save(customerSaveRequest);
    }

    //Read
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<CustomerResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return this.customerService.cursor(page,pageSize);
    }

    //Update
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CustomerResponse> update(@Valid @RequestBody CustomerUpdateRequest customerUpdateRequest) {
        return this.customerService.update(customerUpdateRequest);
    }


    //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id){

        return this.customerService.delete(id);
    }

    //Get Animals -Değerlendirme Formu 14
    @GetMapping("/{id}/animals")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getAnimals(@PathVariable("id") long id){
        return this.customerService.getAnimals(id);
    }

    //GetByName -Değerlendirme Formu 11
    @GetMapping("/get-by-name")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<CustomerResponse>> getByName(@RequestParam (name ="name") String name){
        return this.customerService.getByName(name);
    }

}
