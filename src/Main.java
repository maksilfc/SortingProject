import java.awt.EventQueue;
public class Main {
    private static  Visualization visualization;

    public Main() {
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main.visualization = new Visualization();
                    Main.visualization.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
