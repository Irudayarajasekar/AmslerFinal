package Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.darkknight.amslerfinal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iruda on 10-02-2018.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolderboxOffice>{
    private List<ListDetails> chartList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ListAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
    }

    public void setChartList(ArrayList<ListDetails> chartList){
        this.chartList = chartList;
        notifyItemRangeChanged(0,chartList.size()-1);
    }

    @Override
    public int getItemCount() {
        return chartList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolderboxOffice holder, int position) {
        holder.name.setText(chartList.get(position).getName());
        holder.imageView.setImageResource(chartList.get(position).getImage());
    }

    @Override
    public ViewHolderboxOffice onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.list_row, viewGroup, false);
        ViewHolderboxOffice viewHolderboxOffice = new ViewHolderboxOffice(view);

        return viewHolderboxOffice;
    }

    public static class ViewHolderboxOffice extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView imageView;

        public ViewHolderboxOffice(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.chartname);
            imageView = (ImageView)itemView.findViewById(R.id.imageview);
        }
    }

}
