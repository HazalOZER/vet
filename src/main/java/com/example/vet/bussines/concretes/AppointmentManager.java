package com.example.vet.bussines.concretes;

import com.example.vet.bussines.abstracts.IAnimalService;
import com.example.vet.bussines.abstracts.IAppointmentService;
import com.example.vet.bussines.abstracts.IDoctorService;
import com.example.vet.core.config.modelMapper.IModelMapperService;
import com.example.vet.core.exceptions.NotFoundException;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.core.utilies.Msg;
import com.example.vet.core.utilies.ResultHelper;
import com.example.vet.dao.AppointmentRepo;
import com.example.vet.dto.request.appointmentRequest.AppointmentSaveRequest;
import com.example.vet.dto.request.appointmentRequest.AppointmentUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.appointmentResponse.AppointmentResponse;
import com.example.vet.entity.Animal;
import com.example.vet.entity.Appointment;
import com.example.vet.entity.AvailableDate;
import com.example.vet.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentManager implements IAppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;
    private final IAnimalService animalService;

    public AppointmentManager(AppointmentRepo appointmentRepo, IModelMapperService modelMapper, IDoctorService doctorService, IAnimalService animalService) {
        this.appointmentRepo = appointmentRepo;
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
        this.animalService = animalService;
    }

    @Override
    public ResultData<AppointmentResponse> save(AppointmentSaveRequest appointmentSaveRequest) {

        Doctor doctor = this.doctorService.get(appointmentSaveRequest.getDoctorId());
        appointmentSaveRequest.setDoctorId(0);

        //-Değerlendirme Formu 18


        if( appointmentSaveRequest.getAppointmentDate().getMinute()!= 00.00){

            throw new NotFoundException("Tam saatler dışında randavu seçeneği bulunmamaktadır");

        }

        List<AvailableDate> availableDateList= doctor.getAvailableDates();

        boolean isAvailable = false;
        for (AvailableDate date: availableDateList) {
            isAvailable = date.getAvailableDate().equals(appointmentSaveRequest.getAppointmentDate().toLocalDate());
            if(isAvailable) break;
        }
        if (!isAvailable){
            throw new NotFoundException("Doktor seçilen tarihte müssaitliği bulunmamaktadır");
        }
        List <Appointment> appointmentList = doctor.getAppointments();

        boolean isBusy = false;

        for (Appointment appointment :appointmentList){
            isBusy= appointment.getAppointmentDate().equals(appointmentSaveRequest.getAppointmentDate());
            if(isBusy) throw new  NotFoundException("Doktoron randevu saatinde müsaitliği bulunmamaktadır ");
        }


        Animal animal = this.animalService.get(appointmentSaveRequest.getAnimalId());
        appointmentSaveRequest.setAnimalId(0);

        Appointment appointment = this.modelMapper.forRequest().map(appointmentSaveRequest,Appointment.class);
        appointment.setAnimal(animal);
        appointment.setDoctor(doctor);

        this.appointmentRepo.save(appointment);

        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(appointment,AppointmentResponse.class);
        return ResultHelper.created(appointmentResponse);

    }

    @Override
    public Appointment get(long id) {
        return this.appointmentRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public ResultData<CursorResponse<AppointmentResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Appointment> appointmentPage = this.appointmentRepo.findAll(pageable);
        Page<AppointmentResponse> appointmentResponsePage = appointmentPage
                .map(appointment -> this.modelMapper.forResponse().map(appointment,AppointmentResponse.class));

        return ResultHelper.cursor(appointmentResponsePage);
    }

    @Override
    public ResultData<AppointmentResponse> update(AppointmentUpdateRequest appointmentUpdateRequest) {
        this.get(appointmentUpdateRequest.getId());
        Appointment appointment = this.modelMapper.forRequest().map(appointmentUpdateRequest,Appointment.class);
        if(appointmentUpdateRequest.getAnimalId() != 0){
            Animal animal =this.animalService.get(appointmentUpdateRequest.getAnimalId());
            appointment.setAnimal(animal);
        }
        if (appointmentUpdateRequest.getDoctorId() != 0){
            Doctor doctor = this.doctorService.get(appointmentUpdateRequest.getDoctorId());
            appointment.setDoctor(doctor);
        }
        this.appointmentRepo.save(appointment);
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(appointment,AppointmentResponse.class);

        return ResultHelper.success(appointmentResponse);
    }

    @Override
    public Result delete(long id) {
        Appointment appointment = this.get(id);
        this.appointmentRepo.delete(appointment);
        return ResultHelper.ok();
    }

    public ResultData<List<AppointmentResponse>> getByDateAndDoctor(LocalDate date, long doctorId){

        Doctor doctor = this.doctorService.get(doctorId);
        List<Appointment> appointmentList = doctor.getAppointments();
        List<AppointmentResponse> appointmentResponseList= new ArrayList<>();
        for (Appointment appointment : appointmentList){
            if(appointment.getAppointmentDate().toLocalDate().equals(date)){
                appointmentResponseList.add(this.modelMapper.forResponse().map(appointment,AppointmentResponse.class));
            }
        }
        return ResultHelper.success(appointmentResponseList);

    }

    public ResultData<List<AppointmentResponse>> getByDateAndAnimal(LocalDate date, long animalId){
        Animal animal = this.animalService.get(animalId);
        List<Appointment> appointmentList = animal.getAppointments();
        List<AppointmentResponse> appointmentResponseList= new ArrayList<>();
        for (Appointment appointment : appointmentList){
            if(appointment.getAppointmentDate().toLocalDate().equals(date)){
                appointmentResponseList.add(this.modelMapper.forResponse().map(appointment,AppointmentResponse.class));
            }
        }
        return ResultHelper.success(appointmentResponseList);
    }


}
