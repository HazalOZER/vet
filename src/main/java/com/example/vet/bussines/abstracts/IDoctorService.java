package com.example.vet.bussines.abstracts;



import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.doctorRequest.DoctorSaveRequest;
import com.example.vet.dto.request.doctorRequest.DoctorUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.doctorResponse.DoctorResponse;
import com.example.vet.entity.Doctor;

import java.util.List;


public interface IDoctorService {
    ResultData<DoctorResponse> save (DoctorSaveRequest doctorSaveRequest);
    Doctor get(long id);
    ResultData<CursorResponse<DoctorResponse>>  cursor(int page, int pageSize);
    ResultData<DoctorResponse> update(DoctorUpdateRequest doctorUpdateRequest);
    Result delete(long id);
}
