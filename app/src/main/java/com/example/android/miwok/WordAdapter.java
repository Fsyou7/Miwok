package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Marc on 9/16/2016.
 */
public class WordAdapter extends ArrayAdapter<Word> {

    //The background color of the LinearLayout
    private int mColorResource;
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, the list is the data we want
     * to populate into the lists, and the color is the background color.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param words A List of Word objects to display in a list
     * @param color The color passed to the TextView as a Resource ID
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int color) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResource = color;

//        public int getColorResource(){
//        return mColorResource;
//        }
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_word
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_word);

        // Get the miwok_word from the current Word object and
        // set this text on the miwok_word TextView
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID default_translation
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.english_word);

        // Get the english_word from the current Word object and
        // set this text on the english_word TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        /*
        * Find the ImageView in the list_item.xml layout with the ID @id/image
        * */
        ImageView iconImageView = (ImageView) listItemView.findViewById(R.id.image);

        if(currentWord.hasImage()) {
        /*
        * Get the image resource ID from the current word object and
        * set the image on the image ImageView
        * */
            iconImageView.setImageResource(currentWord.getImageResourceId());
            /*Make sure the view is visible*/
            iconImageView.setVisibility(View.VISIBLE);
        } else {
            /*Otherwise hide the ImageView (set visibility to GONE)*/
            iconImageView.setVisibility(View.GONE);
        }
         /*
        * Find the ViewGroup in the list_item.xml layout with the ID @id/list_view
        * */
        ViewGroup listView = (ViewGroup) listItemView.findViewById(R.id.list_view);

//        miwokTextView.setBackgroundColor(ContextCompat.getColor(getContext(), WordAdapter.mColorResource));
        listView.setBackgroundColor(ContextCompat.getColor(getContext(), mColorResource));

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }


}
