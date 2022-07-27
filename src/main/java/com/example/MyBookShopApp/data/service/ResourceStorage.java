package com.example.MyBookShopApp.data.service;

import com.example.MyBookShopApp.data.repository.BookFileRepository;
import com.example.MyBookShopApp.data.struct.book.file.BookFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class ResourceStorage {

    // путь для загрузки изображения который находится в application.properties
    @Value("${upload.path}")
    String uploadPath;

    // путь для скачивания изображения который находится в application.properties
    @Value("${download.path}")
    String downloadPath;

    private final BookFileRepository bookFileRepository;

    @Autowired
    public ResourceStorage(BookFileRepository bookFileRepository) {
        this.bookFileRepository = bookFileRepository;
    }

    public String saveNewBookImage(MultipartFile file, String slug) throws IOException {
        String resourceURI = null;
        //создаём дерикторию для хранения картинок
        if(!file.isEmpty()){
            if (!new File(uploadPath).exists()){
                Files.createDirectories(Paths.get(uploadPath));
                Logger.getLogger(this.getClass().getSimpleName()).info("created image folder in " + uploadPath);
            }
            // при помощи метода getExtension передав в аргумент оригинальное имя файла включая название и расширение получаем только имя файла
            String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // путь который будет использоваться для сохранения книг
            Path path = Paths.get(uploadPath, fileName);
            // book-covers это название католога в котором непосредственно храняться книги
            resourceURI = "/book-covers/" + fileName;
            // трансфер файла из вне в нашу файловую систему
            file.transferTo(path); // uploading user file here
            Logger.getLogger(this.getClass().getSimpleName()).info(fileName + " upload OK!");
        }
        return resourceURI;
    }

    public Path getBookFilePath(String hash) {
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        return Paths.get(bookFile.getPath());
    }

    public MediaType getBookFileMine(String hash) {
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        String mineType = URLConnection.guessContentTypeFromName(Paths.get(bookFile.getPath()).getFileName().toString());
        if (mineType != null) {
            return MediaType.parseMediaType(mineType);
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    public byte[] getBookFileArray(String hash) throws IOException {
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        Path path = Paths.get(downloadPath, bookFile.getPath());
        return  Files.readAllBytes(path);
    }
}
