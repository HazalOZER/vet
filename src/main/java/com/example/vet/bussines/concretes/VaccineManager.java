package com.example.vet.bussines.concretes;

import com.example.vet.bussines.abstracts.IAnimalService;
import com.example.vet.bussines.abstracts.IVaccineService;
import com.example.vet.core.config.modelMapper.IModelMapperService;
import com.example.vet.core.exceptions.NotFoundException;
import com.example.vet.core.exceptions.VaccineProtectionException;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.core.utilies.Msg;
import com.example.vet.core.utilies.ResultHelper;
import com.example.vet.dao.VaccineRepo;
import com.example.vet.dto.request.vaccineRequest.VaccineSaveRequest;
import com.example.vet.dto.request.vaccineRequest.VaccineUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.vaccineResponse.VaccineResponse;
import com.example.vet.entity.Animal;
import com.example.vet.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service


public class VaccineManager implements IVaccineService {
    private final IModelMapperService modelMapper;
    private final VaccineRepo vaccineRepo;
    private final IAnimalService animalService;

    public VaccineManager(IModelMapperService modelMapper, VaccineRepo vaccineRepo, IAnimalService animalService) {
        this.modelMapper = modelMapper;
        this.vaccineRepo = vaccineRepo;
        this.animalService = animalService;
    }


    @Override
    public ResultData<VaccineResponse> save(VaccineSaveRequest vaccineSaveRequest) {


        Animal animal = this.animalService.get(vaccineSaveRequest.getAnimalId());
        vaccineSaveRequest.setAnimalId(0);

        //Değerlendirme Formu 22

        List<Vaccine> vaccineList = animal.getVaccines();

        for (Vaccine vaccine : vaccineList) {
            if (vaccine.getCode().equals(vaccineSaveRequest.getCode())
                    &&
                    vaccine.getProtectionFinishDate().isAfter(vaccineSaveRequest.getProtectionStartDate())) {
                throw new VaccineProtectionException(Msg.VACCINE);
            }
        }

        Vaccine vaccine = this.modelMapper.forRequest().map(vaccineSaveRequest, Vaccine.class);
        vaccine.setAnimal(animal);

        this.vaccineRepo.save(vaccine);

        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        return ResultHelper.created(vaccineResponse);
    }

    @Override
    public Vaccine get(long id) {
        return this.vaccineRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));

    }

    @Override
    public ResultData<CursorResponse<VaccineResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Vaccine> vaccinePage = this.vaccineRepo.findAll(pageable);
        Page<VaccineResponse> vaccineResponsePage = vaccinePage
                .map(vaccine -> this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
        return ResultHelper.cursor(vaccineResponsePage);
    }

    @Override
    public ResultData<VaccineResponse> update(VaccineUpdateRequest vaccineUpdateRequest) {
        this.get(vaccineUpdateRequest.getId());
        Vaccine vaccine = this.modelMapper.forRequest().map(vaccineUpdateRequest, Vaccine.class);
        if (vaccineUpdateRequest.getAnimalId() != 0) {
            Animal animal = this.animalService.get(vaccineUpdateRequest.getAnimalId());
            vaccine.setAnimal(animal);
        }
        this.vaccineRepo.save(vaccine);
        VaccineResponse vaccineResponse = this.modelMapper.forResponse().map(vaccine, VaccineResponse.class);
        return ResultHelper.success(vaccineResponse);
    }

    @Override
    public Result delete(long id) {
        Vaccine vaccine = this.get(id);
        this.vaccineRepo.delete(vaccine);
        return ResultHelper.ok();
    }

    @Override
    public ResultData<List<VaccineResponse>> findByDate(LocalDate startDate, LocalDate finishDate) {

        List<Vaccine> vaccineList = this.vaccineRepo.findByProtectionFinishDateBetween(startDate, finishDate);
        if (vaccineList.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }
        List<VaccineResponse> vaccineResponseList = new ArrayList<>();

        for (Vaccine vaccine : vaccineList) {
            vaccineResponseList.add(this.modelMapper.forResponse().map(vaccine, VaccineResponse.class));
        }

        return ResultHelper.success(vaccineResponseList);
    }
}
