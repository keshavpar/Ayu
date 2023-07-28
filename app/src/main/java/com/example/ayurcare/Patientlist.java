package com.example.ayurcare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Patientlist#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Patientlist extends Fragment {
    private ListView listView;
    private ArrayList<Patient> patientList;
    private ArrayAdapter<Patient> adapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Patientlist() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Patientlist.
     */
    // TODO: Rename and change types and number of parameters
    public static Patientlist newInstance(String param1, String param2) {
        Patientlist fragment = new Patientlist();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_patientlist, container, false);

        listView = rootView.findViewById(R.id.listView);
        patientList = new ArrayList<>();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, patientList);
        listView.setAdapter(adapter);

        fetchPatientData();

        return rootView;
    }
    private void fetchPatientData() {
        String url = "http://43.205.208.194:4000/patientlists"; // Replace with your API endpoint

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
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
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
//                        boolean success = jsonObject.getBoolean("success");
                        JSONArray dataArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject item = dataArray.getJSONObject(i);
                            int age =item.getInt("age");
                            String id = item.getString("_id");
                            String name = item.getString("name");
                            String address = item.getString("address");
                            String bp = item.getString("bp");
                            String pulse = item.getString("pulse");
                            String prakruti = item.getString("prakruti");
                            String gender=item.getString("gender");
                            if(gender.length()==0){
                                gender="M";
                            }


                            Patient patient = new Patient(name, age,address,bp,pulse,prakruti,gender);
                            patientList.add(patient);
                        }


                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        showToast("Error parsing JSON: " + e.getMessage());
                    }
                } else {
                    showToast("Error: " + response.code());
                }
            }
        });
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
