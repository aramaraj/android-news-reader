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

import adalwin.com.models.Article;
import adalwin.com.activities.R;

/**
 * Created by aramar1 on 5/26/16.
 */
public class ArticleArrayAdapter extends  ArrayAdapter<Article> {

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
        ArticleViewHolder articleViewHolder;
        if (convertView == null) {
            //articleViewHolder = new ArticleViewHolder();
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.item_article_result, parent, false);
            /*articleViewHolder.tvHeadline = (TextView) convertView.findViewById(R.id.tvHeadlines);
            articleViewHolder.ivThumbImage = (ImageView) convertView.findViewById(R.id.ivThumbs);
            articleViewHolder.ivThumbImage.setImageResource(0);
            convertView.setTag(articleViewHolder);*/

             }
            /*else {
            articleViewHolder = (ArticleViewHolder) convertView.getTag();
            }*/
        //articleViewHolder.tvHeadline.setText(article.getNewsHeadline());

        TextView tvHeadline = (TextView) convertView.findViewById(R.id.tvHeadlines);
        ImageView ivThumbImage = (ImageView) convertView.findViewById(R.id.ivThumbs);

        tvHeadline.setText(article.getNewsHeadline());

        ivThumbImage.setImageResource(0);
        String thumbnail = article.getNewsThumbImage();

        if(!thumbnail.isEmpty()){
            Picasso.with(getContext()).load(thumbnail).into(ivThumbImage);
        }
        return convertView;
        }


    }
