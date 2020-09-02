package application;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.property.Property;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class editController {
	
	@FXML
	private ImageView btn_text;
    EventHandler<MouseEvent> eventHandlerText;
    EventHandler<MouseEvent> eventHandlerLinePressed;
    EventHandler<MouseEvent> eventHandlerLineDragged;
    EventHandler<MouseEvent> eventHandlerLineReleased;
    EventHandler<MouseEvent> eventHandlerRectPressed;
    EventHandler<MouseEvent> eventHandlerRectDragged;
    EventHandler<MouseEvent> eventHandlerRectReleased;
    EventHandler<MouseEvent> eventHandlerLabLinePressed;
    EventHandler<MouseEvent> eventHandlerLabLineDragged;
    EventHandler<MouseEvent> eventHandlerLabLineReleased;
    
    private static TextField txt= new TextField();
    double x1,y1,xIR,yIR;
    double x,y,xDR,yDR,xRR,yRR;
    double width,height;
    private  Line line = new Line(x1,y1,x,y);
    private Rectangle rect = new Rectangle(xIR,yIR,width,height);
    private Label labDist = new Label();
    
    String dist="";
    private static final String APPLICATION_NAME = "ImageCropper";
    private ImageView mainImageView;
    private Image mainImage;
    private boolean isAreaSelected = false;
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane orta_anchor;

    @FXML
    private Button btn_lin;
    
    @FXML
    private Button btn_lablin;

    @FXML
    private Button btn_cro;
    
    @FXML
    private ColorPicker color_picker;

    @FXML
    private static ImageView imv;

    public static ImageView getImv() {
		return imv;
	}

	public static void setImv(ImageView imv) {
		editController.imv = imv;
	}

	@FXML
    private MenuBar menu_bar;

	
    @FXML
    private ListView<String> list_uzaklýk;
    
    @FXML
    private ListView<String> list_names;

    @FXML
    private ImageView btn_crop;

    @FXML
    private Canvas tuval;

    @FXML
    private Button btn_tex;

    @FXML
    private Button btn_rect;
    
    @FXML
    void btn_lablin_clicked(ActionEvent event) {
    	//final GraphicsContext gc2 = tuval.getGraphicsContext2D();
    	if(eventHandlerText != null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerText);
    	}
    	
    	if(eventHandlerRectPressed != null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerRectPressed);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerRectDragged);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerRectReleased);
    	}
    	if(eventHandlerLinePressed != null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLinePressed);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLineDragged);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLineReleased);
    	}
    	if(x == 0){
    		
    		orta_anchor.getChildren().add(line);
    	}
	
    	orta_anchor.getChildren().remove(txt);
    	
    	tuval.setCursor(Cursor.DEFAULT);
	
    	eventHandlerLabLinePressed = new EventHandler<MouseEvent>(){

    		public void handle(MouseEvent e) {

    			
    			x = e.getX();
				y = e.getY();
				x1 = e.getX();
				y1 = e.getY();
			
			
				line.setStartX(x);
				line.setStartY(y);
				line.setEndX(x1);
				line.setEndY(y1);
				line.setStroke(color_picker.getValue());
				
				labDist.setLayoutX(x);
				labDist.setLayoutY(y);
				String test = Double.toString(x);
				
				labDist.setText(test);
				orta_anchor.getChildren().add(labDist);
				
    			}

    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLabLinePressed);
    	
    	eventHandlerLabLineDragged = new EventHandler<MouseEvent>(){
			
    		public void handle(MouseEvent e) {
    			
				x = e.getX();
				y = e.getY();
			
				
				line.setStartX(x);
				line.setStartY(y);
				line.setEndX(x1);
				line.setEndY(y1);
				line.setStroke(color_picker.getValue());
				
				labDist.setLayoutX(x+15);
				labDist.setLayoutY(y+5);
				String test = Integer.toString((int)Math.sqrt(Math.abs(Math.abs(x-x1) * Math.abs(x-x1) + Math.abs(y-y1) * Math.abs(y-y1))));
				
				labDist.setText(test);
				
				orta_anchor.getChildren().addAll(line,labDist);
				
    			}
    		
    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLabLineDragged);
    	
    	eventHandlerLabLineReleased = new EventHandler<MouseEvent>(){

    		public void handle(MouseEvent e) {

    			
    			//gc2.strokeLine(x1, y1, x, y);
    			x = e.getX();
    			y = e.getY();
    			labDist.setLayoutX(x+15);
				labDist.setLayoutY(y+5);
				
				
				line = new Line(x1,y1,x,y);
				line.setStroke(color_picker.getValue());
				
				int distance = (int)Math.sqrt(Math.abs(Math.abs(x-x1) * Math.abs(x-x1) - Math.abs(y-y1) * Math.abs(y-y1)));
				dist = Integer.toString(distance);

				list_names.getItems().add("Degisken");
		    	list_uzaklýk.getItems().add(dist);
		    	
		    	
				list_names.setEditable(true);
				list_names.setCellFactory(TextFieldListCell.forListView());
				orta_anchor.getChildren().add(line);
				orta_anchor.getChildren().remove(labDist);
				
				Label labDist2 = new Label();
				labDist2.setText(labDist.getText());
				labDist2.setLayoutX(Math.abs((e.getX()+ x1)/ 2) - 10);
				labDist2.setLayoutY(Math.abs((e.getY()+ y1)/ 2) - 22);
				orta_anchor.getChildren().addAll(labDist2);
				
    			}
    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLabLineReleased);
    }

    
    
    
    @FXML
    void btn_line_clicked(ActionEvent event) {

    	//final GraphicsContext gc2 = tuval.getGraphicsContext2D();
    	if(eventHandlerText != null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerText);
    	}
    	
    	if(eventHandlerRectPressed != null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerRectPressed);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerRectDragged);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerRectReleased);
    	}
    	if(eventHandlerLabLinePressed != null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLabLinePressed);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLabLineDragged);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLabLineReleased);
    	}
    	
    	if(x == 0){
    		
    		orta_anchor.getChildren().add(line);
    	}
	
    	orta_anchor.getChildren().remove(txt);
    	
    	tuval.setCursor(Cursor.DEFAULT);
	
		eventHandlerLinePressed = new EventHandler<MouseEvent>(){

    		public void handle(MouseEvent e) {

    			
    			x = e.getX();
				y = e.getY();
				x1 = e.getX();
				y1 = e.getY();
			
			
				line.setStartX(x);
				line.setStartY(y);
				line.setEndX(x1);
				line.setEndY(y1);
				line.setStroke(color_picker.getValue());
				
				labDist.setLayoutX(x);
				labDist.setLayoutY(y);
				String test = Double.toString(x);
				
				labDist.setText(test);
				orta_anchor.getChildren().add(labDist);
				
    			}

    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLinePressed);
    	
		eventHandlerLineDragged = new EventHandler<MouseEvent>(){
			
    		public void handle(MouseEvent e) {
    			
				x = e.getX();
				y = e.getY();
			
				
				line.setStartX(x);
				line.setStartY(y);
				line.setEndX(x1);
				line.setEndY(y1);
				line.setStroke(color_picker.getValue());
				
				labDist.setLayoutX(x+15);
				labDist.setLayoutY(y+5);
				String test = Integer.toString((int)Math.sqrt(Math.abs(Math.abs(x-x1) * Math.abs(x-x1) - Math.abs(y-y1) * Math.abs(y-y1))));
				
				labDist.setText(test);
				
				orta_anchor.getChildren().addAll(line,labDist);
				
    			}
    		
    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLineDragged);
    	
		eventHandlerLineReleased = new EventHandler<MouseEvent>(){

    		public void handle(MouseEvent e) {

    			
    			//gc2.strokeLine(x1, y1, x, y);
    			x = e.getX();
    			y = e.getY();
    			labDist.setLayoutX(x+15);
				labDist.setLayoutY(y+5);
				
				
				line = new Line(x1,y1,x,y);
				line.setStroke(color_picker.getValue());
				
				int distance = (int)Math.sqrt(Math.abs(Math.abs(x-x1) * Math.abs(x-x1) - Math.abs(y-y1) * Math.abs(y-y1)));
				dist = Integer.toString(distance);

				list_names.getItems().add("Degisken");
		    	list_uzaklýk.getItems().add(dist);
		    	
		    	
				list_names.setEditable(true);
				list_names.setCellFactory(TextFieldListCell.forListView());
				orta_anchor.getChildren().add(line);
				orta_anchor.getChildren().remove(labDist);				
    			}
    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLineReleased);
    }

    @FXML
    void btn_rect_clicked(ActionEvent event) {
    	
    	if(eventHandlerText != null ) { 
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerText);
    	}
    	if(eventHandlerLinePressed!=null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLinePressed);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLineDragged);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLineReleased);
    	}
    	if(eventHandlerLabLinePressed != null) {
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLabLinePressed);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLabLineDragged);
    		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLabLineReleased);
    	}
    	orta_anchor.getChildren().remove(txt);
    	
    	rect = new Rectangle();
    	rect.setFill(Color.TRANSPARENT);
    	rect.setStroke(color_picker.getValue());
		
    	orta_anchor.getChildren().add(rect);
    	tuval.setCursor(Cursor.DEFAULT);
    	eventHandlerRectPressed = new EventHandler<MouseEvent>(){

    		public void handle(MouseEvent e) {	
    			xIR = e.getX();
				yIR = e.getY();
    			}
	
    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerRectPressed);
    	
		eventHandlerRectDragged = new EventHandler<MouseEvent>(){
			
    		public void handle(MouseEvent e) {
    			
				xDR = e.getX();
				yDR = e.getY();
				System.out.println("surukle x: " + xDR);
				System.out.println("surukle y: " + yDR);
				width = Math.abs(xDR-xIR);
				height = Math.abs(yDR-yIR);
				
				rect.setLayoutX(xIR);
				rect.setLayoutY(yIR);
				rect.setWidth(width);
				rect.setHeight(height);
				rect.setFill(Color.TRANSPARENT);
		    	rect.setStroke(color_picker.getValue());
    			}
    		
    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerRectDragged);
    	
		eventHandlerRectReleased = new EventHandler<MouseEvent>(){

    		public void handle(MouseEvent e) {

    			
    			//gc2.strokeLine(x1, y1, x, y);
    			
    			xRR = e.getX();
    			yRR = e.getY();
    			
    			width = Math.abs(xIR-xRR);
    			height = Math.abs(yIR-yRR);
    			
    			/*rect.setLayoutX(xIR);
    			rect.setLayoutY(yIR);
    			rect.setWidth(width);
    			rect.setHeight(height);
    			*/
    			rect = new Rectangle();
    			
  
    			rect.setFill(Color.TRANSPARENT);
		    	rect.setStroke(color_picker.getValue());

		    	
		    	orta_anchor.getChildren().add(rect);
				
    			}
    	};
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerRectReleased);

    }

    @FXML
    void btn_crop_clicked(ActionEvent event) {
    	Stage cropStage = new Stage();
    	
    	if(eventHandlerLinePressed != null) {
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLinePressed);
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLineDragged);
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLineReleased);
        	}
    	
        if(eventHandlerRectPressed != null || eventHandlerRectDragged != null || eventHandlerRectReleased != null) {
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerRectPressed);
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerRectDragged);
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerRectReleased);
        }
        if(eventHandlerText != null) {
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerText);
        }
        cropStage.setTitle(APPLICATION_NAME);
        cropStage.setResizable(true);

        final BorderPane borderPane = new BorderPane();
        final ScrollPane rootPane = new ScrollPane();
        final Scene scene =  new Scene(borderPane, 500, 500);
        mainImageView = new ImageView();
        final AreaSelection areaSelection = new AreaSelection();
        final Group selectionGroup = new Group();
        final MenuBar menuBar = new MenuBar();

        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Options");

        final MenuItem open = new MenuItem("Open");
        final MenuItem clear = new MenuItem("Clear");
        menu1.getItems().addAll(open,clear);
        clear.setOnAction(e -> {
            clearSelection(selectionGroup);
            mainImageView.setImage(null);
            System.gc();
        });

        final MenuItem select = new MenuItem("Select Area");
        menu2.getItems().add(select);
        select.setOnAction(e -> areaSelection.selectArea(selectionGroup));

        final MenuItem crop = new MenuItem("Crop");
        menu2.getItems().add(crop);
        crop.setOnAction(e -> {
            if (isAreaSelected)
                cropImage(areaSelection.selectArea(selectionGroup).getBoundsInParent(),mainImageView);
        });

        open.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image file");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Image Files", "*.png", "*.jpg"));
            File selectedFile = fileChooser.showOpenDialog(cropStage);
            if (selectedFile != null) {
                clearSelection(selectionGroup);
                mainImage = convertFileToImage(selectedFile);
                mainImageView.setImage(mainImage);
                changeStageSizeImageDimensions(cropStage,mainImage);
            }
        });

        final MenuItem clearSelectionItem = new MenuItem("Clear Selection");
        menu2.getItems().add(clearSelectionItem);
        clearSelectionItem.setOnAction(e -> clearSelection(selectionGroup));

        selectionGroup.getChildren().add(mainImageView);
        rootPane.setContent(selectionGroup);
        borderPane.setCenter(rootPane);
        menuBar.getMenus().addAll(menu1,menu2);
        borderPane.setTop(menuBar);
        cropStage.setScene(scene);
        cropStage.show();
    }

    private void cropImage(Bounds bounds, ImageView imageView) {

        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();

        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(bounds.getMinX(), bounds.getMinY(), width, height));

        WritableImage wi = new WritableImage(width, height);
        Image croppedImage = imageView.snapshot(parameters, wi);

        showCroppedImageNewStage(wi, croppedImage);
    }

    private void showCroppedImageNewStage(WritableImage wi, Image croppedImage) {
        final Stage croppedImageStage = new Stage();
        croppedImageStage.setResizable(true);
        croppedImageStage.setTitle("Cropped Image");
        changeStageSizeImageDimensions(croppedImageStage,croppedImage);
        final BorderPane borderPane = new BorderPane();
        final MenuBar menuBar = new MenuBar();
        final Menu menu1 = new Menu("File");
        final MenuItem save = new MenuItem("Save");
        save.setOnAction(event -> saveCroppedImage(croppedImageStage,wi));
        menu1.getItems().add(save);
        menuBar.getMenus().add(menu1);
        borderPane.setTop(menuBar);
        borderPane.setCenter(new ImageView(croppedImage));
        final Scene scene = new Scene(borderPane);
        croppedImageStage.setScene(scene);
    }

    private void saveCroppedImage(Stage stage, WritableImage wi) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialFileName("cats.png");

        File file = fileChooser.showSaveDialog(stage);
        if (file == null)
            return;

        Runnable runnable = () -> {
            BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(wi, null);
            BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(),
                    bufImageARGB.getHeight(), BufferedImage.BITMASK);

            Graphics2D graphics = bufImageRGB.createGraphics();
            graphics.drawImage(bufImageARGB, 0, 0, null);

            try {
                ImageIO.write(bufImageRGB, "png", file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                graphics.dispose();
                System.gc();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        stage.close();
    }

    private void clearSelection(Group group) {
        //deletes everything except for base container layer
        isAreaSelected = false;
        group.getChildren().remove(1,group.getChildren().size());

    }

    private void changeStageSizeImageDimensions(Stage stage, Image image) {
        if (image != null) {
            stage.setMinHeight(250);
            stage.setMinWidth(250);
            stage.setWidth(image.getWidth()+4);
            stage.setHeight(image.getHeight()+56);
        }
        stage.show();
    }

    private Image convertFileToImage(File imageFile) {
        Image image = null;
        try (FileInputStream fileInputStream = new FileInputStream(imageFile)){
            image = new Image(fileInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }



    private class AreaSelection {

        private Group group;

        private ResizableRectangle selectionRectangle = null;
        private double rectangleStartX;
        private double rectangleStartY;
        private Paint darkAreaColor = Color.color(0,0,0,0.5);

        private ResizableRectangle selectArea(Group group) {
            this.group = group;

            // group.getChildren().get(0) == mainImageView. We assume image view as base container layer
            if (mainImageView != null && mainImage != null) {
                this.group.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
                this.group.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
                this.group.getChildren().get(0).addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
            }

            return selectionRectangle;
        }

        EventHandler<MouseEvent> onMousePressedEventHandler = event -> {
            if (event.isSecondaryButtonDown())
                return;

            rectangleStartX = event.getX();
            rectangleStartY = event.getY();

            clearSelection(group);

            selectionRectangle = new ResizableRectangle(rectangleStartX, rectangleStartY, 0, 0, group);

            darkenOutsideRectangle(selectionRectangle);

        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {
            if (event.isSecondaryButtonDown())
                return;

            double offsetX = event.getX() - rectangleStartX;
            double offsetY = event.getY() - rectangleStartY;

            if (offsetX > 0) {
                if (event.getX() > mainImage.getWidth())
                    selectionRectangle.setWidth(mainImage.getWidth() - rectangleStartX);
                else
                    selectionRectangle.setWidth(offsetX);
            } else {
                if (event.getX() < 0)
                    selectionRectangle.setX(0);
                else
                    selectionRectangle.setX(event.getX());
                selectionRectangle.setWidth(rectangleStartX - selectionRectangle.getX());
            }

            if (offsetY > 0) {
                if (event.getY() > mainImage.getHeight())
                    selectionRectangle.setHeight(mainImage.getHeight() - rectangleStartY);
                else
                    selectionRectangle.setHeight(offsetY);
            } else {
                if (event.getY() < 0)
                    selectionRectangle.setY(0);
                else
                    selectionRectangle.setY(event.getY());
                selectionRectangle.setHeight(rectangleStartY - selectionRectangle.getY());
            }

        };

        EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
            if (selectionRectangle != null)
                isAreaSelected = true;
        };


        private void darkenOutsideRectangle(Rectangle rectangle) {
            Rectangle darkAreaTop = new Rectangle(0,0,darkAreaColor);
            Rectangle darkAreaLeft = new Rectangle(0,0,darkAreaColor);
            Rectangle darkAreaRight = new Rectangle(0,0,darkAreaColor);
            Rectangle darkAreaBottom = new Rectangle(0,0,darkAreaColor);

            darkAreaTop.widthProperty().bind(mainImage.widthProperty());
            darkAreaTop.heightProperty().bind(rectangle.yProperty());

            darkAreaLeft.yProperty().bind(rectangle.yProperty());
            darkAreaLeft.widthProperty().bind(rectangle.xProperty());
            darkAreaLeft.heightProperty().bind(rectangle.heightProperty());

            darkAreaRight.xProperty().bind(rectangle.xProperty().add(rectangle.widthProperty()));
            darkAreaRight.yProperty().bind(rectangle.yProperty());
            darkAreaRight.widthProperty().bind(mainImage.widthProperty().subtract(
                    rectangle.xProperty().add(rectangle.widthProperty())));
            darkAreaRight.heightProperty().bind(rectangle.heightProperty());

            darkAreaBottom.yProperty().bind(rectangle.yProperty().add(rectangle.heightProperty()));
            darkAreaBottom.widthProperty().bind(mainImage.widthProperty());
            darkAreaBottom.heightProperty().bind(mainImage.heightProperty().subtract(
                    rectangle.yProperty().add(rectangle.heightProperty())));

            // adding dark area rectangles before the selectionRectangle. So it can't overlap rectangle
            group.getChildren().add(1,darkAreaTop);
            group.getChildren().add(1,darkAreaLeft);
            group.getChildren().add(1,darkAreaBottom);
            group.getChildren().add(1,darkAreaRight);

            // make dark area container layer as well
            darkAreaTop.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaTop.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaTop.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

            darkAreaLeft.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaLeft.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaLeft.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

            darkAreaRight.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaRight.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaRight.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);

            darkAreaBottom.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
            darkAreaBottom.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
            darkAreaBottom.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        }
    }


    @FXML
    void btn_text_clicked(ActionEvent event) {
    	
    	if(eventHandlerLinePressed != null) {
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLinePressed);
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLineDragged);
        	orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLineReleased);
        	}
    	
        	if(eventHandlerRectPressed != null || eventHandlerRectDragged != null || eventHandlerRectReleased != null) {
        		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerRectPressed);
        		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerRectDragged);
        		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerRectReleased);
        	}
        	if(eventHandlerLabLinePressed != null) {
        		orta_anchor.removeEventHandler(MouseEvent.MOUSE_PRESSED, eventHandlerLabLinePressed);
        		orta_anchor.removeEventHandler(MouseEvent.MOUSE_DRAGGED, eventHandlerLabLineDragged);
        		orta_anchor.removeEventHandler(MouseEvent.MOUSE_RELEASED, eventHandlerLabLineReleased);
        	}
    	
		tuval.setCursor(Cursor.TEXT);
    	GraphicsContext gc = tuval.getGraphicsContext2D();
    	eventHandlerText = new EventHandler<MouseEvent>(){
    		
    		double x,y;
		
    		public void handle(MouseEvent e) {
    			x = e.getX();
				y = e.getY();
    			
    			
    				
    				txt.setOpacity(0.3);
    				txt.setLayoutX(x);
    				txt.setLayoutY(y);
    				orta_anchor.getChildren().add(txt);

    			txt.setOnKeyPressed(event ->{
    				if(event.getCode().toString().equals("ENTER") ) {
    					
    					gc.setStroke(color_picker.getValue());
    					
    					gc.strokeText(txt.getText(), x+8, y+12);
    					txt.clear();
    					orta_anchor.getChildren().remove(txt);
    				}
    			});
    			
    			
    			}
    		
    	};
    	
    	orta_anchor.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerText);
    	orta_anchor.getChildren().remove(txt);
		
    	
    }
    
    @FXML
    void menu_save_clicked(ActionEvent event) {
    	
    	Stage fileDialog  =new Stage();
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files","*.png","*.jpg"));
    	
    	WritableImage image = orta_anchor.snapshot(new SnapshotParameters(), null);
        fileChooser.setTitle("Save Image");
        File file = fileChooser.showSaveDialog(fileDialog);
        if (file == null)
            return;

        Runnable runnable = () -> {
            BufferedImage bufImageARGB = SwingFXUtils.fromFXImage(image, null);
            BufferedImage bufImageRGB = new BufferedImage(bufImageARGB.getWidth(),
                    bufImageARGB.getHeight(), BufferedImage.BITMASK);

            Graphics2D graphics = bufImageRGB.createGraphics();
            graphics.drawImage(bufImageARGB, 0, 0, null);

            try {
                ImageIO.write(bufImageRGB, "png", file);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                graphics.dispose();
                System.gc();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
        fileDialog.close();
    }

    
    
   
  
    @FXML
    void initialize() {
    
        
    	 
    	
    }
    	}



