package adalwin.com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import adalwin.com.activities.R;
import adalwin.com.models.Article;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by aramar1 on 5/26/16.
 */
public class ArticleArrayAdapter  extends ArrayAdapter<Article> {



    public ArticleArrayAdapter(Context context, ArrayList<Article> articles){
        super(context, android.R.layout.simple_list_item_1,articles);
    }

    private static class ArticleViewHolder {
        ImageView ivThumbImage;
        TextView tvHeadline;
        TextView tvUrl;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Article article = (Article) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_article_result, parent, false);


        }
        TextView tvHeadline = (TextView) convertView.findViewById(R.id.tvHeadlines);
        ImageView ivThumbImage = (ImageView) convertView.findViewById(R.id.ivThumbs);
        tvHeadline.setText(article.getNewsHeadline());
        ivThumbImage.setImageResource(0);
        String thumbnail = article.getNewsThumbImage();

            Picasso.with(getContext()).load(article.getNewsThumbImage()).placeholder(getContext().getResources().getDrawable(R.mipmap.ic_launcher)).transform(new RoundedCornersTransformation(10, 10)).into(ivThumbImage);

        return convertView;

    }






}
