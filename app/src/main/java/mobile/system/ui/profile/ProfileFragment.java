package mobile.system.ui.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import mobile.system.API.models.User;
import mobile.system.BottomNavigationActivity;
import mobile.system.LoginActivity;
import mobile.system.R;
import mobile.system.Singleton;

public class ProfileFragment extends Fragment {

//    RequestQueue queue;
//    JSONObject credentials;

//    boolean ResponseCode;
//    private List<User> mUserList;
//    EditText mEmail;
//    EditText mPass;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        queue = Volley.newRequestQueue(requireActivity());

        Button button_logout = view.findViewById(R.id.button_logout);
        button_logout.setOnClickListener(clickListener);

        Singleton obj = Singleton.getInstance();

        String FIO;
        if (obj.getLastname().equals("null"))
            FIO = obj.getSurname() + " " + obj.getName();
        else
            FIO = obj.getSurname() + " " + obj.getName() + " " + obj.getLastname();

        TextView name_profile = view.findViewById(R.id.name_profile);
        name_profile.setText(FIO);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            switch (button.getId())
            {
                case R.id.button_logout:
                    requireActivity().finish();
                default:
                    // fuck u
            }
        }
    };

//    private void getResponse() {
//        String IP = "10.0.2.2";
//        String port = "8000";
//        try {
//            JSONObject credentials = new JSONObject();
//            credentials.put("button_logout", "button_logout");
//
//            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://" + IP + ":" + port + "/home", credentials,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            try {
//                                // Check the response status code
//                                boolean statusCode = response.getBoolean("success");
//
//                                if (statusCode) {
//                                    requireActivity().finish();
//                                } else {
//                                    Toast.makeText(requireActivity(), "Cannot logout!", Toast.LENGTH_SHORT).show();
//                                }
//                            } catch (JSONException e) {
//                                // Handle any JSON parsing errors
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // Handle any errors that may have occurred
//                        }
//                    }
//            );
//
//            // Add the request to the RequestQueue to send it to the server
//            queue.add(request);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
}