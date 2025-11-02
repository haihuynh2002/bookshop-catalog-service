package com.bookshop.catalog_service.catalog.domain;

import com.bookshop.catalog_service.catalog.web.BookCreationRequest;
import com.bookshop.catalog_service.catalog.web.BookEditRequest;
import com.bookshop.catalog_service.catalog.web.BookTypeRequest;
import com.bookshop.catalog_service.catalog.web.BookResponse;
import com.bookshop.catalog_service.file.FileClient;
import com.bookshop.catalog_service.file.FileResponse;
import com.bookshop.catalog_service.file.ImageType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService {

    @NonFinal
    Logger log = LoggerFactory.getLogger(BookService.class);

    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    TypeRepository typeRepository;
    BookTypeRepository bookTypeRepository;
    FileClient fileClient;
    BookMapper bookMapper;

    @Transactional(readOnly = true)
    public List<BookResponse> viewBookList() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookResponse)
                .map(response -> {
                    Set<FileResponse> imageResponses = fileClient.findFilesByOwnerId(response.getId(), ImageType.BOOK);
                    response.setImages(imageResponses);
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookResponse viewBookDetails(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBookResponse)
                .map(response -> {
                    Set<FileResponse> imageResponses = fileClient.findFilesByOwnerId(response.getId(), ImageType.BOOK);
                    response.setImages(imageResponses);
                    return response;
                })
                .orElseThrow(() -> new BookNotFoundException(id.toString()));
    }

    @Transactional
    public Book addBookToCatalog(BookCreationRequest bookRequest, List<MultipartFile> imageFiles) {
        if (bookRepository.existsByIsbn(bookRequest.getIsbn())) {
            throw new BookAlreadyExistsException(bookRequest.getIsbn());
        }

        Book book = bookMapper.toBook(bookRequest);

        // Handle categories
        if (bookRequest.getCategories() != null && !bookRequest.getCategories().isEmpty()) {
            Set<Category> categories = bookRequest.getCategories().stream()
                    .map(categoryId -> categoryRepository.findById(Long.parseLong(categoryId))
                            .orElseThrow(() -> new CategoryNotFoundException(categoryId)))
                    .collect(Collectors.toSet());
            book.setCategories(categories);
        }

        if (bookRequest.getTypes() != null && !bookRequest.getTypes().isEmpty()) {
            for (String typeId : bookRequest.getTypes()) {
                Type type = typeRepository.findById(Long.parseLong(typeId))
                        .orElseThrow(() -> new TypeNotFoundException(typeId));

                // Initialize with quantity 0, can be updated later
                book.addType(type, 0);
            }
        }

        var savedBook = bookRepository.save(book);

        if(!Objects.isNull(imageFiles) && !imageFiles.isEmpty()) {
            try {
                List<FileResponse> response = fileClient.uploadFiles(imageFiles, savedBook.getId(), ImageType.BOOK);
                log.info("Uploaded {} images for book {}", response.size(), savedBook.getId());
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }
        return savedBook;
    }

    @Transactional
    public void removeBookFromCatalog(Long id) {
        bookRepository.deleteById(id);
        fileClient.deleteFiles(id, ImageType.BOOK);
    }

    @Transactional
    public Book editBookDetails(Long id, BookEditRequest bookRequest, List<MultipartFile> imageFiles) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    bookMapper.updateBook(existingBook, bookRequest);

                    existingBook.setCategories(new HashSet<>());
                    // Update categories if provided
                    if (bookRequest.getCategories() != null) {
                        Set<Category> categories = bookRequest.getCategories().stream()
                                .map(categoryId -> categoryRepository.findById(Long.parseLong(categoryId))
                                        .orElseThrow(() -> new CategoryNotFoundException(categoryId)))
                                .collect(Collectors.toSet());
                        existingBook.setCategories(categories);
                    }

                    List<BookType> existingBookTypes = List.copyOf(existingBook.getBookTypes());
                    existingBook.getBookTypes().clear();
                    // Update types with quantities if provided
                    if (bookRequest.getTypes() != null) {
                        // Add new types with quantities
                        for (String typeId : bookRequest.getTypes()) {
                            Type type = typeRepository.findById(Long.parseLong(typeId))
                                    .orElseThrow(() -> new TypeNotFoundException(typeId));
                            Integer existingQuantity = getExistingQuantity(existingBookTypes, Long.parseLong(typeId));
                            existingBook.addType(type, existingQuantity);
                        }
                    }

                    if(!Objects.isNull(imageFiles) && !imageFiles.isEmpty()) {
                        try {
                            List<FileResponse> response = fileClient.updateFiles(imageFiles, existingBook.getId(), ImageType.BOOK);
                            log.info("Update {} images for book {}", response.size(), existingBook.getId());
                        } catch (Exception e) {
                            log.info(e.getMessage());
                        }
                    }

                    return bookRepository.save(existingBook);
                }).orElseThrow(() -> new BookNotFoundException(id.toString()));
    }

    private Integer getExistingQuantity(List<BookType> existingBookTypes, Long typeId) {
        return existingBookTypes.stream()
                .filter(bt -> bt.getType().getId().equals(typeId))
                .findFirst()
                .map(BookType::getQuantity)
                .orElse(0);
    }

    @Transactional
    public void updateBookTypeQuantity(Long bookId, Long typeId, Integer quantity) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId.toString()));

        BookType bookType = book.getBookTypes().stream()
                .filter(bt -> bt.getType().getId().equals(typeId))
                .findFirst()
                .orElseThrow(() -> new BookTypeNotFoundException(bookId, typeId));

        bookType.setQuantity(quantity);
        bookTypeRepository.save(bookType);
    }

    @Transactional
    public void addTypeToBook(Long bookId, BookTypeRequest typeRequest) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId.toString()));
        Type type = typeRepository.findById(typeRequest.getTypeId())
                .orElseThrow(() -> new TypeNotFoundException(typeRequest.getTypeId().toString()));

        // Check if relationship already exists
        boolean alreadyExists = book.getBookTypes().stream()
                .anyMatch(bt -> bt.getType().getId().equals(typeRequest.getTypeId()));

        if (alreadyExists) {
            throw new BookTypeAlreadyExistsException(bookId, typeRequest.getTypeId());
        }

        book.addType(type, typeRequest.getQuantity());
        bookRepository.save(book);
    }

    @Transactional
    public void removeTypeFromBook(Long bookId, Long typeId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId.toString()));
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new TypeNotFoundException(typeId.toString()));

        BookType bookType = book.getBookTypes().stream()
                .filter(bt -> bt.getType().getId().equals(typeId))
                .findFirst()
                .orElseThrow(() -> new BookTypeNotFoundException(bookId, typeId));

        book.removeType(type);
        bookRepository.save(book);
    }
}