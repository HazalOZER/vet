package com.example.vet.api;

import com.example.vet.bussines.abstracts.IAppointmentService;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.dto.request.animalRequest.AnimalSaveRequest;
import com.example.vet.dto.request.appointmentRequest.AppointmentSaveRequest;
import com.example.vet.dto.request.appointmentRequest.AppointmentUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.appointmentResponse.AppointmentResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //Create -Değerlendirme Formu 17

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> save(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest){
        return this.appointmentService.save(appointmentSaveRequest);
    }

    //Read

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            @RequestParam(name = "page",required = false,defaultValue = "0") int page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize
    ){
        return this.appointmentService.cursor(page,pageSize);
    }

    //Update

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update (@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest){
        return this.appointmentService.update(appointmentUpdateRequest);
    }

    //Delete

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") long id) {

        return this.appointmentService.delete(id);
    }

    //getByDateAndDoctor

    @GetMapping("/get-by-date-and-doctor")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getByDateAndDoctor(
            @RequestParam (name = "date")LocalDate date,
            @RequestParam(name ="doctorId") long doctorId
            ){
        return this.appointmentService.getByDateAndDoctor(date,doctorId);
    }
    @GetMapping("/get-by-date-and-animal")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AppointmentResponse>> getByDateAndAnimal(
            @RequestParam (name = "date")LocalDate date,
            @RequestParam(name ="animalId") long animalId
    ){
        return this.appointmentService.getByDateAndAnimal(date,animalId);
    }

}
