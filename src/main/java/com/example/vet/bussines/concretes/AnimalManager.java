package com.example.vet.bussines.concretes;

import com.example.vet.bussines.abstracts.IAnimalService;
import com.example.vet.bussines.abstracts.ICustomerService;
import com.example.vet.core.config.modelMapper.IModelMapperService;
import com.example.vet.core.exceptions.NotFoundException;
import com.example.vet.core.result.Result;
import com.example.vet.core.result.ResultData;
import com.example.vet.core.utilies.Msg;
import com.example.vet.core.utilies.ResultHelper;
import com.example.vet.dao.AnimalRepo;
import com.example.vet.dto.request.animalRequest.AnimalSaveRequest;
import com.example.vet.dto.request.animalRequest.AnimalUpdateRequest;
import com.example.vet.dto.response.CursorResponse;
import com.example.vet.dto.response.animalResponse.AnimalResponse;
import com.example.vet.dto.response.vaccineResponse.VaccineResponse;
import com.example.vet.entity.Animal;
import com.example.vet.entity.Customer;
import com.example.vet.entity.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AnimalManager implements IAnimalService {
    private final AnimalRepo animalRepo;
    private final IModelMapperService modelMapper;
    private final ICustomerService customerService;

    public AnimalManager(AnimalRepo animalRepo, IModelMapperService modelMapper, ICustomerService customerService) {
        this.animalRepo = animalRepo;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @Override
    public ResultData<AnimalResponse> save(AnimalSaveRequest animalSaveRequest) {
        Customer customer = this.customerService.get(animalSaveRequest.getCustomerId());
        animalSaveRequest.setCustomerId(0);

        Animal animal = this.modelMapper.forRequest().map(animalSaveRequest, Animal.class);
        animal.setCustomer(customer);


        this.animalRepo.save(animal);
        AnimalResponse animalResponse = this.modelMapper.forResponse().map(animal, AnimalResponse.class);

        return ResultHelper.created(animalResponse);
    }

    @Override
    public Animal get(long id) {
        return this.animalRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(Msg.NOT_FOUND));
    }

    @Override
    public ResultData<CursorResponse<AnimalResponse>> cursor(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Animal> animalPage = this.animalRepo.findAll(pageable);
        Page<AnimalResponse> animalResponsePage = animalPage
                .map(animal -> this.modelMapper.forResponse().map(animal, AnimalResponse.class));

        return ResultHelper.cursor(animalResponsePage);
    }

    @Override
    public ResultData<AnimalResponse> update(AnimalUpdateRequest animalUpdateRequest) {
        this.get(animalUpdateRequest.getId());
        Animal animal = this.modelMapper.forRequest().map(animalUpdateRequest, Animal.class);
        if (animalUpdateRequest.getCustomerId() != 0) {
            Customer customer = this.customerService.get(animalUpdateRequest.getCustomerId());
            animal.setCustomer(customer);
        }
        this.animalRepo.save(animal);
        AnimalResponse animalResponse = this.modelMapper.forResponse().map(animal, AnimalResponse.class);
        return ResultHelper.success(animalResponse);
    }

    @Override
    public Result delete(long id) {
        Animal animal = this.get(id);
        this.animalRepo.delete(animal);
        return ResultHelper.ok();
    }

    @Override
    public ResultData<List<AnimalResponse>> getByName(String name) {
        List<Animal> animals = this.animalRepo.findByName(name);

        if (animals.isEmpty()) {
            throw new NotFoundException(Msg.NOT_FOUND);
        }

        List<AnimalResponse> animalResponseList = new ArrayList<>();
        for (Animal animal : animals) {
            animalResponseList.add(this.modelMapper.forResponse().map(animal, AnimalResponse.class));
        }

        return ResultHelper.success(animalResponseList);
    }

    @Override
    public ResultData<List<VaccineResponse>> getVaccines(long id) {

        Animal animal =this.get(id);

        List<Vaccine> vaccineList = animal.getVaccines();

        List<VaccineResponse> vaccineResponseList = new ArrayList<>();

        for (Vaccine vaccine:vaccineList) {
            vaccineResponseList.add(this.modelMapper.forResponse().map(vaccine,VaccineResponse.class));
        }

        return ResultHelper.success(vaccineResponseList);
    }


}
