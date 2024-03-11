package com.example.vet.bussines.abstracts;


import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.appointmentRequest.AppointmentSaveRequest;
import com.example.vet.dto.request.appointmentRequest.AppointmentUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.appointmentResponse.AppointmentResponse;
import com.example.vet.entity.Appointment;

import java.time.LocalDate;
import java.util.List;

public interface IAppointmentService {
    ResultData<AppointmentResponse> save(AppointmentSaveRequest appointmentSaveRequest);

    Appointment get(long id);

    ResultData<CursorResponse<AppointmentResponse>> cursor(int page, int pageSize);

    ResultData<AppointmentResponse> update(AppointmentUpdateRequest appointmentUpdateRequest);

    Result delete(long id);
    public ResultData<List<AppointmentResponse>> getByDateAndDoctor(LocalDate startDate, LocalDate finishDate, long doctorId);
    public ResultData<List<AppointmentResponse>> getByDateAndAnimal(LocalDate date,LocalDate finishDate, long animalId);
}
