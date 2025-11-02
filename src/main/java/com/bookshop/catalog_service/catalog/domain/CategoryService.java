package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.CategoryRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {

    CategoryRepository categoryRepository;
    BookRepository bookRepository;
    CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(name));
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id.toString()));
    }

    @Transactional
    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExistsException(request.getName());
        }
        Category category = categoryMapper.toCategory(request);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryRequest request) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    categoryMapper.updateCategory(existingCategory, request);
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new CategoryNotFoundException(id.toString()));
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id.toString());
        }
        categoryRepository.deleteById(id);
    }

//    public Page<Book> getBooksByCategory(Long categoryId, Pageable pageable) {
//        Category category = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new CategoryNotFoundException(categoryId.toString()));
//        return bookRepository.findByCategoriesName(category.getName(), pageable);
//    }

    public Set<Category> getBookCategories(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId.toString()));
        return book.getCategories();
    }
}