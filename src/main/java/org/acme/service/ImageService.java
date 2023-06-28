package org.acme.service;

import org.acme.dto.request.ImagePath;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

@ApplicationScoped
public class ImageService {

    public List<String> uploadFile(MultipartFormDataInput input, String path) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        //List<String> fileNames = new ArrayList<>();
        List<InputPart> inputParts = uploadForm.get("file");

        String fileName = null;
        List<String> filePath = new ArrayList<>();
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                //fileNames.add(fileName);

                String extension = FilenameUtils.getExtension(fileName);
                List<String> validExtensions = List.of("png", "jpg", "jpeg");
                if (!validExtensions.contains(extension)) {
                    new BadRequestException();
                }

                fileName = UUID.randomUUID() + "." + extension;
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                String pathName = writeFile(inputStream, fileName, path);
                filePath.add(pathName);
            } catch (Exception e) {
                throw new BadRequestException();
            }
        }
        return filePath;
    }


    private String writeFile(InputStream inputStream,String fileName, String path)
            throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);

        //String caminhoDiretorioRecursos = getClass().getResource("/").getPath();
        //Path caminhoDiretorio = Paths.get(caminhoDiretorioRecursos);

        File customDir = new File(path);

        String pathName = "src/main/resources" +
                 path + "/" + fileName;

        Files.write(Paths.get(pathName), bytes,
                StandardOpenOption.CREATE_NEW);
        return fileName;
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.
                getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "";
    }
}
