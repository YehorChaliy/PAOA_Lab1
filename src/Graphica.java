import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;



public class Graphica extends Frame {

    static List<Main.Point> list;
    static Main.Result result;
    static int res_dimension;

    Graphica(List<Main.Point> list, Main.Result result, int res_dimension){
        super("Show Graphic");
        Graphica.list = list;
        Graphica.result = result;
        Graphica.res_dimension = res_dimension;
        int size = 500;
        Dimension toCentre = Toolkit.getDefaultToolkit().getScreenSize();
        int X = (toCentre.width - size)/2;
        int Y = (toCentre.height - size)/2;
        setBounds(X, Y, size, size);
        setVisible(true);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent ev){
                System.exit(0);
            }
        });
    }

    static void drawPoint(Graphics graphics, Main.Point point){
        graphics.setColor(Color.blue);
        int x = point.coordinates.get(0) * 2;
        if(res_dimension == 1) {
            graphics.fillOval(250+x-5, 245, 10, 10);
            graphics.drawString(point.toString(),250+x-12, 240);
        }
        else {
            int y = point.coordinates.get(1) * 2;
            graphics.fillOval(250+x-5, 250-y-5, 10, 10);
            graphics.drawString(point.toString(),250+x-12, 250-y-10);
        }
    }

    static void drawResult(Graphics graphics, Main.Result result) {
        graphics.setColor(Color.MAGENTA);
        if (res_dimension == 1){
            for (int i = 0; i < result.allFirstPoints.size(); i++){
                int x_a = result.allFirstPoints.get(i).coordinates.get(0) * 2;
                int x_b = result.allSecondPoints.get(i).coordinates.get(0) * 2;
                graphics.fillOval(250+Math.min(x_a,x_b), 248, Math.abs(x_b-x_a),4);
            }
        }
        else {
            for (int i = 0; i < result.allFirstPoints.size(); i++) {
                int x_resFirstPoints = result.allFirstPoints.get(i).coordinates.get(0) * 2;
                int y_resFirstPoints = result.allFirstPoints.get(i).coordinates.get(1) * 2;
                int x_resSecondPoints = result.allSecondPoints.get(i).coordinates.get(0) * 2;
                int y_resSecondPoints = result.allSecondPoints.get(i).coordinates.get(1) * 2;
                graphics.drawLine(250+x_resFirstPoints,250-y_resFirstPoints,250+x_resSecondPoints, 250-y_resSecondPoints);
            }
        }
    }

    public void paint(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawString("0",240,265);
        graphics.drawLine(0,250,500,250);
        graphics.drawLine(250,0,250,500);

        for (Main.Point point : list) {
            drawPoint(graphics, point);
        }
        drawResult(graphics, result);
    }
}
