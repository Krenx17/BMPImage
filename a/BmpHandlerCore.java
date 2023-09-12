public class BmpHandlerCore{
    public static void img(String img) throws Exception {
        BmpHandlerImgeData.image(img, "red");
        BmpHandlerImgeData.image(img, "green");
        BmpHandlerImgeData.image(img, "blue");
    }
}