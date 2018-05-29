package com.dangdoan.app.weather.page;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangdoan.app.weather.R;
import com.dangdoan.app.weather.models.WeatherDataViewModel;
import com.dangdoan.app.weather.models.WeatherHourly;

import java.util.ArrayList;
import java.util.List;

import static com.dangdoan.app.weather.utils.Constant.EXTRA_INDEX;

public class DailyFragment extends Fragment{
    private int mIndex;

    private List<WeatherHourly> weatherHourlyList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DailyFragmentAdapter mAdapter;



    public static final String TAG = MainPage.class.getSimpleName();

    public static DailyFragment newInstance(int index){
        Bundle args = new Bundle();
        args.putInt(EXTRA_INDEX, index);
        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            mIndex = args.getInt(EXTRA_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View root = inflater.inflate(R.layout.fragment_daily, container, false);
        mRecyclerView = root.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new DailyFragmentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData(){
        WeatherDataViewModel viewModel = ViewModelProviders.of(getActivity()).get(WeatherDataViewModel.class);
        viewModel.getWeatherForDay(getContext(), mIndex).observe(getActivity(), weatherList ->{
            updateUI(weatherList);
        });
    }

    private void updateUI(List<WeatherHourly> list){
        weatherHourlyList = list;
        mAdapter.setData(weatherHourlyList);
    }

    private class DailyFragmentAdapter extends RecyclerView.Adapter{
        private List<WeatherHourly> weatherHourlyList = new ArrayList<>();
        public DailyFragmentAdapter(){}

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.fragment_weather_detail, parent, false);
            return new ContentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ContentViewHolder contentViewHolder = (ContentViewHolder)holder;
            contentViewHolder.onBind(weatherHourlyList.get(position));
        }

        public class ContentViewHolder extends RecyclerView.ViewHolder{
            private View parent;
            private TextView time;

            public ContentViewHolder(View view){
                super(view);
                parent = view;
                time = parent.findViewById(R.id.time);
            }

            public void onBind(WeatherHourly weatherHourly){
                time.setText(weatherHourly.getDt_txt());
            }
        }

        public void setData(List<WeatherHourly> weatherHourlyList){
            this.weatherHourlyList = weatherHourlyList;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return weatherHourlyList.size();
        }
    }

}
