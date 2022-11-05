package ru.practicum.main_service.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.main_service.compilation.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long>, PagingAndSortingRepository<Compilation, Long> {

    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}