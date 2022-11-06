package ru.practicum.main_service.event.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.event.model.Location;
import ru.practicum.main_service.event.repository.LocationRepository;
import ru.practicum.main_service.exceptions.WrongIdException;

import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public Location update(Location location, Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isEmpty()) {
            throw new WrongIdException("Location with id=" + id +  "not found");
        }
        Location updateLocation = optionalLocation.get();
        updateLocation.setLat(location.getLat());
        updateLocation.setLon(location.getLon());
        return locationRepository.save(updateLocation);
    }
}
