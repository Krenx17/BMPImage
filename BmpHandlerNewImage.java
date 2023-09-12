import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BmpHandlerNewImage {
    static String name;
    static int pixeles;
    static int initData;
    static int width;
    static int height;
    static int[] imagen;
    static int[][] newImage;

    public static int[] readHeader(String name) throws Exception{
        FileInputStream fis = new FileInputStream(name);
        int[] header = new int[54];
        for (int i = 0; i < header.length; i++) {
            header[i] = fis.read();
        }
        fis.close();
        return header;
    }

    public static int convertInt(int b0, int b1, int b2, int b3) {
        return b0 | b1 << 8 | b2 << 16 | b3 << 24 ;
    }

    public static int convertInt(int b0, int b1, int b2) {
        return b0 | b1 << 8 | b2 << 16 ;
    }

    public static void image(String img, String opt) throws Exception{
        name = img.substring(0, img.length() - 4);
        int[] header = readHeader(img);
        if (header[0] == 'B' && header[1] == 'M') {
            //Datos importantes
            initData = convertInt(header[10], header[11], header[12], header[13]);
            width = convertInt(header[18], header[19], header[20], header[21]);
            height = convertInt(header[22], header[23], header[24], header[25]);

            //Set dimension for Arrays
            if (opt.equals("flat")) {
                width = width/2;
            } else if (opt.equals("thin")) {
                height = height/2;
            }
            
            newImage = new int[width][height];
        } else {
            System.err.println("No es un archivo BMP");
        }
    }

    public void option(String opt) {
        switch (opt) {
            case "red":
                
                break;
            case "green":
                
                break;
            case "blue":
                
                break;
            case "sepia":
                
                break;
            case "hrotation":
                
                break;
            case "vrotation":
                
                break;
            case "thin":
                
                break;
            case "flat":
                
                break;
        }
    }

    public static void printImage(String opt){
        initData = initData - 1;
        switch (opt) {
            case "thin":
            for (int w = 0; w < width/2; w++) {
                for (int h = 0; h < height; h++) {
                    int rgb = newImage[w][h];
                    imagen[initData] = ((rgb>>16) & 0xff);
                    imagen[initData+2] = ((rgb>>8) & 0xff);
                    imagen[initData+1] = (rgb & 0xff);
                    initData +=3;
                }
            }
                
                break;
            case "flat":
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height/2; h++) {
                    int rgb = newImage[w][h];
                    imagen[initData] = ((rgb>>16) & 0xff);
                    imagen[initData+2] = ((rgb>>8) & 0xff);
                    imagen[initData+1] = (rgb & 0xff);
                    initData +=3;
                }
            }
                
                break;
        
            default:
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    int rgb = newImage[w][h];
                    imagen[initData] = ((rgb>>16) & 0xff);
                    imagen[initData+2] = ((rgb>>8) & 0xff);
                    imagen[initData+1] = (rgb & 0xff);
                    initData +=3;
                }
            }
                break;
        }
        
        try {
            FileOutputStream nImage = new FileOutputStream(name+"-"+opt+".bmp");
            for (int x = 0; x < imagen.length ; x++) {
                nImage.write(imagen[x]);
            }
            nImage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
