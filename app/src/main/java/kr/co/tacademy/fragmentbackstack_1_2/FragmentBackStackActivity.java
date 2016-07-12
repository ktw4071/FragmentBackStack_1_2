package kr.co.tacademy.fragmentbackstack_1_2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class FragmentBackStackActivity extends FragmentActivity {
    int mStackLevel = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newBtn = (Button) findViewById(R.id.newFragment);
        newBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addFragmentToStack();
            }
        });
        Button removeBtn = (Button) findViewById(R.id.deleteFragment);
        removeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getSupportFragmentManager().popBackStack();
            }
        });
        if (savedInstanceState == null) {
            Fragment newFragment = CountingFragment.
                    newInstance(mStackLevel);
            FragmentTransaction ft =
                    getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragmentInFrame, newFragment).commit();

        } else {
            mStackLevel = savedInstanceState.getInt("level");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("level", mStackLevel);
    }

    private void addFragmentToStack() {
        Fragment newFragment = CountingFragment.
                newInstance(++mStackLevel);
        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentInFrame, newFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();

    }

    public static class CountingFragment extends Fragment {
        int mNum;

        static CountingFragment newInstance(int num) {
            CountingFragment f = new CountingFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ?
                    getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_simple_text, container, false);
            TextView tv = (TextView) v.findViewById(R.id.messageText);
            tv.setText(tv.getText() + " CountingFragment " + mNum + " °");
            tv.setBackgroundResource(android.R.drawable.gallery_thumb);
            return v;
        }
    }
}
