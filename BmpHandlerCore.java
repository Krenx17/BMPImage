public class BmpHandlerCore {
    public static void img(String img) throws Exception {
        BmpHandlerNewImage.image(img, "red");
        BmpHandlerNewImage.image(img, "green");
        BmpHandlerNewImage.image(img, "blue");
        BmpHandlerNewImage.image(img, "sepia");
    }
}