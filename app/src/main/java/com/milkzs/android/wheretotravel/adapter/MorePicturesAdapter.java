package com.milkzs.android.wheretotravel.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.db.PlaceContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


/**
 * Created by milkdz on 2018/4/29.
 */

public class MorePicturesAdapter extends RecyclerView.Adapter<MorePicturesAdapter.PicturesHolder> {

    private Cursor mCursor;
    private Context context;

    public MorePicturesAdapter() {
    }

    @Override
    public PicturesHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        int recyclerIndex = R.layout.recycler_grid_pictures;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(recyclerIndex,parent,false);
        return new PicturesHolder(v);
    }

    @Override
    public void onBindViewHolder(PicturesHolder holder, int position) {
        holder.bindUI(position);
    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    class PicturesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;

        PicturesHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detail_more_pic);
            imageView.setOnClickListener(this);
        }

        /**
         * bind data to UI
         *
         * @param position cursor position
         */
        void bindUI(int position){
            mCursor.moveToPosition(position);
            final String sUri = mCursor.getString(
                    mCursor.getColumnIndex(PlaceContract.SceneImgBase.COLUMN_SCENE_IMG_URI));
            if(sUri == null){
                return;
            }
            Picasso.with(context)
                    .load(sUri)
                    .fetch(new Callback() {
                        @Override
                        public void onSuccess() {
                            Picasso.with(context)
                                    .load(sUri)
                                    .into(imageView);
                        }

                        @Override
                        public void onError() {
                        }
                    });
        }

        @Override
        public void onClick(View v) {
            // Toast.makeText(context.getApplicationContext(),getAdapterPosition()+"",Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(context.getApplicationContext(), ZooImageActivity.class);
            //intent.putExtra(BaseInfo.IntentFlag.FLAG_PICTURE_POSITION,getAdapterPosition());
            //intent.putExtra(BaseInfo.IntentFlag.FLAG_PICTURE_LIST,picUriArray);
           // context.startActivity(intent);
        }
    }

    /**
     * Swap data and notify
     *
     * @param mCursor swap cursor for notification
     */
    public void swapData(Cursor mCursor){
        this.mCursor = mCursor;
        notifyDataSetChanged();
    }
}
