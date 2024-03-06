package com.example.vet.bussines.abstracts;


import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.vaccineRequest.VaccineSaveRequest;
import com.example.vet.dto.request.vaccineRequest.VaccineUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.vaccineResponse.VaccineResponse;
import com.example.vet.entity.Vaccine;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IVaccineService {
    ResultData<VaccineResponse> save (VaccineSaveRequest vaccineSaveRequest);
    Vaccine get(long id);
    ResultData<CursorResponse<VaccineResponse>> cursor(int page, int pageSize);
    ResultData<VaccineResponse> update(VaccineUpdateRequest vaccineUpdateRequest);
    Result delete(long id);
    ResultData<List<VaccineResponse>> findByDate(LocalDate startDate, LocalDate finishDate);
}
