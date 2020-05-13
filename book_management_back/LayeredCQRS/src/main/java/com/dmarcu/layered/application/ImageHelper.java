package com.dmarcu.layered.application;

import com.dmarcu.layered.application.exceptions.ApplicationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

@Component
public class ImageHelper {

    @Value("${books.image_source_dir}")
    private String imagesDir;
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageHelper.class);

    /**
     * Method to store the image on the drive
     * @param imageType image extension(jpg, jpeg, png)
     * @param imageAsBytes image as a byte array
     * @return path to the image location
     */
    public String uploadImage(String imageType, byte[] imageAsBytes) {
        String imageFileName = RandomStringUtils.randomAlphanumeric(20);
        String imagePath = imagesDir + imageFileName + "." + imageType;
        try {
            FileUtils.writeByteArrayToFile(new File(imagePath), imageAsBytes);
        } catch (IOException e) {
            LOGGER.error("There was an error writing the image to file: " + e.getMessage());
            throw new ApplicationException();
        }
        return imagePath;
    }

    /**
     * Method to get the image bytes from given path
     * @param imagePath the image location
     * @return the image as a byte array
     */
    public String getImageFromPath(String imagePath) {
        String imageAsString;
        try {
            URL url = getURL(imagePath);
            byte[] imageBytes; imageBytes = url != null
                    ? IOUtils.toByteArray(url)
                    : FileUtils.readFileToByteArray(new File(imagePath));
            imageAsString = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            LOGGER.error("There was an error reading image from file/url: " + e.getMessage());
            throw new ApplicationException();
        }
        return imageAsString;
    }

    private URL getURL(String urlString) {
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            url = null;
        }
        return url;
    }
}
