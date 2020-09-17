package com.example.raspicam.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.raspicam.R;
import com.example.raspicam.data.DeviceSettingData;
import com.example.raspicam.data.ScanDeviceData;
import com.example.raspicam.viewmodel.DeviceSettingViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScanDeviceListActivity extends AppCompatActivity {

    DeviceSettingViewModel viewmodel = null;

    // ListViewにArrayAdapterを設定する
    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_device_list);

        listView = (ListView)findViewById(R.id.scandevicelistview);

        //LiveData設定
        viewmodel = new ViewModelProvider(this).get(DeviceSettingViewModel.class);

        viewmodel.getScanDeviceList();

        //リスト項目が選択された時のイベントを追加
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = position + "番目のアイテムがクリックされました";
                Log.d("msg: ", msg);
                viewmodel.clickDevice(position);
                finish();
            }
        });

        // Create the observer which updates the UI.
        final Observer<ArrayList<ScanDeviceData>> scanlistObserver = new Observer<ArrayList<ScanDeviceData>>() {

            @Override
            public void onChanged(ArrayList<ScanDeviceData> scanlist) {
                // Update the UI, in this case, a TextView.
                dsipDeviceList(scanlist);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewmodel.getScanDeviceList().observe(this, scanlistObserver);


    }

    /**
     * スキャンされたデバイスをリストに表示する
     * @param scanlist
     */
    private void dsipDeviceList(@NotNull ArrayList<ScanDeviceData> scanlist) {

        // ListViewに表示するリスト項目をArrayListで準備する
        List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for (int i=0; i<scanlist.size(); i++){
            Map<String, String> item = new HashMap<String, String>();
            item.put("DeviceName", scanlist.get(i).mdeviceName);
            item.put("MacAddress", scanlist.get(i).macAddress);
            data.add(item);
        }

        // リスト項目とListViewを対応付けるArrayAdapterを用意する
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] { "DeviceName", "MacAddress" },
                new int[] { android.R.id.text1, android.R.id.text2});

        // ListViewにArrayAdapterを設定する
        ListView listView = (ListView)findViewById(R.id.scandevicelistview);
        listView.setAdapter(adapter);
    }
}