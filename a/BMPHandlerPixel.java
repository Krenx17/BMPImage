public class BMPHandlerPixel {
    public byte blue;
    public byte green;
    public byte red;

    public void Pixel (byte[] arreglo) {
        if (arreglo.length == 3) {
            blue = arreglo[0];
            green = arreglo[1];
            red = arreglo[2];
        }
    }

    public byte[] getBlueOnly () {
        byte[] resultado = {blue, 0, 0};
        return resultado;
    }

    public byte[] getGreenOnly () {
        byte[] resultado = {0, green, 0};
        return resultado;
    }

    public byte[] getRedOnly () {
        byte[] resultado = {0, 0, red};
        return resultado;
    }

    public byte[] toSepia () {
        int newRed = (int) (0.393*red + 0.769*green + 0.189*blue);
        int newGreen = (int) (0.349*red + 0.686*green + 0.168*blue);
        int newBlue = (int) (0.272*red + 0.534*green + 0.131*blue);
        byte nRed;
        if (newRed > 255) {
            nRed = (byte) 255;
        } else {
            nRed = (byte) newRed;
        }
        byte nGreen;
        if (newGreen > 255) {
            nGreen = (byte) 255;
        } else {
            nGreen = (byte) newGreen;
        }
        byte nBlue;
        if (newBlue > 255) {
            nBlue = (byte) 255;
        } else {
            nBlue = (byte) newBlue;
        }
        byte[] resultado = {nRed, nGreen, nBlue};
        
        return resultado;
    }
}