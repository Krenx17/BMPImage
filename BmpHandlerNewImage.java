import java.io.*;

public class BmpHandlerNewImage {
    private static String name;
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
            if (opt.equals("flat")) {
                width = width/2;
            } else if (opt.equals("thin")) {
                height = height/2;
            }

            //Set dimension for Arrays
            newImage = new int[width][height];
            imagen = new int[(width*height*3)+54];

            option(opt);
        } else {
            System.err.println("No es un archivo BMP");
        }
    }

    public static void option(String opt) throws IOException {
        FileInputStream fis = new FileInputStream(name+".bmp");
        
        try (fis) {
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    int b = fis.read();
                    int g = fis.read();
                    int r = fis.read();
                    switch (opt) {
                        case "red":
                        int red = convertInt(0, 0, r);
                        newImage[w][h] = red;
                            break;
                        case "green":
                        int green = convertInt(0, g, 0);
                        newImage[w][h] = green;
                            break;
                        case "blue":
                        int blue = convertInt(b, 0, 0);
                        newImage[w][h] = blue;
                            break;
                        case "sepia":
                        int newRed = (int) (0.393*r + 0.769*g + 0.189*b);
                        int newGreen = (int) (0.349*r + 0.686*g + 0.168*b);
                        int newBlue = (int) (0.272*r + 0.534*g + 0.131*b); 
                        int sepia = convertInt(newBlue, newGreen, newRed);
                        newImage[w][h] = sepia;
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
            }
            try {
                printImage(opt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printImage(String opt) throws Exception{
        try {
            FileOutputStream nImage = new FileOutputStream(name+"-"+opt+".bmp");
            int[] header = readHeader(name+".bmp");
            initData = initData - 1;
            for (int x = 0; x < 54; x++) {
                imagen[x] = header[x];
            }
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    int rgb = newImage[w][h];
                    imagen[initData] = ((rgb>>16) & 0xff);
                    imagen[initData+2] = ((rgb>>8) & 0xff);
                    imagen[initData+1] = (rgb & 0xff);
                    initData +=3;
                }
            }
            for (int x = 0; x < imagen.length ; x++) {
                nImage.write(imagen[x]);
            }
            nImage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
