import javax.swing.*;

public class io {
    public static void main(String[] args) {
        String test1= JOptionPane.showInputDialog("Input folder absolute path");

        String test2= JOptionPane.showInputDialog("output folder absolute path");

        CircleImage ci = new CircleImage(test1,test2);

        PictureMarkProcessor pmp = new PictureMarkProcessor(test2,test2);

       if(ci.readFilelist() == 0){
           if(pmp.readFilelist()==0){
               JOptionPane.showMessageDialog(null,"success");
               return;
           }
       }
    }
}
