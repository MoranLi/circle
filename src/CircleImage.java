import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CircleImage {

    private String inputPath;

    private String outputPath;

    public CircleImage(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public int readFilelist(){
        try {
            File folder = new File(inputPath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                File currentFile = listOfFiles[i];
                if (currentFile.isFile() && currentFile.getName().substring(currentFile.getName().length()-3,currentFile.getName().length()).equals("jpg")) {
                    System.out.println(currentFile.getAbsolutePath());
                    BufferedImage icon;
                    icon = ImageIO.read(new File(currentFile.getAbsolutePath()));
                    BufferedImage rounded = makeRoundedCorner(icon);
                    ImageIO.write(rounded, "png", new File(outputPath+"\\"+currentFile.getName()));
                }
            }
            return 0;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 1;
        }
    }

    public static BufferedImage makeRoundedCorner(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println(w+" "+h);
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = output.createGraphics();

        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new Ellipse2D.Double(80.0, 30.0, 1700.0, 1700.0));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
        g2.drawImage(image, 0, 0, null);
        g2.dispose();

        return output;
    }

    public static void main(String[] args) {
        CircleImage ci = new CircleImage("D://circle","D://created");
        ci.readFilelist();
    }

}
