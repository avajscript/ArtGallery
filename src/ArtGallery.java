import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

class Photo {
    BufferedImage image;
    public static int MAX_HEIGHT = 100;
    public int dx = 0;
    public int dy = 0;
    public static String PHOTO_PATH = "photos/";
    public Photo() {

    }

    public Photo(String path) {
        PHOTO_PATH = path;
    }
    public void load(String source) {
        try {
            image = ImageIO.read(new File(PHOTO_PATH + source));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void load(File file) {
        try {
            image = ImageIO.read(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return image.getWidth();
    }

    public BufferedImage getImage() {
        return image;
    }
    public int getHeight() {
        return image.getHeight();
    }

    public double getMaxWidth() {
        double imgWidth = getWidth();
        return imgWidth / (imgWidth / 100);
    }

    public double[] getDimensions() {
        double height = getHeight();
        double width = getWidth();
        double ratio;
        double[] dimensions = new double[2];
        // portrait
        if(height > width) {
            ratio = height / width;
            dimensions[0] = MAX_HEIGHT / ratio;
            dimensions[1] = MAX_HEIGHT;
            // landscape
        } else {
            ratio = width / height;
            dimensions[0] = MAX_HEIGHT;
            dimensions[1] = MAX_HEIGHT / ratio;
        }
        return dimensions;
    }


    public double getMaxHeight() {
        double imgHeight = getHeight();
        return imgHeight / (imgHeight / 100);
    }

    public void move(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

public class ArtGallery extends JFrame {
    Photo[] photos;
    public static final int GAP = 8;
    Canvas canvas;
    public ArtGallery() {
        createFrame();
    }

    public ArtGallery(Photo[] photos) {
        this.photos = photos;
        createFrame();

    }

    public void createFrame() {
        setTitle("Art Gallery");

        canvas = new Canvas(photos);
        canvas.setPreferredSize(new Dimension(800, 800));
        add(canvas);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    class Canvas extends JPanel {
        Photo[] photos;
        public Canvas(Photo[] photos) {
            this.photos = photos;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int left = 0;
            int top = 0;
            double[] dimensions = new double[2];
            int panelWidth = this.getWidth();
            for (int i = 0; i < photos.length; i++) {
                if(left + photos[i].getMaxWidth() >= panelWidth) {
                    top += 100 + GAP;
                    left = 0;
                }
                dimensions = photos[i].getDimensions();
                g.drawImage(photos[i].getImage(), left, top, (int) dimensions[0], (int) dimensions[1], null);
                left += (int) photos[i].getMaxHeight() + GAP;
            }
        }
    }

    public static void main(String[] args) {
        Photo photo1 = new Photo();


        photo1.load("7978097.jpg");
        File folder = new File("photos");

        File[] listOfFiles = folder.listFiles();
        Photo[] photos = new Photo[listOfFiles.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            if(listOfFiles[i].isFile()){
                photos[i] = new Photo();
                photos[i].load(listOfFiles[i]);
            }
        }
        ArtGallery artGallery = new ArtGallery(photos);


    }
}
