package com.rakuten.fullstackrecruitmenttest.web;

import com.rakuten.fullstackrecruitmenttest.model.Employee;
import com.rakuten.fullstackrecruitmenttest.model.EmployeeRepository;
import com.rakuten.fullstackrecruitmenttest.payload.UploadFileResponse;
import com.rakuten.fullstackrecruitmenttest.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
class EmployeeController {

    private final Logger log = LoggerFactory.getLogger(EmployeeController.class);
    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/employees")
    Collection<Employee> employees() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/{id}")
    ResponseEntity<?> getEmployee(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/employee")
    ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) throws URISyntaxException {
        log.info("Request to create employee: {}", employee);
        Employee result = employeeRepository.save(employee);
        return ResponseEntity.created(new URI("/api/employee/" + result.getId()))
                .body(result);
    }

    @PutMapping("/employee")
    ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) {
        log.info("Request to update employee: {}", employee);
        Employee result = employeeRepository.save(employee);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        log.info("Request to delete employee: {}", id);
        employeeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Autowired
    private FileStorageService fileStorageService;
    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName)
                .toUriString();
        log.info(fileDownloadUri);

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}