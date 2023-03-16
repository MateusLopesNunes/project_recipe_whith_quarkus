package org.acme.service;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
        File customDir = new File(path);

        String pathName = customDir.getAbsolutePath() +
                File.separator + fileName;

        Files.write(Paths.get(pathName), bytes,
                StandardOpenOption.CREATE_NEW);
        return pathName;
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
