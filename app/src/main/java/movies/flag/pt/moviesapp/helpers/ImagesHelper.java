package movies.flag.pt.moviesapp.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

//import pt.flag.database.entities.ImageDbEntity;

/**
 * Created by Ricardo Neves on 30/11/2014.
 */
public final class ImagesHelper {

    private static final String TAG = ImagesHelper.class.getSimpleName();

    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmap.recycle(); // clear bitmap memory
        return stream.toByteArray();
    }

    public static Bitmap convertByteArrayToBitmap(byte[] bitmapArray){
        if(bitmapArray == null){
            return null;
        }

        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

    public static void storeImage(final Bitmap image, final File pictureFile) {

        if (pictureFile == null || image == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 95, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    /**
     * Return the filePath
     * */
    public static String saveImageToFile(Context context, byte[] imageBytes){
        Bitmap bitmap = ImagesHelper.convertByteArrayToBitmap(imageBytes);
        long filePathTimestamp = System.currentTimeMillis(); // obtain timestamp to have unique value
        String filePath = String.valueOf(filePathTimestamp);
        File bitmapFile = new File(context.getFilesDir(), filePath);
        ImagesHelper.storeImage(bitmap, bitmapFile);
        return filePath;
    }
    /**
     * Return the filePath
     * */
    //public static void saveImageToFile(Context context, byte[] imageBytes,
                                       //ImageDbEntity imageEntity){
        //Bitmap bitmap = ImagesHelper.convertByteArrayToBitmap(imageBytes);
        //long filePathTimestamp = System.currentTimeMillis(); // obtain timestamp to have unique value
        //String filePath = String.valueOf(filePathTimestamp);
        //File bitmapFile = new File(context.getFilesDir(), filePath);
        //ImagesHelper.storeImage(bitmap, bitmapFile);
        //imageEntity.setFilePath(filePath);
        //imageEntity.markImageAlreadyDownloaded();
        //imageEntity.save(); // Save in DB
    //}


    public static Bitmap getImageBitmapFromFilePath(Context context, String filePath){
        File imageFile = new File(context.getFilesDir(), filePath);
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options(); // add options like resolution that we want
            bitmap = BitmapFactory.decodeStream(new FileInputStream(imageFile), null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static final byte[] downloadImageUrl(String url){
        try {
            InputStream is = new java.net.URL(url).openStream();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[4096]; // buffer size

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();
            is.close();

            Log.e(TAG, String.format("Download efectuado com sucesso do url = %s", url));

            return buffer.toByteArray();
        }
        catch(IOException e){
            Log.e(TAG, "Excepção: " + e.getMessage());
        }

        return null;
    }

}