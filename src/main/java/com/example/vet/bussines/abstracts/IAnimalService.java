package com.example.vet.bussines.abstracts;

import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.animalRequest.AnimalSaveRequest;
import com.example.vet.dto.request.animalRequest.AnimalUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.vaccineResponse.VaccineResponse;
import com.example.vet.entity.Animal;

import java.util.List;

public interface IAnimalService {
    ResultData<AnimalResponse> save(AnimalSaveRequest animalSaveRequest);

    Animal get(long id);

    ResultData<CursorResponse<AnimalResponse>> cursor(int page, int pageSize);

    ResultData<AnimalResponse> update(AnimalUpdateRequest animalUpdateRequest);

    Result delete(long id);
    ResultData<List<AnimalResponse>> getByName(String name);
    ResultData<List<VaccineResponse>> getVaccines(long id);


}
