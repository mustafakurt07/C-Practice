package file;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ImageFile {
    private static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Date createTime;
    String name;
    String path;
    ArrayList<file.Metadata> metadata;

    public ImageFile(String name, String path, ArrayList<file.Metadata> tags,Date createTime) {
        this.name = name;
        this.path = path;
        this.metadata = tags;
        this.createTime = createTime;
    }


    public ArrayList<file.Metadata> getMetadata() {
        return metadata;
    }

    public void setMetadata(ArrayList<file.Metadata> metadata) {
        this.metadata = metadata;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public BufferedImage readImage(){
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public String toString() {
        return name;
    }

    private void write(byte[] data) throws IOException {
        Files.write(new File(path).toPath(), data);
        metadata = FileHelper.readMetadata(new File(path));
    }

    public void writeMetadata(String key,String value){
        try {
            byte[] data =  writeCustomData(readImage(),key,value);
            write(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] writeCustomData(BufferedImage buffImg, String key, String value) throws Exception {
        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();

        ImageWriteParam writeParam = writer.getDefaultWriteParam();
        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);


        ImageInputStream iis = ImageIO.createImageInputStream(new File(path));
        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        ImageReader reader = readers.next();
        reader.setInput(iis, true);
        IIOMetadata metadata = reader.getImageMetadata(0);


        IIOMetadataNode textEntry = new IIOMetadataNode("tEXtEntry");
        textEntry.setAttribute("keyword", key);
        textEntry.setAttribute("value", value);

        IIOMetadataNode text = new IIOMetadataNode("tEXt");
        text.appendChild(textEntry);

        IIOMetadataNode root = new IIOMetadataNode("javax_imageio_png_1.0");
        root.appendChild(text);

        metadata.mergeTree("javax_imageio_png_1.0", root);

        //writing the data
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream stream = ImageIO.createImageOutputStream(baos);
        writer.setOutput(stream);
        writer.write(metadata, new IIOImage(buffImg, null, metadata), writeParam);
        stream.close();
        return baos.toByteArray();
    }


    public boolean hasTag(String tag) {
        if(isDate(tag)){
            String g = format.format(createTime);
            return tag.equals(g);
        }
        for(file.Metadata s :metadata){
            if(s.value.contains(tag)){
                return true;
            }
        }
        return false;
    }

    private boolean isDate(String date){
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getTag() {
        if(metadata == null){
            return "";
        }
        String value=null;
        for(file.Metadata s :metadata){
           if(s.key.equals("Tag")){
               value = s.value;
           }
        }
        if(value != null)
            return value;
        return "";
    }
    public String getLocation() {
        if(metadata == null){
            return "";
        }
        String value=null;
        for(file.Metadata s :metadata){
            if(s.key.equals("Location")){
                value = s.value;
            }
        }
        if(value != null)
            return value;
        return "";
    }
}
