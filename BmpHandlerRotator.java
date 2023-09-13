public class BmpHandlerRotator {
    public static void img(String img) throws Exception {
        BmpHandlerNewImage.image(img, "vrotation");
        BmpHandlerNewImage.image(img, "hrotation");
    }
}
