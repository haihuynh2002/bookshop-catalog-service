package com.bookshop.catalog_service.catalog.web;

import com.bookshop.catalog_service.catalog.domain.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("types")
@RequiredArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    public List<TypeResponse> getAllTypes() {
        return typeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse> getTypeById(@PathVariable Long id) {
        TypeResponse type = typeService.getTypeById(id);
        return ResponseEntity.ok(type);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TypeResponse> getTypeByName(@PathVariable String name) {
        TypeResponse type = typeService.getTypeByName(name);
        return ResponseEntity.ok(type);
    }

    @PostMapping
    public ResponseEntity<TypeResponse> createType(@Valid @RequestBody TypeRequest typeRequest) {
        TypeResponse createdType = typeService.createType(typeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeResponse> updateType(
            @PathVariable Long id,
            @Valid @RequestBody TypeRequest typeRequest) {
        TypeResponse updatedType = typeService.updateType(id, typeRequest);
        return ResponseEntity.ok(updatedType);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<TypeResponse>> searchTypesByName(@RequestParam String name) {
        List<TypeResponse> types = typeService.searchTypesByName(name);
        return ResponseEntity.ok(types);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<TypeResponse>> getTypesByBookId(@PathVariable Long bookId) {
        List<TypeResponse> types = typeService.getTypesByBookId(bookId);
        return ResponseEntity.ok(types);
    }
}