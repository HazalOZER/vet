package com.example.vet.bussines.abstracts;


import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.availableRequest.AvailableDateSaveRequest;
import com.example.vet.dto.request.availableRequest.AvailableDateUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.availableResponse.AvailableDateResponse;
import com.example.vet.entity.AvailableDate;


public interface IAvailableDateService {
    ResultData<AvailableDateResponse> save(AvailableDateSaveRequest availableDateSaveRequest);

    AvailableDate get(long id);

    ResultData<CursorResponse<AvailableDateResponse>> cursor(int page, int pageSize);

    ResultData<AvailableDateResponse> update(AvailableDateUpdateRequest availableDateUpdateRequest);

    Result delete(long id);
}
