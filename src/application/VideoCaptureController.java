package application;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import org.opencv.core.Core;
//import org.opencv.core.CvType;
import org.opencv.core.Mat;
//import org.opencv.core.MatOfRect;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Utils;

public class VideoCaptureController implements Initializable {
	
	@FXML private ImageView originalFrame;
	@FXML private Button cameraButton;
	@FXML private Button entrenador;
	@FXML private Label hola;
	
	private ScheduledExecutorService timer;
	private boolean camaraAct = false;
	private static int cameraId = 0;
	private VideoCapture capture = new VideoCapture();
	//private CascadeClassifier faceCascade;
	//private int absoluteFaceSize;
	//private int d = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat mat = Mat.eye(3, 3, CvType.CV_8SC1);
		System.out.println("mat= "+mat.dump()); */
		
		this.capture = new VideoCapture();
		//this.faceCascade = new CascadeClassifier();
		//this.absoluteFaceSize = 0;
		
		originalFrame.setFitWidth(600);
		originalFrame.setPreserveRatio(true);
		
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	@FXML protected void startCamera(ActionEvent event) {
		if(!this.camaraAct) {
			this.capture.open(cameraId);
			
			if(this.capture.isOpened()) {
				this.camaraAct = true;
				
				Runnable frameGrabber = new Runnable() {

					@Override
					public void run() {
						Mat frame = grabFrame();
						
						Image imageToShow = Utils.mat2Image(frame);
						
						updateImageView(originalFrame, imageToShow);
					}
				};
				
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				
				this.cameraButton.setText("Parar Camara");
			}
			else {
				System.err.println("Error al abrir la conexion con la camara...");
			}
		}
		else {
			this.camaraAct = false;
			this.cameraButton.setText("Iniciar Camara");
			
			this.stopAcquisition();
		}
	}
	
	private Mat grabFrame() {
		Mat frame = new Mat();
		
		if(this.capture.isOpened()) {
			try {
				this.capture.read(frame);
				
				if(!frame.empty()) {
					// Deteccion de caras
					Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
					//this.detectAndDisplay(frame);
				}
			}
			catch (Exception e) {
				System.err.println("Exepcion durante la elaboracion de imagen: "+e);
			}
		}
		
		return frame;
	}
	
/*	private void detectAndDisplay(Mat frame) {
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		
		// Conversion de imagen en escala de grises
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		// Ecualizador del histograma del frmae para mejorar resultados
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		//Minimo tamaño de la cara (20% de la altura del frame)
		if(this.absoluteFaceSize == 0) {
			int height = grayFrame.rows();
			if(Math.round(height * 0.2f) >0) {
				this.absoluteFaceSize = Math.round(height * 0.2f);
			}
		}
		this.faceCascade.load("resources/haarcascades/haarcascade_frontalface_alt.xml");
		// Se detecta las caras
		this.faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.absoluteFaceSize, this.absoluteFaceSize), new Size() );
		
		//Para pintar cada cuadro, que representa una cara 
		Rect[] facesArray = faces.toArray();
		for(Rect rect: facesArray) {
			Imgproc.rectangle(frame, rect.tl(), rect.br(), new Scalar(0, 255, 0), 3);
			Rect rectCrop = new Rect(rect.x, rect.y, rect.width, rect.height);
			Mat imageROI = new Mat(grayFrame, rectCrop);
			
			String filename = "dataset/"+"1-"+d+".png";
			System.out.println(String.format("Writing %s", filename));
			Imgcodecs.imwrite(filename, imageROI);
			d++;
		}
	} */
	
	private void stopAcquisition() {
		if(this.timer != null && !this.timer.isShutdown()) {
			try {
				// Paramos el timer
				this.timer.shutdown();
				this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e) {
				//log any exception
				System.err.println("Exepcion dentro de la parada de la captura de cuadros, probando liberar la camara ahora..." +e);
			}
		}
		if (this.capture.isOpened()) {
			//Liberar camara
			this.capture.release();
		}
	}
	
	private void updateImageView(ImageView view, Image image) {
		Utils.onFXThread(view.imageProperty(), image);
	}
	
/*	private void setClosed() {
		this.stopAcquisition();
	}*/
}
