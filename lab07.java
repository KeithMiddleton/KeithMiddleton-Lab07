import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class lab07 extends Application
{
    private Canvas canvas;
    
    @Override
    
    public void start(Stage primaryStage) throws Exception
    {
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 800);
        canvas = new Canvas();
        canvas.widthProperty().bind(primaryStage.widthProperty());
        canvas.heightProperty().bind(primaryStage.heightProperty());
        root.getChildren().add(canvas);
        
        primaryStage.setTitle("Lab07");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        draw(root);
    }
    
    private void draw(Group root)
    {
        Color[] pieColors = {Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.LAWNGREEN, Color.PLUM};
        List<String> type = new ArrayList<String>();
        
        HashMap<String, Integer> weather = new HashMap<String, Integer>();
        int temp = 0;
        Scanner scanner = null;

        try{
            scanner = new Scanner(new File(System.getProperty("user.home") + "\\Desktop\\weatherwarnings-2015.csv"));
        }
        catch (Exception e){
            System.out.println("Couldn't find the file.");
        }

        while (scanner.hasNextLine()){
            List<String> data = new ArrayList<String>();
            try (Scanner scanRows = new Scanner(scanner.nextLine()))
            {
                scanRows.useDelimiter(",");
                while (scanRows.hasNext()){
                    data.add(scanRows.next());
                }
            }
            if (!weather.containsKey(data.get(5))){
                weather.put(data.get(5), 0);
                type.add(data.get(5));
            }
            weather.put(data.get(5), weather.get(data.get(5)) + 1);
            temp += 1;
        }
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        double start = 0.0;
        for (int i = 0; i < type.size(); i++){
            double slicePercentage = (double)weather.get(type.get(i)) / (double)temp;
            double sweepAngle = slicePercentage * 360.0;
            
            gc.setFill(pieColors[i]);
            gc.fillArc(500,125, 300, 300, start, sweepAngle, ArcType.ROUND);

            gc.setStroke(Color.BLACK);
            gc.strokeRect(150, 100 + i * 100, 100, 60);
            gc.fillRect(150, 100 + i * 100, 100, 60);
            gc.setFill(Color.BLACK);
            gc.fillText(type.get(i), 275, 135 + i * 100);
            
            start += sweepAngle;
        }
    }
    
    public static void main(String[] args)
    {
        launch();
    }
}