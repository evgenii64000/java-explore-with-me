package ru.practicum.main_service.compilation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.main_service.compilation.model.Compilation;
import ru.practicum.main_service.compilation.model.CompilationDto;
import ru.practicum.main_service.compilation.model.CompilationMapper;
import ru.practicum.main_service.compilation.model.NewCompilationDto;
import ru.practicum.main_service.compilation.repository.CompilationRepository;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.repository.EventRepository;
import ru.practicum.main_service.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Autowired
    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        List<Compilation> compilations = compilationRepository.findAllByPinned(pinned, PageRequest.of(from / size, size)).toList();
        return compilations.stream().map(compilation -> CompilationMapper.fromCompilationToDto(compilation)).collect(Collectors.toList());
    }

    @Override
    public CompilationDto getCompilation(Long compId) {
        Optional<Compilation> compilation = compilationRepository.findById(compId);
        if (compilation.isEmpty()) {
            throw new NotFoundException("Compilation with id=" + compId + " was not found.");
        }
        return CompilationMapper.fromCompilationToDto(compilation.get());
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setTitle(newCompilationDto.getTitle());
        if (newCompilationDto.getPinned().equals(null)) {
            compilation.setPinned(false);
        } else {
            compilation.setPinned(newCompilationDto.getPinned());
        }
        if (newCompilationDto.getEvents().isEmpty()) {
            compilation.setEvents(null);
        } else {
            List<Long> events = newCompilationDto.getEvents();
            List<Event> eventsForCompilation = null;
            for (Long eventId : events) {
                Optional<Event> event = eventRepository.findById(eventId);
                if (event.isEmpty()) {
                    throw new NotFoundException("Event with id=" + eventId + " was not found.");
                }
                eventsForCompilation.add(event.get());
            }
            compilation.setEvents(eventsForCompilation);
        }
        Compilation newCompilation = compilationRepository.save(compilation);
        return CompilationMapper.fromCompilationToDto(newCompilation);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Optional<Compilation> compilationToRedact = compilationRepository.findById(compId);
        if (compilationToRedact.isEmpty()) {
            throw new NotFoundException("Compilation with id=" + compId + " was not found.");
        }
        Compilation compilation = compilationToRedact.get();
        boolean delete = false;
        for (Event event : compilation.getEvents()) {
            if (event.getId().equals(eventId)) {
                compilation.getEvents().remove(event);
                delete = true;
            }
        }
        if (!delete) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        compilationRepository.save(compilation);
    }

    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        Optional<Compilation> compilationToRedact = compilationRepository.findById(compId);
        if (compilationToRedact.isEmpty()) {
            throw new NotFoundException("Compilation with id=" + compId + " was not found.");
        }
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new NotFoundException("Event with id=" + eventId + " was not found.");
        }
        Compilation compilation = compilationToRedact.get();
        List<Event> newEvents = compilation.getEvents();
        newEvents.add(event.get());
        compilation.setEvents(newEvents);
        compilationRepository.save(compilation);
    }

    @Override
    public void unpinCompilation(Long compId) {
        Optional<Compilation> compilationToRedact = compilationRepository.findById(compId);
        if (compilationToRedact.isEmpty()) {
            throw new NotFoundException("Compilation with id=" + compId + " was not found.");
        }
        Compilation compilation = compilationToRedact.get();
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilation(Long compId) {
        Optional<Compilation> compilationToRedact = compilationRepository.findById(compId);
        if (compilationToRedact.isEmpty()) {
            throw new NotFoundException("Compilation with id=" + compId + " was not found.");
        }
        Compilation compilation = compilationToRedact.get();
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
