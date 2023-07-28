package com.example.ayurcare;



import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPatient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPatient extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPatient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPatient.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPatient newInstance(String param1, String param2) {
        AddPatient fragment = new AddPatient();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private View rootView;
    String gender="M";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_add_patient,container,false);
        EditText name=rootView.findViewById(R.id.names);
        EditText age=rootView.findViewById(R.id.age);
        EditText address=rootView.findViewById(R.id.address);
        EditText ph=rootView.findViewById(R.id.Phone);
        EditText bp=rootView.findViewById(R.id.bp);
        EditText pulse=rootView.findViewById(R.id.pulse);
        EditText prakruti=rootView.findViewById(R.id.Prakruti);
        RadioButton maleRadioButton = rootView.findViewById(R.id.male);
        RadioButton femaleRadioButton = rootView.findViewById(R.id.female);
        RadioButton otherRadioButton = rootView.findViewById(R.id.other);
        Button btn=rootView.findViewById(R.id.button);



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maleRadioButton.isSelected()){
                    gender=maleRadioButton.getText().toString();
                } else if (femaleRadioButton.isSelected()) {
                    gender=femaleRadioButton.getText().toString();
                }
                else if(otherRadioButton.isSelected()){
                    gender=otherRadioButton.getText().toString();
                }
                String nameValue = name.getText().toString();
                String ageValue = age.getText().toString();
                String addressValue = address.getText().toString();
                String phValue = ph.getText().toString();
                String bpValue = bp.getText().toString();
                String pulseValue = pulse.getText().toString();
                String prakrutiValue = prakruti.getText().toString();


                 sendData(nameValue, ageValue, addressValue, phValue, bpValue, pulseValue, prakrutiValue,gender);
            }
        });



        // Inflate the layout for this fragment
        return rootView;

    }
    private void sendData(String name, String age, String address, String ph, String bp, String pulse, String prakruti,String gender) {
        String url = "http://43.205.208.194:4000/addpatient"; // Replace with your API endpoint

        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("age", age);
            jsonObject.put("Gender",gender);
            jsonObject.put("address", address);
            jsonObject.put("ph", ph);
            jsonObject.put("bp", bp);
            jsonObject.put("pulse", pulse);
            jsonObject.put("prakruti", prakruti);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        // Log the request body in a human-readable format
        Log.d("Request Body", jsonObject.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                showToast("Error: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Update UI with the response data
                            showToast(responseData);
                            clearEditTextFields();
                        }
                    });
                } else {
                    showToast("Error: " + response.code());
                }
            }
        });
    }
    private void clearEditTextFields() {
        EditText name = rootView.findViewById(R.id.names);
        EditText age = rootView.findViewById(R.id.age);
        EditText address = rootView.findViewById(R.id.address);
        EditText ph = rootView.findViewById(R.id.Phone);
        EditText bp = rootView.findViewById(R.id.bp);
        EditText pulse = rootView.findViewById(R.id.pulse);
        EditText prakruti = rootView.findViewById(R.id.Prakruti);

        name.setText("");
        age.setText("");
        address.setText("");
        ph.setText("");
        bp.setText("");
        pulse.setText("");
        prakruti.setText("");
    }
    private void showToast(final String message) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
