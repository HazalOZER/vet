package com.example.vet.api;

import com.example.vet.bussines.abstracts.IAvailableDateService;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.availableRequest.AvailableDateSaveRequest;
import com.example.vet.dto.request.availableRequest.AvailableDateUpdateRequest;
import com.example.vet.dto.request.customerRequest.CustomerSaveRequest;
import com.example.vet.dto.request.customerRequest.CustomerUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.availableResponse.AvailableDateResponse;
import com.example.vet.dto.response.customerResponse.CustomerResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/dates")
public class AvailableDateController {
    private final IAvailableDateService availableDateService;

    public AvailableDateController(IAvailableDateService availableDateService) {
        this.availableDateService = availableDateService;
    }

    //Create -Değerlendirme Formu 16
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {

        return this.availableDateService.save(availableDateSaveRequest);
    }

    //Read
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return this.availableDateService.cursor(page,pageSize);
    }

    //Update
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        return this.availableDateService.update(availableDateUpdateRequest);
    }


    //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id){

        return this.availableDateService.delete(id);
    }
}
