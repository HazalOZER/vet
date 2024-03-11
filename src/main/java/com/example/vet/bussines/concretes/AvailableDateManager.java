package com.example.vet.bussines.concretes;

import com.example.vet.bussines.abstracts.IAvailableDateService;
import com.example.vet.bussines.abstracts.IDoctorService;
import com.example.vet.core.config.modelMapper.IModelMapperService;
import com.example.vet.core.exceptions.AlreadyExistsException;
import com.example.vet.core.exceptions.NotFoundException;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.core.utilies.Msg;
import com.example.vet.core.utilies.ResultHelper;
import com.example.vet.dao.AvailableDateRepo;
import com.example.vet.dto.request.availableRequest.AvailableDateSaveRequest;
import com.example.vet.dto.request.availableRequest.AvailableDateUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.availableResponse.AvailableDateResponse;
import com.example.vet.entity.AvailableDate;
import com.example.vet.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvailableDateManager implements IAvailableDateService {
    private final AvailableDateRepo availableDateRepo;
    private final IModelMapperService modelMapper;
    private final IDoctorService doctorService;

    public AvailableDateManager(AvailableDateRepo availableDateRepo, IModelMapperService mapperService, IDoctorService doctorService) {
        this.availableDateRepo = availableDateRepo;
        this.modelMapper = mapperService;
        this.doctorService = doctorService;
    }

    @Override
    public ResultData<AvailableDateResponse> save(AvailableDateSaveRequest availableDateSaveRequest) {

        Doctor doctor = this.doctorService.get(availableDateSaveRequest.getDoctorId());
        availableDateSaveRequest.setDoctorId(0);



        List<AvailableDate> availableDateList = doctor.getAvailableDates();
        for (AvailableDate dates: availableDateList) {
            if (availableDateSaveRequest.getAvailableDate().equals(dates.getAvailableDate())){
                throw new AlreadyExistsException(Msg.ALREADY_EXISTS);
            }
        }

        AvailableDate availableDate = this.modelMapper.forRequest().map(availableDateSaveRequest, AvailableDate.class);

        availableDate.setDoctor(doctor);

        this.availableDateRepo.save(availableDate);

        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class);

        return ResultHelper.created(availableDateResponse);
    }

    @Override
    public AvailableDate get(long id) {
        return this.availableDateRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<AvailableDate> availableDatePage = this.availableDateRepo.findAll(pageable);
        Page<AvailableDateResponse> availableDateResponsePage = availableDatePage
                .map(availableDate -> this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class));


        return ResultHelper.cursor(availableDateResponsePage);
    }

    @Override
    public ResultData<AvailableDateResponse> update(AvailableDateUpdateRequest availableDateUpdateRequest) {
        this.get(availableDateUpdateRequest.getId());
        AvailableDate availableDate = this.modelMapper.forRequest().map(availableDateUpdateRequest,AvailableDate.class);
        if(availableDateUpdateRequest.getDoctorId() !=0){
            Doctor doctor = this.doctorService.get(availableDateUpdateRequest.getDoctorId());
            availableDate.setDoctor(doctor);
        }
        this.availableDateRepo.save(availableDate);
        AvailableDateResponse availableDateResponse = this.modelMapper.forResponse().map(availableDate,AvailableDateResponse.class);

        return ResultHelper.success(availableDateResponse);
    }

    @Override
    public Result delete(long id) {
        AvailableDate availableDate = this.get(id);
        this.availableDateRepo.delete(availableDate);
        return ResultHelper.ok();
    }
}
