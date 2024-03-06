package com.example.vet.api;

import com.example.vet.bussines.abstracts.IVaccineService;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.vaccineRequest.VaccineSaveRequest;
import com.example.vet.dto.request.vaccineRequest.VaccineUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.vaccineResponse.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;

    public VaccineController(IVaccineService vaccineService) {
        this.vaccineService = vaccineService;
    }
    //Create -Değerlendirme Formu 21
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<VaccineResponse> save(@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {

        return this.vaccineService.save(vaccineSaveRequest);
    }

    //Read
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return this.vaccineService.cursor(page,pageSize);
    }

    //Update
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {
        return this.vaccineService.update(vaccineUpdateRequest);
    }


    //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id){

        return this.vaccineService.delete(id);
    }

    //Find By Date -Değerlendirme Formu 24
    @GetMapping("/find-by-date")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> findByDate(
            @RequestParam (name = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(name = "finishDate")@DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate finishDate){
        return this.vaccineService.findByDate(startDate,finishDate);
    }
}
