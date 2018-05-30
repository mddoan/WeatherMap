package com.dangdoan.app.weather.page;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dangdoan.app.weather.R;
import com.dangdoan.app.weather.models.ShareLocationViewModel;
import com.dangdoan.app.weather.models.WeatherDataViewModel;
import com.dangdoan.app.weather.models.WeatherHourly;
import com.dangdoan.app.weather.utils.Helpers;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static com.dangdoan.app.weather.utils.Constant.EXTRA_INDEX;

public class DailyFragment extends Fragment{
    public static final String TAG = DailyFragment.class.getSimpleName();
    private int mIndex;

    private List<WeatherHourly> weatherHourlyList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private DailyFragmentAdapter mAdapter;

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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
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
        ShareLocationViewModel shareLocationViewModel = ViewModelProviders.of(this).get(ShareLocationViewModel.class);
        shareLocationViewModel.getLocation(getActivity()).observe(getActivity(), location -> {
            WeatherDataViewModel viewModel = ViewModelProviders.of(getActivity()).get(WeatherDataViewModel.class);
            viewModel.getWeatherObject(getContext(), location).observe(getActivity(), weatherObject ->{
                if(weatherObject != null){
                    List<WeatherHourly> weatherHourlyList = viewModel.getWeatherForDay(mIndex);
                    updateUI(weatherHourlyList);
                }
            });
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
            contentViewHolder.onBind(weatherHourlyList.get(position), position);
        }

        public class ContentViewHolder extends RecyclerView.ViewHolder{
            private View parent;
            private TextView time;
            private TextView currentLabel;
            private TextView temp;
            private TextView tempMax;
            private TextView tempMin;
            private TextView humidity;
            private TextView clouds;
            private TextView wind;
            private TextView rain;
            private TextView snow;

            public ContentViewHolder(View view){
                super(view);
                parent = view;
                currentLabel = parent.findViewById(R.id.currentLabel);
                time = parent.findViewById(R.id.time);
                temp = parent.findViewById(R.id.temp);
                tempMax = parent.findViewById(R.id.tempMax);
                tempMin = parent.findViewById(R.id.tempMin);
                humidity = parent.findViewById(R.id.humidity);
                clouds = parent.findViewById(R.id.clouds);
                wind= parent.findViewById(R.id.wind);
                rain = parent.findViewById(R.id.rain);
                snow = parent.findViewById(R.id.snow);
            }

            public void onBind(WeatherHourly weatherHourly, int pos){
                if(pos == 0){
                    currentLabel.setVisibility(View.VISIBLE);
                }else{
                    currentLabel.setVisibility(View.GONE);
                }

                String txtTime = getTimeText(weatherHourly.getDt_txt());
                setText(time, txtTime);

                String txtTemp = getString(R.string.temp, String.valueOf(Helpers.getFarenheitTemp(weatherHourly
                        .getMain().getTemp())));
                setText(temp, txtTemp);

                String txtTempMax = getString(R.string.temp_max, String.valueOf(Helpers.getFarenheitTemp
                        (weatherHourly.getMain().getTemp_max())));
                setText(tempMax, txtTempMax);

                String txtTempMin = getString(R.string.temp_min, String.valueOf(Helpers.getFarenheitTemp
                        (weatherHourly.getMain().getTemp_min())));
                setText(tempMin, txtTempMin);

                String txtHumidity = getString(R.string.humidity, String.valueOf(weatherHourly.getMain().getHumidity()));
                setText(humidity, txtHumidity);

                String txtCloud = getCloudinessDesc(weatherHourly.getWeatherCloudConditions().getAll());
                setText(clouds, txtCloud);

                String txtWind = getString(R.string.wind, String.valueOf(weatherHourly.getWeatherWindConditions()
                        .getSpeed()));
                setText(wind, txtWind);
            }
        }

        public void setData(List<WeatherHourly> weatherHourlyList){
            this.weatherHourlyList.clear();
            this.weatherHourlyList.addAll(weatherHourlyList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return weatherHourlyList.size();
        }
    }

    private String getTimeText(String date){
        StringTokenizer stringTokenizer = new StringTokenizer(date);
        for(int i=0; i<2; i++){
            String time = stringTokenizer.nextToken();
            if(i == 1){
                return time;
            }
        }
        return "";
    }

    private void setText(TextView view, String text){
        if(TextUtils.isEmpty(text)){
            view.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.VISIBLE);
            view.setText(text);
        }
    }

    private String getCloudinessDesc(int percentage){
        if(percentage == 0){
            return "Clear";
        }else if(percentage < 20){
            return "Mostly Clear";
        }else if(percentage < 60){
            return "Partly Cloudy";
        }else{
            return "Cloudy";
        }
    }

}
