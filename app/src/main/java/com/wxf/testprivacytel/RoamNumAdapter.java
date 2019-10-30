package com.wxf.testprivacytel;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.roamtech.privacytel.bean.RoamNumBean;
import com.roamtech.privacytel.intfc.ResultDataListener;
import com.roamtech.privacytel.manager.RoamNumManager;
import com.roamtech.privacytel.utils.ToastUtils;

import java.util.List;

public class RoamNumAdapter extends RecyclerView.Adapter<RoamNumAdapter.ViewHolder> {
    private Context context;
    private List<RoamNumBean> list;

    public RoamNumAdapter(Context context){
        this.context = context;
    }

    public void setData(List<RoamNumBean> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public List<RoamNumBean> getData(){
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_roam_num,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvRoamNum.setText(list.get(position).getRoamNum());
        String phoneNum = list.get(position).getPhone();
        holder.tvPhoneNum.setText(TextUtils.isEmpty(phoneNum) ? "暂未绑定": phoneNum);
        holder.btnBind.setOnClickListener(v -> {
            showDialog(list.get(position).getRoamNum());
        });
    }

    private void showDialog(final String roamNum){
        final EditText et = new EditText(context);
        et.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        new AlertDialog.Builder(context).setTitle("请输入消息")
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setView(et)
                .setPositiveButton("确定", (dialogInterface, i) -> {
                    if(TextUtils.isEmpty(et.getText())){
                        // 解绑
                        RoamNumManager.getInstance().unbindNum(roamNum,new ResultDataListener<RoamNumBean>() {
                            @Override
                            public void onSuccess(RoamNumBean data) {
                                setData(data);
                                ToastUtils.show("解绑成功");
                            }

                            @Override
                            public void onFailed(int code, String msg) {
                                ToastUtils.show("解绑失败:" + msg);
                            }
                        });
                    } else {
                        // 绑定
                        RoamNumManager.getInstance().bindNum(roamNum, et.getText().toString(), new ResultDataListener<RoamNumBean>() {
                            @Override
                            public void onSuccess(RoamNumBean data) {
                                setData(data);
                                ToastUtils.show("绑定成功");
                            }

                            @Override
                            public void onFailed(int code, String msg) {
                                ToastUtils.show("绑定失败:" + msg);
                            }
                        });
                    }

                }).setNegativeButton("取消",null).show();
    }

    private void setData(RoamNumBean roamNumBean){
        if(null != roamNumBean){
            for(RoamNumBean bean : list){
                if(bean.getRoamNum().equals(roamNumBean.getRoamNum())){
                    bean.setPhone(roamNumBean.getPhone());
                    break;
                }
            }
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvRoamNum;
        private TextView tvPhoneNum;
        private Button btnBind;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRoamNum = itemView.findViewById(R.id.tv_roam_num);
            tvPhoneNum = itemView.findViewById(R.id.tv_phone_num);
            btnBind = itemView.findViewById(R.id.btn_bind);
        }
    }

}
