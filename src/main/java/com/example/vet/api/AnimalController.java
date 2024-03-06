package com.example.vet.api;

import com.example.vet.bussines.abstracts.IAnimalService;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.animalRequest.AnimalSaveRequest;
import com.example.vet.dto.request.animalRequest.AnimalUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.vaccineResponse.VaccineResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animals")
public class AnimalController {
private final IAnimalService animalService;

    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    //Create -Değerlendirme Formu 12
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> save(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {

        return this.animalService.save(animalSaveRequest);
    }

    //Update
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> update (@Valid @RequestBody AnimalUpdateRequest animalUpdateRequest){
        return this.animalService.update(animalUpdateRequest);
    }
    //Read
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AnimalResponse>> cursor (
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize)
    {
    return this.animalService.cursor(page,pageSize);
    }


    //Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {

        return this.animalService.delete(id);
    }

    //GetByName -Değerlendirme Formu 13
    @GetMapping("/get-by-name")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> getByName(@RequestParam (name ="name") String name){
        return this.animalService.getByName(name);
    }

    //Get Vaccines -Değerlendirme Formu 24
    @GetMapping("/{id}/vaccines")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<VaccineResponse>> getVaccines(@PathVariable("id")long id){
        return this.animalService.getVaccines(id);
    }
}
