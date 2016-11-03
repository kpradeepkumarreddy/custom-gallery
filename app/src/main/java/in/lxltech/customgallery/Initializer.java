package in.lxltech.customgallery;

import android.app.Application;

import net.gotev.uploadservice.UploadService;

/**
 * Created by pradeepkumar on 8/2/2016.
 */
public class Initializer extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // setup the broadcast action namespace string which will
        // be used to notify upload status.
        // Gradle automatically generates proper variable as below.
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;
        // Or, you can define it manually.
        UploadService.NAMESPACE = "com.yourcompany.yourapp";
    }
}