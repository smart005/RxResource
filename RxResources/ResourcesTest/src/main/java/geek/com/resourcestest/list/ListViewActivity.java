package geek.com.resourcestest.list;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cloud.resources.refresh.OnXListViewListener;
import com.cloud.resources.refresh.XRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import geek.com.resourcestest.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/26
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ListViewActivity extends Activity {

    @BindView(R.id.list_xrlv)
    XRefreshListView listXrlv;
    private List<String> datalist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        ButterKnife.bind(this);
        peraperData();
        listXrlv.setAdapter(new ListAdapter());
        listXrlv.setXListViewListener(new OnXListViewListener() {
            @Override
            public void onRefresh() {
                listXrlv.initRL();
            }

            @Override
            public void onLoadMore() {
                listXrlv.initRL();
            }
        });
    }

    private void peraperData() {
        datalist.add("英语");
        datalist.add("数据");
        datalist.add("政治");
        datalist.add("设计");
        datalist.add("前台");
        datalist.add("服务");
    }

    private class ListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return datalist.size();
        }

        @Override
        public Object getItem(int position) {
            return datalist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = null;
            if (convertView == null) {
                convertView = View.inflate(ListViewActivity.this, R.layout.list_item_view, null);
                textView = (TextView) convertView.findViewById(R.id.name_tv);
                convertView.setTag(textView);
            } else {
                textView = (TextView) convertView.getTag();
            }
            textView.setText(datalist.get(position));
            return convertView;
        }
    }
}
