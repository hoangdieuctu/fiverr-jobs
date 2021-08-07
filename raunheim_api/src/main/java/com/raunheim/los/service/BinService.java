package com.raunheim.los.service;

import com.raunheim.los.dto.request.bin.Bin;
import com.raunheim.los.dto.request.bin.Bins;
import com.raunheim.los.model.Location;
import com.raunheim.los.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Validator;

@Slf4j
@Service
public class BinService extends LosService<Bins> {

    @Value("${storage.location.bins}")
    private String storageLocation;

    private final ClientService clientService;

    private final LocationRepository locationRepository;

    public BinService(FileProcessingService fileProcessingService,
                      FileStorageService fileStorageService,
                      Validator validator,
                      ClientService clientService,
                      LocationRepository locationRepository) {
        super(Bins.class, validator, fileProcessingService, fileStorageService);
        this.clientService = clientService;
        this.locationRepository = locationRepository;
    }

    @Override
    public String getStorageLocation() {
        return storageLocation;
    }

    @Transactional
    public void processUploadBins(MultipartFile file) {
        var bins = super.storeToFile(file);
        log.info("Stored bins into file: {}", bins);

        bins.getBin().forEach(this::saveBin);
    }

    public Location getOrCreate(String locationId) {
        var location = locationRepository.findByLocationId(locationId);
        if (location != null) {
            return location;
        }

        log.info("Create a new location: {}", locationId);
        var newLocation = new Location();
        newLocation.setLocationId(locationId);
        locationRepository.save(newLocation);

        return newLocation;
    }

    private void saveBin(Bin bin) {
        var existing = locationRepository.findByLocationId(bin.getLocationId());
        if (existing == null) {
            existing = Location.from(bin);
        } else {
            existing.copy(bin);
        }

        var client = clientService.getOrCreate(bin.getClient());
        existing.setClient(client.getId());
        locationRepository.save(existing);
        log.info("Updated location: {}", existing);
    }
}
