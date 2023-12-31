package com.groupal.king.store.catalog.adapter.database;

import com.groupal.king.store.catalog.adapter.database.model.CategoryModel;
import com.groupal.king.store.catalog.adapter.database.repository.CategoryDataRepository;
import com.groupal.king.store.catalog.application.port.out.CategoryRepository;
import com.groupal.king.store.catalog.domain.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CategoryDbAdapter implements CategoryRepository {

    private final CategoryDataRepository repository;

    @Override
    public List<Category> findAll() {
        log.info(">> Get all products from DB");

        var results = repository.findAll()
                .stream()
                .map(CategoryModel::toDomain)
                .collect(Collectors.toList());

        log.info("<< Get all categories with response: {}", results);
        return results;
    }

    @Override
    public Category createCategory(Category category) {
        log.info(">> Create category with: {}", category);

        CategoryModel categoryModel = CategoryModel.fromDomain(category);
        var response = repository.save(categoryModel);

        log.info("Saving in db a category with response {}", response);
        return category;
    }

    /*
    ProductDbAdapter(SpringDataBiometricRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @Override
    public Optional<Biometric> getBiometricByIdentification(String identification) {
        log.info(">> Get biometric validations by identification: {}", identification);

        Optional<Biometric> results = this.repository.findByIdentification(identification)
                .map(BiometricModel::toDomain);;

        log.info("<< Get biometric validations with response: {}", results);
        return results;
    }

    @Override
    public void save(Biometric biometric) {

        var optByometricFound = repository.findByIdentification(biometric.getIdentification());

        if(optByometricFound.isPresent()){
            var biometricFound = optByometricFound.get();
            biometricFound.setIdentification(biometric.getIdentification());
            biometricFound.setLiveness(biometric.getLiveness());
            biometricFound.setFacialAuthentication(biometric.getFacialAuthentication());
            biometricFound.setTemplateRaw(biometric.getTemplateRaw());

            log.info("Update in db biometric validations with body {}", biometricFound);
            repository.save(biometricFound);
        }else{
            BiometricModel biometricModel = BiometricModel.fromDomain(biometric);

            log.info("Saving in db biometric validations with body {}", biometricModel);
            repository.save(biometricModel);
        }

    }
     */
}
