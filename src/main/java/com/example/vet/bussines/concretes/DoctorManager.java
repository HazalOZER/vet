package com.example.vet.bussines.concretes;

import com.example.vet.bussines.abstracts.IDoctorService;
import com.example.vet.core.config.modelMapper.IModelMapperService;
import com.example.vet.core.exceptions.AlreadyExistsException;
import com.example.vet.core.exceptions.NotFoundException;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.core.utilies.Msg;
import com.example.vet.core.utilies.ResultHelper;
import com.example.vet.dao.DoctorRepo;
import com.example.vet.dto.request.doctorRequest.DoctorSaveRequest;
import com.example.vet.dto.request.doctorRequest.DoctorUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.doctorResponse.DoctorResponse;
import com.example.vet.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorManager implements IDoctorService {

    private final DoctorRepo doctorRepo;

    private final IModelMapperService modelMapper;

    public DoctorManager(DoctorRepo doctorRepo, IModelMapperService modelMapper) {
        this.doctorRepo = doctorRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultData<DoctorResponse> save(DoctorSaveRequest doctorSaveRequest) {
        Doctor doctor = this.modelMapper.forRequest().map(doctorSaveRequest,Doctor.class);
        Doctor control = this.doctorRepo.findByPhoneAndMail(doctorSaveRequest.getPhone(),doctorSaveRequest.getMail());

        if (control != null) {
            throw new AlreadyExistsException(Msg.ALREADY_EXISTS);
        }

        this.doctorRepo.save(doctor);
        DoctorResponse doctorResponse = this.modelMapper.forResponse().map(doctor,DoctorResponse.class);
        return ResultHelper.created(doctorResponse);
    }

    @Override
    public Doctor get(long id) {
        return this.doctorRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public ResultData<CursorResponse<DoctorResponse>> cursor(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Doctor> doctorPage =this.doctorRepo.findAll(pageable);
        Page<DoctorResponse> doctorResponsePage = doctorPage.map(
                doctor -> this.modelMapper.forResponse().map(doctor,DoctorResponse.class));
        return ResultHelper.cursor(doctorResponsePage);
    }

    @Override
    public ResultData<DoctorResponse> update(DoctorUpdateRequest doctorUpdateRequest) {
        this.get(doctorUpdateRequest.getId());
        Doctor doctor = this.modelMapper.forRequest().map(doctorUpdateRequest,Doctor.class);
        this.doctorRepo.save(doctor);
        DoctorResponse doctorResponse = this.modelMapper.forResponse().map(doctor,DoctorResponse.class);
        return ResultHelper.success(doctorResponse);
    }

    @Override
    public Result delete(long id) {
        Doctor doctor = this.get(id);
        this.doctorRepo.delete(doctor);
        return ResultHelper.ok();
    }
}
