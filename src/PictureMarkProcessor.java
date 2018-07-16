import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * 添加图片水印
 *
 * @author Ricky Fung
 */
public class PictureMarkProcessor {

    private String inputPath;

    private String outputPath;

    public PictureMarkProcessor(String inputPath, String outputPath) {
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    public int readFilelist(){
        try {
            File VertImageFile = new File("vert.png");
            File HorzImageFile = new File("horz.png");
            File folder = new File(inputPath);
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                File currentFile = listOfFiles[i];
                if (currentFile.isFile() && currentFile.getName().substring(currentFile.getName().length()-3,currentFile.getName().length()).equals("jpg")) {
                    System.out.println(currentFile.getAbsolutePath());
                    File srcImageFile = new File(currentFile.getAbsolutePath());
                    File outputRoateImageFile = new File(outputPath+"\\"+currentFile.getName());
                    createWaterMarkByIcon(srcImageFile, VertImageFile, outputRoateImageFile, 0,909,0);
                    createWaterMarkByIcon(outputRoateImageFile,HorzImageFile,outputRoateImageFile,0,0,909);
                }
            }
            return 0;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 1;
        }
    }


    public void createWaterMarkByIcon(File srcImageFile, File logoImageFile,
                                      File outputImageFile, double degree,int x, int y) {

        OutputStream os = null;
        try {
            Image srcImg = ImageIO.read(srcImageFile);

            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics = buffImg.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null),
                    srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);

            ImageIcon logoImgIcon = new ImageIcon(ImageIO.read(logoImageFile));
            Image logoImg = logoImgIcon.getImage();

            //旋转
            if (degree>0) {
                graphics.rotate(Math.toRadians(degree),
                        (double) buffImg.getWidth() / 2,
                        (double) buffImg.getWidth() / 2);
            }

            float alpha = 0.8f; // 透明度
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            //水印 的位置
            graphics.drawImage(logoImg, x,y, null);
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            graphics.dispose();

            os = new FileOutputStream(outputImageFile);
            // 生成图片
            ImageIO.write(buffImg, "JPG", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}