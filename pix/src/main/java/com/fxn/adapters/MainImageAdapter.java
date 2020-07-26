package com.fxn.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.fxn.interfaces.OnSelectionListener;
import com.fxn.interfaces.SectionIndexer;
import com.fxn.modals.Img;
import com.fxn.pix.R;
import com.fxn.utility.HeaderItemDecoration;
import com.fxn.utility.Utility;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by akshay on 17/03/18.
 */

public class MainImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements HeaderItemDecoration.StickyHeaderInterface, SectionIndexer {

    public static final int HEADER = 1;
    public static final int ITEM = 2;
    public static final int SPAN_COUNT = 4;
    private static final int MARGIN = 4;

    private ArrayList<Img> list;
    private OnSelectionListener onSelectionListener;
    private FrameLayout.LayoutParams layoutParams;
    private RequestManager glide;
    private RequestOptions options;
    private boolean isImageOrVideo = false;
    private int imageCount = 1;
    private int videoCount = 1;
    private Context context;

    public MainImageAdapter(Context context) {
        this.list = new ArrayList<>();
        this.context = context;
        int size = (Utility.WIDTH / SPAN_COUNT) - (MARGIN / 2);
        layoutParams = new FrameLayout.LayoutParams(size, size);
        layoutParams.setMargins(MARGIN, MARGIN - (MARGIN / 2), MARGIN, MARGIN - (MARGIN / 2));
        options = new RequestOptions().override(300).transform(new CenterCrop()).transform(new FitCenter());
        glide = Glide.with(context);
    }

    public ArrayList<Img> getItemList() {
        return list;
    }

    public MainImageAdapter addImage(Img image) {
        list.add(image);
        notifyDataSetChanged();
        return this;
    }

    public void addOnSelectionListener(OnSelectionListener onSelectionListener) {
        this.onSelectionListener = onSelectionListener;
    }

    public void addImageList(ArrayList<Img> images) {
        list.addAll(images);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() <= position) {
            return 0;
        }
        Img i = list.get(position);
        return (i.getContentUrl().equalsIgnoreCase("")) ?
                HEADER : ITEM;
    }

    public void clearList() {
        list.clear();
    }

    public void select(boolean selection, int pos) {
        list.get(pos).setSelected(selection);
        notifyItemChanged(pos);
    }

    public void setSelectionData(boolean isImageOrVideo, int imageCount, int videoCount) {
        this.isImageOrVideo = isImageOrVideo;
        this.imageCount = imageCount;
        this.videoCount = videoCount;
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getContentUrl().hashCode();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER) {
            return new HeaderHolder(LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.header_row, parent, false));
        } else {
            View view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.main_image, parent, false);
            return new Holder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Img image = list.get(position);
        if (holder instanceof Holder) {
            Holder imageHolder = (Holder) holder;
            if (image.getMedia_type() == 1) {
                glide.load(image.getContentUrl()).apply(options).into(imageHolder.preview);
                imageHolder.isVideo.setVisibility(View.GONE);

                Log.e("MainAdapter", "onBindViewHolder: isAnyVideoSelected: " + isAnyVideoSelected());
                Log.e("MainAdapter", "onBindViewHolder: isAnyImageSelected: " + isAnyImageSelected());

                if (isAnyVideoSelected() && isImageOrVideo && !image.getSelected()) {
                    imageHolder.disabled.setVisibility(View.VISIBLE);
                } else if (getSelectedImageCount() >= imageCount) {
                    imageHolder.disabled.setVisibility(View.VISIBLE);
                } else {
                    imageHolder.disabled.setVisibility(View.GONE);
                }
            } else if (image.getMedia_type() == 3) {
                glide.asBitmap()
                        .load(Uri.fromFile(new File(image.getUrl())))
                        .apply(options)
                        .into(imageHolder.preview);
                imageHolder.isVideo.setVisibility(View.VISIBLE);

                if (isAnyImageSelected() && isImageOrVideo && !image.getSelected()) {
                    imageHolder.disabled.setVisibility(View.VISIBLE);
                } else if (getSelectedVideoCount() >= videoCount) {
                    imageHolder.disabled.setVisibility(View.VISIBLE);
                } else {
                    imageHolder.disabled.setVisibility(View.GONE);
                }
            }
            imageHolder.selection.setVisibility(image.getSelected() ? View.VISIBLE : View.GONE);
        } else if (holder instanceof HeaderHolder) {
            HeaderHolder headerHolder = (HeaderHolder) holder;
            headerHolder.header.setText(image.getHeaderDate());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getHeaderPositionForItem(int position) {
        int itemPosition = position;
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.header_row;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {
        Img image = list.get(headerPosition);
        ((TextView) header.findViewById(R.id.header)).setText(image.getHeaderDate());
    }

    @Override
    public boolean isHeader(int itemPosition) {
        return getItemViewType(itemPosition) == 1;
    }

    @Override
    public String getSectionText(int position) {
        return list.get(position).getHeaderDate();
    }

    public String getSectionMonthYearText(int position) {
        if (list.size() <= position) {
            return "";
        }
        return list.get(position).getHeaderDate();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ImageView preview;
        private ImageView selection;
        private ImageView isVideo;
        private ImageView disabled;

        Holder(View itemView) {
            super(itemView);
            preview = itemView.findViewById(R.id.preview);
            selection = itemView.findViewById(R.id.selection);
            isVideo = itemView.findViewById(R.id.isVideo);
            disabled = itemView.findViewById(R.id.disabled);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            preview.setLayoutParams(layoutParams);
            disabled.setLayoutParams(layoutParams);
            selection.setLayoutParams(layoutParams);
            float scale = context.getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (26 * scale + 0.5f);
            selection.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        }

        @Override
        public void onClick(View view) {
            int id = this.getLayoutPosition();

            Log.e("MainAdapter", "onClick: " + list.get(id).getSelected());

            if (list.get(id).getSelected()) {
                onSelectionListener.onClick(list.get(id), view, id);
                Log.e("MainAdapter", "onClick: unselect");
            } else if (list.get(id).getMedia_type() == 1) {
                if (getSelectedImageCount() < imageCount && !isAnyVideoSelected()) {
                    onSelectionListener.onClick(list.get(id), view, id);
                    Log.e("MainAdapter", "onClick: image");
                }
            } else if (list.get(id).getMedia_type() == 3) {
                if (getSelectedVideoCount() < videoCount && !isAnyImageSelected()) {
                    onSelectionListener.onClick(list.get(id), view, id);
                    Log.e("MainAdapter", "onClick: video");
                }
            }

        }

        @Override
        public boolean onLongClick(View view) {
            int id = this.getLayoutPosition();
            onSelectionListener.onLongClick(list.get(id), view, id);
            return true;
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        private TextView header;

        HeaderHolder(View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header);
        }
    }

    public boolean isAnyImageSelected() {
        if (list.isEmpty())
            return false;
        for (Img img : list) {
            if (img.getMedia_type() == 1 && img.getSelected())
                return true;
        }
        return false;
    }

    public boolean isAnyVideoSelected() {
        if (list.isEmpty())
            return false;
        for (Img img : list) {
            if (img.getMedia_type() == 3 && img.getSelected())
                return true;
        }
        return false;
    }

    public int getSelectedImageCount() {
        if (list.isEmpty())
            return 0;

        int count = 0;
        for (Img img : list) {
            if (img.getMedia_type() == 1 && img.getSelected())
                count++;
        }
        return count;
    }

    public int getSelectedVideoCount() {
        if (list.isEmpty())
            return 0;

        int count = 0;
        for (Img img : list) {
            if (img.getMedia_type() == 3 && img.getSelected())
                count++;
        }
        return count;
    }
}
