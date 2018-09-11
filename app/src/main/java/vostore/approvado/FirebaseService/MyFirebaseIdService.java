package vostore.approvado.FirebaseService;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        sendNewTokenServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void sendNewTokenServer(String token) {

        Log.d("Approvado", token);
    }
}
