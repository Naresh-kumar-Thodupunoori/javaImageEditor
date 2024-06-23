import java.io.File;
import java.util.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class ImageEditor {

   

    public static BufferedImage convertToGray(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage OutputImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                OutputImage.setRGB(j, i, input.getRGB(j, i));
            }
        }
        return OutputImage;
    }

    public static BufferedImage rightRotate(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage OutputImage = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                OutputImage.setRGB(i, j, input.getRGB(j, i));
            }
        }
        int cols = OutputImage.getWidth() - 1;
        for (int i = 0; i < OutputImage.getHeight(); i++) {
            for (int j = 0; j < (OutputImage.getWidth() / 2); j++) {
                Color pixel = new Color(OutputImage.getRGB(j, i));
                OutputImage.setRGB(j, i, OutputImage.getRGB(cols - j, i));
                OutputImage.setRGB(cols - j, i, pixel.getRGB());
            }
        }
        return OutputImage;
    }

    public static BufferedImage leftRotate(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage OutputImage = new BufferedImage(height, width, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                OutputImage.setRGB(i, j, input.getRGB(j, i));
            }
        }
        int rows = OutputImage.getHeight() - 1;
        for (int j = 0; j < OutputImage.getWidth(); j++) {
            for (int i = 0; i < OutputImage.getHeight() / 2; i++) {
                Color pixel = new Color(OutputImage.getRGB(j, i));
                OutputImage.setRGB(j, i, OutputImage.getRGB(j, rows - i));
                OutputImage.setRGB(j, rows - i, pixel.getRGB());
            }
        }
        return OutputImage;
    }

    public static BufferedImage verticalInvert(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage OutputImage = new BufferedImage(width, height,BufferedImage.TYPE_3BYTE_BGR);
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height / 2; i++) {
                Color temp = new Color(input.getRGB(j, i));
                OutputImage.setRGB(j, i, input.getRGB(j, height - 1 - i));
                OutputImage.setRGB(j, height - 1 - i, temp.getRGB());
            }
        }
        return OutputImage;
    }

    public static BufferedImage horizantalInvert(BufferedImage input) {
        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage OutputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                OutputImage.setRGB(width - 1 - j, i, input.getRGB(j, i));
            }
        }
        return OutputImage;
    }

    public static BufferedImage changeBrightness(BufferedImage input, int Brightness) {
        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage OutputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color pixel = new Color(input.getRGB(j, i));
                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();
                red += Brightness;
                green += Brightness;
                blue += Brightness;
                if (red > 255)
                    red = 255;
                if (green > 255)
                    green = 255;
                if (blue > 255)
                    blue = 255;
                if (red < 0)
                    red = 0;
                if (green < 0)
                    green = 0;
                if (blue < 0)
                    blue = 0;
                Color newpixel = new Color(red, green, blue);
                OutputImage.setRGB(j, i, newpixel.getRGB());
            }
        }
        return OutputImage;
    }


    public static BufferedImage blur(BufferedImage input, int pixels) {
        int height = input.getHeight();
        int width = input.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    
        for (int i = 0; i < height / pixels; i++) {
            for (int j = 0; j < width / pixels; j++) {
    
                int red = 0;
                int green = 0;
                int blue = 0;
    
                for (int k = i * pixels; k < i * pixels + pixels; k++) {
                    for (int l = j * pixels; l < j * pixels + pixels; l++) {
                        Color pixel = new Color(input.getRGB(l, k));
                        red += pixel.getRed();
                        blue += pixel.getBlue();
                        green += pixel.getGreen();
                    }
                }
    
                int finalRed = red / (pixels * pixels);
                int finalGreen = green / (pixels * pixels);
                int finalBlue = blue / (pixels * pixels);
    
                for (int k = i * pixels; k < i * pixels + pixels; k++) {
                    for (int l = j * pixels; l < j * pixels + pixels; l++) {
                        Color newPixel = new Color(finalRed, finalGreen, finalBlue);
                        outputImage.setRGB(l, k, newPixel.getRGB());
                    }
                }
            }
        }
    
        return outputImage;
    }


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter the path of image file: ");

        String ImagePath = sc.next();

        File inputFile = new File(ImagePath);
        try {
            BufferedImage inputImage = ImageIO.read(inputFile);
            File OutputImage = new File("OutputImage.jpeg");
            
            System.out.println();
            System.out.println("1. Convert Image to Grayscale");
            System.out.println("2. Rotate Image");
            System.out.println("3. Invert Image Vertically");
            System.out.println("4. Invert Image Horizontally");
            System.out.println("5. Change Image Brightness");
            System.out.println("6. Blur the Image");
            System.out.println();

            System.out.print("Enter the number to work: ");
            int Operation = sc.nextInt();
            switch (Operation){

                case 1:
                    BufferedImage GreyScaleImage = convertToGray(inputImage);
                    ImageIO.write(GreyScaleImage, "jpeg", OutputImage);
                    break;

                    
                case 2:
                    System.out.print("Enter 1 to rotate right or 2 to rotate left: ");
                    int Direction = sc.nextInt();
                    if (Direction == 1) {
                        BufferedImage RotatedImage = rightRotate(inputImage);
                        ImageIO.write(RotatedImage, "jpeg", OutputImage);
                    }
                    else if (Direction == 2) {
                        BufferedImage RotatedImage = leftRotate(inputImage);
                        ImageIO.write(RotatedImage, "jpeg", OutputImage);
                    }
                    else{
                        System.out.println("Enter either 1 or 2 only");
                    }
                    break;
                    
                case 3:
                    BufferedImage VerticalImage = verticalInvert(inputImage);
                    ImageIO.write(VerticalImage, "jpeg", OutputImage);
                    break;
                    
                case 4:
                    BufferedImage HorizontalImage = horizantalInvert(inputImage);
                    ImageIO.write(HorizontalImage, "jpeg", OutputImage);
                    break;
                    
                    
                case 5:
                    System.out.print("Change Brightness by: ");
                    int a = sc.nextInt();
                    BufferedImage BrighterImage = changeBrightness(inputImage, a);
                    ImageIO.write(BrighterImage, "jpeg", OutputImage);
                    break;

                    case 6:
                    System.out.print("Enter the No. of pixels to blur: ");
                    int Pixels = sc.nextInt();
                    BufferedImage BlurredImage = blur(inputImage, Pixels);
                    ImageIO.write(BlurredImage, "jpeg" , OutputImage);
                    break;
                
                default:
                    break;
                }
            } 
            
            catch (IOException e) {
                System.out.println("Please Enter Valid Image Path!");
            }
            
        sc.close();
    }
}