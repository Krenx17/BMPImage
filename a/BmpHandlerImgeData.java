import java.io.*;

public class BmpHandlerImgeData {
    static String name;
    static int pixeles;
    static int initData;
    static int width;
    static int height;
    static int[] imagen;
    static int[][] newImage;
    static int[][] resize;

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
            newImage = new int[width][height];

            option(opt);
        } else {
            System.err.println("No es un archivo BMP");
        }
    }

    public static void separarColores(String opt) throws IOException {
        FileInputStream fis = new FileInputStream(name+".bmp");
        try (fis) {
            for (int w = 0; w < width; w++) {
                for (int h = 0; h < height; h++) {
                    int b = fis.read();
                    int g = fis.read();
                    int r = fis.read();
                    //Aplicar filtros
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
                    
                        default:
                        int rgb = convertInt(b, g, r);
                        newImage[w][h] = rgb;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void header(String opt) throws Exception{
        int[] header = readHeader(name+".bmp");
            for (int x = 0; x < 54; x++) {
                imagen[x] = header[x];
            }
        switch (opt) {
            case "thin":
            width = width/2;
            header[18] = (width & 0xff); 
            header[19] = ((width & 0xff)>>8); 
            header[20] = ((width & 0xff)>>16); 
            header[21] = ((width & 0xff)>>24);
                
                break;
            case "flat":
            height = height/2;
            header[22] = (height & 0xff); 
            header[23] = ((height & 0xff)>>8); 
            header[24] = ((height & 0xff)>>16); 
            header[25] = ((height & 0xff)>>24);
                
                break;
        }
    }

    public static void option(String opt) throws Exception{
        if (opt.equals("thin") || opt.equals("flat")) {
            if (opt.equals("thin")) {
                resize = new int[width/2][height];
                pixeles = ((width/2)*height*3)+54;
                imagen = new int[pixeles];
                separarColores(opt);
                header(opt);
                for (int w = 0; w < width; w++) {
                    for (int h = 0; h < height; h++) {
                        resize[w][h] = newImage[w][h];
                    }
                    w += 1;
                }
            } else {
                resize = new int[width][height/2];
                pixeles = (width*(height/2)*3)+54;
                imagen = new int[pixeles];
                separarColores(opt);
                header(opt);
                for (int w = 0; w < width; w++) {
                    for (int h = 0; h < height; h++) {
                        resize[w][h] = newImage[w][h];
                        h += 1;
                    }
                }
            }
        } else {
            pixeles = (width*height*3)+54;
            imagen = new int[pixeles];
            separarColores(opt);
            header(opt);
            switch (opt) {
                case "hrotation":
                    int h1 = (width/2)-1;
                    int y2 = width;
                    int[] a;
                    for (int h = 0; h < h1; h++) {
                        y2--;
                        aux = newImage[h];
                        newImage[h] = newImage[h+1];
                        newImage[h+1] = aux;
                    }
                    break;
                case "vrotation":
                    int v1 = (height/2)-1;
                    int b;
                    for (int h = 0; h < width; h++) {
                        for (int v = 0; v < v1; v++) {
                            int x2 = height - v - 1;
                            b = newImage[h][v];
                            newImage[h][v] = newImage[h][x2];
                            newImage[h][x2] = b;
                        }
                    }
                    break;
            }
        }
        printImage(opt);
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
