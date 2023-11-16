package com.groupal.king.store.products.adapter.database;

import com.groupal.king.store.products.adapter.database.model.ProductModel;
import com.groupal.king.store.products.adapter.database.repository.ProductDataRepository;
import com.groupal.king.store.products.application.port.out.ProductRepository;
import com.groupal.king.store.products.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDbAdapter implements ProductRepository {

    private final ProductDataRepository repository;

    @Override
    public List<Product> findAll() {
        log.info(">> Get all products from DB");

        var results = repository.findAll()
                .stream()
                .map(ProductModel::toDomain)
                .collect(Collectors.toList());

        log.info("<< Get all products with response: {}", results);
        return results;
    }

    @Override
    public Product createProduct(Product product) {
        log.info(">> Create product with: {}", product);

        ProductModel productModel = ProductModel.fromDomain(product);
        var response = repository.save(productModel);

        log.info("Saving in db a product with response {}", response);
        return product;
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
