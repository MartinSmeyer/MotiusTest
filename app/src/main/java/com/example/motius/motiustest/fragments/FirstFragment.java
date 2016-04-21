package com.example.motius.motiustest.fragments;

/**
 * Created by Martin on 20.04.2016.
 */

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.Toast;

        import com.example.motius.motiustest.R;


public class FirstFragment extends Fragment implements View.OnClickListener{
    private Button b;
    View view;

    public FirstFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);

        b = (Button) view.findViewById(R.id.login);
        b.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity().getApplicationContext(),
                "Hello Motius", Toast.LENGTH_LONG).show();
    }

}