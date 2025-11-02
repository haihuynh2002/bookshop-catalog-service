package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.TypeRequest;
import com.bookshop.catalog_service.catalog.web.TypeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeMapper typeMapper;

    @Transactional(readOnly = true)
    public List<TypeResponse> getAllTypes() {
        log.debug("Fetching all types");
        return typeRepository.findAll().stream()
                .map(typeMapper::toTypeResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TypeResponse getTypeById(Long id) {
        log.debug("Fetching type by ID: {}", id);
        return typeRepository.findById(id)
                .map(typeMapper::toTypeResponse)
                .orElseThrow(() -> new TypeNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public TypeResponse getTypeByName(String name) {
        log.debug("Fetching type by name: {}", name);
        return typeRepository.findByName(name)
                .map(typeMapper::toTypeResponse)
                .orElseThrow(() -> new TypeNotFoundException("Name: " + name));
    }

    @Transactional
    public TypeResponse createType(TypeRequest typeRequest) {
        log.debug("Creating new type: {}", typeRequest);
        
        if (typeRepository.existsByName(typeRequest.getName())) {
            throw new TypeAlreadyExistsException(typeRequest.getName());
        }

        Type type = typeMapper.toType(typeRequest);
        Type savedType = typeRepository.save(type);
        
        log.info("Created new type with ID: {}", savedType.getId());
        return typeMapper.toTypeResponse(savedType);
    }

    @Transactional
    public TypeResponse updateType(Long id, TypeRequest typeRequest) {
        log.debug("Updating type with ID: {}", id);
        
        Type existingType = typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(id));

        // Check if name is being changed and if it conflicts with another type
        if (!existingType.getName().equals(typeRequest.getName()) &&
            typeRepository.existsByNameAndIdNot(typeRequest.getName(), id)) {
            throw new TypeAlreadyExistsException(typeRequest.getName());
        }

        typeMapper.updateTypeFromRequest(typeRequest, existingType);
        Type updatedType = typeRepository.save(existingType);
        
        log.info("Updated type with ID: {}", id);
        return typeMapper.toTypeResponse(updatedType);
    }

    @Transactional
    public void deleteType(Long id) {
        log.debug("Deleting type with ID: {}", id);
        
        if (!typeRepository.existsById(id)) {
            throw new TypeNotFoundException(id);
        }

        // Check if type is associated with any books
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new TypeNotFoundException(id));
        
        if (!type.getBooks().isEmpty()) {
            throw new TypeInUseException(id, type.getBooks().size());
        }

        typeRepository.deleteById(id);
        log.info("Deleted type with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<TypeResponse> searchTypesByName(String name) {
        log.debug("Searching types by name: {}", name);
        return typeRepository.findByNameContainingIgnoreCase(name).stream()
                .map(typeMapper::toTypeResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TypeResponse> searchTypesByDescription(String keyword) {
        log.debug("Searching types by description keyword: {}", keyword);
        return typeRepository.findByDescriptionContainingIgnoreCase(keyword).stream()
                .map(typeMapper::toTypeResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TypeResponse> getTypesByBookId(Long bookId) {
        log.debug("Fetching types for book ID: {}", bookId);
        return typeRepository.findByBookId(bookId).stream()
                .map(typeMapper::toTypeResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return typeRepository.existsById(id);
    }
}