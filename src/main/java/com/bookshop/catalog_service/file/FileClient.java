package com.bookshop.catalog_service.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.List;
import java.util.Set;

@FeignClient(name = "file-service", url = "${file.service.url}")
public interface FileClient {

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<FileResponse> uploadFiles(
            @RequestPart("files") List<MultipartFile> files,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("type") ImageType type
    );

    @PutMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    List<FileResponse> updateFiles(
            @RequestPart("files") List<MultipartFile> files,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("type") ImageType type
    );

    @GetMapping(value = "/files/{type}/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    Set<FileResponse> findFilesByOwnerId(@PathVariable Long ownerId,
                                         @PathVariable ImageType type);

    @DeleteMapping("/files/{type}/{ownerId}")
    void deleteFiles(@PathVariable Long ownerId,
                     @PathVariable ImageType type);
}
