package file;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;

public class FileHelper {


    static final String[] EXTENSIONS = ImageIO.getReaderFormatNames();

    static final FilenameFilter IMAGE_FILTER = (dir, name) -> {
        for (final String ext : EXTENSIONS) {
            if (name.endsWith("." + ext)) {
                return (true);
            }
        }
        return (false);
    };

    public ArrayList<Galery> getGaleries(String root_path){
        ArrayList<Galery> galeries = new ArrayList<>();
        File dir = new File(root_path);
        if(dir.isDirectory()){
            for(File f : dir.listFiles()){
                if(f.isDirectory()){
                    Galery gallery = new Galery();
                    gallery.setFile(f);
                    gallery.setName(f.getName());
                    gallery.setPath(f.getAbsolutePath());
                    galeries.add(gallery);
                }
            }
        }

        return galeries;
    }


    public ArrayList<ImageFile> getImageFiles(String path){

        ArrayList<ImageFile> imageFiles = new ArrayList<>();
        File dir = new File(path);
        if (dir.isDirectory()) {
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                ImageFile file = null;
                try {
                    Date d = new Date(Files.readAttributes(f.toPath(), BasicFileAttributes.class).creationTime().toInstant().toEpochMilli());
                    file = new ImageFile(f.getName(),f.getPath(),readMetadata(f),d);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageFiles.add(file);
            }
        }
        return imageFiles;
    }

    /**
     * source:http://johnbokma.com/java/obtaining-image-metadata.html
     * @param file
     * @return
     */
    public static ArrayList<file.Metadata> readMetadata(File file){
        try {
            ArrayList<file.Metadata> metadatas = new ArrayList<>();
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    metadatas.add(new file.Metadata(tag.getDescription()));
                }
            }
            return metadatas;
        } catch (ImageProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  ArrayList<ImageFile> getAllImages(String dir) {
        ArrayList<ImageFile> imageFiles = new ArrayList<>();
        for(Galery galery : getGaleries(dir)){
            for(ImageFile file : getImageFiles(galery.getPath())){
                    imageFiles.add(file);
            }
        }
        return imageFiles;
    }


}
