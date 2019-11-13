package com.ins.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.HtmlUtils;

import com.ins.model.Customer;
import com.ins.model.CustomerUI;
import com.ins.model.FileResponseEntity;
import com.ins.payload.UploadFileResponse;
import com.ins.repository.ResponseRepo;
import com.ins.service.FileStorageService;



@RestController
@RequestMapping("api")
public class FileController {
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    ResponseRepo responseRepo;

    @PostMapping("/uploadFile")
    public  String uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("username")String username) {
        System.out.println("username>>>>>"+username);
    	String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName)
                .toUriString();
       
UploadFileResponse fileResponse=new UploadFileResponse(fileName, fileDownloadUri,
        file.getContentType(), file.getSize(),LocalDateTime.now());
FileResponseEntity responseEntity = new FileResponseEntity();
responseEntity.setFileName(fileName);
responseEntity.setFileDownloadUri(fileDownloadUri);
responseEntity.setUsername(username);
responseRepo.save(responseEntity);
        return fileResponse.getFileDownloadUri();
    }
    
    @GetMapping("/filelist")
    public List<FileResponseEntity> filelist(){
    List<FileResponseEntity> fileList = responseRepo.findAll();
    return fileList;
    }
    @RequestMapping("/searchbyUsername/{username}")
    public List<FileResponseEntity> fetchDataByFirstName(@PathVariable String username){
    List<FileResponseEntity> fileList = responseRepo.findByUsername(username);
//    List<CustomerUI> customerUI = new ArrayList<>();
//    for (Customer customer : customers) {
//    customerUI.add(new CustomerUI(customer.getFirstName(),customer.getLastName()));
//    }
    return fileList;
    }
    
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello";
    }
//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
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
