import file.FileHelper;
import file.ImageFile;
import file.Negative;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.color.ColorSpace;
import java.awt.event.ItemEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Galery {
    static String ROOT_DIR = "./images";

    static JFrame frame;

    private JPanel panel1;
    private JComboBox comboBox1;
    private JList list1;
    private JButton metadataDüzenleButton;
    private JPanel image;
    private JTextField TextField;
    private JButton editLocation;
    private JButton rotate;
    private JButton negativeButton;
    private JButton siyahBeyazButton;
    private JButton Kaydet;

    static FileHelper fileHelper = new FileHelper();
    static ArrayList<ImageFile> files;
    static ImageFile selectedImage;
    static BufferedImage editedImage;

    public Galery(){
        comboBox1.addItemListener(e -> {
            if(e.getStateChange()==ItemEvent.SELECTED){
                selectGalery((file.Galery)e.getItem());
            }
        });
        list1.addListSelectionListener(e -> {
            if(list1.getSelectedIndex()>=0){
                ImageFile file = files.get(list1.getSelectedIndex());
                showImage(file);
            }
        });

        TextField.addActionListener(e -> searchWithTag(TextField.getText()));

        metadataDüzenleButton.addActionListener(e -> { if(selectedImage == null || editedImage == null){
                return;
            }
          String metadata = JOptionPane.showInputDialog(frame,"Metadata düzenle",selectedImage.getTag());
          if(metadata != null)
            selectedImage.writeMetadata("Tag",metadata);
        });
        editLocation.addActionListener(e -> {
            if(selectedImage == null || editedImage == null){
                return;
            }
            String metadata = JOptionPane.showInputDialog(frame,"Konum düzenle",selectedImage.getLocation());
            if(metadata != null)
                selectedImage.writeMetadata("Location",metadata);
        });
        rotate.addActionListener(e -> {
            if(selectedImage == null || editedImage == null){
                return;
            }
            try{
                BufferedImage bufferedImage = selectedImage.readImage();
                AffineTransform transform = new AffineTransform();
                transform.rotate(Integer.valueOf(JOptionPane.showInputDialog(frame,"Açı",90))*0.0174533, bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
                AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
                bufferedImage = op.filter(bufferedImage, null);
                showImage(bufferedImage);
            }catch (Exception b){

            }
        });
        siyahBeyazButton.addActionListener(e -> {
            if(selectedImage == null || editedImage == null){
                return;
            }
            BufferedImage bufferedImage = selectedImage.readImage();
            ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
            op.filter(bufferedImage, bufferedImage);
            showImage(bufferedImage);

        });
        negativeButton.addActionListener(e -> {
            if(selectedImage == null || editedImage == null){
                return;
            }
            BufferedImage bufferedImage = selectedImage.readImage();
            showImage(Negative.negative(bufferedImage));
        });
        Kaydet.addActionListener(e -> {
            if(selectedImage == null || editedImage == null){
                return;
            }
            String filename = JOptionPane.showInputDialog(frame,"Kaydet",selectedImage.getName());
            String new_path = new File(selectedImage.getPath()).getParent()+"/"+filename;
            try {
                ImageIO.write(editedImage,"jpeg",new File(new_path));
            } catch (IOException e1) {
                e1.printStackTrace();
            }


        });

    }

    public void searchWithTag(String tag){
       ArrayList<ImageFile> images = new ArrayList<>();
       for(ImageFile file :  fileHelper.getAllImages(ROOT_DIR)){
           if(file.hasTag(tag)){
               images.add(file);
           }
       }
       files = images;
       list1.setListData(files.toArray());
       refresh();
    }

    public void selectGalery(file.Galery galery){
        files = fileHelper.getImageFiles(galery.getPath());
        list1.setListData(files.toArray());
        refresh();
    }


    public void showImage(ImageFile file){
        selectedImage = file;
        showImage(file.readImage());
    }

    public void showImage(BufferedImage bufferedImage){
        ImageIcon icon = new ImageIcon(bufferedImage);
        JLabel label = new JLabel(icon);
        image.removeAll();
        image.add(label,0);
        editedImage = bufferedImage;
        refresh();

    }

    public static void refresh(){
        frame.pack();
    }


    public static void main(String[] args) {
        Galery main = new Galery();
        frame = new JFrame("Galery");
        frame.setContentPane(main.panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        for(file.Galery galery : fileHelper.getGaleries(ROOT_DIR)){
            main.comboBox1.addItem(galery);
        }
        refresh();
    }
}
